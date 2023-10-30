import java.io.PrintStream;

public class ICLCompiler {

    public static void main(String args[]) {
        Parser parser = new Parser(System.in);
        CodeBlock code;
        while (true) {
            try {
                code = new CodeBlock();
                ASTNode ast = parser.Start();
                Environment<IType> e = new Environment<IType>(null,-1);
                Environment<Coordinates> coord = new Environment<Coordinates>(null,-1);
                ast.compile(code,coord,e);
                //String fileName = args[0];
                //PrintStream outfile = new PrintStream(fileName);
                code.dump();
             
            } catch (Exception e) {
                e.printStackTrace();
                parser.ReInit(System.in);
            }
        }
    }

}
