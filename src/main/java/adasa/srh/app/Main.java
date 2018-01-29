package adasa.srh.app;

import javafx.application.Application;

// mudança na  captura das coordenadas,24 de janeiro de 2018

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Stage stage;
	
	@Override
	public void start (Stage primaryStage) {
		
		stage = primaryStage;
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroFiscal.fxml"));
			Scene scene = new Scene (root);
			
			primaryStage.setMaximized(false);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image ("/images/iconWater.png")); // icone na tela (superior esquerda)
			primaryStage.setTitle("Cadastro");
			primaryStage.setScene(scene);
			scene.getStylesheets().add("css/CadastroFiscalCss.css"); // caminho  do  css
			primaryStage.show();
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	public static Stage getStage(){
		
			return stage;
		
	}

	public static void main(String[] args)  {
		launch(args);


	}

}
