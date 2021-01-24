package project.interpreter.evaluatedExpr;

public class EvalBoolValue extends Value {

    private boolean value;

    public EvalBoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}
