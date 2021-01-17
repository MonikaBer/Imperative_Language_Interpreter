package project.program.content.statements;

import project.interpreter.INodeVisitor;
import project.program.content.ProgramContent;


public abstract class Statement extends ProgramContent {

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
