package pathExplorer;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.InputStream;
import java.util.Scanner;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class PathExplorer extends Application {
	
	
	
   @Override
   public void start(Stage stage){
	   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	   LogPane logPane = new LogPane();	   
	   
	   BrowserPane splitPaneBrowser = new BrowserPane(logPane.m_statuDisplayTextField);  
	   
	   VBox vBox = new VBox();   
	   
	   URLPane urlPane = new URLPane(splitPaneBrowser.m_webEngine,logPane.m_statuDisplayTextField);
	   
	   vBox.getChildren().addAll(urlPane.m_URLPane, splitPaneBrowser.m_browserPane, logPane.m_logPane);
	   
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
