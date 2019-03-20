package beans;

import javax.servlet.http.Part;

public class PDF {
	private Part filePart;
	private String nombreLogotipo;	
	private String folderName;
	private int contratanteId;
	private int ventaId;
	private short tipo;
	private String url;
	private String bucketName;	
	
	public PDF() {
		super();
	}

	public PDF(Part filePart, String nombreLogotipo, String folderName, int contratanteId, int ventaId, short tipo,
			String url, String bucketName) {
		super();
		this.filePart = filePart;
		this.nombreLogotipo = nombreLogotipo;
		this.folderName = folderName;
		this.contratanteId = contratanteId;
		this.ventaId = ventaId;
		this.tipo = tipo;
		this.url = url;
		this.bucketName = bucketName;
	}

	public Part getFilePart() {
		return filePart;
	}

	public void setFilePart(Part filePart) {
		this.filePart = filePart;
	}

	public String getNombreLogotipo() {
		return nombreLogotipo;
	}

	public void setNombreLogotipo(String nombreLogotipo) {
		this.nombreLogotipo = nombreLogotipo;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public int getContratanteId() {
		return contratanteId;
	}

	public void setContratanteId(int contratanteId) {
		this.contratanteId = contratanteId;
	}

	public int getVentaId() {
		return ventaId;
	}

	public void setVentaId(int ventaId) {
		this.ventaId = ventaId;
	}

	public short getTipo() {
		return tipo;
	}

	public void setTipo(short tipo) {
		this.tipo = tipo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}		
}
