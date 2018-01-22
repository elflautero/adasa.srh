package adasa.srh.controller;


import java.net.URL;
import java.util.ResourceBundle;

import adasa.srh.dao.FiscalDao;
import adasa.srh.entity.Fiscal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

public class CadastroFiscalController implements Initializable {

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	// INSTANCIANDO OS BOTÕES
	@FXML
	Button btnAdicionar = new Button();
	@FXML
	Button btnSair = new Button();
	
	@FXML
	TextField txtNome = new TextField();
	@FXML
	TextField txtCargo = new TextField();
	@FXML
	TextField txtMatricula = new TextField();
	@FXML
	TextField txtSenha = new TextField();
	
	// ACÕES COM OS BOTÕES
	public void onAcAdicionar (ActionEvent event) {
		
		//instanciar um objeto da classe fiscal
		Fiscal fiscal  = new Fiscal();
		
		//colocar no objeto o que o usuário digitar
		fiscal.setNome_Fiscal(txtNome.getText());
		fiscal.setCargo_Fiscal(txtCargo.getText());
		fiscal.setMatricula_Fiscal(txtMatricula.getText());
		fiscal.setSenha_Fiscal(txtSenha.getText());
		
		//instancia um objeto de  conexão dao, adiciona e passa como parametro o fiscal
		FiscalDao dao = new FiscalDao();
		dao.addFiscal(fiscal);
		
		
	}

	
	/* BANCO DE DADOS
	1 Cod_Fiscal int(11) AUTO_INCREMENT 

	2 Nome_Fiscal varchar(50) 

	3 Cargo_Fiscal varchar(25) 

	4 Matricula_Fiscal varchar(15) 

	5 Senha_Fiscal varchar(15) 

	*/

}
