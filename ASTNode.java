public interface ASTNode<X> {

    X eval(Environment<X> e) throws Exception;
    void compile(CodeBlock code, Environment<Coordinates> e,Environment<IType> env) throws Exception;
    IType typecheck(Environment<IType> e) throws Exception;
}


