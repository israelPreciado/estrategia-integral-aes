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
 * Servlet implementation class FormUsuarioAPI
 */
@WebServlet("/form-usuario")
public class FormUsuarioAPI extends HttpServlet {
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
				String usuario = request.getParameter("u");
				String pass = request.getParameter("p");
				String strStatus = request.getParameter("s");
				
				Integer usuarioId = 0;
				if (strUsuarioId != null && !"".equals(strUsuarioId)) usuarioId = Integer.valueOf(strUsuarioId);
				Integer status = 1;
				if (strStatus != null && !"".equals(strStatus)) status = Integer.valueOf(strStatus);
				
				if ( strApiKey == null || !apiKey.equals(strApiKey) ) {									
					jMessage.put("error", "access denied");
					out.println(jMessage.toString());
				} else if (usuario == null || "".equals(usuario) || pass == null || "".equals(pass)) { 
					jMessage.put("error", "Invalid parameters");
					out.println(jMessage.toString());
				} else {
					JSONArray jArray = new JSONArray();
					String xml = "<a>";
					xml += "<uid>" + usuarioId + "</uid>";
					xml += "<u>" + usuario + "</u>";
					xml += "<p>" + pass + "</p>";
					xml += "<s>" + status + "</s>";
					xml += "</a>";
					System.out.println("xml:" + xml);
					
					EstrategiaDriverManagerConn estrategiaConn = new EstrategiaDriverManagerConn("0", false);	
					jArray = estrategiaConn.execute("crear_editar_usuario", xml);
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
