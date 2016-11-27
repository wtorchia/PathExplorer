package pathExplorer;

import java.io.InputStream;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import netscape.javascript.JSObject;



public class PathExplorer_Controller {

	public PathExplorer_View m_pathExplorer_View;

	PathExplorer_Controller(PathExplorer_View pathExplorer_View) {
		m_pathExplorer_View = pathExplorer_View;

		createURLFieldActions();
		createURLButtonActions();		
		createWebEnigneListener();		
		createToggleListener();
	}

	private void createURLButtonActions(){
		m_pathExplorer_View.m_loadURLButton.setOnAction(e -> loadURL(m_pathExplorer_View.m_urlField.getText()));
	}
	
	private void createURLFieldActions(){
		m_pathExplorer_View.m_urlField.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
			@Override
			public void handle(KeyEvent keyEvent){
	            if (keyEvent.getCode().equals(KeyCode.ENTER)){
	            	loadURL(m_pathExplorer_View.m_urlField.getText());
	            }
	        }

	    });
	}
		
	private void createApplicationCallBack() {
		JSObject window = (JSObject) m_pathExplorer_View.m_webEngine.executeScript("window");
		window.setMember("app", new ApplicationCallback(this));
	}

	private void createToggleListener() {
		m_pathExplorer_View.m_radioButtonEnable.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
				if (isNowSelected) {
					m_pathExplorer_View.m_webEngine.executeScript("var enableIntercept = false");					
				}
				else {
					m_pathExplorer_View.m_webEngine.executeScript("var enableIntercept = true");				
				}
			}			
		});
	}

	private void createWebEnigneListener() {		
		m_pathExplorer_View.m_webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			
			public void changed(ObservableValue ov, State oldState, State newState) {
				if (newState == State.SUCCEEDED) {

					InputStream inputStream = PathExplorer.class.getResourceAsStream("javaScript.js");

					Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
					String javaScript = scanner.hasNext() ? scanner.next() : "";

					addLogLine("Loading JS");

					m_pathExplorer_View.m_webEngine.executeScript(javaScript);
					
					createApplicationCallBack();

					if (m_pathExplorer_View.m_radioButtonEnable.isSelected()) {						
						m_pathExplorer_View.m_webEngine.executeScript("var enableIntercept = false");
					}

					addLogLine("URL loaded");
				}

				if (newState == State.FAILED) {
					addLogLine("Failed to load URL");
					addLogLine(m_pathExplorer_View.m_webEngine.getLoadWorker().getException().getMessage());
					
				}
				if (newState == State.SCHEDULED) {
					addLogLine("Loading URL : " + m_pathExplorer_View.m_webEngine.locationProperty().getValue());

					m_pathExplorer_View.m_urlField.setText(m_pathExplorer_View.m_webEngine.locationProperty().getValue());
				}
			}
		});
	}

	public void sendMsg(String msg) {

		String[] parseMSGArray = msg.split(",");
		String path = "";
		for (String item : parseMSGArray) {

			if (item.contains("#")) {
				path = "#" + item.split("#")[1];
			} 
			else {
				path += " > ";
				path += item;
			}
		}
		displayPath(path);
	}

	public void loadURL(String URL) {

		try{
			m_pathExplorer_View.m_webEngine.load(URL);
		}
		catch(Error error){
			addLogLine(error.toString());
		}
	}

	public void displayPath(String path) {
		m_pathExplorer_View.m_pathTextField.setText(path);
	}

	public void addLogLine(String logLine) {
		m_pathExplorer_View.m_statuDisplayTextArea.setText(m_pathExplorer_View.m_statuDisplayTextArea.getText() + "\n" + logLine);
	}

}
