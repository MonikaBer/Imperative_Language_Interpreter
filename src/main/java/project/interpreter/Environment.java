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
        if (((HashMap)globals.get("variables")).containsKey(var.getId().getName())) {
            String desc = "Declaration of variable named '" + var.getId().getName() + "' already exists";
            throw new SemanticError(var.getId().getLineNr(), var.getId().getPositionAtLine(), desc);
        }

        ((HashMap)globals.get("variables")).put(var.getId().getName(), var);
    }

    public void makeFuncDefinition(FuncDefinition funcDefinition) {
        if (((HashMap)globals.get("funcDefinitions")).containsKey(funcDefinition.getId().getName())) {
            String desc = "Definition of function named '" + funcDefinition.getId().getName() + "' already exists";
            throw new SemanticError(funcDefinition.getId().getLineNr(), funcDefinition.getId().getPositionAtLine(), desc);
        }

        ((HashMap)globals.get("funcDefinitions")).put(funcDefinition.getId().getName(), funcDefinition);
    }

    public void makeStructDefinition(StructDefinition structDefinition) {
        if (((HashMap)globals.get("structDefinitions")).containsKey(structDefinition.getId().getName())) {
            String desc = "Definition of struct named '" + structDefinition.getId().getName() + "' already exists";
            throw new SemanticError(structDefinition.getId().getLineNr(), structDefinition.getId().getPositionAtLine(), desc);
        }

        ((HashMap)globals.get("structDefinitions")).put(structDefinition.getId().getName(), structDefinition);
    }

    public Map getGlobals() {
        return globals;
    }

    public Stack<CallContext> getCallContexts() {
        return callContexts;
    }
}
