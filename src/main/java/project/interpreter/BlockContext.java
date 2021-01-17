package project.interpreter;

import project.interpreter.declaredObjects.Variable;

import java.util.HashMap;
import java.util.Map;

public class BlockContext {

    private Map localsVars;

    public BlockContext() {
        this.localsVars = new HashMap<String, Variable>();
    }

    public void addLocalVar(Variable var) {
        localsVars.put(var.getName(), var);
    }

    public Variable getVar(String name) {
        if (localsVars.containsKey(name))
            return (Variable)localsVars.get(name);
        return null;
    }

    public Map getLocalsVars() {
        return localsVars;
    }
}
