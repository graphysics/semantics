package semantics.java.api;

import java.util.List;
import java.util.Base64;

import semantics.java.utils.PKIUtils;
import semantics.java.utils.SecurityUtils;

public class SemanticAPI extends APIBase implements ISemantics {

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
	
	Aession session;
	
	@Override
	public Aession getSession() throws Exception {
		if(session==null)
			session = this.Hello();
		return session;
	}
	
	@Override
	public Aession Hello() throws Exception  {
		return (Aession)super.call("Hello");
	}

	@Override
	public Aoken Login(String clientname, String password) throws Exception {
		//byte[] nameEncrypted = PKIUtils.encrypt(getSession().getPubKey(), clientname.getBytes());
		byte[] md5 = SecurityUtils.getMD5(password);
		//byte[] md5Enctypted = PKIUtils.encrypt(getSession().getPubKey(), md5);
		String n = clientname;//Base64.getEncoder().encodeToString(nameEncrypted);
		String p = password;//Base64.getEncoder().encodeToString(md5Enctypted);
		return (Aoken)super.call("Login", new String[] {"clientname", "password"}, n, p);		
	}

	public List<KeyValuePair> GetCorpusSentences() throws Exception {
		return (List<KeyValuePair>)super.call("GetCorpusSentences");		
	}

	public Aesult RegisterWord(String newword, String category, String similar) throws Exception {
		return (Aesult)super.call("RegisterWord", new String[] {"newword", "category", "similar"}, newword, category, similar);
		
	}

	public List<KeyValuePair> GetCustomerDictionary() throws Exception {
		return (List<KeyValuePair>)super.call("GetCustomerDictionary");		
	}

	@Override
	public String GetSDKVersion() {
		return "0.1.1";
	}

}