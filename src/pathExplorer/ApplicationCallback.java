package pathExplorer;

import javafx.scene.control.TextField;

public class ApplicationCallback {
	TextField  m_displayTextField  = new TextField();
	
	
	ApplicationCallback(TextField  displayTextField){
		m_displayTextField = displayTextField;
	}
	
		
    public void sendMsg(String msg){
    	
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
