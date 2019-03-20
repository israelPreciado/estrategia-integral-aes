package com.estrategia.clientes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import db.EstrategiaDriverManagerConn;
import utilities.ConfigProperties;

/**
 * Servlet implementation class FormClienteAPI
 */
@WebServlet("/form-cliente")
public class FormClienteAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
				
		try (PrintWriter out = response.getWriter()) {
			try {
				ConfigProperties configProperties = new ConfigProperties("api-key.properties");
				String apiKey = configProperties.getProperty("key");
				String strApiKey = request.getParameter("api_key");
				String strClienteId = request.getParameter("cid");
				String strNombre = request.getParameter("nombre");
				String strApPat = request.getParameter("appat");
				String strApMat = request.getParameter("apmat");
				String strRfc = request.getParameter("rfc");
				String strDir = request.getParameter("dir");
				String strTel = request.getParameter("tel");
				String strCel = request.getParameter("cel");
				String strEmail = request.getParameter("email");
				
				Integer clienteId = 0;
				if (strClienteId != null && !"".equals(strClienteId)) clienteId = Integer.valueOf(strClienteId);	
				
				if ( strApiKey == null || !apiKey.equals(strApiKey) ) {				
					out.println("{\"error\": \"access denied\"}");
				} else if (strApPat == null) {
					out.println("{\"error\": \"strApPat es nulo\"}");
				} else if (strApMat == null) {
					out.println("{\"error\": \"strApMat es nulo\"}");
				} else if (strRfc == null) {
					out.println("{\"error\": \"strRfc es nulo\"}");
				} else if (strDir == null) {
					out.println("{\"error\": \"strDir es nulo\"}");
				} else if (strTel == null) {
					out.println("{\"error\": \"strTel es nulo\"}");
				} else if (strCel == null) {
					out.println("{\"error\": \"strCel es nulo\"}");
				} else if (strEmail == null) {
					out.println("{\"error\": \"strEmail es nulo\"}");
				} else  {
					if (strNombre.equals("")) {
						out.println("{\"error\": \"1023: El nombre es obligatorio\"}");
					} else {
						JSONArray jArray = new JSONArray();
						String xml = "<a>";
						xml += "<cid>" + clienteId + "</cid>";
						xml += "<nombre>" + strNombre + "</nombre>";
						xml += "<ap-pat>" + strApPat + "</ap-pat>";
						xml += "<ap-mat>" + strApMat + "</ap-mat>";
						xml += "<rfc>" + strRfc + "</rfc>";
						xml += "<dir>" + strDir + "</dir>";
						xml += "<tel>" + strTel + "</tel>";
						xml += "<cel>" + strCel + "</cel>";
						xml += "<email>" + strEmail + "</email>";
						xml += "</a>";
						System.out.println("xml:" + xml);
						
						EstrategiaDriverManagerConn estrategiaConn = new EstrategiaDriverManagerConn("0", false);	
						jArray = estrategiaConn.execute("crear_editar_cliente", xml);
						estrategiaConn.closeConnection();
						
						out.println(jArray); 
					}
				}
			} catch(Exception ex) {
				out.println("{\"error\": \"1029: " + ex.getMessage() + "\"}");
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
