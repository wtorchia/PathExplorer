import java.net.*;

import javafx.scene.web.WebEngine;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.concurrent.Worker.State;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
 
public class Main extends Application
{

	
    @Override
    public void start(Stage stage) 
    {
    
    	stage.setTitle("");
        stage.setWidth(500);
        stage.setHeight(500);
        Scene scene = new Scene(new Group());
        VBox root = new VBox();    
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();       
        
       
        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                    public void changed(ObservableValue ov, State oldState, State newState) {
                        if (newState == State.SUCCEEDED) {
                                                    	
                        	String js = " function stuff(e) "
                        			  + "{"
                        			  + "e.target.style.outline = \"solid blue 5px\";"
                        			  + "var evt = e ? e:window.event;"
                        			  + "e.preventDefault()"                        			  
                        			  + "}"
                        			  + "document.addEventListener('click', stuff);"
                        			  + "document.addEventListener('onclick', stuff);";    
                        	webEngine.executeScript(js);
                        }
                    }
                });
        
        webEngine.load("https://www.experts-exchange.com/");
        
        root.getChildren().addAll(browser);
        scene.setRoot(root);
 
        stage.setScene(scene);
        stage.show();
        
        
        
        
    }
 
    public static void main(String[] args) 
    {
        launch(args);
    }
}
