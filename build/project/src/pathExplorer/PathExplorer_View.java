package pathExplorer;

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
	private SplitPane m_URLPane;
	private SplitPane m_browserPane;
	private SplitPane m_pathPane;

	private VBox m_conrtolPane;
	public VBox m_mainVBox;

	public WebEngine m_webEngine;

	public TextArea m_statuDisplayTextArea;
	public TextField m_pathTextField;
	public TextField m_urlField;
	public Button m_loadURLButton;
	public RadioButton m_radioButtonEnable;
	public RadioButton m_radioButtonDisable;

	PathExplorer_View() {
		m_URLPane = createURLPane();
		m_browserPane = createBrowserPane();
		m_pathPane = createPathPane();
		m_conrtolPane = createControlPane();

		m_mainVBox = createLayout();
	}

	private VBox createLayout() {
		HBox hBox = new HBox();
		HBox.setHgrow(m_browserPane, Priority.ALWAYS);
		hBox.getChildren().addAll(m_conrtolPane, m_browserPane);

		VBox mainVbox = new VBox();
		VBox.setVgrow(hBox, Priority.ALWAYS);
		mainVbox.getChildren().addAll(m_URLPane, hBox, m_pathPane);

		return mainVbox;
	}

	private SplitPane createBrowserPane() {
		WebView browser = new WebView();
		m_webEngine = browser.getEngine();

		SplitPane splitPane = new SplitPane();
		splitPane.getItems().addAll(browser);

		splitPane.setMaxHeight(Double.MAX_VALUE);
		splitPane.setMaxWidth(Double.MAX_VALUE);

		return splitPane;
	}

	private SplitPane createPathPane() {

		SplitPane splitPane = new SplitPane();

		m_pathTextField = new TextField();

		splitPane.getItems().addAll(m_pathTextField);

		return splitPane;
	}

	private SplitPane createURLPane() {

		SplitPane splitPane = new SplitPane();

		m_urlField = new TextField();
		m_urlField.setText("https://www.experts-exchange.com/");

		m_loadURLButton = new Button();
		m_loadURLButton.setText("Open URL");
		m_loadURLButton.setMaxWidth(100);
		m_loadURLButton.setMinWidth(100);

		splitPane.getItems().addAll(m_loadURLButton, m_urlField);

		return splitPane;
	}

	private VBox createControlPane() {
		VBox vBox = new VBox();
		vBox.setMaxWidth(150);

		ToggleGroup toggleGroup = new ToggleGroup();

		m_radioButtonEnable = new RadioButton("Enable links");
		m_radioButtonEnable.setToggleGroup(toggleGroup);
		

		m_radioButtonDisable = new RadioButton("Disable links");
		m_radioButtonDisable.setToggleGroup(toggleGroup);
		m_radioButtonDisable.setSelected(true);

		m_statuDisplayTextArea = new TextArea();

		VBox.setVgrow(m_statuDisplayTextArea, Priority.ALWAYS);

		vBox.getChildren().addAll(m_radioButtonDisable, m_radioButtonEnable,  m_statuDisplayTextArea);

		return vBox;
	}

}
