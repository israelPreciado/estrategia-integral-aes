package com.estrategia.clientes.storage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;

import com.estrategia.clientes.storage.ImageUpload.OnUpload;
import com.google.apphosting.api.ApiProxy;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import beans.Documento;
import db.EstrategiaDriverManagerConn;
import net.coobird.thumbnailator.Thumbnails;
import utilities.Constants;

public class PdfUpload {
	private Documento pdf;
	private Storage storage;
	private String url;
	private String fileName;
	private String randomFileName;	
	private String mediaLink;	
	private String fileExt;
	Connection conn;
	
	public interface OnUpload {
		void onUpload(String response);
	}
	
	{
		try {
			// Instance Cloud Storage
			storage = StorageOptions.getDefaultInstance().getService();
			
			try {
				EstrategiaDriverManagerConn estrategiaConn = new EstrategiaDriverManagerConn("0", false);
				conn = estrategiaConn.connection;
			} catch (Exception ex) {
				System.out.println("Unable to connect to Cloud SQL: " + ex.getMessage());
			}			
		} finally {
			// Nothing really to do here.
		}
	}
	
	public PdfUpload(Documento pdf, Storage storage) {
		this.pdf = pdf;
		this.storage = storage;
	}
	
	public void setOnUploadListener(OnUpload listener) {		
		try {			
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYYHHmmss");
			Calendar c = Calendar.getInstance();
			Date date = c.getTime();						
			
			this.fileName = pdf.getFilePart().getSubmittedFileName();		
			this.fileExt = this.fileName.substring(this.fileName.lastIndexOf('.') + 1);
			this.randomFileName = this.fileName.substring(0, this.fileName.lastIndexOf(".")) + "_" + sdf.format(date) + "." + fileExt;				
			this.mediaLink = Constants.STORAGE_GOOGLE_API_URL + pdf.getBucketName() + "/" + pdf.getFolderName() + "/"
					+ randomFileName;

			uploadFile(listener);
		} catch(Exception ex) {
			listener.onUpload(ex.getMessage());
		}		
	}
	
	private void uploadFile(OnUpload listener) {
		try {					
			// Upload to Google cloud storage >>>>>>>>>>>>>>>>>>
			boolean save = true;
			String errorMessageUploadingFile = "";										

			try {
				// Modify access list to allow all users with link to read file
				List<Acl> acls = new ArrayList<>();
				acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
				// the inputstream is closed by default, so we don't need to close it here
				
				// Los inmutabilidad de los objectos, lo que significa que un objeto subido no puede cambiar durante toda su vida útil de almacenamiento.				
				BlobId blobId = BlobId.of(pdf.getBucketName(), pdf.getFolderName() + "/" + randomFileName);
				BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setAcl(acls)
						.setContentType(pdf.getFilePart().getContentType()).build();
				// Blob blob = storage.create(blobInfo, "a simple blob".getBytes(UTF_8));
				Blob blob = storage.create(blobInfo, pdf.getFilePart().getInputStream());				
			} catch (Exception ex) {
				save = false;
				errorMessageUploadingFile = ex.getMessage();
			}

			if (!save) {
				// No persistir datos
				listener.onUpload("{\"error\": \"1002" + errorMessageUploadingFile + "\"}");
				//out.println("{\"error\": \"1002" + errorMessageUploadingFile + "\"}");
			} else {
				// Persistir datos
				updateValues(listener, mediaLink);				
			}
		} catch (Exception ex) {			
			listener.onUpload("{\"error\": \"1004:" + ex.getMessage() + "\"}");
		}	
	}
	
	private void updateValues(OnUpload listener, String mediaLink) {
		try {
			PreparedStatement ps;
			String insertSql = "";	
			String errorMessageUploadingFile = "";
								
			insertSql = "insert into file_uploads(cliente_id, proyecto_id, tipo, nombre, nombre_fisico, url) ";
			insertSql += "values(?,?,?,?,?,?)";						
			
			// Si se envió una imagen en la carga (en el caso de actualizar, no es obligatorio envíar una imágen para poder cambiar el nombre)
			if (pdf.isFileData()) {
				// asignamos las nuevas urls
				pdf.setUrl(mediaLink);				
			}			
																	
			ps = conn.prepareStatement(insertSql);
			ps.setInt(1, pdf.getClienteId()); // cliente_id
			ps.setInt(2, pdf.getProyectoId()); // proyecto_id
			ps.setString(3, fileExt); // tipo
			ps.setString(4, this.fileName); // nombre
			ps.setString(5, this.randomFileName); // nombre físico
			ps.setString(6, this.mediaLink); // url
			
			if (ps.executeUpdate() > 0) {				
				listener.onUpload("[{\"resultado\": \"OK\", \"url\": \"" + pdf.getUrl() + "\", \"msg_delete\": \"" + errorMessageUploadingFile + "\"}]");
			} else {						
				listener.onUpload("{\"error\": \"Ocurrió un problema al persistir la información.\"}");
			}
		} catch(Exception ex) {					
			listener.onUpload("{\"error\": \"1003:" + ex.getMessage() + "\"}");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
}
