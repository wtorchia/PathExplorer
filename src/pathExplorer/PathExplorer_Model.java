package pathExplorer;

public class PathExplorer_Model
{

	public enum HighlightColorsEnum
	{
		RED, GREEN, BLUE
	}

	public HighlightColorsEnum m_highlightColorsList;

	public boolean m_showLogs;
	public boolean m_showSetting;
	public boolean m_stayOnPage;
	public boolean m_mouseOver;
	public String m_hightlightColor;

	final public String LOADING_MESSAGE = "Loading utils";
	final public String PAGE_READY_MESSAGE = "Page ready";
	final public String DEFAULT_URL = "https://experts-exchange.com";
	final public String USERAGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";

	final public String ENABLE_MOUSEOVER_JS = "document.addEventListener('mouseover', intercept, true);";
	final public String DISABLE_MOUSEOVER_JS = "document.removeEventListener('mouseover', intercept, true);";
	final public String ENABLE_INTERCEPT_JS = "var enableIntercept = true";
	final public String DISABLE_INTERCEPT_JS = "var enableIntercept = false";

	final public double MIN_LOG_HEIGHT = 300;

	PathExplorer_Model()
	{

	}

	public void initialize()
	{
		m_showLogs = false;
		m_stayOnPage = true;
	}
}
