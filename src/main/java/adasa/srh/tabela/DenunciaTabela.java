package adasa.srh.tabela;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DenunciaTabela {
	
	private final SimpleIntegerProperty Cod_Denuncia;
	private final SimpleStringProperty Documento_Denuncia;
	private final SimpleStringProperty Documento_SEI_Denuncia;
	private final SimpleStringProperty Processo_SEI_Denuncia;
	
	public DenunciaTabela (int Cod_Denuncia, String Documento_Denuncia, String Documento_SEI_Denuncia, String Processo_SEI_Denuncia) {
		
		super();
		
		this.Cod_Denuncia = new SimpleIntegerProperty(Cod_Denuncia);
		this.Documento_Denuncia = new SimpleStringProperty(Documento_Denuncia);
		this.Documento_SEI_Denuncia = new SimpleStringProperty(Documento_SEI_Denuncia);
		this.Processo_SEI_Denuncia = new SimpleStringProperty(Processo_SEI_Denuncia);
		
	}
	public int getCod_Denuncia () {
		return Cod_Denuncia.get();// esse get é para pegar apenas o que queremos do dado, 
	}
	public String getDocumento_Denuncia () {
		return Documento_Denuncia.get();
	}
	public String getDocumento_SEI_Denuncia () {
		return Documento_SEI_Denuncia.get();
	}
	public String getProcesso_SEI_Denuncia () {
		return Processo_SEI_Denuncia.get();
	}
		
}


/*
  		Classe Denuncia

private int Cod_Denuncia;

@Column (name="Documento_Denuncia", nullable = false)
private String Documento_Denuncia;

@Column (name="Documento_SEI_Denuncia", nullable = false)
private String Documento_SEI_Denuncia;

@Column (name="Processo_SEI_Denuncia", nullable = false)
private String Processo_SEI_Denuncia;

@Column (name="Descricao_Denuncia", nullable = false)
private String Descricao_Denuncia;

 Cod_Denuncia Documento_Denuncia Documento_SEI_Denuncia Processo_SEI_Denuncia

*/