package semantics.java.api;

import java.util.ArrayList;

/**
 * API Node
 * @author alexa
 *
 */
public class Aode{

	public String Label;

    public Aode(String value)
    {
        this.Label = value;
    }
    public Aode() {
    	
    }
    
    private Aode parent;
    public void putParent(Aode parent) {
    	this.parent = parent;
    }
    public Aode getParent() {
    	return this.parent;
    }

    /**
     * JSON传递后不能保障Parent/Children互相索引
     * @throws Exception 
     */
    public void rectifyHierarchy(int depth) throws Exception {
    	if(depth>20)
    		throw new Exception("Stack overflowed!");
    	if(this.Children==null)
    		return;
    	for(int i=0;i<this.Children.size();i++) {
    		Aode child = this.Children.get(i);
    		child.putParent(this);
    		child.rectifyHierarchy(depth+1);
    	}
    }
    public ArrayList<Aode> Children;
    
    @Override

    public String toString()
    {
        return "APINode '" + Label + "'";
    }
    
	public Aode copy() {
		// TODO Auto-generated method stub
		Aode aode = new Aode();
		aode.Label = this.Label;
		if(this.Children!=null) {
			for(Aode child : this.Children) {
				Aode childcopy = child.copy();
				aode.addChild(childcopy);
			}
		}
		return aode;
	}

	public void addChild(Aode child) {
		if(this.Children==null)
			this.Children = new ArrayList<Aode>();
		this.Children.add(child);
	}
	public void removeChild(Aode movedAode) {
		this.Children.remove(movedAode);
		if(this.Children.size()==0)
			this.Children = null;
	}
}