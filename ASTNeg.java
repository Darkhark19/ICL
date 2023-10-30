public class ASTNeg<X> implements ASTNode<IValue> {

        ASTNode<IValue> node;

        public ASTNeg(ASTNode<IValue> n) {
                node = n;
        }

        public IValue eval(Environment<IValue> e) throws Exception {
                if(node instanceof VInt) {	
                        return new VInt(-((VInt)node).getval());	
                }
        
                throw new Exception("TypeError: illegal arguments to - operator");
        }

        @Override
        public void compile(CodeBlock code, Environment<Coordinates> env , Environment<IType> e) throws Exception {
                typecheck(e);
                node.compile(code,env,e);
                
                code.emit("ineg");  
        }

        @Override
        public IType typecheck(Environment<IType> e) throws Exception {
                IType n = node.typecheck(e);
                if(n instanceof TypeInt){
                        return new TypeInt();
                }
                throw new Exception("TypeChecker Error: illegal arguments to - operator");
        }

}
