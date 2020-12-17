package project.program;

import project.program.content.Declaration;
import project.program.content.instructions.Instruction;

import java.util.ArrayList;

public class Program {

    private ArrayList<Declaration> declarations;
    private ArrayList<Instruction> instructions;

    public Program (ArrayList<Declaration> declarations, ArrayList<Instruction> instructions) {
        this.declarations = declarations;
        this.instructions = instructions;
    }
}
