package pathExplorer;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class PathExplorer_View {
	private HBox m_browserPane;
	private SplitPane m_pathPane;
	private SplitPane m_conrtolPane;

	public VBox m_LogPane;
	public VBox m_mainVBox;

	public WebEngine m_webEngine;

	public TextArea m_statuDisplayTextArea;
	public TextField m_pathTextField;
	public TextField m_urlField;
	public Button m_loadURLButton;
	public RadioButton m_radioButtonEnable;
	public RadioButton m_radioButtonDisable;
	public Button m_logButton;

	PathExplorer_View() {		
		m_browserPane = createBrowserPane();
		m_pathPane = createPathPane();
		m_conrtolPane = createControlPane();

		m_mainVBox = createLayout();
	}

	private VBox createLayout() {
		HBox hBox = new HBox();
		HBox.setHgrow(m_browserPane, Priority.ALWAYS);
		hBox.getChildren().addAll(m_browserPane);

		VBox mainVbox = new VBox();
		VBox.setVgrow(hBox, Priority.ALWAYS);
		mainVbox.getChildren().addAll(m_conrtolPane, hBox, m_pathPane);

		return mainVbox;
	}

	private HBox createBrowserPane() {
		WebView browser = new WebView();
		m_webEngine = browser.getEngine();

		HBox hBox = new HBox();
		
		HBox.setHgrow(browser, Priority.ALWAYS);		
		
		m_statuDisplayTextArea = new TextArea();				
		m_statuDisplayTextArea.setVisible(false);
		m_statuDisplayTextArea.setManaged(false);	
		
		hBox.getChildren().addAll(m_statuDisplayTextArea, browser);
		
		hBox.setMargin(m_statuDisplayTextArea, new Insets(0,5,0,0));	

		return hBox;
	}

	private SplitPane createPathPane() {

		SplitPane splitPane = new SplitPane();

		m_pathTextField = new TextField();

		splitPane.getItems().addAll(m_pathTextField);

		return splitPane;
	}
	

	private SplitPane createControlPane() {
		SplitPane splitPane = new SplitPane();
		
		m_urlField = new TextField();
		m_urlField.setText("https://www.experts-exchange.com/");

		m_loadURLButton = new Button("Open URL");
		m_loadURLButton.setMaxWidth(100);
		m_loadURLButton.setMinWidth(100);		
		
		ToggleGroup toggleGroup = new ToggleGroup();

		m_radioButtonEnable = new RadioButton("Enable links");
		m_radioButtonEnable.setToggleGroup(toggleGroup);
		m_radioButtonEnable.setMaxWidth(100);
		m_radioButtonEnable.setMinWidth(100);

		m_radioButtonDisable = new RadioButton("Disable links");
		m_radioButtonDisable.setToggleGroup(toggleGroup);
		m_radioButtonDisable.setSelected(true);
		m_radioButtonDisable.setMaxWidth(100);
		m_radioButtonDisable.setMinWidth(100);	

		m_logButton = new Button("Show logs");
		m_logButton.setMaxWidth(100);
		m_logButton.setMinWidth(100);		

		splitPane.getItems().addAll(m_loadURLButton, 
									m_urlField, 
									m_radioButtonDisable, 
									m_radioButtonEnable, 
									m_logButton);

		return splitPane;
	}

}
