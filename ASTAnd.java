public class ASTAnd<X> implements ASTNode<IValue> {

    ASTNode<IValue> left, right;
    public ASTAnd(ASTNode<IValue> left, ASTNode<IValue> right) {
        this.left = left;
        this.right = right;
    }
   
    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
        IValue v1 = left.eval(e);
        if (v1 instanceof VBool){
            IValue v2 = right.eval(e);
            if (v2 instanceof VBool){
                    return new VBool(((VBool)v1).getval() && ((VBool)v2).getval());
            }
               
        }

        throw new Exception("Type error: Illegal arguments to && operator");
    }

    @Override
    public void compile(CodeBlock code, Environment<Coordinates> e, Environment<IType> env) throws Exception {
        typecheck(env);
        left.compile(code, e,env);
        right.compile(code, e,env);
        code.emit("iand");
        
    }

    @Override
    public IType typecheck(Environment<IType> e) throws Exception {
        IType t1 = left.typecheck(e);
        IType t2 = right.typecheck(e);
        if (t1 instanceof TypeBool && t2 instanceof TypeBool){
            return new TypeBool();
        }
        throw new Exception("TypeChecker error: Illegal arguments to && operator");
    }
    
}
