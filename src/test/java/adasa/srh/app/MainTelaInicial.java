package adasa.srh.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainTelaInicial extends Application {
	
	public static void Main (String[] args) {
		launch(args);
		}
	
	private static Stage stage;
	
	@Override
	public void start (final Stage primaryStage) {
		
		stage = primaryStage;
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/TelaInicial.fxml"));
			Scene scene = new Scene (root);
		
			stage.setMaximized(false);
			stage.setResizable(false);
			stage.setTitle("Cadastra cliente");
			stage.setScene(scene);
			
			scene.getStylesheets().add("css/TelaInicialCss.css");
			
			stage.show();
		
		    } catch (Exception e){
			e.printStackTrace();
		    }
		
	}
	
	public static Stage getStage() {
		return stage;

	}

	

	

}
