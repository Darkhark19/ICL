public class TypeInt implements IType{

    public TypeInt(){
        
    }

    @Override
    public String toStr() {
       return "int";
    }

    public String toJasmin() {
        return "I";
    }
}