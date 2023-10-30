public class ASTNew<X> implements ASTNode<IValue> {

    ASTNode<IValue> exp;

    public ASTNew(ASTNode<IValue> exp) {
        this.exp = exp;
    }
    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
        IValue v1 = exp.eval(e);
        return new VCell(v1);
    }

    @Override
    public void compile(CodeBlock code, Environment<Coordinates> env, Environment<IType> e) throws Exception{
        // TODO Auto-generated method stub
        IType t = exp.typecheck(e);
        code.addRef(t);
        code.emit("new ref_of_"+ t.toStr());
        code.emit("dup");
        code.emit("invokespecial ref_of_"+ t.toStr() + "/<init>()V");
        code.emit("dup");
        exp.compile(code,env,e);
        code.emit("putfield ref_of_"+ t.toStr() + "/v " + t.toJasmin());
        
    }
    @Override
    public IType typecheck(Environment<IType> e) throws Exception {
        IType t = exp.typecheck(e);
        if(t instanceof TypeInt || t instanceof TypeBool || t instanceof TypeRef || t instanceof TypeString){
            return new TypeRef(t);
        }
        throw new Exception("TypeChecker error: Illegal arguments to new operator");
    }
    
}
