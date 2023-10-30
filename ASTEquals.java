public class ASTEquals<X> implements ASTNode<IValue> {

    ASTNode<IValue> left,right;
    boolean equals;
    public ASTEquals(ASTNode<IValue> left, ASTNode<IValue> right,boolean equals) {
        this.left = left;
        this.right = right;
        this.equals = equals;
    }
    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
        IValue v1 = left.eval(e);
        if (v1 instanceof VBool){
                IValue v2 = right.eval(e);
                if (v2 instanceof VBool){
                    if(equals){
                        return new VBool(((VBool)v1).getval() == ((VBool)v2).getval());
                    }else{
                        return new VBool(((VBool)v1).getval() != ((VBool)v2).getval());
                    }
                }
               
        }else if(v1 instanceof VInt){
            IValue v2 = right.eval(e);
                if (v2 instanceof VInt){
                    if (equals){
                        return new VBool(((VInt)v1).getval() == ((VInt)v2).getval());
                    }else{
                        return new VBool(((VInt)v1).getval() != ((VInt)v2).getval());
                    }
                }
        }
        throw new Exception("Type error: Illegal arguments to == ~= operator");
    
    }

    @Override
    public void compile(CodeBlock code, Environment<Coordinates> env , Environment<IType> e) throws Exception{
        typecheck(e);
        String label1 = "L" + code.newLabel();
        String label2 = "L" + code.newLabel();
        left.compile(code, env,e);
        right.compile(code, env,e);
        code.emit("isub");
        if(equals){
            code.emit("ifeq " + label1);
        }else{
            code.emit("ifne " + label2);
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
          
        }else if(v1 instanceof TypeBool && v2 instanceof TypeBool){
            return new TypeBool();
        }
        throw new Exception("TypeChecker error: Illegal arguments types to == ~= operator");
    }
    
}
