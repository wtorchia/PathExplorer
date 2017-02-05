package pathExplorer;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class PathExplorer_View {

	private Border m_borderStyle = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null));
	
	
	private SplitPane m_pathPane;
	private SplitPane m_urlPane;
	private HBox m_logPane;
		
	public VBox m_settingPane;
	public VBox m_mainVBox;

	//public WebEngine m_webEngine;

	public StackPane m_browserPane;
	
	public TextArea m_logTextArea;
	public TextField m_pathTextField;
	public TextField m_urlField;	
	public CheckBox m_interceptCheckBox;
	public CheckBox m_showLogsCheckBox;
	public Button m_loadURLButton;
	public Button m_settingButton;
	public ComboBox m_colorComboBox;
	public Button m_backButton;	
	public VBox m_progressIndicatorVBox;
	public WebView m_browser;
	
	

	PathExplorer_View() 
	{		
	
	}
	
	public void initialize()
	{
		m_browserPane = createBrowserPane();
		m_pathPane = createPathPane();		
		m_urlPane = createURLPane();
		m_logPane = createLogPane();
		m_mainVBox = createLayout();
	}
	

	private VBox createLayout() 
	{
		HBox hBox = new HBox();
		HBox.setHgrow(m_browserPane, Priority.ALWAYS);
		hBox.getChildren().addAll(m_browserPane);

		VBox mainVbox = new VBox();
		VBox.setVgrow(hBox, Priority.ALWAYS);		
		
		mainVbox.getChildren().addAll(m_urlPane, m_pathPane, hBox, m_logPane);		

		return mainVbox;
	}

	
	private HBox createLogPane()
	{
		HBox hBox = new HBox();		
		
		m_logTextArea = new TextArea();				
		m_logTextArea.setVisible(false);
		m_logTextArea.setManaged(false);	
		m_logTextArea.setMinWidth(300);
		m_logTextArea.setBorder(m_borderStyle);
		
		HBox.setHgrow(m_logTextArea, Priority.ALWAYS);
		
		hBox.getChildren().addAll(m_logTextArea); 
		
		return hBox;
	}
	
	
	private StackPane createBrowserPane() 
	{
		m_browser = new WebView();		
		
		StackPane stackPane = new StackPane();		
	
		ProgressIndicator progressIndicator = new ProgressIndicator();
		
		progressIndicator.setMinSize(300, 300);
		
		m_settingPane = createSettingPane();		
		
	    m_progressIndicatorVBox = new VBox(progressIndicator);
	    m_progressIndicatorVBox.setAlignment(Pos.CENTER);	 	    
	    m_progressIndicatorVBox.setStyle("-fx-background-color: rgba(128, 128, 128, 0.75);");
		
	    HBox hBox = new HBox();
	    
	    m_settingPane.setVisible(false);
	    m_settingPane.setManaged(false);
	    
	
	    
	    hBox.getChildren().addAll( m_settingPane, m_browser );
	    
	    HBox.setHgrow(m_browser, Priority.ALWAYS);
	    
	    
	    
		stackPane.getChildren().addAll(hBox);
		stackPane.setAlignment(Pos.TOP_LEFT);
		
		return stackPane;
	}
	
	private SplitPane createPathPane()
	{

		SplitPane splitPane = new SplitPane();

		m_pathTextField = new TextField();
		m_pathTextField.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		m_pathTextField.setMinHeight(50);
								
		splitPane.getItems().addAll(m_pathTextField);

		splitPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, BorderWidths.FULL)));
		
		return splitPane;
	}
	
	
	public SplitPane createURLPane()
	{
		SplitPane splitPane = new SplitPane();
		
		m_urlField = new TextField();		

		m_loadURLButton = new Button("Open URL");
		m_loadURLButton.setMaxWidth(100);
		m_loadURLButton.setMinWidth(100);			
		
		m_settingButton = new Button("Settings");
		m_settingButton.setMaxWidth(100);
		m_settingButton.setMinWidth(100);	
		
		m_backButton = new Button("Back");
		m_backButton.setMaxWidth(100);
		m_backButton.setMinWidth(100);
		
		splitPane.getItems().addAll(m_loadURLButton, m_urlField, m_backButton, m_settingButton);
		
		splitPane.setBorder(m_borderStyle);
		
		return splitPane;
	}
	

	private VBox createSettingPane() 
	{
		VBox vBox = new VBox();		
		int minWidth = 150;		
		
		m_interceptCheckBox = new CheckBox("Stay on page");		
		m_interceptCheckBox.setBorder(m_borderStyle);
		m_interceptCheckBox.setMinWidth(minWidth);
		
		m_showLogsCheckBox = new CheckBox("Show Logs");		
		m_showLogsCheckBox.setBorder(m_borderStyle);
		m_showLogsCheckBox.setMinWidth(minWidth);
		
		Label colorComoBoxLabel = new Label("Element highlight color");
		colorComoBoxLabel.setBorder(m_borderStyle);
		colorComoBoxLabel.setMinWidth(minWidth);
		colorComoBoxLabel.setBorder(m_borderStyle);
		
		m_colorComboBox = new ComboBox();
		m_colorComboBox.setBorder(m_borderStyle);
		m_colorComboBox.setMinWidth(minWidth);

		vBox.getChildren().addAll(m_interceptCheckBox, m_showLogsCheckBox, colorComoBoxLabel, m_colorComboBox);
		
		vBox.setBorder(m_borderStyle);		
		vBox.setMinWidth(minWidth + 5);	
		vBox.setMaxWidth(minWidth + 5);
		
		vBox.setStyle("-fx-background-color: rgba(256, 256, 256, 1);");		
		vBox.setBorder(m_borderStyle);
		
		return vBox;
	}
}
