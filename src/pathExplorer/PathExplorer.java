package pathExplorer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PathExplorer extends Application {	
	
	@Override
	public void start(Stage stage) {		
		
		PathExplorer_View pathExplorer_view = new PathExplorer_View();
		pathExplorer_view.initialize();
		
		PathExplorer_Controller pathExplorer_controller = new PathExplorer_Controller(pathExplorer_view);	
		pathExplorer_controller.initialize();
		
		Scene scene = new Scene(pathExplorer_controller.m_pathExplorer_View.m_mainVBox);
		
		stage.setWidth(1000);
		stage.setHeight(700);
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
