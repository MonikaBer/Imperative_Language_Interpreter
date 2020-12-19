package project.program.content.statements.expressions.others;

import project.program.content.StructDef;
import project.program.content.statements.expressions.Expression;
import project.program.content.types.StructType;

public class StructObj extends Expression {

    private final StructType type;
    private StructDef def;

    public StructObj(StructType type, StructDef def) {
        this.type = type;
        this.def = def;
    }

    public StructType getType() {
        return type;
    }

    public StructDef getDef() {
        return def;
    }

    public void setDef(StructDef def) {
        this.def = def;
    }
}
