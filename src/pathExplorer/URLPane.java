package pathExplorer;

import java.io.InputStream;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;

public class URLPane {
	private TextField  m_statuDisplayTextField;
	private WebEngine m_webEngine;
	public SplitPane m_URLPane;
	
	URLPane(WebEngine webEngine, TextField statuDisplayTextField){
		m_statuDisplayTextField = statuDisplayTextField;
		m_webEngine = webEngine;	
		m_URLPane = CreateURLPane();
	}
	
	
	private SplitPane CreateURLPane(){			
	
		SplitPane splitPaneControlls = new SplitPane();
    
		TextField urlField = new TextField ();
		   urlField.setText("https://www.experts-exchange.com/");
		   
		   
		   Button loadURLButton = new Button("Open URL");
		   loadURLButton.setMaxWidth(100);
		   loadURLButton.setMinWidth(100);
		   
		   loadURLButton.setOnAction(e -> loadURL(urlField.getText() ) );	   
		   
		   splitPaneControlls.getItems().addAll( loadURLButton,urlField);		    
		   
		   return splitPaneControlls;
	}
	
	
	private void loadURL(String URL ){
		m_webEngine.getLoadWorker().stateProperty().addListener(
               new ChangeListener<State>() {
                   public void changed(ObservableValue ov, State oldState, State newState) {
                       if (newState == State.SUCCEEDED){
                     
	                       	InputStream inputStream = PathExplorer.class.getResourceAsStream("javaScript.js");
	                       	
	                       	Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
	                       	String javaScript = scanner.hasNext() ? scanner.next() : "";
	                       	
	                       	m_webEngine.executeScript(javaScript);  
	                       	
	                       m_statuDisplayTextField.setText("URL loaded");   
                       }                      
                   }
               });
		
		m_statuDisplayTextField.setText("Loading URL : " + URL );
		m_webEngine.load(URL);
	}
}
