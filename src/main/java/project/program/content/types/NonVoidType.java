package project.program.content.types;


import project.interpreter.INodeVisitor;
import project.program.INode;

public abstract class NonVoidType extends Type implements INode {

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
