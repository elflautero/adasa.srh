package adasa.srh.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import adasa.srh.dao.DenunciaDao;
import adasa.srh.entity.Denuncia;
import adasa.srh.tabela.DenunciaTabela;

import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;



public class TelaInicialController implements Initializable, MapComponentInitializedListener  {
	
	// MAPS //
	Double latCoord = -15.7754084; // latitude inicial do mapa - ADASA
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
	
			// ABA DENUNCIA - TEXTFIELDS //
	
	@FXML
	TextField tfDocumento = new TextField();
	@FXML
	TextField tfDocSei = new TextField();
	@FXML
	TextField tfProcSei =  new TextField();
	@FXML
	TextField tfResDen = new TextField();
	@FXML
	TextField tfPesquisar = new TextField();
	
		// ABA DENÚNCIA - BOTÕES //
	@FXML
	Button btnNovo = new Button();
	@FXML
	Button btnSalvar = new Button();
	@FXML
	Button btnEditar = new Button();
	@FXML
	Button btnExcluir = new Button();
	@FXML
	Button btnCancelar = new Button();
	@FXML
	Button btnPesquisar = new Button();
	@FXML
	Button btnSair = new Button();
	
	// BOTÕES - ATOS SRH
	
		@FXML
		Button btnRelatorio = new Button();
		@FXML
		Button btnTN = new Button();
		@FXML
		Button btnAIA = new Button();
		@FXML
		Button btnMulta = new Button();
		@FXML
		Button btnHTML = new Button();
		@FXML
		Button btnGoogle = new Button();
		@FXML
		Button btnSEI = new Button();
		@FXML
		Button btnNavegador = new Button();
		

		//STRING DE TESTE EM HTML PARA MOSTAR NO VISOR DO HTML EDITOR
		String INITIAL_TEXT = "<table style='border:4px solid;border-bottom-width:0px; margin-left:auto;margin-right:auto;width:800px;'>"
				+ 				"<tbody>" 
				+					"<tr>"
				+						"<td align='left'><strong>RELAT&Oacute;RIO DE VISTORIA E FISCALIZA&Ccedil;&Atilde;O N&deg;</strong></td>" 
				+						"<td align='left'><strong>SEI N&deg;</strong></td>"
				+					"</tr>"
				+				"</tbody>"
				+			"</table>";
		String google = "https://www.google.com.br/search?source=hp&ei=BqVsWruNOMuXwgSko764DA&q=";
		
		// TEXT FIELD
		@FXML
		TextField tfLink = new TextField();
		
		//WEB BROWSER
		@FXML
		WebView navegador = new WebView();
		@FXML
		WebEngine engine = new WebEngine();
		
		//AÇÕES DOS BOTÕES
		public void btnHTMLCopiar (ActionEvent event) {
				
		}
		
		public void btnGoogleEntrar (ActionEvent event) {
			engine.load("http://www.google.com.br");
			}
		
		public void btnSEIEntrar (ActionEvent event) {
			engine.load("https://sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI");
		}
		
		public void btnNavegadorIr (ActionEvent event) {
			String pesquisa = tfLink.getText().toString();
			engine.load(google + pesquisa);
			
			
		}
		
		// RELATORIOS E TNS
		public void btnRelatorioCriar (ActionEvent event) {
			engine.loadContent(INITIAL_TEXT);
			
		}
		
		public void btnTNCriar (ActionEvent event) {
			
		}
		
		public void btnAIACriar (ActionEvent event) {
			
		}
		
		public void btnMultaCriar (ActionEvent event) {
			
		}

	
	// TABLE VIEW
		@FXML
		private TableView <DenunciaTabela> tvLista;
		
		// COLUMNS
		@FXML
		private TableColumn<DenunciaTabela, String> tcDocumento;
		@FXML
		private TableColumn<DenunciaTabela, String> tcDocSEI;
		@FXML
		private TableColumn<DenunciaTabela, String> tcProcSEI;
		
		// EVENTOS ABA DENÚNCIA //
	

