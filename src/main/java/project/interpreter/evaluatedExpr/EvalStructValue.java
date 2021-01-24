package project.interpreter.evaluatedExpr;

import java.util.HashMap;

public class EvalStructValue extends Value {

    private String structType;
    private HashMap<String, Box> map;

    public EvalStructValue(String structType, HashMap<String, Box> map) {
        this.structType = structType;
        this.map = map;
    }

    public String getStructType() {
        return structType;
    }

    public HashMap<String, Box> getMap() {
        return map;
    }
}
