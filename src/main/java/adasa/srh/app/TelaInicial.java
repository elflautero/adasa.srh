package adasa.srh.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TelaInicial extends Application{

	private static Stage stage;
	
	public static void main(String[] args)  {
		launch (args);

	}

	@Override
	public void start (final Stage primaryStage) {
		
		stage = primaryStage;
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/TelaInicial.fxml")); // caminho  do fxml
			Scene scene = new Scene (root);
			
			stage.setMaximized(false);
			stage.setResizable(false);
			
			stage.setTitle("Fiscalização");
			stage.setScene(scene);
			stage.getIcons().add(new Image ("/images/iconWater.png")); // icone na tela (superior esquerda)
			scene.getStylesheets().add("css/TelaInicialCss.css"); // caminho  do  css
			
			stage.show();
		
		    } catch (Exception e){
			e.printStackTrace();
		    }
		
	}
	
	public static Stage getStage() {
		return stage;

	}

}
