package pathExplorer;

import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class BrowserPane {
	private TextField  m_statuDisplayTextField;
	public WebEngine m_webEngine;
	public SplitPane m_browserPane;
	
	BrowserPane(TextField statuDisplayTextField){
		m_statuDisplayTextField = statuDisplayTextField;	
		m_browserPane = createBrowserPane();
	}
	
	
	
		
	public  SplitPane createBrowserPane(){
		WebView browser = new WebView();
		m_webEngine = browser.getEngine();       
	   
	  
		   SplitPane splitPaneBrowser = new SplitPane();
		   splitPaneBrowser.getItems().addAll(browser);
		   
		   //create callback to allow for JS to talk to App
		   JSObject window = (JSObject) m_webEngine.executeScript("window");
		   window.setMember("app", new ApplicationCallback(m_statuDisplayTextField));
		   
		   return splitPaneBrowser;
	}
	

}
