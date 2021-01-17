package project.interpreter;

import project.exceptions.SemanticError;
import project.interpreter.definitions.Definition;
import project.interpreter.definitions.FuncDefinition;
import project.interpreter.definitions.StructDefinition;
import project.interpreter.definitions.Variable;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Environment {

    private Map globals;
    private Stack<CallContext> callContexts;

    public Environment() {
        this.globals = new HashMap<String, Definition>();
        this.callContexts = new Stack<>();
    }

    public Variable getVar(String name) {
        Variable var;
        if ((var = callContexts.lastElement().getVar(name)) != null)
            return var;

        if (globals.containsKey("var:" + name))
            return (Variable) globals.get("var:" + name);

        return null;
    }

    public void makeCallContext(CallContext callContext) {
        callContexts.push(callContext);
    }

    public void makeBlockContext(BlockContext blockContext) {
        callContexts.lastElement().makeBlockContext(blockContext);
    }

    public void makeVar(Variable var) {
        if (globals.containsKey("var:" + var.getId().getName())) {
            String desc = "Declaration of variable named '" + var.getId().getName() + "' already exists";
            throw new SemanticError(var.getId().getLineNr(), var.getId().getPositionAtLine(), desc);
        }

        globals.put("var:" + var.getId().getName(), var);
    }

    public void makeFuncDefinition(FuncDefinition funcDefinition) {
        if (globals.containsKey("func:" + funcDefinition.getId().getName())) {
            String desc = "Definition of function named '" + funcDefinition.getId().getName() + "' already exists";
            throw new SemanticError(funcDefinition.getId().getLineNr(), funcDefinition.getId().getPositionAtLine(), desc);
        }

        globals.put("func:" + funcDefinition.getId().getName(), funcDefinition);
    }

    public void makeStructDefinition(StructDefinition structDefinition) {
        if (globals.containsKey("struct:" + structDefinition.getId().getName())) {
            String desc = "Definition of struct named '" + structDefinition.getId().getName() + "' already exists";
            throw new SemanticError(structDefinition.getId().getLineNr(), structDefinition.getId().getPositionAtLine(), desc);
        }

        globals.put("struct:" + structDefinition.getId().getName(), structDefinition);
    }

    public Map getGlobals() {
        return globals;
    }

    public Stack<CallContext> getCallContexts() {
        return callContexts;
    }
}
