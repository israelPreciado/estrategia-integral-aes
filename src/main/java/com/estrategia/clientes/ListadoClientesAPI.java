package com.estrategia.clientes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import db.EstrategiaDriverManagerConn;
import utilities.ConfigProperties;

/**
 * Servlet implementation class ListadoClientesAPI
 */
@WebServlet("/listado-clientes")
public class ListadoClientesAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
				String strClienteId = request.getParameter("cid");
				int clienteId = 0;
				if (strClienteId != null && !"".equals(strClienteId)) clienteId = Integer.valueOf(strClienteId);
							
				try {
					if ( strApiKey == null || !apiKey.equals(strApiKey) ) {
						jMessage.put("error", "access denied");
						out.println(jMessage.toString());
					} else {
						JSONArray jArray = new JSONArray();
						String xml = "<a>";
						xml += "<cid>" + clienteId + "</cid>";				
						xml += "</a>";
						System.out.println("xml:" + xml);
						
						EstrategiaDriverManagerConn estrategiaConn = new EstrategiaDriverManagerConn("0", false);	
						jArray = estrategiaConn.execute("listado_clientes", xml);
						estrategiaConn.closeConnection();
						
						out.println(jArray);
					}					
				} catch(Exception ex) {
					jMessage.put("error", ex.getMessage());
					out.println(jMessage.toString());
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
