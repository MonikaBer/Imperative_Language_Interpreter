package project.program.content.types;


import project.interpreter.INodeVisitor;

public class DoubleType extends NumericalType {

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
