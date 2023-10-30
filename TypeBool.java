public class TypeBool implements IType{
    
    public TypeBool(){
        
    }

    @Override
    public String toStr() {
        return "bool";
    }

    public String toJasmin() {
        return "Z";
    }
}
