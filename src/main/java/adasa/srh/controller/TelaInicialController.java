package adasa.srh.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;



public class TelaInicialController implements Initializable, MapComponentInitializedListener  {
	
	Double latCoord = -15.7754084; // latidute inicial do mapa - ADASA
	Double longCoord = -47.9411395; // longitude inicial do mapa - ADASA
	
	@FXML
	Button btnAtualizar = new Button();
	@FXML
	Button btnObterCoord = new Button();
	@FXML
	Button btnCapturar = new Button();
	@FXML
	TabPane tpTelaInicial = new TabPane();
	
	// LATITUDE E LONGITUDE
	@FXML
	TextField tfLat = new TextField();
	@FXML
	TextField tfLon = new TextField();
	@FXML
	TextField tfInserirLink = new TextField();
	
	@FXML 
	Button btnHome = new Button();
	@FXML
	ImageView imgVHome = new ImageView();
	
	
	// GOOGLE MAPS - GMAPSFX
	@FXML
	private GoogleMapView mapView; 
	
	private GoogleMap map;
	
	//BOX DE ESCOLHA PESSOA FÍSICA E JURÍDICA
	@FXML
	ChoiceBox<String> cbTipoPessoa = new ChoiceBox<String>();
		ObservableList<String> olTipoPessoa = FXCollections
			.observableArrayList("Física" , "Jurídica"); // box - seleção pessoa físcia ou jurídica
	
	// BOTÃO - OBTER COORDENADAS E INICIALIZAR GMAPSFX
	public void btnAtualizarLatLong (ActionEvent event) {
		latCoord = Double.parseDouble(tfLat.getText());
		longCoord = Double.parseDouble(tfLon.getText());
		
		mapInitialized();
		
	}
	
	// BOTÃO - CAPTURAR TELA DO MAPA PARA O CROQUI
	public void btnCapturarCorqui (ActionEvent event) {
	
	WritableImage i = mapView.snapshot(new SnapshotParameters(), null);
	
	File output = new File("C:\\Users\\fabmu\\OneDrive\\Documentos\\mapViewInicial.png");
	
	try {
		//ImageIO.write(i, "png", output); 
		ImageIO.write(SwingFXUtils.fromFXImage(i, null), "png", output);
	} catch (IOException e){
		e.printStackTrace();
	}
	}
	public void btnObterCoordEvento (ActionEvent event) {
			
		String linkCoord = (tfInserirLink.getText());

		int pInicial= linkCoord.indexOf("@");
		int pFinal = linkCoord.lastIndexOf(",");
		String coord = linkCoord.substring(pInicial, pFinal);
				
		//capturar separado @-15.8562721 e ,-48.0364734
		pInicial = coord.indexOf("@");
		pFinal = coord.indexOf(",");
		
		//tirar o @ e a ,
		String lat = coord.substring(pInicial, pFinal);
		String latLat = lat.substring(1);
		
		String lon = coord.substring(pFinal);
		String lonLon = lon.substring(1);
		
		tfLat.setText(latLat);
		tfLon.setText(lonLon);
		
	}

	
	public void initialize(URL url, ResourceBundle rb) {
		cbTipoPessoa.setValue("Física");
		cbTipoPessoa.setItems(olTipoPessoa);
		mapView.addMapInializedListener(this);
	}
		
	public void mapInitialized() {
		
		// Propriedades e opcoes do mapa //
		MapOptions mapaOpcoes = new MapOptions();
		
		mapaOpcoes.center(new LatLong(latCoord,longCoord))
        .mapType(MapTypeIdEnum.SATELLITE)
        .overviewMapControl(false)
        .panControl(false)
        .rotateControl(false)
        .scaleControl(false)
        .streetViewControl(false)
        .zoomControl(true)
        .zoom(12)
        ;
		
		map = mapView.createMap(mapaOpcoes);

	//adicionar marcador
    MarkerOptions marcador = new MarkerOptions();
    
    marcador.position(new LatLong(latCoord,longCoord));
    
    Marker markerMap = new Marker(marcador);
    
    map.addMarker( markerMap );  
	}
	
}