package adasa.srh.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DenunciaMain extends Application{
	
	private static Stage stage;

	public static void main(String[] args) {
		launch (args);

	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		stage = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Denuncia.fxml"));
			Scene scene = new Scene (root);
			
			stage.setMaximized(false);
			stage.setResizable(false);
			
			stage.setTitle("Denúncia");
			stage.setScene(scene);
			
			stage.show();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static Stage getStage() {
		return stage;

	}

}
