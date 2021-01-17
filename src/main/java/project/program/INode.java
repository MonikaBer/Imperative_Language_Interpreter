package project.program;

import project.interpreter.INodeVisitor;

public interface INode {

    void accept(INodeVisitor visitor);
}
