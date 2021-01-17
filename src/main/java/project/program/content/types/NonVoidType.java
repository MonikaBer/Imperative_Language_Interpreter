package project.program.content.types;


import project.interpreter.INodeVisitor;

public abstract class NonVoidType extends Type {

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
