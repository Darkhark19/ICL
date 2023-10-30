public class ASTOr<X> implements ASTNode<IValue>{

    ASTNode<IValue> left,right;
    public ASTOr(ASTNode<IValue> left, ASTNode<IValue> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
        IValue v1 = left.eval(e);
        if (v1 instanceof VBool){
                IValue v2 = right.eval(e);
                if (v2 instanceof VBool){
                        return new VBool(((VBool)v1).getval() || ((VBool)v2).getval());
                }  
        }

        throw new Exception("Type error: Illegal arguments to || operator");
    }

    @Override
    public void compile(CodeBlock code, Environment<Coordinates> env , Environment<IType> e) throws Exception{
        typecheck(e);
        left.compile(code,env,e);
        right.compile(code,env,e);
        code.emit("ior");
    }

    @Override
    public IType typecheck(Environment<IType> e) throws Exception {
        IType v1 = left.typecheck(e);
        IType v2 = right.typecheck(e);
        if( v1 instanceof TypeBool && v2 instanceof TypeBool){
            return new TypeBool();
        }
        throw new Exception("TypeChecker error: Illegal arguments types to || operator");
    }
    
}
