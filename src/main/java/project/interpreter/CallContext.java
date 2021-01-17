package project.interpreter;

import project.interpreter.definitions.Variable;
import project.program.content.statements.Block;

import java.util.ArrayList;
import java.util.List;

public class CallContext {

    private List blockContexts;

    public CallContext() {
        this.blockContexts = new ArrayList<BlockContext>();
    }

    public void makeBlockContext(BlockContext blockContext) {
        blockContexts.add(blockContext);
    }

    public void deleteBlockContext() {
        blockContexts.remove(blockContexts.size()-1);
    }

    public Variable getVar(String name) {
        Variable var;
        for (int i = blockContexts.size()-1; i > -1; --i) {
            if ((var =  ((BlockContext)blockContexts.get(i)).getVar(name)) != null)
                return var;
        }
        return null;
    }

    public BlockContext getLastBlockContext() {
        return (BlockContext)blockContexts.get(blockContexts.size()-1);
    }

    public void removeLastBlockContext() {
        if (!blockContexts.isEmpty())
            blockContexts.remove(blockContexts.size()-1);
    }

    public List getBlockContexts() {
        return blockContexts;
    }
}
