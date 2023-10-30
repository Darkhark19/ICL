public class ASTAssign<X> implements ASTNode<IValue>{

    ASTNode<IValue> left , right;

    public ASTAssign(ASTNode<IValue> left, ASTNode<IValue> right) {
      this.right = right;
      this.left = left;
    }
    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
        IValue v1 = left.eval(e);
        if (v1 instanceof VCell)	{	
		    IValue v2 = right.eval(e);	
			((VCell)v1).set(v2);	
			return v1;
        }
        throw new Exception("TypeError: illegal arguments to := operator");
    }

    @Override
    public void compile(CodeBlock code, Environment<Coordinates> en,Environment<IType> e) throws Exception {
        left.compile(code, en,e);
        right.compile(code, en,e);
        IType t1 = right.typecheck(e);
        code.emit("putfield ref_of_" + t1.toStr() + "/v "+ t1.toJasmin());
    }
    @Override
    public IType typecheck(Environment<IType> e) throws Exception {
        IType t1 = left.typecheck(e);
        IType t2 = right.typecheck(e);
        IType t3 = new TypeRef(t2);
        if(t1 == t3){
            return t2;
        }
        throw new Exception("TypeChecker Error: illegal arguments to := operator");
    }
    
}
