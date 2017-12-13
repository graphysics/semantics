/**
 * @copyright Semantics Graphysics 2006~
 */
package semantics.java.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 2016-09-12
 *
 */
public class BaseRequest {

	private ObjectMapper mapper = new ObjectMapper();
	
	public <T extends BaseResponse> T send(String adminUrl, Class<T> resClass) throws Exception {
		try {
			InputStream stream = post(adminUrl, getMethod(), getParams());
			T res = mapper.readValue(stream, resClass);
			return res;
		} catch (Exception e) {
			String msg = e.getMessage();
			throw e;
//			try {
//				T res = resClass.newInstance();
//				res.msg = e.getMessage();
//				return (T)res;
//			} catch (Exception e1) {
//			}
		}
//		return null;
	}
	
	protected String getMethod() {
		String name = getClass().getSimpleName();
		String method = name.substring(0, name.length()-3);
		char[] buffer = method.toCharArray();
		buffer[0] = Character.toLowerCase(method.charAt(0));
		return new String(buffer);
	}
	
	protected String getParams() {
		String params = "";
		Field[] fields = getClass().getFields();
		for (Field field : fields) {
			try {
				Object value = field.get(this);
				if (value == null) continue;
				
				if (!params.isEmpty()) params += "&";
				params += field.getName() + "=";
				if (field.getType().equals(String.class)) {
					params += URLEncoder.encode(value.toString(), "utf-8");
				}
				else {
					params += value.toString();
				}
			} catch (Exception e) {
				
			}
		}
		return params;
	}
	
	protected InputStream post(String serviceUrl, String method, String params) throws Exception {
        PrintWriter out = null;
        try {
            URL url = new URL(serviceUrl + "semanticapi/" + method);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.write(params);
            out.flush();
            return conn.getInputStream();
        } catch (Exception e) {
        	throw e;
        } finally {
        	if (out != null)  out.close(); 
        }
    }

	public Object call(String method, String params, Class<?> resClass) throws Exception {
		InputStream stream = null;
		try {
			SemanticAPI api =SemanticAPI.getSemanticAPI(); 
			stream = post(api.getServiceUrl(), method, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		Object res = null;
		try {
			res = mapper.readValue(stream, resClass);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	} 
}
