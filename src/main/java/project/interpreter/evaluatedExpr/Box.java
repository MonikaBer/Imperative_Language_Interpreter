package project.interpreter.evaluatedExpr;

public class Box {

    private Value value;

    public Box(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
