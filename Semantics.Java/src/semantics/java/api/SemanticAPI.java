package semantics.java.api;

public class SemanticAPI {

	private static SemanticAPI staticapi;
	public static SemanticAPI getSemanticAPI() {
		if(staticapi==null)
			staticapi = new SemanticAPI();
		return staticapi;
	}
	
	public ISemanticParser getParser() {
		return new SemanticParserImpl();
	}

	private String serviceurl;
	public String getServiceUrl() {
		return serviceurl;
	}
	public void setServiceUrl(String url) {
		serviceurl = url;		
	}
}
