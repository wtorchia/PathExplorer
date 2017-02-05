package pathExplorer;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import com.sun.javafx.fxml.builder.URLBuilder;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

public class PathExplorer_Controller 
{

	private PathExplorer_View m_view;
	private PathExplorer_Model m_model;
	private boolean m_forceGoBack = false;
	private WebEngine m_webEngine;
	
	
	PathExplorer_Controller(PathExplorer_View pathExplorer_View, PathExplorer_Model pathExplorer_model) 
	{
		m_view = pathExplorer_View;
		m_model = pathExplorer_model;
	}

	
	public void initialize() throws Exception 
	{
		m_webEngine = m_view.m_browser.getEngine();
		
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
		
		m_webEngine.setUserAgent(m_model.USERAGENT);
		
		m_view.m_urlField.setText(m_model.DEFAULT_URL);
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
				if(m_webEngine.getHistory().getEntries().size() > 1)
				{
					showLoadingDialog();
					m_webEngine.executeScript("history.back()");
				}
				
			}
		});		
	}
	
	
	private void createURLButtonAction() throws Exception 
	{
		m_view.m_loadURLButton.setOnAction(e -> {
			try
			{
				m_forceGoBack = false;
				
				loadURL(m_view.m_urlField.getText());
			}
			catch (Exception exception)
			{
				displayPath("There was an error. Please see the logs for more detail.");
				addLogLine(exception.toString());
			}
		});
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
					try 
					{
						m_forceGoBack = false;
				
						loadURL(m_view.m_urlField.getText());
					} 
					catch (Exception exception)
					{
						displayPath("There was an error. Please see the logs for more detail.");
						addLogLine(exception.toString());
					}
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
					m_webEngine.executeScript("var enableIntercept = true");
					
				}
				else
				{
					m_model.m_stayOnPage = false;
					m_webEngine.executeScript("var enableIntercept = false");
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
		JSObject window = (JSObject) m_webEngine.executeScript("window");
		window.setMember("app", new ApplicationCallback(this));
	}

	
	private void createWebEnigneListener()
	{
		m_webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>()
		{

			public void changed(ObservableValue ov, State oldState, State newState)
			{
				if (newState == State.SUCCEEDED) 
				{
					if(!m_forceGoBack && m_model.m_stayOnPage)
					{
						m_forceGoBack = true;						
					}
					else if(m_forceGoBack && m_model.m_stayOnPage)
					{
						showLoadingDialog();
						m_forceGoBack = false;	
						m_webEngine.executeScript("history.back()");
					}

					InputStream inputStream = PathExplorer.class.getResourceAsStream("javaScript.js");

					Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
					String javaScript = scanner.hasNext() ? scanner.next() : "";

					addLogLine(m_model.LOADING_MESSAGE);

					displayPath(m_model.LOADING_MESSAGE);

					m_webEngine.executeScript(javaScript);

					createApplicationCallBack();

					if (m_model.m_stayOnPage) 
					{
						m_webEngine.executeScript("var enableIntercept = true");						
					}
					else
					{
						m_webEngine.executeScript("var enableIntercept = false");						
					}
					
					setHighlightColor();
					
					addLogLine(m_model.PAGE_READY_MESSAGE);

					displayPath(m_model.PAGE_READY_MESSAGE);
					
					hideLoadingDialog();
				}
				else if (newState == State.FAILED) 
				{
					addLogLine("Failed to load URL");
					addLogLine(m_webEngine.getLoadWorker().getException().getMessage());

					displayPath("There was an error. Please see the logs for more detail.");

				}
				else if (newState == State.SCHEDULED)
				{
					showLoadingDialog();
				
					
					addLogLine(m_model.LOADING_MESSAGE);
					displayPath(m_model.LOADING_MESSAGE);
					
					addLogLine("Opening URL : " + m_webEngine.locationProperty().getValue());
	
					m_view.m_urlField.setText(m_webEngine.locationProperty().getValue());					
				}
			}
		});
	}

	
	private void setHighlightColor()
	{
		
		String highlightColor = m_view.m_colorComboBox.getSelectionModel().getSelectedItem().toString();
	
		m_webEngine.executeScript("var highlightColor = '" + highlightColor + "'");
	}
	
	
	private String formatURL(String requestedURL) throws Exception
	{
		URL url;
		
		String[] parseRequest = requestedURL.split("://");
		
		if(parseRequest.length == 2)
		{
			url = new URL(requestedURL);
		}
		else
		{
			url = new URL("http://" + requestedURL);
		}
		
		
		return url.toString();
	}
	
	
	private void showLoadingDialog()
	{
		if(!m_view.m_browserPane.getChildren().contains(m_view.m_progressIndicatorVBox))
		{
			m_view.m_browserPane.getChildren().add(m_view.m_progressIndicatorVBox);
		}
	}
	
	private void hideLoadingDialog()
	{
		m_view.m_browserPane.getChildren().remove(m_view.m_progressIndicatorVBox);
	}
	
	
	public void loadURL(String URL) throws Exception 
	{
		try 
		{
			showLoadingDialog();
			 
			m_webEngine.load(formatURL(URL));
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
