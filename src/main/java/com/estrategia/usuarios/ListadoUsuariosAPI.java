package com.estrategia.usuarios;

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
 * Servlet implementation class ListadoUsuariosAPI
 */
@WebServlet("/listado-usuarios")
public class ListadoUsuariosAPI extends HttpServlet {
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
				String strUsuarioId = request.getParameter("uid");
				String strFindByUsuarioId = request.getParameter("fbuid");	
				String strProyectoId = request.getParameter("pid");
				
				int usuarioId = 0;
				if (strUsuarioId != null && !"".equals(strUsuarioId)) usuarioId = Integer.valueOf(strUsuarioId);	
				int findByUsuarioId = 0;
				if (strFindByUsuarioId != null && !"".equals(strFindByUsuarioId)) findByUsuarioId = Integer.valueOf(strFindByUsuarioId);
				int proyectoId = 0;
				if (strProyectoId != null && !"".equals(strProyectoId)) proyectoId = Integer.valueOf(strProyectoId);					
				
				if ( strApiKey == null || !apiKey.equals(strApiKey) || usuarioId == 0 ) {
					jMessage.put("error", "access denied");
					out.println(jMessage.toString());
				} else {
					try {
						JSONArray jArray = new JSONArray();
						String xml = "<a>";
						xml += "<uid>" + usuarioId + "</uid>";
						xml += "<fbuid>" + findByUsuarioId + "</fbuid>";
						xml += "<pid>" + proyectoId + "</pid>";				
						xml += "</a>";
						System.out.println("xml:" + xml);
						
						EstrategiaDriverManagerConn estrategiaConn = new EstrategiaDriverManagerConn("0", false);	
						jArray = estrategiaConn.execute("listado_usuarios", xml);
						estrategiaConn.closeConnection();
						
						out.println(jArray); 
					} catch(Exception ex) {
						jMessage.put("error", ex.getMessage());
						out.println(jMessage.toString());
					}
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
