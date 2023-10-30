public class ASTGreater<X> implements ASTNode<IValue> {

    ASTNode<IValue> left,right;
    boolean equals, greater;
    public ASTGreater(ASTNode<IValue> left, ASTNode<IValue> right, boolean equals, boolean greater) {
        this.left = left;
        this.right = right;
        this.equals = equals;
        this.greater = greater;
    }
    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
        IValue v1 = left.eval(e);
        if(v1 instanceof VInt){
            IValue v2 = right.eval(e);
             if (v2 instanceof VInt){
                if (equals && greater){
                    return new VBool(((VInt)v1).getval() >= ((VInt)v2).getval());
                } else if(greater){
                    return new VBool(((VInt)v1).getval() > ((VInt)v2).getval());
                } else if(equals){
                    return new VBool(((VInt)v1).getval() <= ((VInt)v2).getval());
                } else {
                    return new VBool(((VInt)v1).getval() < ((VInt)v2).getval());
                }
            }
        }
    
        String message = "Type error: Illegal arguments to relops <= < >= > operator";
        throw new Exception(message);
    
    }
    @Override
    public void compile(CodeBlock code, Environment<Coordinates> env, Environment<IType> e) throws Exception {
        typecheck(e);
        String label1 = "L" + code.newLabel();
        String label2 = "L" + code.newLabel();
        left.compile(code, env,e);
        right.compile(code, env,e);
        code.emit("isub");
        if(equals && greater){
            code.emit("ifge " + label1);
        }else if(greater){
            code.emit("ifgt " + label1);  
        }else if(equals){
            code.emit("ifle " + label1);
        }else{
            code.emit("iflt " + label1);
        }
        code.emit("sipush 0");
        code.emit("goto " + label2);
        code.emit(label1+": ");
        code.emit("sipush 1");
        code.emit(label2+": ");
        
    }
    @Override
    public IType typecheck(Environment<IType> e) throws Exception {
        IType v1 = left.typecheck(e);
        IType v2 = right.typecheck(e);
        if(v1 instanceof TypeInt && v2 instanceof TypeInt){
            return new TypeBool();
        } else if(v1 instanceof TypeBool && v2 instanceof TypeBool){
            return new TypeBool();
        }
        throw new Exception("TypeChecker error: Illegal arguments types to relops <= < >= > operator");
    }
    
}
