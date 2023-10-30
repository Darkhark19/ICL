/** ID lister. */
public class ICLInterpreter {

  public static void main(String args[]) {
    Parser parser = new Parser(System.in);
    ASTNode<IValue> exp;

    while (true) {
      try {
        exp = parser.Start();
        Environment<IValue> e = new Environment<>(null,-1);
        IValue c = (IValue) exp.eval(e);
        if(c == null) 
          System.out.println("void");
        else 
          System.out.println(((IValue)exp.eval(e)).toStr());
         
          
      } catch (Exception e) {
        e.printStackTrace();
        parser.ReInit(System.in);
      }
    }
  }

}
