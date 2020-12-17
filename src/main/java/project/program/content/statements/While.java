package project.program.content.statements;

public class While extends Statement {

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
