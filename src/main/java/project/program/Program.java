package project.program;

import project.program.content.statements.Declaration;
import project.program.content.statements.Statement;

import java.util.ArrayList;

public class Program {

    private ArrayList<Declaration> declarations;
    private ArrayList<Statement> statements;

    public Program (ArrayList<Declaration> declarations, ArrayList<Statement> statements) {
        this.declarations = declarations;
        this.statements = statements;
    }
}
