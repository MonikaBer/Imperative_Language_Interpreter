package project.program.content.statements.expressions.others;

import project.program.content.statements.expressions.Expression;

import java.util.ArrayList;

public class FuncCall extends Expression {

    private final String funcName;
    private final ArrayList<Expression> params;
    private Expression result;

    public FuncCall(String funcName, ArrayList<Expression> params) {
        this.funcName = funcName;
        this.params = params;
    }

    public String getFuncName() {
        return funcName;
    }

    public ArrayList<Expression> getParams() {
        return params;
    }

    public Expression getResult() {
        return result;
    }

    public void setResult(Expression result) {
        this.result = result;
    }
}
