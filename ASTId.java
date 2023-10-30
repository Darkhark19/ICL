public class ASTId<X> implements ASTNode<IValue> {

    String id;

    public ASTId(String id) {
            this.id = id;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws Exception {
            return e.find(id);
    }

    public void compile(CodeBlock c, Environment<Coordinates> e ,Environment<IType> env) {
		try {
			Coordinates x = e.find(id);
			int level_shift = e.depth()-x.left;
			c.emit("aload_3");
			for(int i = 0; i< level_shift; i++ ) {	
				int frame = e.getFrame() - i;
				c.emit("getfield frame_"+frame+"/sl Lframe_"+ (frame -1)+";");
			}
			IType p = typecheck(env);
			c.emit("getfield frame_"+x.left+"/"+x.right+" "+p.toJasmin() );
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
    	
    }

	@Override
	public IType typecheck(Environment<IType> e) throws Exception {
		return e.find(id);
	}




}
