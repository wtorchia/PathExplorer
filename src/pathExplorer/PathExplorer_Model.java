package pathExplorer;

public class PathExplorer_Model {
	
	public enum HighlightColorsEnum{RED, GREEN, BLUE}
	
	public HighlightColorsEnum m_highlightColorsList; 
	
	public boolean m_showLogs; 
	public boolean m_showSetting;
	public boolean m_followLinks;
	public boolean m_stayOnPage;
	
	public String m_loadingMessage = "Loading utils";
	public String m_pageReadyMessage = "Page ready";
	public String m_defaultURL = "https://www.experts-exchange.com/";
	public String m_hightlightColor;
	
	PathExplorer_Model()
	{
		
	}

	public void initialize()
	{
		m_showLogs = false;
		m_stayOnPage = true;		
	}
}
