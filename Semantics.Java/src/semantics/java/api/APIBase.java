package semantics.java.api;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URLEncoder;
import java.util.Dictionary;
import java.util.HashMap;

public class APIBase {

	public Object call(String method) throws Exception {
		return this.call(method, null);
	}

	public Object call(String method, String[] parameterNames, Object... parameterValues) throws Exception {
		Method m = this.getMethod(method);
		String parastring = null;
		Parameter[] methodparameters = m.getParameters();
		parastring = this.makeTokenString(method) + this.formatParameterString(parameterNames, parameterValues);
		return this.Request(m.getName(), parastring, m.getReturnType());
	}

	private String makeTokenString(String method) throws Exception {
		String url = "__token__=";
		Aoken token = null;
		if (!method.equals("Hello") && !method.equals("Login")) {
			token = SemanticAPI.getSemanticAPI().getSession().getToken();
			if (token == null)
				url += "null";
			else
				url += token.token;
		}
		return url;
	}

	private Object Request(String method, String url, Class<?> resClass) throws Exception {
		BaseRequest request = new BaseRequest();
		Object o = request.call(method, url, resClass);
		return o;
	}

	private String formatParameterString(String[] parameterNames, Object[] parameterValues) throws Exception {
		String url = "";
		if (parameterNames != null) {
			for (int i = 0; i < parameterNames.length; i++) {
				url += "&" + parameterNames[i] + "=" + this.getValueString(parameterValues[i]);
			}
		}
		return url;
	}

	private String getValueString(Object param) {
		if (param.getClass().equals(String.class)) {
			try {
				return URLEncoder.encode(param.toString(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return param.toString();
	}

	Method getMethod(String name) {
		if (methods == null) {
			methods = new HashMap<String, Method>();
			Class<?> cls = this.getClass();
			Method[] all = cls.getMethods();
			for (Method m : all) {
				if (m.getDeclaringClass().equals(cls))
					this.methods.put(m.getName(), m);
			}
		}
		return methods.get(name);
	}

	HashMap<String, Method> methods;

}
