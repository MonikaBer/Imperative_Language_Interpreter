package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.Expression;

import java.util.ArrayList;

public final class FuncCall extends SimpleExpression {

    private final Identifier funcName;
    private final ArrayList<Expression> params;

    public FuncCall(Identifier funcName, ArrayList<Expression> params, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.funcName = funcName;
        this.params = params;
    }

    public Identifier getFuncName() {
        return funcName;
    }

    public ArrayList<Expression> getParams() {
        return params;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
