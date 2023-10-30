import java.util.List;

public class VClosure implements IValue {
    Environment<IValue> env;
    ASTNode body;
    List<String> params;
    public VClosure(Environment<IValue> env0, ASTNode body0, List<String> params0) {
        env = env0;
        body = body0;
        params = params0;
    }
    Environment<IValue> getEnv() {
        return env;
    }
    ASTNode getBody() {
        return body;
    }
    List<String> getParams() {
        return params;
    }
    @Override
    public String toStr() {
        return "Closure";
    }
}