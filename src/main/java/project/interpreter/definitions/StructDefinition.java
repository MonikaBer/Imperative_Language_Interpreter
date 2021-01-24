package project.interpreter.definitions;

import project.interpreter.evaluatedExpr.Box;

import java.util.HashMap;

public class StructDefinition {

    private final String name;
    private final HashMap<String, Box> map;

    public StructDefinition(String name, HashMap<String, Box> map) {
        this.name = name;
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Box> getMap() {
        return map;
    }
}
