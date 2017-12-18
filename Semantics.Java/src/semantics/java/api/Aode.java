package semantics.java.api;

import java.util.ArrayList;

/**
 * API Node
 * @author alexa
 *
 */
public class Aode{
	public String A;

	public String Label;

    public Aode(String value)
    {
        this.Label = value;
    }
    public Aode() {
    	
    }

    public ArrayList<Aode> Children;
    
    @Override

    public String toString()
    {
        return "APINode '" + Label + "'";
    }
}
