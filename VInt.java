public class VInt implements IValue{

    int	v;	

    public VInt(int v0) { 
        v = v0; 
    }	

    int	getval() {
        return	v;
    }

    @Override
    public String toStr() {
        return Integer.toString(v);
    }

    
}
