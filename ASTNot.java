public class ASTNot<X> implements ASTNode<IValue> {

    ASTNode<IValue> node;

    public ASTNot(ASTNode<IValue> n) {
            node = n;
    }

    public IValue eval(Environment<IValue> e) throws Exception {
        IValue bool = node.eval(e);
        if(bool instanceof VBool) {
            return new VBool(!((VBool)bool).getval());	
        }
    
        throw new Exception("TypeError: illegal arguments to ~ operator");
    }

    @Override
    public void compile(CodeBlock code, Environment<Coordinates> env , Environment<IType> e) throws Exception{    
        typecheck(e);
        String l1 = "L" + code.newLabel();
        String l2 = "L" + code.newLabel();
        node.compile(code,env,e);
        code.emit("ifeq " + l1);
        code.emit("sipush 0");
        code.emit( "goto " + l2);
        code.emit(l1+":");
        code.emit("sipush 1");
        code.emit(l2+":");
    }

    @Override
    public IType typecheck(Environment<IType> e) throws Exception {
        IType n = node.typecheck(e);
        if(n instanceof TypeBool){
                return new TypeBool();
        }
        throw new Exception("TypeChecker Error: illegal arguments to ~ operator");
    }

}