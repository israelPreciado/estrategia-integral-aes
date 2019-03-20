package beans;

import javax.servlet.http.Part;

public class Documento {
	private Part filePart;
	private Integer clienteId;
	private Integer proyectoId;
	private String nombreLogotipo;	
	private String folderName;	
	private String url;
	private String bucketName;
	private boolean fileData;
	
	public Documento() {
		super();
	}

	public Documento(Part filePart, Integer clienteId, Integer proyectoId, String nombreLogotipo, String folderName,
			String url, String bucketName, boolean fileData) {
		super();
		this.filePart = filePart;
		this.clienteId = clienteId;
		this.proyectoId = proyectoId;
		this.nombreLogotipo = nombreLogotipo;
		this.folderName = folderName;
		this.url = url;
		this.bucketName = bucketName;
		this.fileData = fileData;
	}

	public Part getFilePart() {
		return filePart;
	}

	public void setFilePart(Part filePart) {
		this.filePart = filePart;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public Integer getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(Integer proyectoId) {
		this.proyectoId = proyectoId;
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

	public boolean isFileData() {
		return fileData;
	}

	public void setFileData(boolean fileData) {
		this.fileData = fileData;
	}		
}
