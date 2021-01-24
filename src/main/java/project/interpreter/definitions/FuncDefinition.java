package project.interpreter.definitions;

import project.program.content.statements.Block;
import project.program.content.types.Type;

import java.util.ArrayList;

public class FuncDefinition {

    private final Type retType;
    private final String name;
    private final ArrayList<Arg> args;
    private final Block block;

    public FuncDefinition(Type retType, String name, ArrayList<Arg> args, Block block) {
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

    public ArrayList<Arg> getArgs() {
        return args;
    }

    public Block getBlock() {
        return block;
    }
}
