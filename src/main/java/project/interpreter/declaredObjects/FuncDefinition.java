package project.interpreter.declaredObjects;

import project.program.content.FuncDef;
import project.program.content.statements.Block;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;
import project.program.content.types.Type;

import java.util.ArrayList;

public class FuncDefinition extends DeclaredObject {

    private final Type retType;
    private final Identifier id;
    private final ArrayList<Declaration> args;
    private final Block block;

    public FuncDefinition(FuncDef funcDef) {
        this.retType = funcDef.getRetType();
        this.id = funcDef.getId();
        this.args = funcDef.getArgs();
        this.block = funcDef.getBlock();
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
