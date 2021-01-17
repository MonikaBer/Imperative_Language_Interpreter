package project.program.content;


import project.interpreter.INodeVisitor;
import project.program.INode;

public class ProgramContent implements INode {

    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
