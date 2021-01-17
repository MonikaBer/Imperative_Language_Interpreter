package project.interpreter;

import project.exceptions.SemanticError;
import project.interpreter.definitions.Definition;
import project.interpreter.definitions.FuncDefinition;
import project.interpreter.definitions.StructDefinition;
import project.interpreter.definitions.Variable;
import project.program.content.statements.expressions.Expression;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Environment {

    private Map globals;
    private Stack<CallContext> callContexts;
    private Expression lastResult;

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

    public void makeCallContext() {
        CallContext callContext = new CallContext();
        callContexts.push(callContext);
    }

    public void makeBlockContext() {
        this.getLastCallContext().makeBlockContext();
    }

    public void makeVar(Variable var) {
        if (callContexts.isEmpty()) {
            //try to put var to globals
            if (globals.containsKey("var:" + var.getId().getName())) {
                String desc = "Declaration of variable named '" + var.getId().getName() + "' already exists in globals";
                throw new SemanticError(var.getId().getLineNr(), var.getId().getPositionAtLine(), desc);
            }
            globals.put("var:" + var.getId().getName(), var);
            return;
        }

        //put var to locals of recent block context of recent call context
        if (this.getLastCallContext().getLastBlockContext().getLocalsVars().containsKey(var.getId().getName())) {
            String desc = "Variable named '" + var.getId().getName() + "' already exists in locals of block context";
            throw new SemanticError(var.getId().getLineNr(), var.getId().getPositionAtLine(), desc);
        }

        this.getLastCallContext().getLastBlockContext().addLocalVar(var);
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

    public void deleteBlockContext() {
        if (!callContexts.isEmpty())
            this.getLastCallContext().removeLastBlockContext();
    }

    public void deleteCallContext() {
        if (!callContexts.isEmpty())
            callContexts.pop();
    }

    public CallContext getLastCallContext() {
        return callContexts.lastElement();
    }

    public Map getGlobals() {
        return globals;
    }

    public Stack<CallContext> getCallContexts() {
        return callContexts;
    }

    public Expression getLastResult() {
        return lastResult;
    }

    public void setLastResult(Expression lastResult) {
        this.lastResult = lastResult;
    }
}