	public void btnNovoHabilitar (ActionEvent event) {
		
		// Field 'Documento' doesn't have a default value Deu erro ao não preencher um dos campos
		// e mesmo depois acrescentando o valor não digitado, não deu pra cadastrar
		// mas a pesquisa continuou pesquisando bem...
		
		tfDocumento.setDisable(false);
		tfDocumento.setDisable(false);
		tfDocSei.setDisable(false);
		tfProcSei.setDisable(false);
		tfResDen.setDisable(false);
		btnSalvar.setDisable(false);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		btnNovo.setDisable(true);
		
	}
	
	public void btnSalvarSalvar (ActionEvent event) {
		
		Denuncia denuncia = new Denuncia();
		
		denuncia.setDocumento_Denuncia(tfDocumento.getText());
		denuncia.setProcesso_SEI_Denuncia(tfProcSei.getText());
		denuncia.setDocumento_SEI_Denuncia(tfDocSei.getText());
		denuncia.setDescricao_Denuncia(tfResDen.getText());
		
		DenunciaDao dao = new DenunciaDao();
		dao.addDenuncia(denuncia);
		
	}

	public void btnEditarHabilitar (ActionEvent event) {
		
		tfDocumento.setDisable(false);
		tfDocumento.setDisable(false);
		tfDocSei.setDisable(false);
		tfProcSei.setDisable(false);
		tfResDen.setDisable(false);
		btnSalvar.setDisable(true);
		btnNovo.setDisable(true);
		
	}
	public void btnExcluirHabilitar (ActionEvent event) {
		
	}
	public void btnCancelarHabilitar (ActionEvent event) {
		
		/* ver um jeito de limpar os campos mas dar um reset no programa também
		 * 
		 */
		
		modularBotoesInicial();
	}
	public void btnPesquisarHabilitar (ActionEvent event) {
		
		// colocar tecla enter também além do botão pesquisar
		
		String strPesquisa = (String) tfPesquisar.getText();
		
		listarDenuncia(strPesquisa);
	}
	
	public void listarDenuncia (String strPesquisa) {
		
		/* ver como faz para quando clicar num documento ele preencher tudo acima e abaixo em outra lista
		 * buscar outros documentos  relacionados como tn, rv etc
		 */
		
		//CONEXÃO  DAO
		DenunciaDao denunciaDaoController = new DenunciaDao ();
		// LIST (para guardar a DenunciaDao
		List<Denuncia> denunciaListController = denunciaDaoController.listDenunciaEntity(strPesquisa);
		// PARA JOGAR O DADO NO TABLEVIEW JAVAFX
		
		ObservableList<DenunciaTabela> listDenunciaTabelaOB = FXCollections.observableArrayList();
		// MÉTODO PARA CHAMAR OS DADOS
		
		if (!listDenunciaTabelaOB.isEmpty()) {
			listDenunciaTabelaOB.clear();
		}
		for (Denuncia denuncia : denunciaListController) {
			DenunciaTabela d = new DenunciaTabela(denuncia.getCod_Denuncia(), denuncia.getDocumento_Denuncia(), denuncia.getDocumento_SEI_Denuncia(), denuncia.getProcesso_SEI_Denuncia());
			listDenunciaTabelaOB.add(d);
		}
		
		tcDocumento.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Documento_Denuncia"));
		tcDocSEI.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Documento_SEI_Denuncia"));
		tcProcSEI.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Processo_SEI_Denuncia"));
		
		tvLista.setItems(listDenunciaTabelaOB);
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
	public void btnCapturarCorqui (ActionEvent event) {  // consertar o nome do método, a palavra é croqui
	
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
		
		// DENUNCIA - BOTÕES
		modularBotoesInicial();
		
		
		cbTipoPessoa.setValue("Física");
		cbTipoPessoa.setItems(olTipoPessoa);
		
		//MAPA
		mapView.addMapInializedListener(this);
		
		//ATOS SRH - WEBVIEW
		engine = navegador.getEngine();
		engine.load("https://sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI");
	}
	
	private void modularBotoesInicial () {
		
		tfDocumento.setDisable(true);
		tfDocumento.setDisable(true);
		tfDocSei.setDisable(true);
		tfProcSei.setDisable(true);
		tfResDen.setDisable(true);
		btnSalvar.setDisable(true);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		btnNovo.setDisable(false);
		
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