package project.program.content;

import project.program.content.statements.Block;
import project.program.content.statements.Declaration;
import project.program.content.types.Type;

import java.util.ArrayList;

public class FuncDef {

    private final Type retType;
    private final String name;
    private final ArrayList<Declaration> args;
    private final Block block;

    public FuncDef(Type retType, String name, ArrayList<Declaration> args, Block block) {
        this.retType = retType;
        this.name = name;
        this.args = args;
        this.block = block;
    }

    public Type getRetType() {
        return retType;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Declaration> getArgs() {
        return args;
    }

    public Block getBlock() {
        return block;
    }
}
