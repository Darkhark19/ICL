public class ASTSenquence<X> implements ASTNode<IValue>{

    ASTNode<IValue> left, right;
    public ASTSenquence(ASTNode<IValue> left, ASTNode<IValue> right) {
        this.left= left;
        this.right = right;
    }

    
    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
        left.eval(e);
        return right.eval(e);
    }

    @Override
    public void compile(CodeBlock code, Environment<Coordinates> env , Environment<IType> e) throws Exception{
        // TODO Auto-generated method stub
        left.compile(code,env,e);
        right.compile(code,env,e);
    }


    @Override
    public IType typecheck(Environment<IType> e) throws Exception {
        left.typecheck(e);
        return right.typecheck(e);
    }
}