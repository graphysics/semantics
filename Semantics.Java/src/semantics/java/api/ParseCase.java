package semantics.java.api;

import java.util.ArrayList;

public class ParseCase{
	public boolean IsSuccess;
	public Aode Result;
    public ArrayList<Aode> AmbiguousResults;
    public ArrayList<WordSenseAmbiguity> WordSenseAmbiguities;
	public Failure Failure;
	public long CaseId;

}
