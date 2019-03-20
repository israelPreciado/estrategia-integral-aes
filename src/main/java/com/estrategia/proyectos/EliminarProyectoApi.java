package com.estrategia.proyectos;

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
 * Servlet implementation class EliminarProyectoApi
 */
@WebServlet("/eliminar-proyecto")
public class EliminarProyectoApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("UTF-8");
		
		try (PrintWriter out = response.getWriter()) {
			JSONObject jMessage = new JSONObject();
			
			try {
				ConfigProperties configProperties = new ConfigProperties("api-key.properties");
				String apiKey = configProperties.getProperty("key");
				String strApiKey = request.getParameter("api_key");					
				String strProyectoId = request.getParameter("pid");	
				
				Integer proyectoId = 0;
				if (strProyectoId != null && !"".equals(strProyectoId)) proyectoId = Integer.valueOf(strProyectoId);
				
				if ( strApiKey == null || !apiKey.equals(strApiKey) ) {									
					jMessage.put("error", "access denied");
					out.println(jMessage.toString());
				} else if (proyectoId == null || proyectoId == 0) { 
					jMessage.put("error", "Invalid parameters");
					out.println(jMessage.toString());
				} else {
					JSONArray jArray = new JSONArray();
					String xml = "<a>";										
					xml += "<pid>" + proyectoId + "</pid>";										
					xml += "</a>";
					System.out.println("xml:" + xml);
					
					EstrategiaDriverManagerConn estrategiaConn = new EstrategiaDriverManagerConn("0", false);	
					jArray = estrategiaConn.execute("eliminar_proyecto", xml);
					estrategiaConn.closeConnection();
					
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
