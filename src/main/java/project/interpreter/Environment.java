package project.interpreter;

import project.interpreter.declaredObjects.DeclaredObject;
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
        this.globals = new HashMap<String, DeclaredObject>();
        this.callContexts = new Stack<>();
    }

    public Variable getVar(String name) {
        Variable var;
        if ((var = callContexts.lastElement().getVar(name)) != null)
            return var;

        if (globals.containsKey(name))
            return (Variable) globals.get(name);

        return null;
    }

    public void makeCallContext(CallContext callContext) {
        callContexts.push(callContext);
    }

    public void makeBlockContext(BlockContext blockContext) {
        callContexts.lastElement().makeBlockContext(blockContext);
    }
//
//    public void makeVar(Variable var) {
//
//    }
//
//    public void makeFuncDefinition(FuncDefinition funcDefinition) {
//
//
//    }
//
//    public void makeStructDefinition(StructDefinition structDefinition) {
//
//    }

    public Map getGlobals() {
        return globals;
    }

    public Stack<CallContext> getCallContexts() {
        return callContexts;
    }
}
