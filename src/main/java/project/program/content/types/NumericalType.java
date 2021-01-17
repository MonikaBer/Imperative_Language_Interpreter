package project.program.content.types;


import project.interpreter.INodeVisitor;

public class NumericalType extends NonVoidType implements INumericalType {

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
