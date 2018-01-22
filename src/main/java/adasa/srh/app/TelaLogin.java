package adasa.srh.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TelaLogin extends Application{
		
		private static Stage stage;
		
		@Override
		public void start (final Stage primaryStage) {
			
			stage = primaryStage;
			
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/fxml/TelaLogin.fxml"));
				Scene scene = new Scene (root);
			
				stage.setMaximized(false);
				stage.setResizable(false);
				stage.getIcons().add(new Image ("/images/iconWater.png")); // icone na tela (superior esquerda)
				stage.setTitle("Tela de Login");
				stage.setScene(scene);
				
				scene.getStylesheets().add("css/TelaLoginCss.css");
				
				
				stage.show();
			
			    } catch (Exception e){
				e.printStackTrace();
			    }
			
		}
		
	public static Stage getStage() {
			return stage;

		}

	public static void main(String[] args) {
		launch (args);

	}

}
