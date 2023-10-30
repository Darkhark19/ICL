public class ASTFun<X> implements ASTNode<IValue>{

    private String id;
    private ASTNode<IValue> body;
    public ASTFun(String id, ASTNode<IValue> body) {
        this.id = id;
        this.body = body;
    }
    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
        return new VClosure(e,body,null);
    }
    @Override
    public void compile(CodeBlock code, Environment<Coordinates> env , Environment<IType> e) {
        
    }
    @Override
    public IType typecheck(Environment<IType> e) throws Exception {
        return new TypeString();
    }
}

