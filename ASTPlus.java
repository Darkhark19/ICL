public class ASTPlus<X> implements ASTNode<IValue> {

        ASTNode<IValue> lft, rgt;

        public ASTPlus(ASTNode<IValue> l, ASTNode<IValue> r) {
                lft = l;
                rgt = r;
        }


      /*  public void compile(CodeBlock c) {
                lhs.compile(c);
                rhs.compile(c);
                c.emit("iadd");
        }*/

        @Override
        public IValue eval(Environment<IValue> e) throws Exception {
                IValue v1 = lft.eval(e);
                if (v1 instanceof VInt){
                        IValue v2 = rgt.eval(e);
                        if (v2 instanceof VInt){
                                return new VInt(((VInt)v1).getval() + ((VInt)v2).getval());
                        }
                        else if(v2 instanceof VCell){
                                return new VInt(((VInt)v1).getval() + ((VInt)((VCell)v2).get()).getval());
                        }
                       
                }else if(v1 instanceof VString){
                        IValue v2 = rgt.eval(e);
                        if (v2 instanceof VString){
                                StringBuilder sb = new StringBuilder();
                                sb.append(((VString)v1).getval());
                                sb.append(((VString)v2).getval());
                                return new VString( sb.toString());
                        }
                }
                
                throw new Exception("Type error: Illegal arguments to + operator");
        }

        @Override
        public void compile(CodeBlock code, Environment<Coordinates> env , Environment<IType> e) throws Exception{
                IType t1 = typecheck(e);
                if(t1 instanceof TypeString){
                        code.emit("new java/lang/StringBuffer");
			code.emit("dup");
			code.emit("invokespecial java/lang/StringBuffer/<init>()V");    
                        lft.compile(code,env,e);
			code.emit("invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;");
			rgt.compile(code, env, e);
			code.emit("invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;");
			code.emit("invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;");
                }else{
                        lft.compile(code,env,e);
                        rgt.compile(code,env,e);
                        code.emit("iadd");
                }
        }


        @Override
        public IType typecheck(Environment<IType> e) throws Exception {
                IType v1 = lft.typecheck(e);
                if (v1 instanceof TypeInt){
                        IType v2 = rgt.typecheck(e);
                        if (v2 instanceof TypeInt){
                                return v1;
                        }
                }else if(v1 instanceof TypeString){
                        IType v2 = rgt.typecheck(e);
                        if(v2 instanceof TypeString){
                                return v1;
                                
                        }
                }
                throw new Exception("TypeChecker error: Illegal arguments types to + operator");

        }


}
