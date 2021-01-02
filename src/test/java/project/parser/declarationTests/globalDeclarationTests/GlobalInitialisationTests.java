package project.parser.declarationTests.globalDeclarationTests;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.Initialisation;
import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.*;
import project.program.content.types.*;
import project.source.Source;
import project.source.StringSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GlobalInitialisationTests {

    @Test
    void shouldParseInitialisationOfInt() {
        Source source = new StringSource("int a = 1;");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(1, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(0, structDefs.size());

        Declaration declaration = declarations.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof IntValue);

        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) declaration).getExpression()).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseInitialisationOfInts() {
        Source source = new StringSource("int a = 1, b = 2, c = 3;");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(3, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(0, structDefs.size());

        Declaration declaration = declarations.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof IntValue);

        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) declaration).getExpression()).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));


        declaration = declarations.get(1);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("b", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof IntValue);

        actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) declaration).getExpression()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));


        declaration = declarations.get(2);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("c", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof IntValue);

        actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) declaration).getExpression()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseInitialisationOfDouble() {
        Source source = new StringSource("double a = 0.01;");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(1, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(0, structDefs.size());

        Declaration declaration = declarations.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof DoubleType);
        assertEquals("a", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof DoubleValue);

        BigDecimal actualValue = new BigDecimal(String.valueOf(((DoubleValue)((Initialisation) declaration).getExpression()).getValue()));
        BigDecimal expectedValue = new BigDecimal("0.01");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseInitialisationOfDoubles() {
        Source source = new StringSource("double a = 0.01, b = 0.02, c = 0.03;");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(3, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(0, structDefs.size());

        Declaration declaration = declarations.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof DoubleType);
        assertEquals("a", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof DoubleValue);

        BigDecimal actualValue = new BigDecimal(String.valueOf(((DoubleValue)((Initialisation) declaration).getExpression()).getValue()));
        BigDecimal expectedValue = new BigDecimal("0.01");
        assertEquals(0, expectedValue.compareTo(actualValue));


        declaration = declarations.get(1);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof DoubleType);
        assertEquals("b", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof DoubleValue);

        actualValue = new BigDecimal(String.valueOf(((DoubleValue)((Initialisation) declaration).getExpression()).getValue()));
        expectedValue = new BigDecimal("0.02");
        assertEquals(0, expectedValue.compareTo(actualValue));


        declaration = declarations.get(2);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof DoubleType);
        assertEquals("c", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof DoubleValue);

        actualValue = new BigDecimal(String.valueOf(((DoubleValue)((Initialisation) declaration).getExpression()).getValue()));
        expectedValue = new BigDecimal("0.03");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseInitialisationOfTrueBool() {
        Source source = new StringSource("bool a = true;");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(1, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(0, structDefs.size());

        Declaration declaration = declarations.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("a", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof TrueExpression);
    }

    @Test
    void shouldParseInitialisationOfTrueBools() {
        Source source = new StringSource("bool a = true, b = true, c = true;");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(3, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(0, structDefs.size());

        Declaration declaration = declarations.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("a", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof TrueExpression);


        declaration = declarations.get(1);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("b", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof TrueExpression);


        declaration = declarations.get(2);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("c", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof TrueExpression);
    }

    @Test
    void shouldParseInitialisationOfFalseBool() {
        Source source = new StringSource("bool a = false;");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(1, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(0, structDefs.size());

        Declaration declaration = declarations.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("a", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof FalseExpression);
    }

    @Test
    void shouldParseInitialisationOfFalseBools() {
        Source source = new StringSource("bool a = false, b = false, c = false;");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(3, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(0, structDefs.size());

        Declaration declaration = declarations.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("a", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof FalseExpression);


        declaration = declarations.get(1);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("b", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof FalseExpression);


        declaration = declarations.get(2);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("c", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof FalseExpression);
    }

    @Test
    void shouldParseInitialisationOfApostropheString() {
        Source source = new StringSource("string a = 'Ala ma kota';");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(1, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(0, structDefs.size());

        Declaration declaration = declarations.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("a", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof StringValue);
        assertEquals("Ala ma kota", ((StringValue)((Initialisation) declaration).getExpression()).getValue());
    }

    @Test
    void shouldParseInitialisationOfApostropheStrings() {
        Source source = new StringSource("string a = 'Ala ma kota', b = 'Alek ma psa', c = 'Ola ma rybki';");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(3, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(0, structDefs.size());

        Declaration declaration = declarations.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("a", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof StringValue);
        assertEquals("Ala ma kota", ((StringValue)((Initialisation) declaration).getExpression()).getValue());


        declaration = declarations.get(1);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("b", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof StringValue);
        assertEquals("Alek ma psa", ((StringValue)((Initialisation) declaration).getExpression()).getValue());


        declaration = declarations.get(2);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("c", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof StringValue);
        assertEquals("Ola ma rybki", ((StringValue)((Initialisation) declaration).getExpression()).getValue());
    }

    @Test
    void shouldParseInitialisationOfDoubleQuoteString() {
        Source source = new StringSource("string a = \"Ala ma kota\";");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(1, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(0, structDefs.size());

        Declaration declaration = declarations.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("a", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof StringValue);
        assertEquals("Ala ma kota", ((StringValue)((Initialisation) declaration).getExpression()).getValue());
    }
}
