package project.interpreter.declaredObjects;

import project.program.content.FuncDef;
import project.program.content.statements.Block;
import project.program.content.statements.declarations.Declaration;
import project.program.content.types.Type;

import java.util.ArrayList;

public class FuncDefinition {

    private final Type retType;
    private final String name;
    private final ArrayList<Declaration> args;
    private final Block block;

    public FuncDefinition(FuncDef funcDef) {
        this.retType = funcDef.getRetType();
        this.name = funcDef.getId().getName();
        this.args = funcDef.getArgs();
        this.block = funcDef.getBlock();
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
