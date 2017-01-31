package pathExplorer;



public class ApplicationCallback {
	PathExplorer_Controller m_pathExplorer_Controller;
	
	
	ApplicationCallback(PathExplorer_Controller pathExplorer_Controller){
		m_pathExplorer_Controller = pathExplorer_Controller;
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
    	
    	m_pathExplorer_Controller.displayPath(path);
    	 
    	m_pathExplorer_Controller.addLogLine("Clicked element: \n   " + path);
    }
}
