import java.util.HashMap;
import java.util.Map;


public class Environment<X> {

    Environment<X> ancestor;
    Map<String,X> binding;
    private int frame;

    public Environment(Environment<X> ancestor,int counter){
        this.ancestor = ancestor;
        binding = new HashMap<>();
        frame = counter;
    }

    public Environment<X> beginScope(){
        return new Environment<>(this,frame +1);
    }
    public int getFrame() {
        return frame;
    }
    	//—	push	level	
    public Environment<X>	endScope(){//	-	pop	top	level	
        return ancestor;
    }

    public void assoc(String id, X val){
        binding.put(id,val);
    }
    int depth(){
        int counter = 0;
        Environment<X> aux = ancestor;
        while(aux.getFrame() > -1){
            counter++;
            aux = aux.ancestor;
        }
        return counter;
    }
    public X find(String id) throws Exception{
        X i = binding.get(id);
        if(i != null ){
           return i;
        }else if(ancestor == null)
            throw new Exception("Variable not found");
        else
            return ancestor.find(id);
    }
    
}


