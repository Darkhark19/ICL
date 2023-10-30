public class ASTWhile<X> implements ASTNode<IValue> {

    ASTNode<IValue> cond, body;   
    public ASTWhile(ASTNode<IValue> cond, ASTNode<IValue> body) {
        this.cond = cond;
        this.body = body;
    }


    @Override
    public void compile(CodeBlock code, Environment<Coordinates> env , Environment<IType> e) throws Exception {
        String label1 = "L" + code.newLabel();
        String label2 = "L" + code.newLabel();
        code.emit(label1+":");
        cond.compile(code,env,e);
        code.emit("ifeq " + label2);
        body.compile(code,env,e);
        //code.emit("pop");
        code.emit("goto " + label1);
        code.emit(label2+":");
            
    }
    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
        ASTNode<IValue> c = cond;
        IValue e1 = c.eval(e);
        if(e1 instanceof VBool){
            boolean b = ((VBool)e1).getval();
            Environment<IValue> newEnv = e.beginScope();
            while(b){
                body.eval(newEnv);
                e1 = c.eval(newEnv);
                b = ((VBool)e1).getval();
               // c = (ASTNode<IValue>) c.eval(e);
            }
            newEnv.endScope();
            return new VBool(false);
        }
        throw new Exception("Type error: Illegal arguments to while operator");
    }

    @Override
    public IType typecheck(Environment<IType> e) throws Exception {
        IType p = cond.typecheck(e);
        if(p instanceof TypeBool){
            body.typecheck(e);
            return new TypeBool();
        }
        throw new Exception("TypeChecker error: Illegal arguments to while operator");
    }
    
    
}
