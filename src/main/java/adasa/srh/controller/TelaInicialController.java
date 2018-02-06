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
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import adasa.srh.dao.DenunciaDao;
import adasa.srh.entity.Denuncia;
import adasa.srh.tabela.DenunciaTabela;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;


public class TelaInicialController implements Initializable, MapComponentInitializedListener  {
	
	// GERAL
	
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
			
	
	//  TAB DENUNCIA - TEXTFIELD

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

			// TAB DENÚNCIA - BOTÕES //
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

			// colocar o botão atualizar, para a atualizar a tabela com o novo documento salvo
		
					// TABLEVIEW - TAB DENÚNCIA
						// string para pesquisar no banco
					
					
					@FXML
					private TableView <DenunciaTabela> tvLista;
					// COLUMNS
					@FXML
					private TableColumn<DenunciaTabela, String> tcDocumento;
					@FXML
					private TableColumn<DenunciaTabela, String> tcDocSEI;
					@FXML
					private TableColumn<DenunciaTabela, String> tcProcSEI;
					
					//método de retorno observable list
					/*
					private ObservableList<Denuncia> obsListPesquisa () {
						ObservableList<Denuncia> denunciaObsListPesquisa = FXCollections.observableArrayList();
						return obsListPesquisa();
					}
					*/
					
					String strPesquisa = "";
					
					private DenunciaDao denunciaDao = new DenunciaDao();	
					private List<Denuncia> denunciaList = denunciaDao.listDenuncia(strPesquisa);
					private ObservableList<DenunciaTabela> obsListDenunciaTabela= FXCollections.observableArrayList();
					
					public void listarDenuncias () {
						if (!obsListDenunciaTabela.isEmpty()) {
							obsListDenunciaTabela.clear();
						}
						for (Denuncia denuncia : denunciaList) {
							DenunciaTabela denTab = new DenunciaTabela(
									denuncia.getCod_Denuncia(), 
									denuncia.getDocumento_Denuncia(),
									denuncia.getDocumento_SEI_Denuncia(), 
									denuncia.getProcesso_SEI_Denuncia(),
									denuncia.getDescricao_Denuncia()
									);
							
								obsListDenunciaTabela.add(denTab);
						}
						
						tcDocumento.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Documento_Denuncia")); 

                        tcDocSEI.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Documento_SEI_Denuncia")); 

                        tcProcSEI.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Processo_SEI_Denuncia")); 
                        
                        tvLista.setItems(obsListDenunciaTabela); 
					}
					
	
	// TAB ENDEREÇO - GOOGLE MAPS ////WEB BROWSER
					
		// colocar alerta de webview sem conexao
					
					
	Double latCoord = -15.7754084; // latitude inicial do mapa - ADASA
	Double longCoord = -47.9411395; // longitude inicial do mapa - ADASA
	
	String linkSEI = "http://treinamento3.sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI";
	
	@FXML
	Button btnAtualizar = new Button();
	@FXML
	Button btnObterCoord = new Button();
	@FXML
	Button btnCapturar = new Button();
	@FXML
	TabPane tpTelaInicial = new TabPane();

		// TAB ENDEREÇO - LATITUDE E LONGITUDE
		@FXML
		TextField tfLat = new TextField();
		@FXML
		TextField tfLon = new TextField();
		@FXML
		TextField tfInserirLink = new TextField();
		
			// TAB ENDEREÇO - BOTÕES
			@FXML 
			Button btnHome = new Button();
			@FXML
			ImageView imgVHome = new ImageView();
	
	
	
	// TAB ATOS SRH - BOTÕES
	
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
	
		// TAB ATOS SRH - TEXT FIELD
		@FXML
		TextField tfLink = new TextField();
		
			//TAB ATOS SRH - WEB VIEW
			@FXML
			WebView navegador = new WebView();
			@FXML
			WebEngine engine = new WebEngine();
		
		
		//TAB ATOS SRH - AÇÕES DOS BOTÕES
		public void btnHTMLCopiar (ActionEvent event) {
				
		}
		
		public void btnGoogleEntrar (ActionEvent event) {
			//engine.load("http://www.google.com.br");
			}
		
		public void btnSEIEntrar (ActionEvent event) {
			//engine.load("https://sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI");
		}
		
		public void btnNavegadorIr (ActionEvent event) {
			//String pesquisa = tfLink.getText().toString();
			//engine.load(google + pesquisa);
			
			
		}
		
		// RELATORIOS E TNS
		public void btnRelatorioCriar (ActionEvent event) {
			//engine.loadContent(INITIAL_TEXT);
			
		}
		
		public void btnTNCriar (ActionEvent event) {
			
		}
		
		public void btnAIACriar (ActionEvent event) {
			
		}
		
		public void btnMultaCriar (ActionEvent event) {
			
		}

	
	// EVENTOS TAB DENÚNCIA //
	
	public void btnNovoHabilitar (ActionEvent event) {
		
		tfDocumento.setText("");
		tfDocSei.setText("");
		tfProcSei.setText("");
		tfResDen.setText("");
		
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
		dao.salvaDenuncia(denuncia);
		
		tfDocumento.setText("");
		tfDocSei.setText("");
		tfProcSei.setText("");
		tfResDen.setText("");
		
		modularBotoesInicial (); 
			
		}

