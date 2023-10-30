public class ASTNum<X> implements ASTNode<IValue> {

        int val;

        public ASTNum(int n) {
                val = n;
        }

        public int eval() {
                return val;
        }

        public void compile(CodeBlock c) {
                c.emit("sipush" + val);
        }

        @Override
        public IValue eval(Environment<IValue> e) throws Exception {
                return new VInt(val);
        }

        @Override
        public void compile(CodeBlock code, Environment<Coordinates> env , Environment<IType> e) throws Exception{
                typecheck(e);
                code.emit("sipush " + val);
                
                
        }

        @Override
        public IType typecheck(Environment<IType> e) throws Exception {
                return new TypeInt();
        }

}
