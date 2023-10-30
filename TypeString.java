public class TypeString implements IType{

    public TypeString() {
    }
    @Override
    public String toStr() {
        return "String";
    }
    public String toJasmin(){
        return "Ljava/lang/String;";
    }
    
}
