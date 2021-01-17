package project.program;

import project.interpreter.INodeVisitor;
import project.program.content.FuncDef;
import project.program.content.ProgramContent;
import project.program.content.StructDef;
import project.program.content.statements.declarations.Declaration;

import java.util.ArrayList;

public class Program implements INode {

    private ArrayList<Declaration> declarations;
    private ArrayList<FuncDef> funcDefs;
    private ArrayList<StructDef> structDefs;

    private ArrayList<ProgramContent> programContents;

    public Program (ArrayList<Declaration> declarations, ArrayList<FuncDef> funcDefs, ArrayList<StructDef> structDefs,
                    ArrayList<ProgramContent> programContents) {
        this.declarations = declarations;
        this.funcDefs = funcDefs;
        this.structDefs = structDefs;

        this.programContents = programContents;
    }

    public ArrayList<Declaration> getDeclarations() {
        return declarations;
    }

    public ArrayList<FuncDef> getFuncDefs() {
        return funcDefs;
    }

    public ArrayList<StructDef> getStructDefs() {
        return structDefs;
    }

    public ArrayList<ProgramContent> getProgramContents() { return programContents; }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
