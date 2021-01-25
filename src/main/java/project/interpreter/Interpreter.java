package project.interpreter;

import project.exceptions.SemanticError;
import project.interpreter.definitions.Arg;
import project.interpreter.definitions.FuncDefinition;
import project.interpreter.definitions.StructDefinition;
import project.interpreter.evaluatedExpr.*;
import project.interpreter.evaluatedExpr.EvalDoubleValue;
import project.interpreter.evaluatedExpr.EvalIntValue;
import project.interpreter.evaluatedExpr.EvalStringValue;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.ProgramContent;
import project.program.content.StructDef;
import project.program.content.statements.*;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.Initialisation;
import project.program.content.statements.declarations.OnlyDeclaration;
import project.program.content.statements.expressions.Expression;
import project.program.content.statements.expressions.orExpressions.AlternativeExpression;
import project.program.content.statements.expressions.orExpressions.OrExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.AndExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.ConjunctionExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.*;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.AddExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.AdditionExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.SubtractExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.DivExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.ModExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.MultExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.MultiplicationExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.NegationExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.NegativeExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.NotExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.*;
import project.program.content.statements.switchStmt.Case;
import project.program.content.statements.switchStmt.Switch;
import project.program.content.types.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;


public class Interpreter implements INodeVisitor {

    private final Program program;
    private final Environment env;

    public Interpreter(Program program) {
        this.program = program;
        this.env = new Environment();
    }

    public void execute() {
        program.accept(this);
    }

    public void start() {
        FuncCall mainFuncCall = new FuncCall(
                                        new Identifier("main", -1, -1),
                                        null,
                                        -1,
                                        -1);
        mainFuncCall.accept(this);
    }

    public Environment getEnv() {
        return env;
    }


    @Override
    public void visit(Program program) {
        for (ProgramContent programContent : program.getProgramContents()) {
            programContent.accept(this);
        }
    }

    @Override
    public void visit(ProgramContent programContent) {
        if (programContent instanceof Declaration)
            ((Declaration) programContent).accept(this);
        else if (programContent instanceof FuncDef)
            ((FuncDef) programContent).accept(this);
        else if (programContent instanceof StructDef)
            ((StructDef) programContent).accept(this);
    }

    @Override
    public void visit(Declaration declaration) {
        if (declaration instanceof OnlyDeclaration)
            ((OnlyDeclaration)declaration).accept(this);
        else if (declaration instanceof Initialisation)
            ((Initialisation)declaration).accept(this);
    }

    @Override
    public void visit(OnlyDeclaration onlyDeclaration) {
        Type type = onlyDeclaration.getType();
        if (type instanceof VoidType) {
            String desc = "Type of var in declaration is instance of void";
            throw new SemanticError(((VoidType)type).getLineNr(), ((VoidType)type).getPositionAtLine(), desc);
        }

        Value value;
        if (type instanceof IntType) {
            value = new EvalIntValue(new BigInteger("0"));
        } else if (type instanceof DoubleType) {
            value = new EvalDoubleValue(new BigDecimal("0.0"));
        } else if (type instanceof BoolType) {
            value = new EvalBoolValue(false);
        } else if (type instanceof StringType) {
            value = new EvalStringValue("");
        } else {  //type is StructType
            HashMap<String, Box> map = env.getDefStructMap(((StructType) type).getId().getName());
            if (map == null) {
                String desc = "Unknown type of struct in declaration";
                int lineNr = ((StructType) type).getId().getLineNr();
                int posAtLine = ((StructType) type).getId().getPositionAtLine();
                throw new SemanticError(lineNr, posAtLine, desc);
            }
            value = new EvalStructValue(((StructType) type).getId().getName(), map);
        }

        int lineNr = onlyDeclaration.getId().getLineNr();
        int posAtLine = onlyDeclaration.getId().getPositionAtLine();
        env.makeVar(onlyDeclaration.getId().getName(), value, lineNr, posAtLine);
    }

