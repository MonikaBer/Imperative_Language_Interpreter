package project.interpreter;

import project.interpreter.evaluatedExpr.Box;

import java.util.ArrayList;

public class CallContext {

    private final ArrayList<BlockContext> blockContexts;

    public CallContext() {
        this.blockContexts = new ArrayList<>();
    }

    public void makeBlockContext() {
        BlockContext blockContext = new BlockContext();
        blockContexts.add(blockContext);
    }

    public void deleteBlockContext() {
        if(!blockContexts.isEmpty())
            blockContexts.remove(blockContexts.size()-1);
    }

    public Box getBox(ArrayList<String> name) {
        Box box;
        for (int i = blockContexts.size()-1; i > -1; --i) {
            if ((box = blockContexts.get(i).getBox(name)) != null)
                return box;
        }
        return null;
    }

    public BlockContext getLastBlockContext() {
        return blockContexts.get(blockContexts.size()-1);
    }
}
