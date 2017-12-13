package semantics.java.api;

public class SemanticParserImpl extends APIBase implements ISemanticParser {

	@Override
	public APINode Parse(String words) throws Exception {
		Object result = super.call("Parse", new String[]{"words"},  words);
		return (APINode)result;
		
	}

}
