package project.interpreter;

import project.exceptions.SemanticError;
import project.interpreter.evaluatedExpr.Box;
import project.interpreter.evaluatedExpr.Value;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockContext {

    private final HashMap<String, Box> locals;

    public BlockContext() {
        this.locals = new HashMap<>();
    }

    public void addLocal(String name, Value value, int lineNr, int posAtLine) {
        if (locals.containsKey(name)) {
            String desc = "Var '" + name + "' already exists in locals of block context";
            throw new SemanticError(lineNr, posAtLine, desc);
        }
        locals.put(name, new Box(value));
    }

    public Box getBox(ArrayList<String> name) {
        return Lib.pullBox(locals, name);
    }
}


