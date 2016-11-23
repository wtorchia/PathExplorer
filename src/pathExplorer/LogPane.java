package pathExplorer;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;


public class LogPane {
	public TextField  m_statuDisplayTextField;
	
	public SplitPane m_logPane;
	
	LogPane(){
					
		m_logPane = CreateLogPane();
	}
	
	private SplitPane CreateLogPane(){	
		 SplitPane splitPaneDisplay = new SplitPane();               
		 m_statuDisplayTextField = new TextField();
		   splitPaneDisplay.getItems().addAll( m_statuDisplayTextField);
		   
		   return splitPaneDisplay;
	}
}
