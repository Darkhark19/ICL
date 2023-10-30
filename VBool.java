public class VBool implements IValue {

    boolean	v;
    public VBool(boolean v0) { 
        v = v0; 
    }

    boolean getval() {
        return	v;
    }
    @Override
    public String toStr() {
        return Boolean.toString(v);
    }


    
}
