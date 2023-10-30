public class VString implements IValue{

    String v;

    public VString(String v0) {
        v = v0;
    }

    String getval() {
        return v;
    }
    
    @Override
    public String toStr() {
        return v;
    }
    
}
