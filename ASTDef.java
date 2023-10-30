import java.util.List;

public class ASTDef<X> implements ASTNode<IValue> {

    List<Pair<X>> bindings;
    ASTNode<IValue>	body;

    public ASTDef(List<Pair<X>> map, ASTNode<IValue> body) {
            bindings = map;
            this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws Exception	{	
        //	def	x1	=	E1	…	xn	=	En	in	Body	end
        env	= env.beginScope();	
        //	for	each	xi	=	Ei	{
        for(Pair<X> e : bindings)	{
            env.assoc(e.getId(), e.getNode().eval(env));
        }	
        IValue v = body.eval(env);	
        env.endScope();	
        return v;
    }

    public void compile(CodeBlock code, Environment<Coordinates> e ,Environment<IType> env) throws Exception {
      
        //	def	x1	=	E1	…	xn	=	En	in	Body	end	   
        env	= env.beginScope();	
        e = e.beginScope(); 
        Environment<IType> ancestor = env.ancestor;
        code.addFrame(env.getFrame(),ancestor.getFrame());
      
        //	generate	code	for	frame_i	class	def	
        //	generate	code	for	frame	init	and	link	into	RT	env	
        //	for	each	xi	=	Ei
        
        code.emit("new frame_"+e.getFrame());
        code.emit("dup");
        code.emit("invokespecial frame_"+e.getFrame()+"/<init>()V");
       
        Environment<Coordinates> anc = e.ancestor;
        if(anc.getFrame() > -1){
            code.emit("dup");
            code.emit("aload_3");
            code.emit("putfield frame_"+e.getFrame()+"/sl Lframe_"+anc.getFrame()+";");
        }
        code.emit("astore_3");
        
        int i = 0;
        for(Pair<X> entry: bindings)	{
            String id = entry.getId();
            ASTNode<IValue> node = entry.getNode();
            code.emit("aload_3");
            node.compile(code,e,env);
            IType type = node.typecheck(env);
            code.emit("putfield frame_"+e.getFrame() + "/v"+i + " " + type.toJasmin() );
            Coordinates coord = new Coordinates(e.depth(), "v"+i);
            e.assoc(id , coord);
            env.assoc(id, type);
            code.addBinding(e.getFrame(),i,type);
          
            i++;

        }
        //		Body.compile(c,	env);				
       //	generate	code	for	frame	pop	off	
        body.compile(code, e,env);
        code.emit("aload_3");
        Environment<Coordinates> a = e.ancestor;
        if(a.getFrame() > -1){
            code.emit("getfield frame_"+e.getFrame()+"/sl Lframe_"+a.getFrame()+";");
        }else{
            code.emit("getfield frame_"+e.getFrame()+"/sl Ljava/lang/Object;");
        }
        code.emit("astore_3");
        env = env.endScope();	
        e = e.endScope();
    }

    @Override
    public IType typecheck(Environment<IType> env) throws Exception {
        
        Environment<IType> E = env.beginScope();	
        for(Pair<X> e : bindings)	{
            E.assoc(e.getId(), e.node.typecheck(env));
        }	
        IType t = body.typecheck(env);	
        env.endScope();	
        return t;
    }
   
}
