import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.scene.web.WebEngine;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.concurrent.Worker.State;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
 



public class Main extends Application
{
	TextField  m_displayTextField  = new TextField();
	
	 public class JavaApplication{

	        public void setElementPath(String msg){
	        	
	        	String[] parseMSGArray = msg.split(",");
	        	String path = "";
	        	for(String item : parseMSGArray){
	        		
	        		if(item.contains("#")){
        				path =  "#" + item.split("#")[1];	        				
        			}
	        		else{
	        			path += " > ";
        				path += item;	
	        		}
	        	}
	        	m_displayTextField.setText(path);
	        }
	 }
	 
	public String getDomPathJS(){
		String js = "function getDomPath(element) {"
					  + "var stack = [];"
					  + "while ( element.parentNode != null ) {"						
						  + "var sibCount = 0;"
						  + "var sibIndex = 0;"
						  + "for ( var i = 0; i < element.parentNode.childNodes.length; i++ ) {"
						      + "var sib = element.parentNode.childNodes[i];"
						      + "if ( sib.nodeName == element.nodeName ) {"
							      + "if ( sib === element ) {"
							      	+ "sibIndex = sibCount;"
							      + "}"
							      + "sibCount++;"
							  + "}"
					      + "}"
					      + "if ( element.hasAttribute('id') && element.id != '' ) {"
					      	+ "stack.unshift(element.nodeName.toLowerCase() + '#' + element.id);"
					      + "} "					      
					      + "else if ( sibCount > 1 && element.className != '') {"
					      	+ "stack.unshift(element.nodeName.toLowerCase() + '.' + element.className);"
					      + "} "
					      + "else if ( sibCount > 1 ) {"
					      	+ "stack.unshift(element.nodeName.toLowerCase() + ':nth-child(' + (sibIndex +1) + ')');"					      	
					      + "} "
					      + "else {"
					      	+ "stack.unshift(element.nodeName.toLowerCase());"
					      + "}"
					      + "element = element.parentNode;"
				      + "}"
				      + "return stack.slice(1); "
			      + "}";
		return js;
	}
	 

	public String outLineJS(){
		String js = "var previousEvent = null;"
				  + "var previousElemetOutline = null;"
				  + "function intercept(event) {"
					  + "if(previousEvent != null) {"
					  	+ "previousEvent.target.style.outline = previousElemetOutline;"
					  + "}"
					  + "previousEvent = event;"
					  + "previousElemetOutLine = event.target.style.outline;"	
		  			  + "event.target.style.outline = \"solid blue 5px\";"
		  			  + "var evt = event ? event:window.event;"
		  			  + "var path = getDomPath(event.target);"
		  			  + "app.setElementPath(path);"		  		
		              + "event.preventDefault();"    	
	  			  + "}"
	  			  + "document.addEventListener('click', intercept);"
	  			  + "document.addEventListener('onclick', intercept);";    
		
		return js;
	}
	
	
	public void loadURL(WebEngine webEngine, String URL ){
		
		webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                    public void changed(ObservableValue ov, State oldState, State newState) {
                        if (newState == State.SUCCEEDED){
                        	webEngine.executeScript(getDomPathJS());
                        	webEngine.executeScript(outLineJS());
                        	m_displayTextField.setText("URL loaded");               	
                        	
                        }                      
                    }
                });
		
		m_displayTextField.setText("Loading URL : " + URL );
		webEngine.load(URL);
	}
	
	
    @Override
    public void start(Stage stage){
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	
    	WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();       
        
       
        SplitPane splitPaneBrowser = new SplitPane();
        splitPaneBrowser.getItems().addAll(browser);
        
        SplitPane splitPaneControlls = new SplitPane();
                
        TextField urlField = new TextField ();
        urlField.setText("https://www.experts-exchange.com/");
        
        
        Button loadURLButton = new Button("Open URL");
        loadURLButton.setMaxWidth(100);
        loadURLButton.setMinWidth(100);
        
        loadURLButton.setOnAction(e -> loadURL(webEngine,urlField.getText() ) );
        
        
        splitPaneControlls.getItems().addAll( loadURLButton,urlField);
        
        JSObject window = (JSObject) webEngine.executeScript("window");
        window.setMember("app", new JavaApplication());
        
        
        
        SplitPane splitPaneDisplay = new SplitPane();
                
        
        
        splitPaneDisplay.getItems().addAll( m_displayTextField);
        
        VBox vBox = new VBox();   
        
        vBox.getChildren().addAll(splitPaneControlls, splitPaneBrowser, splitPaneDisplay);
                
        
        
        Scene scene = new Scene(vBox);
       
        stage.setTitle("Demo");    	 
        stage.setWidth(screenSize.getWidth());
        stage.setHeight(screenSize.getHeight()); 
        stage.setScene(scene);
        stage.show();        
        
    }
 
    public static void main(String[] args){
        launch(args);
    }
}
