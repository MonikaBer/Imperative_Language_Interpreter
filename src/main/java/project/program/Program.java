package project.program;

import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.Declaration;

import java.util.ArrayList;

public class Program {

    private ArrayList<Declaration> declarations;
    private ArrayList<FuncDef> funcDefs;
    private ArrayList<StructDef> structDefs;

    public Program (ArrayList<Declaration> declarations, ArrayList<FuncDef> funcDefs, ArrayList<StructDef> structDefs) {
        this.declarations = declarations;
        this.funcDefs = funcDefs;
        this.structDefs = structDefs;
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
}
