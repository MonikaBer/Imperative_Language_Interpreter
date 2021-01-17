package project.program.content.statements.expressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.Statement;


public abstract class Expression extends Statement {

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
