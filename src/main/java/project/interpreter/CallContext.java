package project.interpreter;

import project.exceptions.SemanticError;
import project.interpreter.evaluatedExpr.Box;
import project.interpreter.evaluatedExpr.Value;

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

//        if (blockContexts.isEmpty())
//            return;
//
//        BlockContext lastBlockContext = blockContexts.get(blockContexts.size()-1);
//        for (String name : lastBlockContext.getLocals().keySet()) {
//            for (int i = blockContexts.size()-2; i > -1; --i) {
//                ArrayList<String> n = new ArrayList<>();
//                n.add(name);
//                if (blockContexts.get(i).getLocals().containsKey(name)) {
//                    blockContexts.get(i).setBox(name, lastBlockContext.getLocals().get(name));
//                    break;
//                }
//            }
//        }
//
//        blockContexts.remove(blockContexts.size()-1);
    }

//    public void addLocal(String name, Value value, int lineNr, int posAtLine) {
//        for (BlockContext blockContext : blockContexts) {
//            if (blockContext.getLocals().containsKey(name)) {
//                String desc = "Var '" + name + "' already exists in this context";
//                throw new SemanticError(lineNr, posAtLine, desc);
//            }
//        }
//        this.getLastBlockContext().addLocal(name, value);
//    }

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

    public ArrayList<BlockContext> getBlockContexts() {
        return blockContexts;
    }
}
