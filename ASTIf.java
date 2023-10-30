public class ASTIf<X>  implements ASTNode<IValue>{

    ASTNode<IValue> cond, then, els;
    public ASTIf(ASTNode<IValue> cond, ASTNode<IValue> then, ASTNode<IValue> els) {
        this.cond = cond;
        this.then = then;
        this.els = els;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
        IValue v1 = cond.eval(e);
        if (v1 instanceof VBool){
            if (((VBool)v1).getval()){
                return then.eval(e);
            }else{
                return els.eval(e);
            }     
        }

        throw new Exception("Type error: Illegal arguments to if operator");
    }

    @Override
    public void compile(CodeBlock code, Environment<Coordinates> env , Environment<IType> e) throws Exception{
        String label1 = "L" + code.newLabel();
        String label2 = "L" + code.newLabel();
        cond.compile(code, env, e);
        code.emit("ifeq " + label1);
        then.compile(code, env, e);
        code.emit("goto " + label2);
        code.emit(label1+":");
        els.compile(code, env, e);
        code.emit(label2+":");

    }

    @Override
    public IType typecheck(Environment<IType> e) throws Exception {
        IType t1 =  cond.typecheck(e);
        if(t1 instanceof TypeBool){
            IType t2 = then.typecheck(e);
            IType t3 = els.typecheck(e);
            if(t2.equals(t3)){
                return t2;
            }
        }
        throw new Exception("TypeChecker error: Illegal arguments to if operator ");
    }
    
}
