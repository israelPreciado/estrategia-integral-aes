package com.estrategia.avances;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import db.EstrategiaDriverManagerConn;
import utilities.ConfigProperties;

/**
 * Servlet implementation class EliminarFile
 */
@WebServlet("/eliminar-file")
public class EliminarFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String BUCKET_NAME = "estrategia-integral";
	private Storage storage;
	
	{
		try {
			// Instance Cloud Storage
			storage = StorageOptions.getDefaultInstance().getService();
		} finally {
			
		}
	}
       
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		try (PrintWriter out = response.getWriter()) {
			JSONObject jMessage = new JSONObject();
			
			try {
				ConfigProperties configProperties = new ConfigProperties("api-key.properties");
				String apiKey = configProperties.getProperty("key");
				
				String strApiKey = request.getParameter("api_key");
				String strFileId = request.getParameter("fid");
				Integer fileId = 0;
				if (strFileId != null && !"".equals(strFileId)) fileId = Integer.valueOf(strFileId);
				
				if ( strApiKey == null || !apiKey.equals(strApiKey) ) {
					jMessage.put("error", "access denied");
					out.println(jMessage.toString());
				} else if (fileId == 0) {					
					jMessage.put("error", "1022: Empty parameters");
					out.println(jMessage.toString());
				} else {
					JSONArray jArray = new JSONArray();
					String xml = "<a>";
					xml += "<fid>" + fileId + "</fid>";					
					xml += "</a>";
					System.out.println("xml:" + xml);
										
					EstrategiaDriverManagerConn estrategiaConn = new EstrategiaDriverManagerConn("0", false);	
					jArray = estrategiaConn.execute("delete_file", xml);
					estrategiaConn.closeConnection();
					
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject obj = jArray.getJSONObject(i);
									
						if (obj.getString("respuesta").equals("OK")) {
							BlobId blobId = BlobId.of(BUCKET_NAME, "avances-proyectos" + "/" + obj.getString("nombre_fisico"));
							boolean delete = storage.delete(blobId);
						}
					}
					
					out.println(jArray); 
				}
			} catch(Exception ex) {
				jMessage.put("error", ex.getMessage());
				out.println(jMessage.toString());
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
