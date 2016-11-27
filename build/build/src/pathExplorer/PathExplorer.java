package pathExplorer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PathExplorer extends Application {	
	
	@Override
	public void start(Stage stage) {		
		
		PathExplorer_Controller pathExplorer_Controller = new PathExplorer_Controller(new PathExplorer_View());	
		
		Scene scene = new Scene(pathExplorer_Controller.m_pathExplorer_View.m_mainVBox);

		//stage.setTitle("EE Path Explorer");
		stage.setWidth(1000);
		stage.setHeight(700);
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
