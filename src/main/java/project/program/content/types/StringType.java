package project.program.content.types;


import project.interpreter.INodeVisitor;

public class StringType extends NonVoidType {

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
