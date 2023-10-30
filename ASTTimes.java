public class ASTTimes<X> implements ASTNode<IValue> {

        ASTNode<IValue> lhs, rhs;

        public ASTTimes(ASTNode<IValue> l, ASTNode<IValue> r) {
                lhs = l;
                rhs = r;
        }
        @Override
        public IValue eval(Environment<IValue> e) throws Exception {
                IValue v1 = lhs.eval(e);
                if (v1 instanceof VInt){
                        IValue v2 = rhs.eval(e);
                        if (v2 instanceof VInt){
                                return new VInt(((VInt)v1).getval() * ((VInt)v2).getval());
                        }
                       
                }
                
                throw new Exception("Type error: Illegal arguments to * operator");
        }

        @Override
        public void compile(CodeBlock code, Environment<Coordinates> env , Environment<IType> e) throws Exception{
                typecheck(e);
                lhs.compile(code,env, e);
                rhs.compile(code, env,e);
                code.emit("imul");
                
        }
        @Override
        public IType typecheck(Environment<IType> e) throws Exception {
                IType v1 = lhs.typecheck(e);
                if (v1 instanceof TypeInt){
                        IType v2 = rhs.typecheck(e);
                        if (v2 instanceof TypeInt){
                                return v1;
                        }
                }
                throw new Exception("TypeChecker error: Illegal arguments types to * operator");
        }
}
