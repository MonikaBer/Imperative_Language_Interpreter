package project.program.content.statements;

import project.interpreter.INodeVisitor;

public class Empty extends Statement {

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
