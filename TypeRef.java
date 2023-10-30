public class TypeRef implements IType{
    
    IType t;
    public TypeRef(IType t){
        this.t = t;
    }

    public IType getType() {
        return t;
    }
    @Override
    public String toStr() {
        if(t instanceof TypeInt){
            return "ref_of_int";
        }else if(t instanceof TypeBool){
            return "ref_of_bool";
        }else if(t instanceof TypeRef){
            return "ref_of_"+((TypeRef)t).getType().toStr();
        }
        return "";
    }

    public String toJasmin(){
        if(t instanceof TypeInt){
            return "Lref_of_int;";
        }else if(t instanceof TypeBool){
            return "Lref_of_int;";
        }else if(t instanceof TypeRef){
            return "Lref_of_"+((TypeRef)t).getType().toStr()+";";
        }
        return "";
    }
}