    @Override
    public void visit(Initialisation initialisation) {
        Type type = initialisation.getType();
        if (type instanceof VoidType) {
            String desc = "Type of var in initialisation is instance of void";
            throw new SemanticError(((VoidType)type).getLineNr(), ((VoidType)type).getPositionAtLine(), desc);
        }

        initialisation.getExpression().accept(this);
        Value value = env.getLastValue();

        String desc = checkTypeAndValueCorrectness("initialisation of var", type, value);
        if (desc != null) {
            int lineNr = initialisation.getExpression().getLineNr();
            int posAtLine = initialisation.getExpression().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        int lineNr = initialisation.getId().getLineNr();
        int posAtLine = initialisation.getId().getPositionAtLine();
        env.makeVar(initialisation.getId().getName(), value, lineNr, posAtLine);
    }

    @Override
    public void visit(FuncDef funcDef) {
        String funcName = funcDef.getId().getName();
        if (env.getEmbeddedFunctions().containsKey(funcName)) {
            String desc = "Name '" + funcName + "' of function in function definition is reserved for embedded function";
            int lineNr = funcDef.getId().getLineNr();
            int posAtLine = funcDef.getId().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        if (env.getFuncDefs().containsKey(funcName)) {
            String desc = "Name '" + funcName + "' of function in function definition already exists";
            int lineNr = funcDef.getId().getLineNr();
            int posAtLine = funcDef.getId().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        ArrayList<Arg> args = new ArrayList<>();
        if (funcDef.getArgs() != null) {
            for (Declaration declaration : funcDef.getArgs()) {
                Type type = declaration.getType();
                if (type instanceof VoidType) {
                    String desc = "Type of argument in function definition can't be instance of void";
                    throw new SemanticError(((VoidType)type).getLineNr(), ((VoidType)type).getPositionAtLine(), desc);
                }

                Value defValue;
                if (declaration instanceof Initialisation) {
                    ((Initialisation)declaration).getExpression().accept(this);
                    defValue = env.getLastValue();

                    String desc = checkTypeAndValueCorrectness("definition of argument of function", type, defValue);
                    if (desc != null) {
                        int lineNr = ((Initialisation)declaration).getExpression().getLineNr();
                        int posAtLine = ((Initialisation)declaration).getExpression().getPositionAtLine();
                        throw new SemanticError(lineNr, posAtLine, desc);
                    }
                } else {
                    defValue = null;
                }

                String name = declaration.getId().getName();
                if (ifNameOfArgIsUnique(name, args)) {
                    args.add(new Arg(type, name, defValue));
                } else {
                    String desc = "Name of argument in function definition isn't unique";
                    int lineNr = declaration.getId().getLineNr();
                    int posAtLine = declaration.getId().getPositionAtLine();
                    throw new SemanticError(lineNr, posAtLine, desc);
                }
            }
        }

        Block block = funcDef.getBlock();
        if (!(funcDef.getRetType() instanceof VoidType)) {
            if (block.getStmts() == null || !(block.getStmts().get(block.getStmts().size()-1) instanceof Return)) {
                String desc = "Lack of return statement at the end of block of non void function definition";
                int lineNr = funcDef.getId().getLineNr();
                int posAtLine = funcDef.getId().getPositionAtLine();
                throw new SemanticError(lineNr, posAtLine, desc);
            }
        }

        FuncDefinition funcDefinition =
                new FuncDefinition(funcDef.getRetType(), funcName, args, block);

        env.makeFuncDefinition(funcDefinition);
    }

    @Override
    public void visit(StructDef structDef) {
        String structName = structDef.getId().getName();
        if (env.getStructDefs().containsKey(structName)) {
            String desc = "Name '" + structName + "' of struct in struct definition already exists";
            int lineNr = structDef.getId().getLineNr();
            int posAtLine = structDef.getId().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        if (structDef.getBody() == null) {
            String desc = "Definition of struct hasn't got body";
            throw new SemanticError(structDef.getId().getLineNr(), structDef.getId().getPositionAtLine(), desc);
        }

        HashMap<String, Box> map = new HashMap<>();

        StructDefinition structDefinition = new StructDefinition(structName, map);
        env.makeStructDefinition(structDefinition);

        for (Declaration declaration : structDef.getBody()) {
            Type type = declaration.getType();
            if (type instanceof VoidType) {
                String desc = "Type of field in struct definition can't be instance of void";
                throw new SemanticError(((VoidType)type).getLineNr(), ((VoidType)type).getPositionAtLine(), desc);
            }

            Value value;
            if (declaration instanceof Initialisation) {
                ((Initialisation)declaration).getExpression().accept(this);
                value = env.getLastValue();

                String desc = checkTypeAndValueCorrectness("initialisation of struct field", type, value);
                if (desc != null) {
                    int lineNr = ((Initialisation)declaration).getExpression().getLineNr();
                    int posAtLine = ((Initialisation)declaration).getExpression().getPositionAtLine();
                    throw new SemanticError(lineNr, posAtLine, desc);
                }
            }
            else {                                                      //declaration is OnlyDeclaration
                if (type instanceof IntType) {
                    value = new EvalIntValue(new BigInteger("0"));
                } else if (type instanceof DoubleType) {
                    value = new EvalDoubleValue(new BigDecimal("0.0"));
                } else if (type instanceof BoolType) {
                    value = new EvalBoolValue(false);
                } else if (type instanceof StringType) {
                    value = new EvalStringValue("");
                } else {  //type is StructType
                    HashMap<String, Box> innerStructMap = env.getDefStructMap(((StructType) type).getId().getName());
                    if (innerStructMap == null) {
                        String desc = "Unknown type of field in struct definition";
                        int lineNr = ((StructType) type).getId().getLineNr();
                        int posAtLine = ((StructType) type).getId().getPositionAtLine();
                        throw new SemanticError(lineNr, posAtLine, desc);
                    }

                    value = new EvalStructValue(((StructType) type).getId().getName(), innerStructMap);
                }
            }

            if (map.containsKey(declaration.getId().getName())) {
                String desc = "Name of field in struct definition isn't unique";
                int lineNr = declaration.getId().getLineNr();
                int posAtLine = declaration.getId().getPositionAtLine();
                throw new SemanticError(lineNr, posAtLine, desc);
            }

            map.put(declaration.getId().getName(), new Box(value));
        }

        env.setStructDefinitionMap(structName, map);
    }

    @Override
    public void visit(Statement statement) {
        if (statement instanceof Declaration)
            ((Declaration)statement).accept(this);
        else if (statement instanceof Switch)
            ((Switch)statement).accept(this);
        else if (statement instanceof Assignment)
            ((Assignment)statement).accept(this);
        else if (statement instanceof Block)
            ((Block)statement).accept(this);
        else if (statement instanceof Decrement)
            ((Decrement)statement).accept(this);
        else if (statement instanceof Empty)
            ((Empty)statement).accept(this);
        else if (statement instanceof IfElse)
            ((IfElse)statement).accept(this);
        else if (statement instanceof If)
            ((If)statement).accept(this);
        else if (statement instanceof Increment)
            ((Increment)statement).accept(this);
        else if (statement instanceof Return)
            ((Return)statement).accept(this);
        else if (statement instanceof VoidReturn)
            ((VoidReturn)statement).accept(this);
        else if (statement instanceof While)
            ((While)statement).accept(this);
    }

    @Override
    public void visit(Assignment assignmentStmt) {
        assignmentStmt.getId().accept(this);
        Box id = env.getLastBox();

        assignmentStmt.getExpression().accept(this);
        Value exp = env.getLastValue();

        env.assignVar(id, exp);
    }

    @Override
    public void visit(Block blockStmt) {
        if (blockStmt.getStmts() == null)
            return;

        env.makeBlockContext();

        for (Statement stmt : blockStmt.getStmts()) {
            stmt.accept(this);
            if (env.getIfFuncEnd()) {
                break;
            }
        }

        env.deleteBlockContext();
    }

    @Override
    public void visit(Decrement decrementStmt) {
        decrementStmt.getExpression().accept(this);
        Box box = env.getLastBox();

        if (box.getValue() instanceof EvalIntValue) {
            BigInteger val = ((EvalIntValue) box.getValue()).getValue();
            val = val.subtract(BigInteger.ONE);
            box.setValue(new EvalIntValue(val));
        }
        else {
            String desc = "Decrement of int is only permitted";
            int lineNr = decrementStmt.getExpression().getLineNr();
            int posAtLine = decrementStmt.getExpression().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }
    }

    @Override
    public void visit(Increment incrementStmt) {
        incrementStmt.getExpression().accept(this);
        Box box = env.getLastBox();

        if (box.getValue() instanceof EvalIntValue) {
            BigInteger val = ((EvalIntValue) box.getValue()).getValue();
            val = val.add(BigInteger.ONE);
            box.setValue(new EvalIntValue(val));
        }
        else {
            String desc = "Increment of int is only permitted";
            int lineNr = incrementStmt.getExpression().getLineNr();
            int posAtLine = incrementStmt.getExpression().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }
    }

    @Override
    public void visit(If ifStmt) {
        ifStmt.getCondition().accept(this);

        if (!(env.getLastValue() instanceof EvalBoolValue)) {
            String desc = "Condition in if statement is not a bool";
            int lineNr = ifStmt.getCondition().getLineNr();
            int posAtLine = ifStmt.getCondition().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        boolean ifConditionTrue = ((EvalBoolValue)env.getLastValue()).getValue();
        if (ifConditionTrue)
            ifStmt.getIfStmt().accept(this);
    }

    @Override
    public void visit(IfElse ifElseStmt) {
        ifElseStmt.getCondition().accept(this);

        if (!(env.getLastValue() instanceof EvalBoolValue)) {
            String desc = "Condition in if-else statement is not a bool";
            int lineNr = ifElseStmt.getCondition().getLineNr();
            int posAtLine = ifElseStmt.getCondition().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        boolean ifConditionTrue = ((EvalBoolValue)env.getLastValue()).getValue();
        if (ifConditionTrue)
            ifElseStmt.getIfStmt().accept(this);
        else
            ifElseStmt.getElseStmt().accept(this);
    }

    @Override
    public void visit(While whileStmt) {
        whileStmt.getCondition().accept(this);

        if (!(env.getLastValue() instanceof EvalBoolValue)) {
            String desc = "Condition in while statement is not a bool";
            int lineNr = whileStmt.getCondition().getLineNr();
            int posAtLine = whileStmt.getCondition().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        boolean ifConditionTrue = ((EvalBoolValue)env.getLastValue()).getValue();
        if (ifConditionTrue)
            whileStmt.getStmt().accept(this);
    }

    @Override
    public void visit(Return returnStmt) {
        returnStmt.getExpression().accept(this);
        env.setIfFuncEnd(true);
    }

    @Override
    public void visit(VoidReturn voidReturnStmt) {
        env.setLastValue(null);
        env.setLastBox(null);
        env.setIfFuncEnd(true);
    }

    @Override
    public void visit(Empty emptyStmt) {}

    @Override
    public void visit(Switch switchStmt) {
        switchStmt.getExpression().accept(this);
        Value switchValue = env.getLastValue();
        if (switchValue instanceof EvalStructValue) {
            String desc = "In switch expression value of struct type is prohibited";
            int lineNr = switchStmt.getExpression().getLineNr();
            int posAtLine = switchStmt.getExpression().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        for (Case caseStmt : switchStmt.getCases()) {
            caseStmt.getExpression().accept(this);
            Value caseValue = env.getLastValue();

            try {
                if (!ifSimpleValuesEquality(switchValue, caseValue))
                    continue;
            } catch (SemanticError err) {
                String desc = err.getDesc() + " in switch and case";
                int lineNr = caseStmt.getExpression().getLineNr();
                int posAtLine = caseStmt.getExpression().getPositionAtLine();
                throw new SemanticError(lineNr, posAtLine, desc);
            }

            //value in switch equals to value in case
            caseStmt.getStmt().accept(this);
            return;
        }

        switchStmt.getDefaultStmt().accept(this);
    }

    @Override
    public void visit(Expression expression) {
        ((OrExpression)expression).accept(this);
    }

    @Override
    public void visit(OrExpression orExpression) {
        if (orExpression instanceof AlternativeExpression)
            ((AlternativeExpression)orExpression).accept(this);
        else
            ((AndExpression)orExpression).accept(this);
    }

    @Override
    public void visit(AlternativeExpression alternativeExpression) {
        alternativeExpression.getLeftOperand().accept(this);
        Value leftOperand = env.getLastValue();

        if (!(leftOperand instanceof EvalBoolValue)) {
            String desc = "Left operand of alternative isn't bool";
            int lineNr = alternativeExpression.getLeftOperand().getLineNr();
            int posAtLine = alternativeExpression.getLeftOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        if (((EvalBoolValue)leftOperand).getValue()) {  //if left operand of alternative is true
            env.setLastValue(new EvalBoolValue(true));
            return;
        }

        alternativeExpression.getRightOperand().accept(this);
        Value rightOperand = env.getLastValue();

        if (!(rightOperand instanceof EvalBoolValue)) {
            String desc = "Right operand of alternative isn't bool";
            int lineNr = alternativeExpression.getRightOperand().getLineNr();
            int posAtLine = alternativeExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        if (((EvalBoolValue)rightOperand).getValue()) {  //if right operand of alternative is true
            env.setLastValue(new EvalBoolValue(true));
            return;
        }

        // left and right operand of alternative are false
        env.setLastValue(new EvalBoolValue(false));
    }

    @Override
    public void visit(AndExpression andExpression) {
        if (andExpression instanceof ConjunctionExpression)
            ((ConjunctionExpression)andExpression).accept(this);
        else
            ((RelationExpression)andExpression).accept(this);
    }

    @Override
    public void visit(ConjunctionExpression conjunctionExpression) {
        conjunctionExpression.getLeftOperand().accept(this);
        Value leftOperand = env.getLastValue();

        if (!(leftOperand instanceof EvalBoolValue)) {
            String desc = "Left operand of conjunction isn't bool";
            int lineNr = conjunctionExpression.getLeftOperand().getLineNr();
            int posAtLine = conjunctionExpression.getLeftOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        if (!((EvalBoolValue)leftOperand).getValue()) {  //if left operand of conjunction is false
            env.setLastValue(new EvalBoolValue(false));
            return;
        }

        conjunctionExpression.getRightOperand().accept(this);
        Value rightOperand = env.getLastValue();

        if (!(rightOperand instanceof EvalBoolValue)) {
            String desc = "Right operand of conjunction isn't bool";
            int lineNr = conjunctionExpression.getRightOperand().getLineNr();
            int posAtLine = conjunctionExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        if (((EvalBoolValue)rightOperand).getValue()) {  //if right operand of conjunction is true
            env.setLastValue(new EvalBoolValue(true));
        } else {
            env.setLastValue(new EvalBoolValue(false));
        }
    }

    @Override
    public void visit(RelationExpression relationExpression) {
        if (relationExpression instanceof EqualExpression)
            ((EqualExpression)relationExpression).accept(this);
        else if (relationExpression instanceof NotEqualExpression)
            ((NotEqualExpression)relationExpression).accept(this);
        else if (relationExpression instanceof GreaterEqualExpression)
            ((GreaterEqualExpression)relationExpression).accept(this);
        else if (relationExpression instanceof GreaterExpression)
            ((GreaterExpression)relationExpression).accept(this);
        else if (relationExpression instanceof LesserEqualExpression)
            ((LesserEqualExpression)relationExpression).accept(this);
        else if (relationExpression instanceof LesserExpression)
            ((LesserExpression)relationExpression).accept(this);
        else
            ((AdditionExpression)relationExpression).accept(this);
    }

    @Override
    public void visit(NotEqualExpression notEqualExpression) {
        notEqualExpression.getLeftOperand().accept(this);
        Value leftOperand = env.getLastValue();

        if (leftOperand instanceof EvalStructValue) {
            String desc = "Left operand of not equal expression is struct - it is prohibited";
            int lineNr = notEqualExpression.getLeftOperand().getLineNr();
            int posAtLine = notEqualExpression.getLeftOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        notEqualExpression.getRightOperand().accept(this);
        Value rightOperand = env.getLastValue();

        if (rightOperand instanceof EvalStructValue) {
            String desc = "Right operand of not equal expression is struct - it is prohibited";
            int lineNr = notEqualExpression.getRightOperand().getLineNr();
            int posAtLine = notEqualExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }


        try {
            if (ifSimpleValuesEquality(leftOperand, rightOperand)) {
                env.setLastValue(new EvalBoolValue(false));
                return;
            }
        } catch (SemanticError err) {
            String desc = err.getDesc() + " - type of right operand of not equal expression is wrong";
            int lineNr = notEqualExpression.getRightOperand().getLineNr();
            int posAtLine = notEqualExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.setLastValue(new EvalBoolValue(true));
    }

    @Override
    public void visit(LesserExpression lesserExpression) {
        lesserExpression.getLeftOperand().accept(this);
        Value leftOperand = env.getLastValue();

        int lineNr = lesserExpression.getLeftOperand().getLineNr();
        int posAtLine = lesserExpression.getLeftOperand().getPositionAtLine();
        checkIfTypeIsNumerical(leftOperand, "Left operand of lesser expression", lineNr, posAtLine);

        lesserExpression.getRightOperand().accept(this);
        Value rightOperand = env.getLastValue();

        lineNr = lesserExpression.getRightOperand().getLineNr();
        posAtLine = lesserExpression.getRightOperand().getPositionAtLine();
        checkIfTypeIsNumerical(rightOperand, "Right operand of lesser expression", lineNr, posAtLine);

        try {
            if (ifSimpleValueLessThanAnother(leftOperand, rightOperand)) {
                env.setLastValue(new EvalBoolValue(true));
                return;
            }
        } catch (SemanticError err) {
            String desc = err.getDesc() + " - type of right operand of lesser expression is wrong";
            lineNr = lesserExpression.getRightOperand().getLineNr();
            posAtLine = lesserExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.setLastValue(new EvalBoolValue(false));
    }

    @Override
    public void visit(LesserEqualExpression lesserEqualExpression) {
        lesserEqualExpression.getLeftOperand().accept(this);
        Value leftOperand = env.getLastValue();

        int lineNr = lesserEqualExpression.getLeftOperand().getLineNr();
        int posAtLine = lesserEqualExpression.getLeftOperand().getPositionAtLine();
        checkIfTypeIsNumerical(leftOperand, "Left operand of lesser/equal expression", lineNr, posAtLine);

        lesserEqualExpression.getRightOperand().accept(this);
        Value rightOperand = env.getLastValue();

        lineNr = lesserEqualExpression.getRightOperand().getLineNr();
        posAtLine = lesserEqualExpression.getRightOperand().getPositionAtLine();
        checkIfTypeIsNumerical(rightOperand, "Right operand of lesser/equal expression", lineNr, posAtLine);

        try {
            if (ifSimpleValueLessEqualThanAnother(leftOperand, rightOperand)) {
                env.setLastValue(new EvalBoolValue(true));
                return;
            }
        } catch (SemanticError err) {
            String desc = err.getDesc() + " - type of right operand of lesser/equal expression is wrong";
            lineNr = lesserEqualExpression.getRightOperand().getLineNr();
            posAtLine = lesserEqualExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.setLastValue(new EvalBoolValue(false));
    }

    @Override
    public void visit(GreaterExpression greaterExpression) {
        greaterExpression.getLeftOperand().accept(this);
        Value leftOperand = env.getLastValue();

        int lineNr = greaterExpression.getLeftOperand().getLineNr();
        int posAtLine = greaterExpression.getLeftOperand().getPositionAtLine();
        checkIfTypeIsNumerical(leftOperand, "Left operand of greater expression", lineNr, posAtLine);

        greaterExpression.getRightOperand().accept(this);
        Value rightOperand = env.getLastValue();

        lineNr = greaterExpression.getRightOperand().getLineNr();
        posAtLine = greaterExpression.getRightOperand().getPositionAtLine();
        checkIfTypeIsNumerical(rightOperand, "Right operand of greater expression", lineNr, posAtLine);

        try {
            if (ifSimpleValueGreaterThanAnother(leftOperand, rightOperand)) {
                env.setLastValue(new EvalBoolValue(true));
                return;
            }
        } catch (SemanticError err) {
            String desc = err.getDesc() + " - type of right operand of greater expression is wrong";
            lineNr = greaterExpression.getRightOperand().getLineNr();
            posAtLine = greaterExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.setLastValue(new EvalBoolValue(false));
    }

    @Override
    public void visit(GreaterEqualExpression greaterEqualExpression) {
        greaterEqualExpression.getLeftOperand().accept(this);
        Value leftOperand = env.getLastValue();

        int lineNr = greaterEqualExpression.getLeftOperand().getLineNr();
        int posAtLine = greaterEqualExpression.getLeftOperand().getPositionAtLine();
        checkIfTypeIsNumerical(leftOperand, "Left operand of greater/equal expression", lineNr, posAtLine);

        greaterEqualExpression.getRightOperand().accept(this);
        Value rightOperand = env.getLastValue();

        lineNr = greaterEqualExpression.getRightOperand().getLineNr();
        posAtLine = greaterEqualExpression.getRightOperand().getPositionAtLine();
        checkIfTypeIsNumerical(rightOperand, "Right operand of greater/equal expression", lineNr, posAtLine);

        try {
            if (ifSimpleValueGreaterEqualThanAnother(leftOperand, rightOperand)) {
                env.setLastValue(new EvalBoolValue(true));
                return;
            }
        } catch (SemanticError err) {
            String desc = err.getDesc() + " - type of right operand of greater/equal expression is wrong";
            lineNr = greaterEqualExpression.getRightOperand().getLineNr();
            posAtLine = greaterEqualExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.setLastValue(new EvalBoolValue(false));
    }

    @Override
    public void visit(EqualExpression equalExpression) {
        equalExpression.getLeftOperand().accept(this);
        Value leftOperand = env.getLastValue();

        if (leftOperand instanceof EvalStructValue) {
            String desc = "Left operand of equal expression is struct - it is prohibited";
            int lineNr = equalExpression.getLeftOperand().getLineNr();
            int posAtLine = equalExpression.getLeftOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        equalExpression.getRightOperand().accept(this);
        Value rightOperand = env.getLastValue();

        if (rightOperand instanceof EvalStructValue) {
            String desc = "Right operand of equal expression is struct - it is prohibited";
            int lineNr = equalExpression.getRightOperand().getLineNr();
            int posAtLine = equalExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        try {
            if (ifSimpleValuesEquality(leftOperand, rightOperand)) {
                env.setLastValue(new EvalBoolValue(true));
                return;
            }
        } catch (SemanticError err) {
            String desc = err.getDesc() + " - type of right operand of equal expression is wrong";
            int lineNr = equalExpression.getRightOperand().getLineNr();
            int posAtLine = equalExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.setLastValue(new EvalBoolValue(false));
    }

    @Override
    public void visit(AdditionExpression additionExpression) {
        if (additionExpression instanceof AddExpression)
            ((AddExpression)additionExpression).accept(this);
        else if (additionExpression instanceof SubtractExpression)
            ((SubtractExpression)additionExpression).accept(this);
        else
            ((MultiplicationExpression)additionExpression).accept(this);
    }

    @Override
    public void visit(AddExpression addExpression) {
        addExpression.getLeftOperand().accept(this);
        Value leftOperand = env.getLastValue();

        int lineNr = addExpression.getLeftOperand().getLineNr();
        int posAtLine = addExpression.getLeftOperand().getPositionAtLine();
        checkIfTypeIsNumericalOrString(leftOperand, "Left operand of add expression", lineNr, posAtLine);

        addExpression.getRightOperand().accept(this);
        Value rightOperand = env.getLastValue();

        lineNr = addExpression.getRightOperand().getLineNr();
        posAtLine = addExpression.getRightOperand().getPositionAtLine();
        checkIfTypeIsNumericalOrString(rightOperand, "Right operand of add expression", lineNr, posAtLine);

        if (leftOperand instanceof EvalIntValue && rightOperand instanceof EvalIntValue) {
            BigInteger result = ((EvalIntValue)leftOperand).getValue().add(((EvalIntValue)rightOperand).getValue());
            env.setLastValue(new EvalIntValue(result));
        }
        else if (leftOperand instanceof EvalDoubleValue && rightOperand instanceof EvalDoubleValue) {
            BigDecimal result = ((EvalDoubleValue)leftOperand).getValue().add(((EvalDoubleValue)rightOperand).getValue());
            env.setLastValue(new EvalDoubleValue(result));
        }
        else if (leftOperand instanceof EvalStringValue && rightOperand instanceof EvalStringValue) {
            String result = ((EvalStringValue)leftOperand).getValue() + ((EvalStringValue)rightOperand).getValue();
            env.setLastValue(new EvalStringValue(result));
        }
        else {
            String desc = "Incorrectness of types of adding values - type of right operand is wrong";
            lineNr = addExpression.getRightOperand().getLineNr();
            posAtLine = addExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }
    }

    @Override
    public void visit(SubtractExpression subtractExpression) {
        subtractExpression.getLeftOperand().accept(this);
        Value leftOperand = env.getLastValue();

        int lineNr = subtractExpression.getLeftOperand().getLineNr();
        int posAtLine = subtractExpression.getLeftOperand().getPositionAtLine();
        checkIfTypeIsNumerical(leftOperand, "Left operand of subtract expression", lineNr, posAtLine);

        subtractExpression.getRightOperand().accept(this);
        Value rightOperand = env.getLastValue();

        lineNr = subtractExpression.getRightOperand().getLineNr();
        posAtLine = subtractExpression.getRightOperand().getPositionAtLine();
        checkIfTypeIsNumerical(rightOperand, "Right operand of subtract expression", lineNr, posAtLine);

        if (leftOperand instanceof EvalIntValue && rightOperand instanceof EvalIntValue) {
            BigInteger result = ((EvalIntValue)leftOperand).getValue().subtract(((EvalIntValue)rightOperand).getValue());
            env.setLastValue(new EvalIntValue(result));
        }
        else if (leftOperand instanceof EvalDoubleValue && rightOperand instanceof EvalDoubleValue) {
            BigDecimal result = ((EvalDoubleValue)leftOperand).getValue().subtract(((EvalDoubleValue)rightOperand).getValue());
            env.setLastValue(new EvalDoubleValue(result));
        }
        else {
            String desc = "Incorrectness of types of subtracting values - type of right operand is wrong";
            lineNr = subtractExpression.getRightOperand().getLineNr();
            posAtLine = subtractExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }
    }

    @Override
    public void visit(MultiplicationExpression multiplicationExpression) {
        if (multiplicationExpression instanceof MultExpression)
            ((MultExpression)multiplicationExpression).accept(this);
        else if (multiplicationExpression instanceof DivExpression)
            ((DivExpression)multiplicationExpression).accept(this);
        else if (multiplicationExpression instanceof ModExpression)
            ((ModExpression)multiplicationExpression).accept(this);
        else
            ((NegationExpression)multiplicationExpression).accept(this);
    }

    @Override
    public void visit(MultExpression multExpression) {
        multExpression.getLeftOperand().accept(this);
        Value leftOperand = env.getLastValue();

        int lineNr = multExpression.getLeftOperand().getLineNr();
        int posAtLine = multExpression.getLeftOperand().getPositionAtLine();
        checkIfTypeIsNumerical(leftOperand, "Left operand of mult expression", lineNr, posAtLine);

        multExpression.getRightOperand().accept(this);
        Value rightOperand = env.getLastValue();

        lineNr = multExpression.getRightOperand().getLineNr();
        posAtLine = multExpression.getRightOperand().getPositionAtLine();
        checkIfTypeIsNumerical(rightOperand, "Right operand of mult expression", lineNr, posAtLine);

        if (leftOperand instanceof EvalIntValue && rightOperand instanceof EvalIntValue) {
            BigInteger result = ((EvalIntValue)leftOperand).getValue().multiply(((EvalIntValue)rightOperand).getValue());
            env.setLastValue(new EvalIntValue(result));
        }
        else if (leftOperand instanceof EvalDoubleValue && rightOperand instanceof EvalDoubleValue) {
            BigDecimal result = ((EvalDoubleValue)leftOperand).getValue().multiply(((EvalDoubleValue)rightOperand).getValue());
            env.setLastValue(new EvalDoubleValue(result));
        }
        else {
            String desc = "Incorrectness of types of multiplying values - type of right operand is wrong";
            lineNr = multExpression.getRightOperand().getLineNr();
            posAtLine = multExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }
    }

    @Override
    public void visit(DivExpression divExpression) {
        divExpression.getLeftOperand().accept(this);
        Value leftOperand = env.getLastValue();

        int lineNr = divExpression.getLeftOperand().getLineNr();
        int posAtLine = divExpression.getLeftOperand().getPositionAtLine();
        checkIfTypeIsNumerical(leftOperand, "Left operand of div expression", lineNr, posAtLine);

        divExpression.getRightOperand().accept(this);
        Value rightOperand = env.getLastValue();

        lineNr = divExpression.getRightOperand().getLineNr();
        posAtLine = divExpression.getRightOperand().getPositionAtLine();
        checkIfTypeIsNumerical(rightOperand, "Right operand of div expression", lineNr, posAtLine);

        if (leftOperand instanceof EvalIntValue && rightOperand instanceof EvalIntValue) {
            if (((EvalIntValue)rightOperand).getValue().compareTo(BigInteger.ZERO) == 0) {
                String desc = "Division by 0 is prohibited!";
                lineNr = divExpression.getRightOperand().getLineNr();
                posAtLine = divExpression.getRightOperand().getPositionAtLine();
                throw new SemanticError(lineNr, posAtLine, desc);
            }

            BigInteger result = ((EvalIntValue)leftOperand).getValue().divide(((EvalIntValue)rightOperand).getValue());
            env.setLastValue(new EvalIntValue(result));
        }
        else if (leftOperand instanceof EvalDoubleValue && rightOperand instanceof EvalDoubleValue) {
            if (((EvalDoubleValue)rightOperand).getValue().compareTo(BigDecimal.ZERO) == 0) {
                String desc = "Division by 0.0 is prohibited!";
                lineNr = divExpression.getRightOperand().getLineNr();
                posAtLine = divExpression.getRightOperand().getPositionAtLine();
                throw new SemanticError(lineNr, posAtLine, desc);
            }

            BigDecimal result = ((EvalDoubleValue)leftOperand).getValue().
                    divide(((EvalDoubleValue)rightOperand).getValue(), 10, RoundingMode.HALF_UP);
            env.setLastValue(new EvalDoubleValue(result));
        }
        else {
            String desc = "Incorrectness of types of dividing values - type of right operand is wrong";
            lineNr = divExpression.getRightOperand().getLineNr();
            posAtLine = divExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }
    }

    @Override
    public void visit(ModExpression modExpression) {
        modExpression.getLeftOperand().accept(this);
        Value leftOperand = env.getLastValue();

        int lineNr = modExpression.getLeftOperand().getLineNr();
        int posAtLine = modExpression.getLeftOperand().getPositionAtLine();
        checkIfTypeIsNumerical(leftOperand, "Left operand of mod expression", lineNr, posAtLine);

        modExpression.getRightOperand().accept(this);
        Value rightOperand = env.getLastValue();

        lineNr = modExpression.getRightOperand().getLineNr();
        posAtLine = modExpression.getRightOperand().getPositionAtLine();
        checkIfTypeIsNumerical(rightOperand, "Right operand of mod expression", lineNr, posAtLine);

        if (rightOperand instanceof EvalDoubleValue) {
            String desc = "Mod by double value is prohibited!";
            lineNr = modExpression.getRightOperand().getLineNr();
            posAtLine = modExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }
        if (leftOperand instanceof EvalDoubleValue) {
            String desc = "Incorrectness of types of values in mod expression! - left operand is wrong";
            lineNr = modExpression.getLeftOperand().getLineNr();
            posAtLine = modExpression.getLeftOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        if (leftOperand instanceof EvalIntValue && rightOperand instanceof EvalIntValue) {
            BigInteger result = ((EvalIntValue)leftOperand).getValue().mod(((EvalIntValue)rightOperand).getValue());
            env.setLastValue(new EvalIntValue(result));
        }
        else {
            String desc = "Incorrectness of types of values in mod expression - type of right operand is wrong";
            lineNr = modExpression.getRightOperand().getLineNr();
            posAtLine = modExpression.getRightOperand().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }
    }

    @Override
    public void visit(NegationExpression negationExpression) {
        if (negationExpression instanceof NotExpression)
            ((NotExpression)negationExpression).accept(this);
        else if (negationExpression instanceof NegativeExpression)
            ((NegativeExpression)negationExpression).accept(this);
        else
            ((SimpleExpression)negationExpression).accept(this);
    }

    @Override
    public void visit(NotExpression notExpression) {
        notExpression.getExpression().accept(this);
        Value value = env.getLastValue();

        if (!(value instanceof EvalBoolValue)) {
            String desc = "Not expression '!' is permitted only for bool";
            int lineNr = notExpression.getExpression().getLineNr();
            int posAtLine = notExpression.getExpression().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        if (((EvalBoolValue) value).getValue())
            env.setLastValue(new EvalBoolValue(false));
        else
            env.setLastValue(new EvalBoolValue(true));
    }

    @Override
    public void visit(NegativeExpression negativeExpression) {
        negativeExpression.getExpression().accept(this);
        Value value = env.getLastValue();

        if (!(value instanceof EvalIntValue || value instanceof EvalDoubleValue)) {
            String desc = "Negative expression '-' is permitted only for int or double";
            int lineNr = negativeExpression.getExpression().getLineNr();
            int posAtLine = negativeExpression.getExpression().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        if (value instanceof EvalIntValue) {
            BigInteger result = BigInteger.ZERO.divide(((EvalIntValue)value).getValue());
            env.setLastValue(new EvalIntValue(result));
        }
        else {  //double value
            BigDecimal result = BigDecimal.ZERO.divide(((EvalDoubleValue)value).getValue());
            env.setLastValue(new EvalDoubleValue(result));
        }
    }

    @Override
    public void visit(SimpleExpression simpleExpression) {
        if (simpleExpression instanceof DoubleValue)
            ((DoubleValue)simpleExpression).accept(this);
        else if (simpleExpression instanceof FalseExpression)
            ((FalseExpression)simpleExpression).accept(this);
        else if (simpleExpression instanceof FuncCall)
            ((FuncCall)simpleExpression).accept(this);
        else if (simpleExpression instanceof Identifier)
            ((Identifier)simpleExpression).accept(this);
        else if (simpleExpression instanceof IntValue)
            ((IntValue)simpleExpression).accept(this);
        else if (simpleExpression instanceof ParenthExpression)
            ((ParenthExpression)simpleExpression).accept(this);
        else if (simpleExpression instanceof StringValue)
            ((StringValue)simpleExpression).accept(this);
        else if (simpleExpression instanceof StructFieldExpression)
            ((StructFieldExpression)simpleExpression).accept(this);
        else
            ((TrueExpression)simpleExpression).accept(this);
    }

    @Override
    public void visit(IntValue intValue) {
        env.setLastValue(new EvalIntValue(intValue.getValue()));
    }

    @Override
    public void visit(DoubleValue doubleValue) {
        env.setLastValue(new EvalDoubleValue(doubleValue.getValue()));
    }

    @Override
    public void visit(TrueExpression trueExpression) {
        env.setLastValue(new EvalBoolValue(true));
    }

    @Override
    public void visit(FalseExpression falseExpression) {
        env.setLastValue(new EvalBoolValue(false));
    }

    @Override
    public void visit(StringValue stringValue) {
        env.setLastValue(new EvalStringValue(stringValue.getValue()));
    }

    @Override
    public void visit(ParenthExpression parenthExpression) {
        parenthExpression.getExpression().accept(this);
    }

    @Override
    public void visit(Identifier identifier) {
        ArrayList<String> name = new ArrayList<>();
        name.add(identifier.getName());
        int lineNr = identifier.getLineNr();
        int posAtLine = identifier.getPositionAtLine();
        env.setLastBoxAndValue(name, lineNr, posAtLine);
    }

    @Override
    public void visit(StructFieldExpression structFieldExpression) {
        ArrayList<String> resultName = unwrapStructFieldExp(structFieldExpression, new ArrayList<>());

        int lineNr = structFieldExpression.getLineNr();
        int posAtLine = structFieldExpression.getPositionAtLine();
        env.setLastBoxAndValue(resultName, lineNr, posAtLine);
    }

    @Override
    public void visit(FuncCall funcCall) {
        String name = funcCall.getFuncName().getName();

        if (env.getEmbeddedFunctions().containsKey(name)) {
            runEmbeddedFunction(funcCall);
            return;
        }


        if (!env.getFuncDefs().containsKey(name)) {
            String desc = "Unknown name of function";
            int lineNr = funcCall.getFuncName().getLineNr();
            int posAtLine = funcCall.getFuncName().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        FuncDefinition funcDefinition = env.getFuncDefs().get(name);

        ArrayList<Arg> args = new ArrayList<>();

        if (funcCall.getParams() != null) {
            if (funcCall.getParams().size() > funcDefinition.getArgs().size()) {
                String desc = "Too much params in func call - " + funcDefinition.getArgs().size() + " params are permitted";
                int lineNr = funcCall.getParams().get(funcCall.getParams().size()-1).getLineNr();
                int posAtLine = funcCall.getParams().get(funcCall.getParams().size()-1).getPositionAtLine();
                throw new SemanticError(lineNr, posAtLine, desc);
            }

            for (int i = 0; i < funcCall.getParams().size(); ++i) {
                funcCall.getParams().get(i).accept(this);  //accept param as expression
                Value evalParam = env.getLastValue();

                String inf = "param of func call";
                String desc = checkTypeAndValueCorrectness(inf, funcDefinition.getArgs().get(i).getType(), evalParam);
                if (desc != null) {
                    int lineNr = funcCall.getParams().get(i).getLineNr();
                    int posAtLine = funcCall.getParams().get(i).getPositionAtLine();
                    throw new SemanticError(lineNr, posAtLine, desc);
                }

                Type argType = funcDefinition.getArgs().get(i).getType();
                String argName = funcDefinition.getArgs().get(i).getName();
                args.add(new Arg(argType, argName, evalParam));
            }
        }

        //complete other params by defult values of args in function definition
        for (int i = args.size(); i < funcDefinition.getArgs().size(); ++i) {
            Type argType = funcDefinition.getArgs().get(i).getType();
            String argName = funcDefinition.getArgs().get(i).getName();
            Value argDefValue = funcDefinition.getArgs().get(i).getDefValue();
            args.add(new Arg(argType, argName, argDefValue));
        }

        if (args.size() != funcDefinition.getArgs().size()) {
            String desc = "Incorrect number or type of params in func call";
            int lineNr = funcCall.getParams().get(funcCall.getParams().size()-1).getLineNr();
            int posAtLine = funcCall.getParams().get(funcCall.getParams().size()-1).getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.makeCallContext();
        env.makeBlockContext();
        //put args to call context
        for (int i = 0; i < args.size(); ++i) {
            int lineNr = funcCall.getParams().get(i).getLineNr();
            int posAtLine = funcCall.getParams().get(i).getPositionAtLine();
            env.makeVar(args.get(i).getName(), args.get(i).getDefValue(), lineNr, posAtLine);
        }

        Type retType = funcDefinition.getRetType();

        funcDefinition.getBlock().accept(this);

        env.setIfFuncEnd(false);

        Value returnedValue = env.getLastValue();
        if (returnedValue == null && !(retType instanceof VoidType)) {
            String desc = "Non void function returned nothing";
            int lineNr = funcCall.getFuncName().getLineNr();
            int posAtLine = funcCall.getFuncName().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        String desc;
        String inf = "returned value from func call";
        if ((desc = checkTypeAndValueCorrectness(inf, retType, returnedValue)) != null) {
            int lineNr = funcCall.getFuncName().getLineNr();
            int posAtLine = funcCall.getFuncName().getPositionAtLine();
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.deleteBlockContext();
        env.deleteCallContext();
        //returned value is already set as last value in env
    }




    private String checkTypeAndValueCorrectness(String inf, Type type, Value value) {
        if (type instanceof IntType && !(value instanceof EvalIntValue)) {
            return "Type required by " + inf + " is int, but value isn't int";
        }
        else if (type instanceof DoubleType && !(value instanceof EvalDoubleValue)) {
            return "Type required by " + inf + " is double, but value isn't double";
        }
        else if (type instanceof BoolType && !(value instanceof EvalBoolValue)) {
            return "Type required by " + inf + " is bool, but value isn't bool";
        }
        else if (type instanceof StringType && !(value instanceof EvalStringValue)) {
            return "Type required by " + inf + " is string, but value isn't string";
        }
        else if (type instanceof StructType) {
            if (!(value instanceof EvalStructValue))
                return "Type required by " + inf + " is struct, but value isn't struct";
            else if (!((StructType) type).getId().getName().equals(((EvalStructValue) value).getStructType()))
                return "Type required by " + inf + " is " + ((StructType)type).getId().getName() + ", but value is" +
                        "type of " + ((EvalStructValue)value).getStructType();
        }

        return null;
    }

    private boolean ifNameOfArgIsUnique(String name, ArrayList<Arg> args) {
        for (Arg arg : args) {
            if (arg.getName().equals(name))
                return false;
        }
        return true;
    }

    private void checkIfTypeIsNumerical(Value value, String inf, int lineNr, int posAtLine) {
        if (value instanceof EvalStructValue) {
            String desc = inf + " is struct - it is prohibited";
            throw new SemanticError(lineNr, posAtLine, desc);
        }
        else if (value instanceof EvalBoolValue) {
            String desc = inf + " is bool - it is prohibited";
            throw new SemanticError(lineNr, posAtLine, desc);
        }
        else if (value instanceof EvalStringValue) {
            String desc = inf + " is string - it is prohibited";
            throw new SemanticError(lineNr, posAtLine, desc);
        }
    }

    private void checkIfTypeIsNumericalOrString(Value value, String inf, int lineNr, int posAtLine) {
        if (value instanceof EvalStructValue) {
            String desc = inf + " is struct - it is prohibited";
            throw new SemanticError(lineNr, posAtLine, desc);
        }
        else if (value instanceof EvalBoolValue) {
            String desc = inf + " is bool - it is prohibited";
            throw new SemanticError(lineNr, posAtLine, desc);
        }
    }

    private boolean ifSimpleValuesEquality(Value val1, Value val2) {
        if (val1 instanceof EvalIntValue && val2 instanceof EvalIntValue) {
            if (((EvalIntValue) val1).getValue().compareTo(((EvalIntValue)val2).getValue()) == 0) {  //val1 equals val2
                return true;
            }
        }
        else if (val1 instanceof EvalDoubleValue && val2 instanceof EvalDoubleValue) {
            if (((EvalDoubleValue) val1).getValue().compareTo(((EvalDoubleValue)val2).getValue()) == 0) {  //val1 equals val2
                return true;
            }
        }
        else if (val1 instanceof EvalBoolValue && val2 instanceof EvalBoolValue) {
            if (((EvalBoolValue) val1).getValue() == ((EvalBoolValue)val2).getValue()) {  //val1 equals val2
                return true;
            }
        }
        else if (val1 instanceof EvalStringValue && val2 instanceof EvalStringValue) {
            if (((EvalStringValue) val1).getValue().equals(((EvalStringValue) val2).getValue())) {  //val1 equals val2
                return true;
            }
        }
        else {
            String desc = "Incorrectness of types of comparing values";
            throw new SemanticError(-1, -1, desc);
        }

        return false;
    }

    private boolean ifSimpleValueLessThanAnother(Value val1, Value val2) {
        if (val1 instanceof EvalIntValue && val2 instanceof EvalIntValue) {
            if (((EvalIntValue) val1).getValue().compareTo(((EvalIntValue)val2).getValue()) < 0) {  //val1 less than val2
                return true;
            }
        }
        else if (val1 instanceof EvalDoubleValue && val2 instanceof EvalDoubleValue) {
            if (((EvalDoubleValue) val1).getValue().compareTo(((EvalDoubleValue)val2).getValue()) < 0) {  //val1 less than val2
                return true;
            }
        }
        else {
            String desc = "Incorrectness of types of comparing values";
            throw new SemanticError(-1, -1, desc);
        }

        return false;
    }

    private boolean ifSimpleValueLessEqualThanAnother(Value val1, Value val2) {
        if (val1 instanceof EvalIntValue && val2 instanceof EvalIntValue) {
            if ((((EvalIntValue) val1).getValue().compareTo(((EvalIntValue)val2).getValue()) < 0) ||
                    (((EvalIntValue) val1).getValue().compareTo(((EvalIntValue)val2).getValue()) == 0)) {
                return true;    //val1 less or equal than val2
            }
        }
        else if (val1 instanceof EvalDoubleValue && val2 instanceof EvalDoubleValue) {
            if ((((EvalDoubleValue) val1).getValue().compareTo(((EvalDoubleValue)val2).getValue()) < 0) ||
                    (((EvalDoubleValue) val1).getValue().compareTo(((EvalDoubleValue)val2).getValue()) == 0)) {
                return true;    //val1 less or equal than val2
            }
        }
        else {
            String desc = "Incorrectness of types of comparing values";
            throw new SemanticError(-1, -1, desc);
        }

        return false;
    }

    private boolean ifSimpleValueGreaterThanAnother(Value val1, Value val2) {
        if (val1 instanceof EvalIntValue && val2 instanceof EvalIntValue) {
            if (((EvalIntValue) val1).getValue().compareTo(((EvalIntValue)val2).getValue()) > 0) {  //val1 greater than val2
                return true;
            }
        }
        else if (val1 instanceof EvalDoubleValue && val2 instanceof EvalDoubleValue) {
            if (((EvalDoubleValue) val1).getValue().compareTo(((EvalDoubleValue)val2).getValue()) > 0) {  //val1 greater than val2
                return true;
            }
        }
        else {
            String desc = "Incorrectness of types of comparing values";
            throw new SemanticError(-1, -1, desc);
        }

        return false;
    }

    private boolean ifSimpleValueGreaterEqualThanAnother(Value val1, Value val2) {
        if (val1 instanceof EvalIntValue && val2 instanceof EvalIntValue) {
            if ((((EvalIntValue) val1).getValue().compareTo(((EvalIntValue)val2).getValue()) > 0) ||
                    (((EvalIntValue) val1).getValue().compareTo(((EvalIntValue)val2).getValue()) == 0)) {
                return true;    //val1 greater or equal than val2
            }
        }
        else if (val1 instanceof EvalDoubleValue && val2 instanceof EvalDoubleValue) {
            if ((((EvalDoubleValue) val1).getValue().compareTo(((EvalDoubleValue)val2).getValue()) > 0) ||
                    (((EvalDoubleValue) val1).getValue().compareTo(((EvalDoubleValue)val2).getValue()) == 0)) {
                return true;    //val1 greater or equal than val2
            }
        }
        else {
            String desc = "Incorrectness of types of comparing values";
            throw new SemanticError(-1, -1, desc);
        }

        return false;
    }

    private ArrayList<String> unwrapStructFieldExp(StructFieldExpression structFieldExpression, ArrayList<String> name) {
        String id = structFieldExpression.getStructVarName().getName();
        name.add(id);

        SimpleExpression field = structFieldExpression.getFieldName();
        if (field instanceof Identifier) {
            name.add(((Identifier)field).getName());
            return name;
        }

        //field instanceof StructFieldExpression
        return unwrapStructFieldExp((StructFieldExpression)field, name);
    }



    //embedded functions

    private void runEmbeddedFunction(FuncCall funcCall) {
        String name = funcCall.getFuncName().getName();
        int lineNr = funcCall.getFuncName().getLineNr();
        int posAtLine = funcCall.getFuncName().getPositionAtLine();

        if (name.equals("readInt") || name.equals("readDouble") || name.equals("readStr")) {
            runRead(name, funcCall.getParams(), lineNr, posAtLine);
        }
        else if (name.equals("printInt")) {
            runPrintInt(name, funcCall.getParams(), lineNr, posAtLine);
        }
        else if (name.equals("printDouble")) {
            runPrintDouble(name, funcCall.getParams(), lineNr, posAtLine);
        }
        else if (name.equals("printStr")) {
            runPrintStr(name, funcCall.getParams(), lineNr, posAtLine);
        }
        else if (name.equals("error")) {
            runError(name, funcCall.getParams(), lineNr, posAtLine);
        }
        else if (name.equals("convertIntToDouble")) {
            runConvertIntToDouble(name, funcCall.getParams(), lineNr, posAtLine);
        }
        else if (name.equals("convertDoubleToInt")) {
            runConvertDoubleToInt(name, funcCall.getParams(), lineNr, posAtLine);
        }
        else if (name.equals("convertStrToInt")) {
            runConvertStrToInt(name, funcCall.getParams(), lineNr, posAtLine);
        }
        else if (name.equals("convertStrToDouble")) {
            runConvertStrToDouble(name, funcCall.getParams(), lineNr, posAtLine);
        }
        else if (name.equals("convertIntToStr")) {
            runConvertIntToStr(name, funcCall.getParams(), lineNr, posAtLine);
        }
        else {
            runConvertDoubleToStr(name, funcCall.getParams(), lineNr, posAtLine);
        }
    }

    private void runPrintInt(String funcName, ArrayList<Expression> params, int lineNr, int posAtLine) {
        checkNumberOfParams(funcName, params, 1, lineNr, posAtLine);

        params.get(0).accept(this);

        if (!(env.getLastValue() instanceof EvalIntValue)) {
            String desc = "Incorrect type of param in printInt() call - int is required";
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.getEmbeddedFunctions().get(funcName).run();
    }

    private void runPrintDouble(String funcName, ArrayList<Expression> params, int lineNr, int posAtLine) {
        checkNumberOfParams(funcName, params, 1, lineNr, posAtLine);

        params.get(0).accept(this);

        if (!(env.getLastValue() instanceof EvalDoubleValue)) {
            String desc = "Incorrect type of param in printDouble() call - double is required";
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.getEmbeddedFunctions().get(funcName).run();
    }

    private void runPrintStr(String funcName, ArrayList<Expression> params, int lineNr, int posAtLine) {
        checkNumberOfParams(funcName, params, 1, lineNr, posAtLine);

        params.get(0).accept(this);

        if (!(env.getLastValue() instanceof EvalStringValue)) {
            String desc = "Incorrect type of param in printStr() call - string is required";
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.getEmbeddedFunctions().get(funcName).run();
    }

    private void runRead(String funcName, ArrayList<Expression> params, int lineNr, int posAtLine) {
        checkNumberOfParams(funcName, params, 0, lineNr, posAtLine);
        env.getEmbeddedFunctions().get(funcName).run();
    }

    private void runError(String funcName, ArrayList<Expression> params, int lineNr, int posAtLine) {
        checkNumberOfParams(funcName, params, 1, lineNr, posAtLine);

        params.get(0).accept(this);

        if (!(env.getLastValue() instanceof EvalStringValue)) {
            String desc = "Incorrect type of param in error() call - string is required";
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.getEmbeddedFunctions().get(funcName).run();
    }

    private void runConvertIntToDouble(String funcName, ArrayList<Expression> params, int lineNr, int posAtLine) {
        checkNumberOfParams(funcName, params, 1, lineNr, posAtLine);

        params.get(0).accept(this);

        if (!(env.getLastValue() instanceof EvalIntValue)) {
            String desc = "Incorrect type of param in convertIntToDouble() call - int is required";
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.getEmbeddedFunctions().get(funcName).run();
    }

    private void runConvertDoubleToInt(String funcName, ArrayList<Expression> params, int lineNr, int posAtLine) {
        checkNumberOfParams(funcName, params, 1, lineNr, posAtLine);

        params.get(0).accept(this);

        if (!(env.getLastValue() instanceof EvalDoubleValue)) {
            String desc = "Incorrect type of param in convertDoubleToInt() call - double is required";
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.getEmbeddedFunctions().get(funcName).run();
    }

    private void runConvertStrToInt(String funcName, ArrayList<Expression> params, int lineNr, int posAtLine) {
        checkNumberOfParams(funcName, params, 1, lineNr, posAtLine);

        params.get(0).accept(this);

        if (!(env.getLastValue() instanceof EvalStringValue)) {
            String desc = "Incorrect type of param in convertStrToInt() call - string is required";
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.getEmbeddedFunctions().get(funcName).run();
    }

    private void runConvertStrToDouble(String funcName, ArrayList<Expression> params, int lineNr, int posAtLine) {
        checkNumberOfParams(funcName, params, 1, lineNr, posAtLine);

        params.get(0).accept(this);

        if (!(env.getLastValue() instanceof EvalStringValue)) {
            String desc = "Incorrect type of param in convertStrToDouble() call - string is required";
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.getEmbeddedFunctions().get(funcName).run();
    }

    private void runConvertIntToStr(String funcName, ArrayList<Expression> params, int lineNr, int posAtLine) {
        checkNumberOfParams(funcName, params, 1, lineNr, posAtLine);

        params.get(0).accept(this);

        if (!(env.getLastValue() instanceof EvalIntValue)) {
            String desc = "Incorrect type of param in convertIntToStr() call - int is required";
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.getEmbeddedFunctions().get(funcName).run();
    }

    private void runConvertDoubleToStr(String funcName, ArrayList<Expression> params, int lineNr, int posAtLine) {
        checkNumberOfParams(funcName, params, 1, lineNr, posAtLine);

        params.get(0).accept(this);

        if (!(env.getLastValue() instanceof EvalDoubleValue)) {
            String desc = "Incorrect type of param in convertDoubleToStr() call - double is required";
            throw new SemanticError(lineNr, posAtLine, desc);
        }

        env.getEmbeddedFunctions().get(funcName).run();
    }


    private void checkNumberOfParams(String funcName, ArrayList<Expression> params, int nrOfParamsRequired,
                                     int lineNr, int posAtLine) {

        if (params == null && nrOfParamsRequired == 0)
            return;  //OK

        if ((params == null) || (params.size() != nrOfParamsRequired)) {
            String desc = "Incorrect number of params in " + funcName + "() call - " + nrOfParamsRequired + " required";
            throw new SemanticError(lineNr, posAtLine, desc);
        }
    }
}
