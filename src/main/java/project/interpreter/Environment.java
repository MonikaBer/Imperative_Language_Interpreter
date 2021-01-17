package project.interpreter;

import project.exceptions.SemanticError;
import project.interpreter.declaredObjects.FuncDefinition;
import project.interpreter.declaredObjects.StructDefinition;
import project.interpreter.declaredObjects.Variable;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Environment {

    private Map globals;
    private Stack<CallContext> callContexts;

    public Environment() {
        this.globals = new HashMap<String, Map>();
        this.globals.put("variables", new HashMap<String, Variable>());
        this.globals.put("funcDefinitions", new HashMap<String, FuncDefinition>());
        this.globals.put("structDefinitions", new HashMap<String, StructDefinition>());
        this.callContexts = new Stack<>();
    }

    public Variable getVar(String name) {
        Variable var;
        if ((var = callContexts.lastElement().getVar(name)) != null)
            return var;

        if (((HashMap)globals.get("variables")).containsKey(name))
            return (Variable) ((HashMap)globals.get("variables")).get(name);

        return null;
    }

    public void makeCallContext(CallContext callContext) {
        callContexts.push(callContext);
    }

    public void makeBlockContext(BlockContext blockContext) {
        callContexts.lastElement().makeBlockContext(blockContext);
    }

    public void makeVar(Variable var) {
        if (((HashMap)globals.get("variables")).containsKey(var.getName()))
            throw new SemanticError("Declaration of variable named '" + var.getName() + "' already exists");

        ((HashMap)globals.get("variables")).put(var.getName(), var);
    }

    public void makeFuncDefinition(FuncDefinition funcDefinition) {
        if (((HashMap)globals.get("funcDefinitions")).containsKey(funcDefinition.getName()))
            throw new SemanticError("Definition of function named '" + funcDefinition.getName() + "' already exists");

        ((HashMap)globals.get("funcDefinitions")).put(funcDefinition.getName(), funcDefinition);
    }

    public void makeStructDefinition(StructDefinition structDefinition) {
        if (((HashMap)globals.get("structDefinitions")).containsKey(structDefinition.getName()))
            throw new SemanticError("Definition of struct named '" + structDefinition.getName() + "' already exists");

        ((HashMap)globals.get("structDefinitions")).put(structDefinition.getName(), structDefinition);
    }

    public Map getGlobals() {
        return globals;
    }

    public Stack<CallContext> getCallContexts() {
        return callContexts;
    }
}
