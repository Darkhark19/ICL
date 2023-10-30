public class ASTDeref<X> implements ASTNode<IValue> {

    ASTNode<IValue> node;
    public ASTDeref(ASTNode<IValue> node) {
        this.node = node;
    }
    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
        IValue n = node.eval(e);
        if (n instanceof VCell) {
            IValue v = ((VCell)n).get();
            return v;
        }
        throw new Exception("TypeError: illegal arguments to ! operator");
    }
    @Override
    public void compile(CodeBlock code,  Environment<Coordinates> env ,Environment<IType> e) throws Exception {
        // TODO Auto-generated method stub
        IType t = typecheck(e);
        node.compile(code, env,e);
        
    
        String type = t.toStr();
        String getField = "getfield ";
     
        String typeJ = null;
        if(t instanceof TypeInt){
            typeJ = "I";
        }else if(t instanceof TypeBool){
            typeJ = "Z";
        }else if(t instanceof TypeRef){ 
            IType t1= ((TypeRef)t).getType();
            if(t1 instanceof TypeInt){
                typeJ = "I";
            }else if(t1 instanceof TypeBool){
                typeJ = "Z";
            }else{
                typeJ =  ((TypeRef)t).getType().toJasmin() + ";";
            }
           
        }
        String result = getField +"ref_of_"+ type + "/v " + typeJ;
        code.emit(result);
    }
    @Override
    public IType typecheck(Environment<IType> e) throws Exception {
        IType t = node.typecheck(e);
        if(t instanceof TypeRef){
            return ((TypeRef)t).getType();
        }
        throw new Exception("TypeError: illegal arguments to ! operator");
    }
    
}