	public void btnEditarHabilitar (ActionEvent event) {
			
		if (tfDocumento.isDisable()) {
			tfDocumento.setDisable(false);
			tfDocumento.setDisable(false);
			tfDocSei.setDisable(false);
			tfProcSei.setDisable(false);
			tfResDen.setDisable(false);
			
		} else {
			
			DenunciaTabela denunciaTabelaEditar = tvLista.getSelectionModel().getSelectedItem();
			Denuncia denunciaEditar = new Denuncia(denunciaTabelaEditar);
			
			denunciaEditar.setDocumento_Denuncia(tfDocumento.getText());
			denunciaEditar.setDocumento_SEI_Denuncia(tfDocSei.getText());
			denunciaEditar.setProcesso_SEI_Denuncia(tfProcSei.getText());
			denunciaEditar.setDescricao_Denuncia(tfResDen.getText());
			
			denunciaDao.editarDenuncia(denunciaEditar);
			denunciaList = denunciaDao.listDenuncia(strPesquisa);
			listarDenuncias();
			
			modularBotoesInicial (); 
				
			}
	}
			
	public void btnExcluirHabilitar (ActionEvent event) {
	
		DenunciaTabela denunciaExcluir = tvLista.getSelectionModel().getSelectedItem();
		int id = denunciaExcluir.getCod_Denuncia();
		System.out.println("O id é: " + id);
		obsListDenunciaTabela.remove(denunciaExcluir);
		denunciaDao.removeDenuncia(id);
		denunciaList = denunciaDao.listDenuncia(strPesquisa);
		listarDenuncias();
		
		modularBotoesInicial (); 		
	}
		
			
	public void btnCancelarHabilitar (ActionEvent event) {
			
		modularBotoesInicial();
	}
		
	public void btnPesquisarHabilitar (ActionEvent event) {
		
		strPesquisa = (String) tfPesquisar.getText();
		
		DenunciaDao denunciaDao = new DenunciaDao();	
		denunciaList = denunciaDao.listDenuncia(strPesquisa);
		listarDenuncias (); 
		
		modularBotoesInicial (); 
		
	}
	
	// TAB ENDEREÇO - GOOGLE MAPS - GMAPSFX
	@FXML
	private GoogleMapView mapView; 
	
	private GoogleMap map;
	
			// BOTÃO - OBTER COORDENADAS E ATUALIZAR GMAPSFX
			public void btnAtualizarLatLong (ActionEvent event) {
				latCoord = Double.parseDouble(tfLat.getText());
				longCoord = Double.parseDouble(tfLon.getText());
				
				mapInitialized();
				
			}
	
	//TAB USUÁRIO - BOX DE ESCOLHA PESSOA FÍSICA E JURÍDICA
	@FXML
	ChoiceBox<String> cbTipoPessoa = new ChoiceBox<String>();
		ObservableList<String> olTipoPessoa = FXCollections
			.observableArrayList("Física" , "Jurídica"); // box - seleção pessoa físcia ou jurídica
	
	
	
	
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
		
		int latIni= linkCoord.indexOf("@");
		String lat = linkCoord.substring(latIni);
		
		int latF = lat.indexOf(",");
		String latitude = lat.substring(1, latF);
		
		String longitude = lat.substring(latF + 1, latF + 1 + latitude.length());
		
		tfLat.setText(latitude);
		tfLon.setText(longitude);
		
	}
	
	// INITIALIZE  - INICIAÇÃO GERAL DA PÁGINA INICIAL
	public void initialize(URL url, ResourceBundle rb) {
		
		// DENUNCIA - BOTÕES
		modularBotoesInicial();
		
		cbTipoPessoa.setValue("Física");
		cbTipoPessoa.setItems(olTipoPessoa);
		
		//MAPA
		mapView.addMapInializedListener(this);
		
		//ATOS SRH - WEBVIEW
		NavegadorWeb ();
		
		// Selecionar um documento pesquisado
		SelecionarDenuncia ();
		
		// listar denuncias
		listarDenuncias();
		
	}
	
	//MÉTODO INICIAL DE HABILITAR E DESABILITAR BOTÕES
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
	
	
	// INICIALIZE DO GOOGLE MAPS
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

			// ADICIONAR MARCADOR AO MAPA
		    MarkerOptions marcador = new MarkerOptions();
		    
		    marcador.position(new LatLong(latCoord,longCoord));
		    
		    Marker markerMap = new Marker(marcador);
		    
		    map.addMarker( markerMap );  
			}
	
	// METODO DE INICIALIZAR O WEBVIEW
	public void NavegadorWeb () {

		navegador.getEngine().setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {

			public WebEngine call(PopupFeatures p) {
				Stage stage = new Stage(StageStyle.UTILITY);
		        WebView wv2 = new WebView();
		        stage.setScene(new Scene(wv2, 900, 600));
		        stage.show();
		        return wv2.getEngine();
		        }
		    });
		//ler conteúdo
		//navegador.getEngine().loadContent(INITIAL_TEXT);
		//ler link
		navegador.getEngine().load("http://treinamento3.sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI");
		
		}
	
	public void SelecionarDenuncia () {
		// TABLE VIEW SELECIONAR DOCUMENTO AO CLICAR NELE
		
				tvLista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
					public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
							DenunciaTabela denTab = (DenunciaTabela) newValue;
							if (denTab == null) {
								
								tfDocumento.setText("");
								tfDocSei.setText("");
								tfProcSei.setText("");
								tfResDen.setText("");
								
								btnNovo.setDisable(true);
								btnSalvar.setDisable(true);
								btnEditar.setDisable(false);
								btnExcluir.setDisable(false);
								btnCancelar.setDisable(false);
								
							} else {
		
								tfDocumento.setText(denTab.getDocumento_Denuncia());
								tfDocSei.setText(denTab.getDocumento_SEI_Denuncia());
								tfProcSei.setText(denTab.getProcesso_SEI_Denuncia());
								tfResDen.setText(denTab.getDescricao_Denuncia());
								
								btnNovo.setDisable(true);
								btnSalvar.setDisable(true);
								btnEditar.setDisable(false);
								btnExcluir.setDisable(false);
								btnCancelar.setDisable(false);
							}
						}
					});
				
	}
	
}