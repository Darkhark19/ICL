public class ASTString<X> implements ASTNode<IValue> {

    private String s;

    public ASTString(String s){
        this.s = s;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
        return new VString(s);
    }

    @Override
    public void compile(CodeBlock code, Environment<Coordinates> env , Environment<IType> e) throws Exception{
        typecheck(e);
        code.emit("ldc " + s);
        
    }

    @Override
    public IType typecheck(Environment<IType> e) throws Exception {
        return new TypeString();
    }
}
