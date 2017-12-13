package semantics.java.api;

import java.util.ArrayList;

public class APINode {

	public String Label;

    public APINode(String value)
    {
        this.Label = value;
    }
    public APINode() {
    	
    }

    public ArrayList<APINode> Children;
    
    @Override

    public String toString()
    {
        return "APINode '" + Label + "'";
    }
}
