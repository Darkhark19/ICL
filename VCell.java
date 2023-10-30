public class VCell implements IValue	{	

    IValue v;

    public VCell(IValue v0)	{
        v =	v0;	
    }	
    IValue get() {
        return v;
    }	
    void set(IValue	v0)	{
        v=v0;
    }
    @Override
    public String toStr() {
        return v.toStr();
    }
    
}
    