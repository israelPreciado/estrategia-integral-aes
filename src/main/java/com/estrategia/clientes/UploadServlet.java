package com.estrategia.clientes;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.estrategia.clientes.storage.ImageUpload;
import com.estrategia.clientes.storage.PdfUpload;
import com.google.apphosting.api.ApiProxy;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import beans.Image;
import beans.Documento;
import interfaces.IipAddress;
import security.AccessAPI;
import security.IaccessAPI;
import utilities.ConfigProperties;
import utilities.IpAddress;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String BUCKET_NAME = "estrategia-integral";
	private static Storage storage = null;
	private String strResponse = "";	
	Connection conn;
       
	@Override
	public void init() throws ServletException {
		try {
			// Instance Cloud Storage
			storage = StorageOptions.getDefaultInstance().getService();			
		} finally {
			// Nothing really to do here.
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");

		try (PrintWriter out = response.getWriter()) {
			try {
				// Obtener IP
				IipAddress iIpAddress = new IpAddress(request);

				// Validar acceso al API
				IaccessAPI iAccessAPI = new AccessAPI();

				if (!iAccessAPI.allowAccess(iIpAddress.getIpAddress())) {
					out.println("{\"error\": \"1001: Access denied\"}");
				} else {	
					ConfigProperties configProperties = new ConfigProperties("api-key.properties");
					String apiKey = configProperties.getProperty("key");
					String strApiKey = request.getParameter("api_key");
					final Part filePart = request.getPart("file");
					final String strClienteId = request.getParameter("cid");
					final String strProyectoId = request.getParameter("pid");
					final String fileName = filePart.getSubmittedFileName().toLowerCase();		
					final String fileExt = fileName.substring(fileName.lastIndexOf('.') + 1);
					String name = request.getParameter("name");	
					String type = request.getParameter("type");
					final String folderName = request.getParameter("folder");																												
					String url = request.getParameter("url");																		
					String bucketName = request.getParameter("bucket");	
					Integer clienteId = 0, proyectoId = 0;
					boolean isImage = false;
					
					if (strClienteId != null && !"".equals(strClienteId)) clienteId = Integer.valueOf(strClienteId);
					if (strProyectoId != null && !"".equals(strProyectoId)) proyectoId = Integer.valueOf(strProyectoId);
					if (bucketName != null && !"".equals(bucketName)) BUCKET_NAME = bucketName; 
					
					/*if (fileExt.equals("pdf") || fileExt.equals("doc") || fileExt.equals("docx") || fileExt.equals("xls") || fileExt.equals("xlsx")) {
						isImage = false;
					}*/
					if (type.equals("imagen")) {
						isImage = true;
					}
					
					if ( strApiKey == null || !apiKey.equals(strApiKey) ) {						
						out.println("{\"error\": \"access denied\"}");						
					} else if (filePart == null || strClienteId == null || strProyectoId == null) {
						out.println("{\"error\": \"1022: Empty parameters\"}");
					} else {
						if (isImage) {						
							Image image = new Image();
							image.setFilePart(filePart);	
							image.setClienteId(clienteId);
							image.setProyectoId(proyectoId);
							image.setNombreLogotipo(name);
							image.setFolderName(folderName);
							image.setFileData(true);
							image.setUrl(""); // se asigna en la clase ImageUpload otro valor si es una imagen nueva											
							image.setBucketName(BUCKET_NAME);
							
							ImageUpload imageUpload = new ImageUpload(image, storage);
							imageUpload.setOnUploadListener(new ImageUpload.OnUpload() {
								
								@Override
								public void onUpload(String response) {
									strResponse = response;
								}
							});
						} else {						
							Documento doc = new Documento();
							doc.setFilePart(filePart);
							doc.setClienteId(clienteId);
							doc.setProyectoId(proyectoId);
							doc.setNombreLogotipo(name);
							doc.setFolderName(folderName);	
							doc.setFileData(true);
							doc.setUrl(""); // se asigna en la clase clase PdfUpload
							doc.setBucketName(BUCKET_NAME);
							
							PdfUpload pdfUpload = new PdfUpload(doc, storage);
							pdfUpload.setOnUploadListener(new PdfUpload.OnUpload() {
								
								@Override
								public void onUpload(String response) {
									strResponse = response;
								}
							});
						}	
						
						out.println(strResponse);
					}										
				}

			} catch (Exception ex) {
				out.println("{\"error\": \"1000:" + ex.getMessage() + "\"}");
			}
		}
	}
}
