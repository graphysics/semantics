package semantics.java.api;

public interface ISemanticParser {

	ParseCase Parse(String words) throws Exception;
	public ParseCase TutorWSD(long caseid, String type, String senseids) throws Exception;
}
