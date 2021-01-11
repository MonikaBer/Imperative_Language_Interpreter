package project.parser.anotherStatementsTests;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.Block;
import project.program.content.statements.Statement;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.Initialisation;
import project.program.content.statements.expressions.Expression;
import project.program.content.statements.expressions.orExpressions.AlternativeExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.ConjunctionExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.*;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.AddExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.AdditionExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.SubtractExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.DivExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.ModExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.MultExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.NegativeExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.NotExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.*;
import project.program.content.types.BoolType;
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

    @Test
    void shouldParseAddExpression() {
        Source source = new StringSource("void function() { int a = 1 + 2 + 3; }");
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
        assertTrue(rightOperandAddExpression instanceof AddExpression);

        AddExpression addExpression = (AddExpression) rightOperandAddExpression;
        assertTrue(addExpression.getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) addExpression.getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(addExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) addExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseSimpleSubtractExpression() {
        Source source = new StringSource("void function() { int a = 1 - 2; }");
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
        assertTrue(expression instanceof SubtractExpression);

        Expression leftOperandSubtractExpression = ((SubtractExpression) expression).getLeftOperand();
        assertTrue(leftOperandSubtractExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandSubtractExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandAddExpression = ((SubtractExpression) expression).getRightOperand();
        assertTrue(rightOperandAddExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandAddExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseSubtractExpression() {
        Source source = new StringSource("void function() { int a = 1 - 2 - 3; }");
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
        assertTrue(expression instanceof SubtractExpression);

        Expression leftOperandSubtractExpression = ((SubtractExpression) expression).getLeftOperand();
        assertTrue(leftOperandSubtractExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandSubtractExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));



        Expression rightOperandSubtractExpression = ((SubtractExpression) expression).getRightOperand();
        assertTrue(rightOperandSubtractExpression instanceof SubtractExpression);

        SubtractExpression subtractExpression = (SubtractExpression) rightOperandSubtractExpression;
        assertTrue(subtractExpression.getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) subtractExpression.getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(subtractExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) subtractExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }



    @Test
    void shouldParseMixAddSubtractExpression() {
        Source source = new StringSource("void function() { int a = 1 + 2 - 3; }");
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
        assertTrue(rightOperandAddExpression instanceof SubtractExpression);

        SubtractExpression subtractExpression = (SubtractExpression) rightOperandAddExpression;
        assertTrue(subtractExpression.getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) subtractExpression.getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(subtractExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) subtractExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseMixSubtractAddExpression() {
        Source source = new StringSource("void function() { int a = 1 - 2 + 3; }");
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
        assertTrue(expression instanceof SubtractExpression);

        Expression leftOperandSubtractExpression = ((SubtractExpression) expression).getLeftOperand();
        assertTrue(leftOperandSubtractExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandSubtractExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));



        Expression rightOperandSubtractExpression = ((SubtractExpression) expression).getRightOperand();
        assertTrue(rightOperandSubtractExpression instanceof AddExpression);

        AddExpression addExpression = (AddExpression) rightOperandSubtractExpression;
        assertTrue(addExpression.getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) addExpression.getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(addExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) addExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }



    @Test
    void shouldParseSimpleMultExpression() {
        Source source = new StringSource("void function() { int a = 1 * 2; }");
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
        assertTrue(expression instanceof MultExpression);

        Expression leftOperandMultExpression = ((MultExpression) expression).getLeftOperand();
        assertTrue(leftOperandMultExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandMultExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandMultExpression = ((MultExpression) expression).getRightOperand();
        assertTrue(rightOperandMultExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandMultExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseMultExpression() {
        Source source = new StringSource("void function() { int a = 1 * 2 * 3; }");
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
        assertTrue(expression instanceof MultExpression);

        Expression leftOperandMultExpression = ((MultExpression) expression).getLeftOperand();
        assertTrue(leftOperandMultExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandMultExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));



        Expression rightOperandMultExpression = ((MultExpression) expression).getRightOperand();
        assertTrue(rightOperandMultExpression instanceof MultExpression);

        MultExpression multExpression = (MultExpression) rightOperandMultExpression;
        assertTrue(multExpression.getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) multExpression.getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(multExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) multExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseSimpleDivExpression() {
        Source source = new StringSource("void function() { int a = 1 / 2; }");
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
        assertTrue(expression instanceof DivExpression);

        Expression leftOperandDivExpression = ((DivExpression) expression).getLeftOperand();
        assertTrue(leftOperandDivExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandDivExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandDivExpression = ((DivExpression) expression).getRightOperand();
        assertTrue(rightOperandDivExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandDivExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseDivExpression() {
        Source source = new StringSource("void function() { int a = 1 / 2 / 3; }");
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
        assertTrue(expression instanceof DivExpression);

        Expression leftOperandDivExpression = ((DivExpression) expression).getLeftOperand();
        assertTrue(leftOperandDivExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandDivExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));



        Expression rightOperandDivExpression = ((DivExpression) expression).getRightOperand();
        assertTrue(rightOperandDivExpression instanceof DivExpression);

        DivExpression divExpression = (DivExpression) rightOperandDivExpression;
        assertTrue(divExpression.getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) divExpression.getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(divExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) divExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseSimpleModExpression() {
        Source source = new StringSource("void function() { int a = 1 % 2; }");
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
        assertTrue(expression instanceof ModExpression);

        Expression leftOperandModExpression = ((ModExpression) expression).getLeftOperand();
        assertTrue(leftOperandModExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandModExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandModExpression = ((ModExpression) expression).getRightOperand();
        assertTrue(rightOperandModExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandModExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseModExpression() {
        Source source = new StringSource("void function() { int a = 1 % 2 % 3; }");
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
        assertTrue(expression instanceof ModExpression);

        Expression leftOperandModExpression = ((ModExpression) expression).getLeftOperand();
        assertTrue(leftOperandModExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandModExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));



        Expression rightOperandModExpression = ((ModExpression) expression).getRightOperand();
        assertTrue(rightOperandModExpression instanceof ModExpression);

        ModExpression modExpression = (ModExpression) rightOperandModExpression;
        assertTrue(modExpression.getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) modExpression.getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(modExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) modExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }


    @Test
    void shouldParseMixMultDivExpression() {
        Source source = new StringSource("void function() { int a = 1 * 2 / 3; }");
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
        assertTrue(expression instanceof MultExpression);

        Expression leftOperandMultExpression = ((MultExpression) expression).getLeftOperand();
        assertTrue(leftOperandMultExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandMultExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));



        Expression rightOperandMultExpression = ((MultExpression) expression).getRightOperand();
        assertTrue(rightOperandMultExpression instanceof DivExpression);

        DivExpression divExpression = (DivExpression) rightOperandMultExpression;
        assertTrue(divExpression.getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) divExpression.getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(divExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) divExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseMixDivMultExpression() {
        Source source = new StringSource("void function() { int a = 1 / 2 * 3; }");
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
        assertTrue(expression instanceof DivExpression);

        Expression leftOperandDivExpression = ((DivExpression) expression).getLeftOperand();
        assertTrue(leftOperandDivExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandDivExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));



        Expression rightOperandDivExpression = ((DivExpression) expression).getRightOperand();
        assertTrue(rightOperandDivExpression instanceof MultExpression);

        MultExpression multExpression = (MultExpression) rightOperandDivExpression;
        assertTrue(multExpression.getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) multExpression.getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(multExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) multExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseMixMultModExpression() {
        Source source = new StringSource("void function() { int a = 1 * 2 % 3; }");
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
        assertTrue(expression instanceof MultExpression);

        Expression leftOperandMultExpression = ((MultExpression) expression).getLeftOperand();
        assertTrue(leftOperandMultExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandMultExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));



        Expression rightOperandMultExpression = ((MultExpression) expression).getRightOperand();
        assertTrue(rightOperandMultExpression instanceof ModExpression);

        ModExpression modExpression = (ModExpression) rightOperandMultExpression;
        assertTrue(modExpression.getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) modExpression.getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(modExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) modExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseMixModMultExpression() {
        Source source = new StringSource("void function() { int a = 1 % 2 * 3; }");
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
        assertTrue(expression instanceof ModExpression);

        Expression leftOperandModExpression = ((ModExpression) expression).getLeftOperand();
        assertTrue(leftOperandModExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandModExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));



        Expression rightOperandModExpression = ((ModExpression) expression).getRightOperand();
        assertTrue(rightOperandModExpression instanceof MultExpression);

        MultExpression multExpression = (MultExpression) rightOperandModExpression;
        assertTrue(multExpression.getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) multExpression.getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(multExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) multExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseMixDivModExpression() {
        Source source = new StringSource("void function() { int a = 1 / 2 % 3; }");
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
        assertTrue(expression instanceof DivExpression);

        Expression leftOperandDivExpression = ((DivExpression) expression).getLeftOperand();
        assertTrue(leftOperandDivExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandDivExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));



        Expression rightOperandDivExpression = ((DivExpression) expression).getRightOperand();
        assertTrue(rightOperandDivExpression instanceof ModExpression);

        ModExpression modExpression = (ModExpression) rightOperandDivExpression;
        assertTrue(modExpression.getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) modExpression.getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(modExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) modExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseMixModDivExpression() {
        Source source = new StringSource("void function() { int a = 1 % 2 / 3; }");
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
        assertTrue(expression instanceof ModExpression);

        Expression leftOperandModExpression = ((ModExpression) expression).getLeftOperand();
        assertTrue(leftOperandModExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandModExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));



        Expression rightOperandModExpression = ((ModExpression) expression).getRightOperand();
        assertTrue(rightOperandModExpression instanceof DivExpression);

        DivExpression divExpression = (DivExpression) rightOperandModExpression;
        assertTrue(divExpression.getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) divExpression.getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(divExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) divExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }



    @Test
    void shouldParseMixAddMultExpression() {
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

    @Test
    void shouldParseMixMultAddExpression() {
        Source source = new StringSource("void function() { int a = 1 * 2 + 3; }");
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
        assertTrue(leftOperandAddExpression instanceof MultExpression);

        MultExpression multExpression = (MultExpression) ((AddExpression) expression).getLeftOperand();
        assertTrue(multExpression.getLeftOperand() instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) multExpression.getLeftOperand()).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(multExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) multExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));


        Expression rightOperandAddExpression = ((AddExpression) expression).getRightOperand();
        assertTrue(rightOperandAddExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandAddExpression).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }



    @Test
    void shouldParseSimpleEqualExpression() {
        Source source = new StringSource("void function() { bool a = 1 == 2; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("a", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof EqualExpression);

        Expression leftOperandEqualExpression = ((EqualExpression) expression).getLeftOperand();
        assertTrue(leftOperandEqualExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandEqualExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandEqualExpression = ((EqualExpression) expression).getRightOperand();
        assertTrue(rightOperandEqualExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandEqualExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseSimpleNotEqualExpression() {
        Source source = new StringSource("void function() { bool a = 1 != 2; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("a", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof NotEqualExpression);

        Expression leftOperandNotEqualExpression = ((NotEqualExpression) expression).getLeftOperand();
        assertTrue(leftOperandNotEqualExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandNotEqualExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandNotEqualExpression = ((NotEqualExpression) expression).getRightOperand();
        assertTrue(rightOperandNotEqualExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandNotEqualExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseSimpleGreaterEqualExpression() {
        Source source = new StringSource("void function() { bool a = 1 >= 2; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("a", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof GreaterEqualExpression);

        Expression leftOperandGreaterEqualExpression = ((GreaterEqualExpression) expression).getLeftOperand();
        assertTrue(leftOperandGreaterEqualExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandGreaterEqualExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandGreaterEqualExpression = ((GreaterEqualExpression) expression).getRightOperand();
        assertTrue(rightOperandGreaterEqualExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandGreaterEqualExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseSimpleGreaterEqualExpression1() {
        Source source = new StringSource("void function() { bool a = a + b > c; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("a", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof GreaterExpression);

        Expression leftOperandGreaterExpression = ((GreaterExpression) expression).getLeftOperand();
        assertTrue(leftOperandGreaterExpression instanceof AddExpression);

        assertTrue(((AddExpression)leftOperandGreaterExpression).getLeftOperand() instanceof Identifier);
        assertEquals("a", ((Identifier)(((AddExpression)leftOperandGreaterExpression).getLeftOperand())).getName());

        assertEquals("b", ((Identifier)(((AddExpression)leftOperandGreaterExpression).getRightOperand())).getName());


        Expression rightOperandGreaterExpression = ((GreaterExpression) expression).getRightOperand();
        assertTrue(rightOperandGreaterExpression instanceof Identifier);
        assertEquals("c", ((Identifier)(rightOperandGreaterExpression)).getName());
    }

    @Test
    void shouldParseSimpleGreaterExpression() {
        Source source = new StringSource("void function() { bool a = 1 > 2; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("a", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof GreaterExpression);

        Expression leftOperandGreaterExpression = ((GreaterExpression) expression).getLeftOperand();
        assertTrue(leftOperandGreaterExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandGreaterExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandGreaterExpression = ((GreaterExpression) expression).getRightOperand();
        assertTrue(rightOperandGreaterExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandGreaterExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseSimpleLesserEqualExpression() {
        Source source = new StringSource("void function() { bool a = 1 <= 2; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("a", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof LesserEqualExpression);

        Expression leftOperandLesserEqualExpression = ((LesserEqualExpression) expression).getLeftOperand();
        assertTrue(leftOperandLesserEqualExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandLesserEqualExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandLesserEqualExpression = ((LesserEqualExpression) expression).getRightOperand();
        assertTrue(rightOperandLesserEqualExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandLesserEqualExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseSimpleLesserExpression() {
        Source source = new StringSource("void function() { bool a = 1 < 2; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("a", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof LesserExpression);

        Expression leftOperandLesserExpression = ((LesserExpression) expression).getLeftOperand();
        assertTrue(leftOperandLesserExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandLesserExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandLesserExpression = ((LesserExpression) expression).getRightOperand();
        assertTrue(rightOperandLesserExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandLesserExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }


    @Test
    void shouldParseMixGreaterExpression() {
        Source source = new StringSource("void function() { bool a = 1 > 2 > 3; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("a", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof GreaterExpression);

        Expression leftOperandGreaterExpression = ((GreaterExpression) expression).getLeftOperand();
        assertTrue(leftOperandGreaterExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandGreaterExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandGreaterExpression = ((GreaterExpression) expression).getRightOperand();
        assertTrue(rightOperandGreaterExpression instanceof GreaterExpression);

        GreaterExpression greaterExpression = (GreaterExpression) rightOperandGreaterExpression;
        assertTrue(greaterExpression.getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) greaterExpression.getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(greaterExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) greaterExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }



    @Test
    void shouldParseSimpleAlternativeExpression() {
        Source source = new StringSource("void function() { bool cond = a || b; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("cond", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof AlternativeExpression);

        Expression leftOperandAlternativeExpression = ((AlternativeExpression) expression).getLeftOperand();
        assertTrue(leftOperandAlternativeExpression instanceof Identifier);
        assertEquals("a", ((Identifier) leftOperandAlternativeExpression).getName());

        Expression rightOperandAlternativeExpression = ((AlternativeExpression) expression).getRightOperand();
        assertTrue(rightOperandAlternativeExpression instanceof Identifier);
        assertEquals("b", ((Identifier) rightOperandAlternativeExpression).getName());
    }

    @Test
    void shouldParseAlternativeExpression() {
        Source source = new StringSource("void function() { bool cond = a || b || c; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("cond", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof AlternativeExpression);

        Expression leftOperandAlternativeExpression = ((AlternativeExpression) expression).getLeftOperand();
        assertTrue(leftOperandAlternativeExpression instanceof Identifier);
        assertEquals("a", ((Identifier) leftOperandAlternativeExpression).getName());

        Expression rightOperandAlternativeExpression = ((AlternativeExpression) expression).getRightOperand();
        assertTrue(rightOperandAlternativeExpression instanceof AlternativeExpression);

        AlternativeExpression alternativeExpression = (AlternativeExpression) ((AlternativeExpression) expression).getRightOperand();
        assertTrue(alternativeExpression.getLeftOperand() instanceof Identifier);
        assertEquals("b", ((Identifier) alternativeExpression.getLeftOperand()).getName());

        assertTrue(alternativeExpression.getRightOperand() instanceof Identifier);
        assertEquals("c", ((Identifier) alternativeExpression.getRightOperand()).getName());
    }

    @Test
    void shouldParseSimpleConjunctionExpression() {
        Source source = new StringSource("void function() { bool cond = a && b; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("cond", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof ConjunctionExpression);

        Expression leftOperandConjunctionExpression = ((ConjunctionExpression) expression).getLeftOperand();
        assertTrue(leftOperandConjunctionExpression instanceof Identifier);
        assertEquals("a", ((Identifier) leftOperandConjunctionExpression).getName());

        Expression rightOperandConjunctionExpression = ((ConjunctionExpression) expression).getRightOperand();
        assertTrue(rightOperandConjunctionExpression instanceof Identifier);
        assertEquals("b", ((Identifier) rightOperandConjunctionExpression).getName());
    }

    @Test
    void shouldParseConjunctionExpression() {
        Source source = new StringSource("void function() { bool cond = a && b && c; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("cond", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof ConjunctionExpression);

        Expression leftOperandConjunctionExpression = ((ConjunctionExpression) expression).getLeftOperand();
        assertTrue(leftOperandConjunctionExpression instanceof Identifier);
        assertEquals("a", ((Identifier) leftOperandConjunctionExpression).getName());

        Expression rightOperandConjunctionExpression = ((ConjunctionExpression) expression).getRightOperand();
        assertTrue(rightOperandConjunctionExpression instanceof ConjunctionExpression);

        ConjunctionExpression conjunctionExpression = (ConjunctionExpression) ((ConjunctionExpression) expression).getRightOperand();
        assertTrue(conjunctionExpression.getLeftOperand() instanceof Identifier);
        assertEquals("b", ((Identifier) conjunctionExpression.getLeftOperand()).getName());

        assertTrue(conjunctionExpression.getRightOperand() instanceof Identifier);
        assertEquals("c", ((Identifier) conjunctionExpression.getRightOperand()).getName());
    }


    @Test
    void shouldParseMixAlternativeConjunctionExpression() {
        Source source = new StringSource("void function() { bool cond = a || b && c; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("cond", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof AlternativeExpression);

        Expression leftOperandAlternativeExpression = ((AlternativeExpression) expression).getLeftOperand();
        assertTrue(leftOperandAlternativeExpression instanceof Identifier);
        assertEquals("a", ((Identifier) leftOperandAlternativeExpression).getName());

        Expression rightOperandAlternativeExpression = ((AlternativeExpression) expression).getRightOperand();
        assertTrue(rightOperandAlternativeExpression instanceof ConjunctionExpression);

        ConjunctionExpression conjunctionExpression = (ConjunctionExpression) ((AlternativeExpression) expression).getRightOperand();
        assertTrue(conjunctionExpression.getLeftOperand() instanceof Identifier);
        assertEquals("b", ((Identifier) conjunctionExpression.getLeftOperand()).getName());

        assertTrue(conjunctionExpression.getRightOperand() instanceof Identifier);
        assertEquals("c", ((Identifier) conjunctionExpression.getRightOperand()).getName());
    }

    @Test
    void shouldParseConjunctionAlternativeExpression() {
        Source source = new StringSource("void function() { bool cond = a && b || c; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("cond", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof AlternativeExpression);


        Expression leftOperandAlternativeExpression = ((AlternativeExpression) expression).getLeftOperand();
        assertTrue(leftOperandAlternativeExpression instanceof ConjunctionExpression);

        ConjunctionExpression conjunctionExpression = (ConjunctionExpression) ((AlternativeExpression) expression).getLeftOperand();
        assertTrue(conjunctionExpression.getLeftOperand() instanceof Identifier);
        assertEquals("a", ((Identifier) conjunctionExpression.getLeftOperand()).getName());

        assertTrue(conjunctionExpression.getRightOperand() instanceof Identifier);
        assertEquals("b", ((Identifier) conjunctionExpression.getRightOperand()).getName());

        Expression rightOperandAlternativeExpression = ((AlternativeExpression) expression).getRightOperand();
        assertTrue(rightOperandAlternativeExpression instanceof Identifier);
        assertEquals("c", ((Identifier) rightOperandAlternativeExpression).getName());
    }



    @Test
    void shouldParseSimpleNegativeExpression() {
        Source source = new StringSource("void function() { int a = -1; }");
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
        assertTrue(expression instanceof NegativeExpression);

        SimpleExpression simpleExpression = ((NegativeExpression) expression).getExpression();
        assertTrue(simpleExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) simpleExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseSimpleNotExpression() {
        Source source = new StringSource("void function() { bool cond = !a; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("cond", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof NotExpression);

        SimpleExpression simpleExpression = ((NotExpression) expression).getExpression();
        assertTrue(simpleExpression instanceof Identifier);
        assertEquals("a", ((Identifier) simpleExpression).getName());
    }


    @Test
    void shouldParseMixNegativeAddExpression() {
        Source source = new StringSource("void function() { int a = -1 + 2; }");
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

        NegativeExpression leftOperandAddExpression = (NegativeExpression) ((AddExpression) expression).getLeftOperand();
        SimpleExpression simpleExpression = leftOperandAddExpression.getExpression();
        assertTrue(simpleExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) simpleExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        AdditionExpression rightOperandAddExpression = ((AddExpression) expression).getRightOperand();
        assertTrue(rightOperandAddExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandAddExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseMixNegativeSubtractExpression() {
        Source source = new StringSource("void function() { int a = -1 - 2; }");
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
        assertTrue(expression instanceof SubtractExpression);

        NegativeExpression leftOperandSubtractExpression = (NegativeExpression) ((SubtractExpression) expression).getLeftOperand();
        SimpleExpression simpleExpression = leftOperandSubtractExpression.getExpression();
        assertTrue(simpleExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) simpleExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        AdditionExpression rightOperandSubtractExpression = ((SubtractExpression) expression).getRightOperand();
        assertTrue(rightOperandSubtractExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandSubtractExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }


    @Test
    void shouldParseMixNotConjunctionExpression() {
        Source source = new StringSource("void function() { bool cond = !a && !b; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("cond", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof ConjunctionExpression);

        Expression leftOperandConjunctionExpression = ((ConjunctionExpression) expression).getLeftOperand();
        assertTrue(leftOperandConjunctionExpression instanceof NotExpression);
        assertEquals("a", ((Identifier) ((NotExpression)leftOperandConjunctionExpression).getExpression()).getName());

        Expression rightOperandConjunctionExpression = ((ConjunctionExpression) expression).getRightOperand();
        assertTrue(rightOperandConjunctionExpression instanceof NotExpression);
        assertEquals("b", ((Identifier) ((NotExpression)rightOperandConjunctionExpression).getExpression()).getName());
    }



    @Test
    void shouldParseMixParenthAddMultExpression() {
        Source source = new StringSource("void function() { int a = (1 + 2) * 3; }");
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
        assertTrue(expression instanceof MultExpression);

        Expression leftOperandMultExpression = ((MultExpression) expression).getLeftOperand();
        assertTrue(leftOperandMultExpression instanceof ParenthExpression);


        Expression exp = ((ParenthExpression) leftOperandMultExpression).getExpression();
        assertTrue(exp instanceof AddExpression);
        assertTrue(((AddExpression) exp).getLeftOperand() instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) ((AddExpression) exp).getLeftOperand()).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(((AddExpression) exp).getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) ((AddExpression) exp).getRightOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));


        Expression rightOperandMultExpression = ((MultExpression) expression).getRightOperand();
        assertTrue(rightOperandMultExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) ((MultExpression) expression).getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseMixMultParenthAddExpression() {
        Source source = new StringSource("void function() { int a = 1 * (2 + 3); }");
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
        assertTrue(expression instanceof MultExpression);

        Expression leftOperandMultExpression = ((MultExpression) expression).getLeftOperand();
        assertTrue(leftOperandMultExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) ((MultExpression) expression).getLeftOperand()).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandMultExpression = ((MultExpression) expression).getRightOperand();
        assertTrue(rightOperandMultExpression instanceof ParenthExpression);

        Expression exp = ((ParenthExpression) rightOperandMultExpression).getExpression();
        assertTrue(exp instanceof AddExpression);
        assertTrue(((AddExpression) exp).getLeftOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) ((AddExpression) exp).getLeftOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(((AddExpression) exp).getRightOperand() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) ((AddExpression) exp).getRightOperand()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseMixParenthAlternativeConjunctionExpression() {
        Source source = new StringSource("void function() { bool cond = (a || b) && c; }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("cond", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof ConjunctionExpression);

        Expression leftOperandConjunctionExpression = ((ConjunctionExpression) expression).getLeftOperand();
        assertTrue(leftOperandConjunctionExpression instanceof ParenthExpression);

        Expression exp = ((ParenthExpression) leftOperandConjunctionExpression).getExpression();
        assertTrue(exp instanceof AlternativeExpression);
        assertEquals("a", ((Identifier) ((AlternativeExpression) exp).getLeftOperand()).getName());
        assertEquals("b", ((Identifier) ((AlternativeExpression) exp).getRightOperand()).getName());


        Expression rightOperandConjunctionExpression = ((ConjunctionExpression) expression).getRightOperand();
        assertTrue(rightOperandConjunctionExpression instanceof Identifier);
        assertEquals("c", ((Identifier)rightOperandConjunctionExpression).getName());
    }

    @Test
    void shouldParseMixConjunctionParenthAlternativeExpression() {
        Source source = new StringSource("void function() { bool cond = a && (b || c); }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("cond", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof ConjunctionExpression);


        Expression leftOperandConjunctionExpression = ((ConjunctionExpression) expression).getLeftOperand();
        assertTrue(leftOperandConjunctionExpression instanceof Identifier);
        assertEquals("a", ((Identifier)leftOperandConjunctionExpression).getName());


        Expression rightOperandConjunctionExpression = ((ConjunctionExpression) expression).getRightOperand();
        assertTrue(rightOperandConjunctionExpression instanceof ParenthExpression);

        Expression exp = ((ParenthExpression) rightOperandConjunctionExpression).getExpression();
        assertTrue(exp instanceof AlternativeExpression);
        assertEquals("b", ((Identifier) ((AlternativeExpression) exp).getLeftOperand()).getName());
        assertEquals("c", ((Identifier) ((AlternativeExpression) exp).getRightOperand()).getName());
    }

    @Test
    void shouldParseMixNegativeParenthAddExpression() {
        Source source = new StringSource("void function() { int a = -(1 + 2); }");
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
        assertTrue(expression instanceof NegativeExpression);


        ParenthExpression parenthExpression = (ParenthExpression) ((NegativeExpression) expression).getExpression();
        Expression exp = parenthExpression.getExpression();
        assertTrue(exp instanceof AddExpression);

        Expression leftOperandAddExpression = ((AddExpression) exp).getLeftOperand();
        assertTrue(leftOperandAddExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) leftOperandAddExpression).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        Expression rightOperandAddExpression = ((AddExpression) exp).getRightOperand();
        assertTrue(rightOperandAddExpression instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandAddExpression).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseMixNotParenthConjunctionExpression() {
        Source source = new StringSource("void function() { bool cond = !(a && b); }");
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
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("cond", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof NotExpression);


        ParenthExpression parenthExpression = (ParenthExpression) ((NotExpression) expression).getExpression();
        Expression exp = parenthExpression.getExpression();
        assertTrue(exp instanceof ConjunctionExpression);

        Expression leftOperandConjunctionExpression = ((ConjunctionExpression) exp).getLeftOperand();
        assertTrue(leftOperandConjunctionExpression instanceof Identifier);
        assertEquals("a", ((Identifier)leftOperandConjunctionExpression).getName());

        Expression rightOperandConjunctionExpression = ((ConjunctionExpression) exp).getRightOperand();
        assertTrue(rightOperandConjunctionExpression instanceof Identifier);
        assertEquals("b", ((Identifier)rightOperandConjunctionExpression).getName());
    }



    @Test
    void shouldParseMixParenthAddMultWithStructsExpression() {
        Source source = new StringSource("void function() { int var = (student.a + student.b) / 2; }");
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
        assertEquals("var", ((Initialisation)stmt).getId().getName());

        Expression expression = ((Initialisation)stmt).getExpression();
        assertTrue(expression instanceof DivExpression);

        Expression leftOperandDivExpression = ((DivExpression) expression).getLeftOperand();
        assertTrue(leftOperandDivExpression instanceof ParenthExpression);


        Expression exp = ((ParenthExpression) leftOperandDivExpression).getExpression();
        assertTrue(exp instanceof AddExpression);

        AddExpression addExpression = (AddExpression) exp;
        assertTrue(addExpression.getLeftOperand() instanceof StructFieldExpression);
        assertEquals("student", ((StructFieldExpression)addExpression.getLeftOperand()).getStructVarName().getName());
        assertEquals("a", ((Identifier) ((StructFieldExpression)addExpression.getLeftOperand()).getFieldName()).getName());


        assertTrue(addExpression.getRightOperand() instanceof StructFieldExpression);
        assertEquals("student", ((StructFieldExpression)addExpression.getRightOperand()).getStructVarName().getName());
        assertEquals("b", ((Identifier) ((StructFieldExpression)addExpression.getRightOperand()).getFieldName()).getName());

        Expression rightOperandDivExpression = ((DivExpression) expression).getRightOperand();
        assertTrue(rightOperandDivExpression instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) rightOperandDivExpression).getValue()));
        BigInteger expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }



    @Test
    void shouldParseSimpleFuncCallWithoutParamsExpression() {
        Source source = new StringSource("void function() { int a = func(); }");
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
        assertTrue(expression instanceof FuncCall);

        FuncCall funcCall = (FuncCall) expression;
        assertEquals("func", funcCall.getFuncName().getName());
        assertNull(funcCall.getParams());
    }

    @Test
    void shouldParseSimpleFuncCallWithTwoParamsExpression() {
        Source source = new StringSource("void function() { int a = func(b, c); }");
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
        assertTrue(expression instanceof FuncCall);

        FuncCall funcCall = (FuncCall) expression;
        assertEquals("func", funcCall.getFuncName().getName());
        assertEquals(2, funcCall.getParams().size());

        assertTrue(funcCall.getParams().get(0) instanceof Identifier);
        assertEquals("b", ((Identifier)funcCall.getParams().get(0)).getName());

        assertTrue(funcCall.getParams().get(1) instanceof Identifier);
        assertEquals("c", ((Identifier)funcCall.getParams().get(1)).getName());
    }

    @Test
    void shouldParseFuncCallWithAnotherFuncCallAsFirstParamExpression() {
        Source source = new StringSource("void function() { int a = func( func2(b, c), d ); }");
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
        assertTrue(expression instanceof FuncCall);

        FuncCall funcCall = (FuncCall) expression;
        assertEquals("func", funcCall.getFuncName().getName());
        assertEquals(2, funcCall.getParams().size());


        assertTrue(funcCall.getParams().get(0) instanceof FuncCall);
        FuncCall funcCall2 = (FuncCall) funcCall.getParams().get(0);
        assertEquals("func2", funcCall2.getFuncName().getName());
        assertEquals(2, funcCall2.getParams().size());
        assertEquals("b", ((Identifier)funcCall2.getParams().get(0)).getName());
        assertEquals("c", ((Identifier)funcCall2.getParams().get(1)).getName());


        assertTrue(funcCall.getParams().get(1) instanceof Identifier);
        assertEquals("d", ((Identifier)funcCall.getParams().get(1)).getName());
    }

    @Test
    void shouldParseFuncCallWithTwoMixParamsExpression() {
        Source source = new StringSource("void function() { int a = func( (1+2)*3, b&&(c||d) ); }");
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
        assertTrue(expression instanceof FuncCall);

        FuncCall funcCall = (FuncCall) expression;
        assertEquals("func", funcCall.getFuncName().getName());
        assertEquals(2, funcCall.getParams().size());


        assertTrue(funcCall.getParams().get(0) instanceof MultExpression);
        MultExpression multExpression = (MultExpression) funcCall.getParams().get(0);
        assertTrue(multExpression.getLeftOperand() instanceof ParenthExpression);

        Expression exp = ((ParenthExpression) multExpression.getLeftOperand()).getExpression();
        assertTrue(exp instanceof AddExpression);

        AddExpression addExpression = (AddExpression) exp;
        assertTrue(addExpression.getLeftOperand() instanceof IntValue);
        BigInteger actualValue = new BigInteger (String.valueOf(((IntValue)addExpression.getLeftOperand()).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));

        assertTrue(addExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger (String.valueOf(((IntValue)addExpression.getRightOperand()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));


        assertTrue(multExpression.getRightOperand() instanceof IntValue);
        actualValue = new BigInteger (String.valueOf(((IntValue)(multExpression.getRightOperand())).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));


        assertTrue(funcCall.getParams().get(1) instanceof ConjunctionExpression);
        ConjunctionExpression conjunctionExpression = (ConjunctionExpression) funcCall.getParams().get(1);

        assertTrue(conjunctionExpression.getLeftOperand() instanceof Identifier);
        assertEquals("b", ((Identifier) conjunctionExpression.getLeftOperand()).getName());

        assertTrue(conjunctionExpression.getRightOperand() instanceof ParenthExpression);
        exp = ((ParenthExpression) conjunctionExpression.getRightOperand()).getExpression();
        assertTrue(exp instanceof AlternativeExpression);

        AlternativeExpression alternativeExpression = (AlternativeExpression) exp;
        assertTrue(alternativeExpression.getLeftOperand() instanceof Identifier);
        assertEquals("c", ((Identifier)alternativeExpression.getLeftOperand()).getName());

        assertTrue(alternativeExpression.getRightOperand() instanceof Identifier);
        assertEquals("d", ((Identifier)alternativeExpression.getRightOperand()).getName());
    }
}
