package pathExplorer;

import java.io.InputStream;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import netscape.javascript.JSObject;

public class PathExplorer_Controller 
{

	private PathExplorer_View m_view;
	private PathExplorer_Model m_model;

	
	PathExplorer_Controller(PathExplorer_View pathExplorer_View, PathExplorer_Model pathExplorer_model) 
	{
		m_view = pathExplorer_View;
		m_model = pathExplorer_model;
	}

	
	public void initialize() 
	{
		//Create event listeners
		createURLFieldButtonAction();
		createURLButtonAction();
		createWebEnigneListener();		
		createSettingButtonAction();			
		createStayOnPageCheckBoxListener();
		createLogCheckBoxListener();
		createColorComboBoxListener();
		createBackButtonAction();

		//Set initial state of view.
		m_view.m_interceptCheckBox.setSelected(m_model.m_stayOnPage);
		
		m_view.m_showLogsCheckBox.setSelected(m_model.m_showLogs);		
		
		m_view.m_colorComboBox.setItems(FXCollections.observableArrayList( m_model.m_highlightColorsList.values()));
		m_view.m_colorComboBox.getSelectionModel().selectFirst();		
		
		m_view.m_urlField.setText(m_model.m_defaultURL);
		loadURL(m_view.m_urlField.getText());
	}
	
	
	private void createColorComboBoxListener(){
		m_view.m_colorComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
		{

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) 
			{
				setHighlightColor();
				
			}
		});			
	}
	
	private void createBackButtonAction()
	{
		m_view.m_backButton.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event) 
			{
				m_view.m_webEngine.executeScript("history.back()");
			}
		});
		
		
		
	}
	
	
	private void createURLButtonAction() 
	{
		m_view.m_loadURLButton.setOnAction(e -> loadURL(m_view.m_urlField.getText()));
	}

	
	private void createURLFieldButtonAction() 
	{
		m_view.m_urlField.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent keyEvent) 
			{
				if (keyEvent.getCode().equals(KeyCode.ENTER))
				{
					loadURL(m_view.m_urlField.getText());
				}
			}

		});
	}
	
	
	private void createStayOnPageCheckBoxListener() 
	{
		m_view.m_interceptCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() 
		{

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
			{
				
				if (m_view.m_interceptCheckBox.selectedProperty().get())
				{

					m_model.m_stayOnPage = true;
					m_view.m_webEngine.executeScript("var enableIntercept = true");
					
				}
				else
				{
					m_model.m_stayOnPage = false;
					m_view.m_webEngine.executeScript("var enableIntercept = false");
				}				
			}

		});
	}
	
	
	private void createLogCheckBoxListener() 
	{
		m_view.m_showLogsCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() 
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) 
			{
				

				if (m_view.m_showLogsCheckBox.selectedProperty().get()) 
				{

					m_model.m_showLogs = true;					
				} 
				else
				{
					m_model.m_showLogs = false;					
				}
						
				m_view.m_logTextArea.setVisible(m_model.m_showLogs);
				m_view.m_logTextArea.setManaged(m_model.m_showLogs);				
			}
		});
	}

	
	private void createSettingButtonAction() 
	{
		m_view.m_settingButton.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event) 
			{
				if (m_view.m_settingPane.isVisible() == true) 
				{

					m_view.m_settingPane.setVisible(false);
					m_view.m_settingPane.setManaged(false);
					
				} 
				else 
				{
					
					m_view.m_settingPane.setVisible(true);
					m_view.m_settingPane.setManaged(true);
				}
			}
		});
	}

	
	private void createApplicationCallBack() 
	{
		JSObject window = (JSObject) m_view.m_webEngine.executeScript("window");
		window.setMember("app", new ApplicationCallback(this));
	}

	
	private void createWebEnigneListener()
	{
		m_view.m_webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>()
		{

			public void changed(ObservableValue ov, State oldState, State newState)
			{
				if (newState == State.SUCCEEDED) 
				{

					InputStream inputStream = PathExplorer.class.getResourceAsStream("javaScript.js");

					Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
					String javaScript = scanner.hasNext() ? scanner.next() : "";

					addLogLine(m_model.m_loadingMessage);

					displayPath(m_model.m_loadingMessage);

					m_view.m_webEngine.executeScript(javaScript);

					createApplicationCallBack();

					if (m_model.m_stayOnPage) 
					{
						m_view.m_webEngine.executeScript("var enableIntercept = true");						
					}
					else
					{
						m_view.m_webEngine.executeScript("var enableIntercept = false");						
					}
					
					setHighlightColor();
					
					addLogLine(m_model.m_pageReadyMessage);

					displayPath(m_model.m_pageReadyMessage);
				}
				else if (newState == State.FAILED) 
				{
					addLogLine("Failed to load URL");
					addLogLine(m_view.m_webEngine.getLoadWorker().getException().getMessage());

					displayPath("There was an error. Please see the logs for more detail.");

				}
				else if (newState == State.SCHEDULED)
				{
					addLogLine(m_model.m_loadingMessage);
					displayPath(m_model.m_loadingMessage);
					addLogLine("Opening URL : " + m_view.m_webEngine.locationProperty().getValue());

					m_view.m_urlField.setText(m_view.m_webEngine.locationProperty().getValue());
				}
			}
		});
	}

	
	private void setHighlightColor()
	{
		
		String highlightColor = m_view.m_colorComboBox.getSelectionModel().getSelectedItem().toString();
	
		m_view.m_webEngine.executeScript("var highlightColor = '" + highlightColor + "'");
	}
	
	
	public void loadURL(String URL) 
	{
		try 
		{
			m_view.m_webEngine.load(URL);
		} 
		catch (Error error) {
			addLogLine(error.toString());
		}
	}

	
	public void displayPath(String path) 
	{
		m_view.m_pathTextField.setText(path);
	}
	

	public void addLogLine(String logLine) 
	{
		m_view.m_logTextArea.appendText(logLine + "\n");
	}
}
