package semantics.java.api;

import java.util.List;

public interface ISemantics {

	public Aession getSession() throws Exception;
	public Aession Hello() throws Exception ; 
	public Aoken Login(String clientname, String password) throws Exception;
	public List<KeyValuePair> GetCorpusSentences() throws Exception;
	public Aesult RegisterWord(String newword, String category, String similar) throws Exception;
	public String GetSDKVersion();
}
