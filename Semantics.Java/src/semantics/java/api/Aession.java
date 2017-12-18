package semantics.java.api;

import java.security.Key;

public class Aession {
	public String A;
	private Aoken token;
	public Aoken getToken() {
		return token;
	}
	public String sessionId;
	public String pubKey;
	public Key getPubKey() {
		return null;
	}
	public void assignToken(Aoken t) {
		token = t;		
	}

}
