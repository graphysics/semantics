package semantics.java.api;

public interface ISemanticParser {

	ParseCase Parse(String words) throws Exception;
	ParseCase TutorWSD(long caseid, String type, String senseids) throws Exception;
	ParseCase TutorPhrase(long caseId, Aode node) throws Exception;
}
