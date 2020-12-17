package project.program.content.instructions;

import project.program.content.others.Block;
import project.program.content.others.Condition;

public class While extends Instruction {

    private final Condition condition;
    private final Block block;

    public While(Condition condition, Block block) {
        this.condition = condition;
        this.block = block;
    }

    public Condition getCondition() {
        return condition;
    }

    public Block getBlock() {
        return block;
    }
}
