package com.estrategia.login;

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
 * Servlet implementation class LoginAPI
 */
@WebServlet("/login")
public class LoginAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
				
		try (PrintWriter out = response.getWriter()) {
			JSONObject jMessage = new JSONObject();
			
			try {
				ConfigProperties configProperties = new ConfigProperties("api-key.properties");
				String apiKey = configProperties.getProperty("key");
				String strApiKey = request.getParameter("api_key");
				String user = request.getParameter("user");
				String pass = request.getParameter("pass");														
				
				if ( strApiKey == null || !apiKey.equals(strApiKey) ) {
					jMessage.put("error", "access denied");
					out.println(jMessage.toString());
				} else {
					if (user == null || "".equals(user) || pass == null || "".equals(pass)) {
						jMessage.put("error", "The parameters can not be null or empty.");
						out.println(jMessage.toString());
					} else {
						JSONArray jArray = new JSONArray();
						String xml = "<a>";
						xml += "<u>" + user + "</u>";
						xml += "<p>" + pass + "</p>";								
						xml += "</a>";
						System.out.println("xml:" + xml);
						
						EstrategiaDriverManagerConn estrategiaConn = new EstrategiaDriverManagerConn("0", false);	
						jArray = estrategiaConn.execute("login", xml);
						estrategiaConn.closeConnection();
						
						out.println(jArray); 
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
