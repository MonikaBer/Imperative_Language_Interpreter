package project.parser;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.Block;
import project.program.content.statements.Statement;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.Initialisation;
import project.program.content.statements.expressions.Expression;
import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.AddExpression;
import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.MultExpression;
import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.IntValue;
import project.program.content.types.IntType;
import project.program.content.types.VoidType;
import project.source.Source;
import project.source.StringSource;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressionTests {

    @Test
    void shouldParseSimpleAddExpression() {
        Source source = new StringSource("void function() { int a = 1 + 2; }");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(0, declarations.size());
        assertEquals(1, funcDefs.size());
        assertEquals(0, structDefs.size());

        FuncDef funcDef = funcDefs.get(0);
        assertTrue(funcDef.getRetType() instanceof VoidType);
        assertEquals("function", funcDef.getId().getName());

        ArrayList<Declaration> args = funcDef.getArgs();
        assertNull(args);

        Block block = funcDef.getBlock();
        assertEquals(1, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof IntType);
        assertEquals("a", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof AddExpression);

        Expression leftOperandAddExpression = ((AddExpression) expression).getLeftOperand();
        assertTrue(leftOperandAddExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandAddExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandAddExpression = ((AddExpression) expression).getRightOperand();
        assertTrue(rightOperandAddExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandAddExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

//    @Test
//    void shouldParseAddExpression() {
//        Source source = new StringSource("void function() { int a = 1 + 2 + 3; }");
//        Lexer lexer = new Lexer(source);
//        lexer.nextToken();
//        Parser parser = new Parser(lexer);
//        Program program = parser.parseProgram();
//
//        ArrayList<Declaration> declarations = program.getDeclarations();
//        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
//        ArrayList<StructDef> structDefs = program.getStructDefs();
//
//        assertEquals(0, declarations.size());
//        assertEquals(1, funcDefs.size());
//        assertEquals(0, structDefs.size());
//
//        FuncDef funcDef = funcDefs.get(0);
//        assertTrue(funcDef.getRetType() instanceof VoidType);
//        assertEquals("function", funcDef.getId().getName());
//
//        ArrayList<Declaration> args = funcDef.getArgs();
//        assertNull(args);
//
//        Block block = funcDef.getBlock();
//        assertEquals(1, block.getStmts().size());
//
//        Statement stmt = block.getStmts().get(0);
//        assertTrue(stmt instanceof Initialisation);
//        assertTrue(((Initialisation)stmt).getType() instanceof IntType);
//        assertEquals("a", ((Initialisation)stmt).getId().getName());
//
//        Expression expression = ((Initialisation)stmt).getExpression();
//        assertTrue(expression instanceof AddExpression);
//
//        Expression leftOperandAddExpression = ((AddExpression) expression).getLeftOperand();
//        assertTrue(leftOperandAddExpression instanceof IntValue);
//        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandAddExpression).getValue()));
//        BigInteger expectedValue = new BigInteger("1");
//        assertEquals(0, expectedValue.compareTo(actualValue));




//        Expression rightOperandAddExpression = ((AddExpression) expression).getRightOperand();
//        assertTrue(rightOperandAddExpression instanceof AddExpression);
//
//        AddExpression addExpression = (AddExpression) rightOperandAddExpression;
//        assertTrue(addExpression.getLeftOperand() instanceof IntValue);
//        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandAddExpression).getValue()));
//        expectedValue = new BigInteger("2");
//        assertEquals(0, expectedValue.compareTo(actualValue));
//
//        assertTrue(addExpression.getRightOperand() instanceof IntValue);
//        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandAddExpression).getValue()));
//        expectedValue = new BigInteger("2");
//        assertEquals(0, expectedValue.compareTo(actualValue));
//    }

    @Test
    void shouldParseMixExp1() {
        Source source = new StringSource("void function() { int a = 1 + 2 * 3; }");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(0, declarations.size());
        assertEquals(1, funcDefs.size());
        assertEquals(0, structDefs.size());

        FuncDef funcDef = funcDefs.get(0);
        assertTrue(funcDef.getRetType() instanceof VoidType);
        assertEquals("function", funcDef.getId().getName());

        ArrayList<Declaration> args = funcDef.getArgs();
        assertNull(args);

        Block block = funcDef.getBlock();
        assertEquals(1, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof IntType);
        assertEquals("a", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof AddExpression);

        Expression leftOperandAddExpression = ((AddExpression) expression).getLeftOperand();
        assertTrue(leftOperandAddExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandAddExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandAddExpression = ((AddExpression) expression).getRightOperand();
        assertTrue(rightOperandAddExpression instanceof MultExpression);

        MultExpression multExpression = (MultExpression) rightOperandAddExpression;

        Expression leftOperandMultExpression = multExpression.getLeftOperand();
        assertTrue(leftOperandMultExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandMultExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandMultExpression = multExpression.getRightOperand();
        assertTrue(rightOperandMultExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandMultExpression).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }
}
