public class ASTPrintln<X> implements ASTNode<IValue>{

    ASTNode<IValue> node;
    public ASTPrintln(ASTNode<IValue> node) {
        this.node = node;
    }

    
    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
        IValue v1 = node.eval(e);
        System.out.println(v1.toStr());
        return null;
    }

    @Override
    public void compile(CodeBlock code, Environment<Coordinates> env , Environment<IType> e) throws Exception{
       
        IType type = typecheck(e);;
        code.emit("getstatic java/lang/System/out Ljava/io/PrintStream;");
		node.compile(code,env,e);
        if(type instanceof TypeString){

        }else{
     
		    code.emit("invokestatic java/lang/String/valueOf(I)Ljava/lang/String;");
		
        }
        code.emit("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
    }


    @Override
    public IType typecheck(Environment<IType> e) throws Exception {
        return node.typecheck(e);
    }
}