package project.interpreter.evaluatedExpr;

public class EvalStringValue extends Value {

    private String value;

    public EvalStringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
