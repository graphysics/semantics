package semantics.java.api;

public class SemanticParserImpl extends APIBase implements ISemanticParser {

	public ParseCase Parse(String words) throws Exception {
		Object result = super.call("Parse", new String[]{"words"},  words);
		return (ParseCase)result;
		
	}

	@Override
	public ParseCase TutorWSD(long caseid, String type, String senseids) throws Exception {
		return (ParseCase)super.call("TutorWSD", new String[] {"caseid", "type", "senseids"},
				caseid, type, senseids);		
	}

	@Override
	public ParseCase TutorPhrase(long caseid, Aode node) throws Exception {
		return (ParseCase)super.call("TutorPhrase", new String[] {"caseid", "node"},
				caseid, node);		
	}
}
