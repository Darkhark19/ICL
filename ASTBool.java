public class ASTBool<X> implements ASTNode<IValue>{

    boolean val;

    public ASTBool(boolean b) {
            val = b;
    }
    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
            return new VBool(val);
    }

    @Override
    public void compile(CodeBlock code,Environment<Coordinates> env, Environment<IType> e) throws Exception{
        typecheck(e);
        if(val){
            code.emit("sipush 1");
        }else{
            code.emit("sipush 0");
        }
        
    }
    @Override
    public IType typecheck(Environment<IType> e) throws Exception {
        return new TypeBool();
    }
    
}
