package adasa.srh.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import adasa.srh.dao.DenunciaDao;
import adasa.srh.entity.Denuncia;
import adasa.srh.tabela.DenunciaTabela;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DenunciaController implements Initializable {
	
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
	
	//CONEXÃO  DAO
	private DenunciaDao denunciaDaoController = new DenunciaDao ();
	// LIST (para guardar a DenunciaDao
	private List<Denuncia> denunciaListController = denunciaDaoController.listDenunciaEntity();
	// PARA JOGAR O DADO NO TABLEVIEW JAVAFX
	private ObservableList<DenunciaTabela> listDenunciaTabelaOB = FXCollections.observableArrayList();
	// MÉTODO PARA CHAMAR OS DADOS
	
	
	public void listarDenuncia () {
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
	
	// BUTTON //
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
	
	public void btnNovoHabilitar (ActionEvent event) {
		tfDocumento.setDisable(false);
		tfDocumento.setDisable(false);
		tfDocSei.setDisable(false);
		tfProcSei.setDisable(false);
		tfResDen.setDisable(false);
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
	}
	public void btnExcluirHabilitar (ActionEvent event) {
		
	}
	public void btnPesquisarHabilitar (ActionEvent event) {
		listarDenuncia();
	}

	public void initialize(URL url, ResourceBundle ResourceBundle) {
		tfDocumento.setDisable(true);
		tfDocumento.setDisable(true);
		tfDocSei.setDisable(true);
		tfProcSei.setDisable(true);
		tfResDen.setDisable(true);
		
	}
}
