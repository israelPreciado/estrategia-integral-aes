/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import org.json.JSONArray;
import org.json.JSONObject;

public class Tools {

	public Tools() {
		// TODO Auto-generated constructor stub
	}
	
	public JSONObject getHeaderGrid(JSONArray dataHeader){
        JSONObject jsonobj = new JSONObject();
        String[] arrayAbc = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
         for(int i=0; i < dataHeader.length(); i++){
             JSONObject jsonHeader = new JSONObject();
             jsonHeader.put("n", dataHeader.getJSONObject(i).getString("n"));
             jsonHeader.put("v", dataHeader.getJSONObject(i).getString("v"));
             jsonHeader.put("s", dataHeader.getJSONObject(i).getString("s"));
             jsonHeader.put("f", dataHeader.getJSONObject(i).getString("f"));
             jsonHeader.put("class", "col");
             jsonobj.put(arrayAbc[i], jsonHeader);
         }
        return jsonobj; 
    }
    
    public JSONObject getBodyGrid(JSONObject data, JSONArray dataHeader){
        
        String[] arrayAbc = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        JSONObject objRen = new JSONObject();
        
        for(int i=0; i < dataHeader.length(); i++){
            JSONObject objCell = new JSONObject();
            String keyName = new String(); 
            String keytipo = new String(); 
            String keyParam = new String(); 
            
            keyName = dataHeader.getJSONObject(i).getString("n");
            keytipo = dataHeader.getJSONObject(i).getString("tipo");
            
            switch(keytipo){
                case "indCxc":
                    String IndCxc = new String(); 
                    IndCxc = data.getString(keyName);
                    String[] parts = IndCxc.split("|");
                    JSONObject objIndCxc = new JSONObject();                    
                    objIndCxc.put("p",parts[0]);
                    objIndCxc.put("c",parts[1]);
                    objIndCxc.put("b",parts[2]);
                    objIndCxc.put("a",parts[3]);
                    objIndCxc.put("s",parts[4]);
                    objIndCxc.put("n",parts[5]);
                    objIndCxc.put("m",parts[6]);
                    objIndCxc.put("cat",parts[7]);
                    objCell.put(keytipo, objIndCxc);
                    break;
                case "lnk":
                    keyParam = dataHeader.getJSONObject(i).getString("param");
                    objCell.put("p", data.get(keyParam));
                    objCell.put(keytipo, data.get(keyName));
                    break;
                case "ck":
                    objCell.put(keytipo, data.get(keyName));
                    break;
                case "v":
                    objCell.put(keytipo, data.get(keyName));
                    break;
//                case "a":                    
//                    keyParam = dataHeader.getJSONObject(i).getString("param");
//                    JSONArray objArray = new JSONArray();
//                    JSONObject objAction = new JSONObject();                    
//                    objAction.put("v","Editar");
//                    objAction.put("p",data.get(keyParam));
//                    objArray.put(objAction);
//                    JSONObject objAction2 = new JSONObject();                    
//                    objAction2.put("v","Eliminar");
//                    objAction2.put("p",data.get(keyParam));
//                    objArray.put(objAction2);
//                    objCell.put(keytipo, objArray);
//                    break;
                case "a":                    
                    keyParam = dataHeader.getJSONObject(i).getString("param");
                    JSONArray objmkArray = new JSONArray();
                    JSONObject objmkAction = new JSONObject();                    
                    objmkAction.put("v","Editar");
                    objmkAction.put("p",data.get(keyParam));
                    objmkArray.put(objmkAction);
                    JSONObject objmkAction2 = new JSONObject();                    
                    objmkAction2.put("v","Ver");
                    objmkAction2.put("p",data.get(keyParam));
                    objmkArray.put(objmkAction2);
                    JSONObject objmkAction3 = new JSONObject();                    
                    objmkAction3.put("v","Deshabilitar");
                    objmkAction3.put("p",data.get(keyParam));
                    objmkArray.put(objmkAction3);
                    objCell.put(keytipo, objmkArray);
                    break;
                case "ico":
                    keyParam = dataHeader.getJSONObject(i).getString("param");
                    JSONArray objArrayico = new JSONArray();
                    JSONObject objico = new JSONObject();
                    objico.put("n",data.getString(keyName));
                    objico.put("class",data.getString(keyParam));
                    objArrayico.put(objico);
                    objCell.put(keytipo, objArrayico);
                    break;
                case "img":
                    keyParam = dataHeader.getJSONObject(i).getString("param");
                    JSONArray objArrayimg = new JSONArray();
                    JSONObject objimg = new JSONObject();
                    objimg.put("src",data.getString(keyName));
                    objimg.put("class",data.getString(keyParam));
                    objArrayimg.put(objimg);
                    objCell.put(keytipo, objArrayimg);
                    break;
            }
            objRen.put(arrayAbc[i], objCell);
        }         
        return objRen;
    }
}