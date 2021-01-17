package project.program.content.types;


import project.interpreter.INodeVisitor;

public class VoidType extends Type {

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
