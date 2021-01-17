package project.program.content.statements;


import project.interpreter.INodeVisitor;

public class VoidReturn extends Statement {

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
