public class ASTDiv<X> implements ASTNode<IValue> {

        ASTNode<IValue> lft, rgt;

        public ASTDiv(ASTNode<IValue> l, ASTNode<IValue> r) {
                lft = l;
                rgt = r;
        }

        @Override
        public IValue eval(Environment<IValue> e) throws Exception {
                IValue v1 = lft.eval(e);	
                if(v1 instanceof VInt) {	
                                IValue v2 = rgt.eval(e);	
                                if(v2 instanceof VInt) {	
                                        return new VInt(((VInt)v1).getval() / ((VInt)v2).getval());	
                                }
                        }
                        throw new Exception("TypeError: illegal arguments to / operator");
        }

        @Override
        public void compile(CodeBlock code, Environment<Coordinates> env , Environment<IType> e) throws Exception {
                typecheck(e);
                lft.compile(code,env,e);
                rgt.compile(code,env,e);
                code.emit("idiv");
                
        }

        @Override
        public IType typecheck(Environment<IType> e) throws Exception {
                IType t1 = lft.typecheck(e);
                IType t2 = rgt.typecheck(e);
                if(t1 instanceof TypeInt && t2 instanceof TypeInt){
                        return new TypeInt();
                }
                throw new Exception("TypeChecker Error: illegal arguments to / operator");
        }
}