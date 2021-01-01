package project.program.content;

import project.program.content.statements.Block;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;
import project.program.content.types.Type;

import java.util.ArrayList;

public class FuncDef {

    private final Type retType;
    private final Identifier id;
    private final ArrayList<Declaration> args;
    private final Block block;

    public FuncDef(Type retType, Identifier id, ArrayList<Declaration> args, Block block) {
        this.retType = retType;
        this.id = id;
        this.args = args;
        this.block = block;
    }

    public Type getRetType() {
        return retType;
    }

    public Identifier getId() {
        return id;
    }

    public ArrayList<Declaration> getArgs() {
        return args;
    }

    public Block getBlock() {
        return block;
    }
}
