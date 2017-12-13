package semantics.java.api;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URLEncoder;
import java.util.Dictionary;
import java.util.HashMap;

public class APIBase {

	public Object call(String method, String[] parameterNames, Object... parameters) throws Exception {
		try {
			Method m = this.getMethod(method);
			Parameter[] methodparameters = m.getParameters();
			String parastring = this.formatUrl(parameterNames, parameters);
			return this.Request(m.getName(), parastring, m.getReturnType());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
	}
	
	private Object Request(String method, String url, Class<?> resClass) throws Exception {
		BaseRequest request = new BaseRequest();
		Object o = request.call(method, url, resClass);
		return o;
	}

	private String formatUrl(String[] methodparameters, Object[] parameters) {
		String url = "";
		for(int i =0;i<methodparameters.length;i++) {
			if(url.length()>0)
				url +="&";
			url+=methodparameters[i]+"="+this.getValueString(parameters[i]);
		}
		return url;
	}

	private String getValueString(Object param) {
		if(param.getClass().equals(String.class)) {
			try {
				return URLEncoder.encode(param.toString(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	Method getMethod(String name) {
		 if(methods == null) {
			 methods = new HashMap<String, Method>();
			 Class<?> cls = this.getClass();
			 Method[] all = cls.getMethods();
			 for(Method m : all) {
				 if(m.getDeclaringClass().equals(cls))
					 this.methods.put(m.getName(), m);
			 }
		 }
		 return methods.get(name);
	}
	
	HashMap<String, Method> methods;

}
