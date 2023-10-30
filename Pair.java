public class Pair<X> {
    
    String id;
    ASTNode<IValue> node;

    public Pair(String id, ASTNode<IValue> node){

        this.id = id;
        this.node = node;
    }
    public String getId(){
        return id;

    }
    public ASTNode<IValue> getNode(){
        return node;
    }
}
