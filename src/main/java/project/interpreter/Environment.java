package project.interpreter;

import project.exceptions.InterpreterError;
import project.exceptions.SemanticError;
import project.interpreter.definitions.FuncDefinition;
import project.interpreter.definitions.StructDefinition;
import project.interpreter.evaluatedExpr.*;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Environment {

    private final HashMap<String, Box> globals;
    private final HashMap<String, FuncDefinition> funcDefs;
    private final HashMap<String, StructDefinition> structDefs;
    private final HashMap<String, Runnable> embeddedFunctions;
    private final Stack<CallContext> callContexts;
    private Box lastBox;
    private Value lastValue;
    private boolean ifFuncEnd;
    private final Writer writer;
    private final Scanner reader;
    private final Writer errWriter;

    public Environment(Writer writer, Scanner reader, Writer errWriter) {
        this.globals = new HashMap<>();
        this.funcDefs = new HashMap<>();
        this.structDefs = new HashMap<>();
        this.callContexts = new Stack<>();
        this.lastBox = null;
        this.lastValue = null;
        this.ifFuncEnd = false;
        if (writer != null && reader != null && errWriter != null) {
            this.writer = writer;
            this.reader = reader;
            this.errWriter = errWriter;
        } else {
            throw new InterpreterError("Writer, reader or errWriter isn't defined!");
        }

        this.embeddedFunctions = new HashMap<>();
        this.embeddedFunctions.put("readInt", this.readInt());
        this.embeddedFunctions.put("readDouble", this.readDouble());
        this.embeddedFunctions.put("readStr", this.readStr());
        this.embeddedFunctions.put("printInt", this.printInt());
        this.embeddedFunctions.put("printDouble", this.printDouble());
        this.embeddedFunctions.put("printStr", this.printStr());
        this.embeddedFunctions.put("error", this.error());
        this.embeddedFunctions.put("convertIntToDouble", this.convertIntToDouble());
        this.embeddedFunctions.put("convertDoubleToInt", this.convertDoubleToInt());
        this.embeddedFunctions.put("convertStrToInt", this.convertStrToInt());
        this.embeddedFunctions.put("convertStrToDouble", this.convertStrToDouble());
        this.embeddedFunctions.put("convertIntToStr", this.convertIntToStr());
        this.embeddedFunctions.put("convertDoubleToStr", this.convertDoubleToStr());
    }

    public void setLastBoxAndValue(ArrayList<String> name, int lineNr, int posAtLine) {
        StringBuilder fullNameBuilder = new StringBuilder();
        for (int i = 0; i < name.size(); ++i) {
            fullNameBuilder.append(name.get(i));
            if (i != name.size()-1)
                fullNameBuilder.append('.');
        }

        Box box;
        if ((box = this.getBox(name)) == null) {
            String desc = "Identifier '" + fullNameBuilder.toString() + "' doesn't exist in this context";
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        Value value = box.getValue();
        if (value == null) {
            String desc = "Identifier '" + fullNameBuilder.toString() + "' equals to null";
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        lastBox = box;
        this.setLastValue(value);
    }

    public void makeCallContext() {
        CallContext callContext = new CallContext();
        callContexts.push(callContext);
    }

    public void makeBlockContext() {
        this.getLastCallContext().makeBlockContext();
    }

    public void makeVar(String name, Value value, int lineNr, int posAtLine) {
        if (!callContexts.isEmpty()) {    //put var to locals of recent block context of recent call context
            //this.getLastCallContext().addLocal(name, value, lineNr, posAtLine);
            this.getLastCallContext().getLastBlockContext().addLocal(name, value, lineNr, posAtLine);
            return;
        }

        if (globals.containsKey(name)) {
            String desc = "Declaration of var '" + name + "' already exists in globals";
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        globals.put(name, new Box(value));      //add var to globals
    }

    public void makeFuncDefinition(FuncDefinition funcDefinition) {
        funcDefs.put(funcDefinition.getName(), funcDefinition);
    }

    public void makeStructDefinition(StructDefinition structDefinition) {
        structDefs.put(structDefinition.getName(), structDefinition);
    }

    public void setStructDefinitionMap(String structName, HashMap<String, Box> map) {
        structDefs.get(structName).setMap(map);
    }

    public void deleteBlockContext() {
        if (!callContexts.isEmpty())
            this.getLastCallContext().deleteBlockContext();
    }

    public void deleteCallContext() {
        if (!callContexts.isEmpty())
            callContexts.pop();
    }

    public void assignVar(Box box, Value value) {
        box.setValue(value);
    }

    public HashMap<String, FuncDefinition> getFuncDefs() {
        return funcDefs;
    }

    public HashMap<String, StructDefinition> getStructDefs() {
        return structDefs;
    }

    public HashMap<String, Runnable> getEmbeddedFunctions() {
        return embeddedFunctions;
    }

    public Value getLastValue() {
        return lastValue;
    }

    public Box getLastBox() {
        return lastBox;
    }

    public boolean getIfFuncEnd() {
        return ifFuncEnd;
    }

    public void setLastValue(Value lastValue) {
        this.lastValue = lastValue;
    }

    public void setLastBox(Box box) {
        lastBox = box;
    }

    public void setIfFuncEnd(boolean b) { ifFuncEnd = b; }

    public HashMap<String, Box> getDefStructMap(String name) {
        if (!structDefs.containsKey(name))
            return null;

        HashMap<String, Box> map = new HashMap<>();
        for (String key : structDefs.get(name).getMap().keySet()) {
            Box box;
            if (structDefs.get(name).getMap().get(key).getValue() instanceof EvalStructValue) {
                HashMap<String, Box> nestedMap =
                        this.getDefStructMap(((EvalStructValue) structDefs.get(name).getMap().get(key).getValue()).getStructType());
                box = new Box(new EvalStructValue(((EvalStructValue) structDefs.get(name).getMap().get(key).getValue()).getStructType(), nestedMap));
            } else {
                box = new Box(structDefs.get(name).getMap().get(key).getValue());
            }
            map.put(key, box);
        }

        return map;
    }


    private CallContext getLastCallContext() {
        return callContexts.lastElement();
    }

    private Box getBox(ArrayList<String> name) {
        Box box;
        if ((box = this.getLastCallContext().getBox(name)) != null)
            return box;

        return Lib.pullBox(globals, name);
    }


    private Runnable readInt() {
        return () -> {
            String userInput = readNewLine();
            try {
                lastValue = new EvalIntValue(new BigInteger(userInput));
            } catch (Exception ex) {
                throw new InterpreterError("You wrote something which isn't int!");
            }
        };
    }

    private Runnable readDouble() {
        return () -> {
            String userInput = readNewLine();
            try {
                lastValue = new EvalDoubleValue(new BigDecimal(userInput));
            } catch (Exception ex) {
                throw new InterpreterError("You wrote something which isn't double!");
            }

        };
    }

    private Runnable readStr() {
        return () -> {
            String userInput = readNewLine();
            try {
                lastValue = new EvalStringValue(userInput);
            } catch (Exception ex) {
                throw new InterpreterError("You wrote something which isn't string!");
            }
        };
    }

    private Runnable printInt() {
        return () -> {
            Value value = lastValue;
            if (value instanceof EvalIntValue)
                writeWithNewLine(((EvalIntValue)value).getValue().toString());
        };
    }

    private Runnable printDouble() {
        return () -> {
            Value value = lastValue;
            if (value instanceof EvalDoubleValue)
                writeWithNewLine(((EvalDoubleValue)value).getValue().toString());
        };
    }

    private Runnable printStr() {
        return () -> {
            Value value = lastValue;
            if (value instanceof EvalStringValue)
                writeWithNewLine(((EvalStringValue)value).getValue());
        };
    }

    private Runnable error() {
        return () -> {
            Value value = lastValue;
            if (value instanceof EvalStringValue)
                writeErrWithNewLine(((EvalStringValue)value).getValue());
        };
    }

    private Runnable convertIntToDouble() {
        return () -> {
            Value value = lastValue;
            if (value instanceof EvalIntValue)
                lastValue = new EvalDoubleValue(new BigDecimal(((EvalIntValue)value).getValue().toString()));
        };
    }

    private Runnable convertDoubleToInt() {
        return () -> {
            Value value = lastValue;
            if (value instanceof EvalDoubleValue)
                lastValue = new EvalIntValue(new BigInteger(((EvalDoubleValue)value).getValue().toString()));
        };
    }

    private Runnable convertStrToInt() {
        return () -> {
            Value value = lastValue;
            if (value instanceof EvalStringValue)
                lastValue = new EvalIntValue(new BigInteger(((EvalStringValue)value).getValue()));
        };
    }

    private Runnable convertStrToDouble() {
        return () -> {
            Value value = lastValue;
            if (value instanceof EvalStringValue)
                lastValue = new EvalDoubleValue(new BigDecimal(((EvalStringValue)value).getValue()));
        };
    }

    private Runnable convertIntToStr() {
        return () -> {
            Value value = lastValue;
            if (value instanceof EvalIntValue)
                lastValue = new EvalStringValue(((EvalIntValue)value).getValue().toString());
        };
    }

    private Runnable convertDoubleToStr() {
        return () -> {
            Value value = lastValue;
            if (value instanceof EvalDoubleValue)
                lastValue = new EvalStringValue(((EvalDoubleValue)value).getValue().toString());
        };
    }

    private void writeWithNewLine(String text) {
        try {
            writer.append(text).append(String.valueOf('\n'));
            writer.flush();
        } catch (IOException ex) {
            throw new InterpreterError("Cannot print!");
        }
    }

    private String readNewLine() {
        return reader.nextLine();
    }

    private void writeErrWithNewLine(String text) {
        try {
            errWriter.append(text).append(String.valueOf('\n'));
            errWriter.flush();
        } catch (IOException ex) {
            throw new InterpreterError("Cannot print!");
        }
    }

//
//    private Value getValue(ArrayList<String> name) {
//        Box box;
//        if ((box = this.getBox(name)) != null)
//            return box.getValue();
//        return null;
//    }
}
