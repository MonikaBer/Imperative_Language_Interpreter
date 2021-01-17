package project.parser.declarationTests.declarationInStructDefTests;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.Initialisation;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.*;
import project.program.content.types.BoolType;
import project.program.content.types.DoubleType;
import project.program.content.types.IntType;
import project.program.content.types.StringType;
import project.source.Source;
import project.source.StringSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InitialisationInStructDefTests {

    @Test
    void shouldParseInitialisationOfInts() {
        Source source = new StringSource("struct Student { int a = 1; int b = 2; int c = 3; }");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(0, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(1, structDefs.size());

        StructDef structDef = structDefs.get(0);
        assertEquals("Student", structDef.getId().getName());

        ArrayList<Declaration> body = structDef.getBody();
        assertEquals(3, body.size());

        Declaration stmt = body.get(0);
        assertTrue(stmt instanceof Initialisation);
        assertTrue((stmt).getType() instanceof IntType);
        assertEquals("a", stmt.getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof IntValue);

        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) stmt).getExpression()).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));


        stmt = body.get(1);
        assertTrue(stmt instanceof Initialisation);
        assertTrue((stmt).getType() instanceof IntType);
        assertEquals("b", stmt.getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof IntValue);

        actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) stmt).getExpression()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));


        stmt = body.get(2);
        assertTrue(stmt instanceof Initialisation);
        assertTrue((stmt).getType() instanceof IntType);
        assertEquals("c", stmt.getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof IntValue);

        actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) stmt).getExpression()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseInitialisationOfDoubles() {
        Source source = new StringSource("struct Student { double a = 0.01; double b = 0.02; double c = 0.03; }");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(0, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(1, structDefs.size());

        StructDef structDef = structDefs.get(0);
        assertEquals("Student", structDef.getId().getName());

        ArrayList<Declaration> body = structDef.getBody();
        assertEquals(3, body.size());

        Declaration stmt = body.get(0);
        assertTrue(stmt instanceof Initialisation);
        assertTrue((stmt).getType() instanceof DoubleType);
        assertEquals("a", stmt.getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof DoubleValue);

        BigDecimal actualValue = new BigDecimal(String.valueOf(((DoubleValue)((Initialisation) stmt).getExpression()).getValue()));
        BigDecimal expectedValue = new BigDecimal("0.01");
        assertEquals(0, expectedValue.compareTo(actualValue));


        stmt = body.get(1);
        assertTrue(stmt instanceof Initialisation);
        assertTrue((stmt).getType() instanceof DoubleType);
        assertEquals("b", stmt.getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof DoubleValue);

        actualValue = new BigDecimal(String.valueOf(((DoubleValue)((Initialisation) stmt).getExpression()).getValue()));
        expectedValue = new BigDecimal("0.02");
        assertEquals(0, expectedValue.compareTo(actualValue));


        stmt = body.get(2);
        assertTrue(stmt instanceof Initialisation);
        assertTrue((stmt).getType() instanceof DoubleType);
        assertEquals("c", stmt.getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof DoubleValue);

        actualValue = new BigDecimal(String.valueOf(((DoubleValue)((Initialisation) stmt).getExpression()).getValue()));
        expectedValue = new BigDecimal("0.03");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseInitialisationOfBools() {
        Source source = new StringSource("struct Student { bool a = true; bool b = false; bool c = true; }");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(0, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(1, structDefs.size());

        StructDef structDef = structDefs.get(0);
        assertEquals("Student", structDef.getId().getName());

        ArrayList<Declaration> body = structDef.getBody();
        assertEquals(3, body.size());

        Declaration declaration = body.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("a", declaration.getId().getName());
        assertTrue(((Initialisation)declaration).getExpression() instanceof TrueExpression);

        declaration = body.get(1);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("b", declaration.getId().getName());
        assertTrue(((Initialisation)declaration).getExpression() instanceof FalseExpression);

        declaration = body.get(2);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("c", declaration.getId().getName());
        assertTrue(((Initialisation)declaration).getExpression() instanceof TrueExpression);
    }

    @Test
    void shouldParseInitialisationOfApostropheStrings() {
        Source source = new StringSource("struct Student { string a = 'Ala ma kota'; string b = 'Alek ma psa'; " +
                                            "string c = 'Ola ma rybki'; }");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(0, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(1, structDefs.size());

        StructDef structDef = structDefs.get(0);
        assertEquals("Student", structDef.getId().getName());

        ArrayList<Declaration> body = structDef.getBody();
        assertEquals(3, body.size());

        Declaration declaration = body.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("a", declaration.getId().getName());
        assertEquals("Ala ma kota", ((StringValue)((Initialisation)declaration).getExpression()).getValue());

        declaration = body.get(1);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("Alek ma psa", ((StringValue)((Initialisation)declaration).getExpression()).getValue());

        declaration = body.get(2);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("Ola ma rybki", ((StringValue)((Initialisation)declaration).getExpression()).getValue());
    }

    @Test
    void shouldParseInitialisationOfDoubleQuoteStrings() {
        Source source = new StringSource("struct Student { string a = \"Ala ma kota\"; string b = \"Alek ma psa\"; " +
                "string c = \"Ola ma rybki\"; }");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(0, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(1, structDefs.size());

        StructDef structDef = structDefs.get(0);
        assertEquals("Student", structDef.getId().getName());

        ArrayList<Declaration> body = structDef.getBody();
        assertEquals(3, body.size());

        Declaration declaration = body.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("a", declaration.getId().getName());
        assertEquals("Ala ma kota", ((StringValue)((Initialisation)declaration).getExpression()).getValue());

        declaration = body.get(1);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("Alek ma psa", ((StringValue)((Initialisation)declaration).getExpression()).getValue());

        declaration = body.get(2);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("Ola ma rybki", ((StringValue)((Initialisation)declaration).getExpression()).getValue());
    }
}
