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

import adasa.srh.dao.DenunciaDao;
import adasa.srh.entity.Denuncia;

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
	
			// ABA DENUNCIA //
	
	@FXML
	Button btnSalvar = new Button();
	@FXML
	TextField tfDocumento = new TextField();
	@FXML
	TextField tfProcSei =  new TextField();
	@FXML
	TextField tfDocSei = new TextField();
	@FXML
	TextField tfResDen = new TextField();
	
	public void btnSalvarSalvar (ActionEvent event) {
		
		Denuncia denuncia = new Denuncia();
		
		denuncia.setDocumento_Denuncia(tfDocumento.getText());
		denuncia.setProcesso_SEI_Denuncia(tfProcSei.getText());
		denuncia.setDocumento_SEI_Denuncia(tfDocSei.getText());
		denuncia.setDescricao_Denuncia(tfResDen.getText());
		
		DenunciaDao dao = new DenunciaDao();
		dao.addDenuncia(denuncia);
		
	}
	
			// ABA ENDEREÇO //
	
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

		// parâmetro de busca: @ e , 

        int pInicial = linkCoord.indexOf("@"); //posição inicial do @ referente ao link completo 

        int pFinal = linkCoord.lastIndexOf(","); // posição da última ,(vírgula) referente ao link completo 

        String coord = linkCoord.substring(pInicial, pFinal); 

         

        System.out.println("Posições do @ e , : " + pInicial + " e " + pFinal); 

        System.out.println("String coord: " + coord); 

         

        // repetir a busca de posicionamento  do @ e , a partir agora da String coord 

        pInicial = coord.indexOf("@"); 

        pFinal = coord.indexOf(","); 

         

        System.out.println(pInicial + " e " + pFinal); 

         

        int pFinalSum = pFinal + 1; 

        int pFinalSub = pFinal - 1; 

        String lat = coord.substring(1,(pFinal)); 

        String lon = coord.substring (pFinalSum, (pFinal + pFinalSub)); 

        System.out.println(lat); 

        System.out.println(lon); 
		
		tfLat.setText(lat);
		tfLon.setText(lon);
		
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