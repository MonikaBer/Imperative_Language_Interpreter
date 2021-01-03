package project.parser.declarationTests.declarationInFuncDefTests;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.Block;
import project.program.content.statements.Return;
import project.program.content.statements.Statement;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.Initialisation;
import project.program.content.statements.declarations.OnlyDeclaration;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.*;
import project.program.content.types.*;
import project.source.Source;
import project.source.StringSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InitialisationInFuncDefTests {

    @Test
    void shouldParseInitialisationOfInts() {
        Source source = new StringSource("void function(int a) { int b = 2, c = 3, d = 4; return; }");
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
        assertEquals(1, args.size());

        Declaration declaration = args.get(0);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());

        Block block = funcDef.getBlock();
        assertEquals(4, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof IntType);
        assertEquals("b", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof IntValue);

        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) stmt).getExpression()).getValue()));
        BigInteger expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));


        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof IntType);
        assertEquals("c", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof IntValue);

        actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) stmt).getExpression()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));


        stmt = block.getStmts().get(2);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof IntType);
        assertEquals("d", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof IntValue);

        actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) stmt).getExpression()).getValue()));
        expectedValue = new BigInteger("4");
        assertEquals(0, expectedValue.compareTo(actualValue));


        stmt = block.getStmts().get(3);
        assertTrue(stmt instanceof Return);
        assertNull(((Return) stmt).getExpression());
    }

    @Test
    void shouldParseInitialisationOfDoubles() {
        Source source = new StringSource("void function(int a) { double b = 0.02, c = 0.03, d = 0.04; return; }");
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
        assertEquals(1, args.size());

        Declaration declaration = args.get(0);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());

        Block block = funcDef.getBlock();
        assertEquals(4, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof DoubleType);
        assertEquals("b", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof DoubleValue);

        BigDecimal actualValue = new BigDecimal(String.valueOf(((DoubleValue)((Initialisation) stmt).getExpression()).getValue()));
        BigDecimal expectedValue = new BigDecimal("0.02");
        assertEquals(0, expectedValue.compareTo(actualValue));


        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof DoubleType);
        assertEquals("c", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof DoubleValue);

        actualValue = new BigDecimal(String.valueOf(((DoubleValue)((Initialisation) stmt).getExpression()).getValue()));
        expectedValue = new BigDecimal("0.03");
        assertEquals(0, expectedValue.compareTo(actualValue));


        stmt = block.getStmts().get(2);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof DoubleType);
        assertEquals("d", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof DoubleValue);

        actualValue = new BigDecimal(String.valueOf(((DoubleValue)((Initialisation) stmt).getExpression()).getValue()));
        expectedValue = new BigDecimal("0.04");
        assertEquals(0, expectedValue.compareTo(actualValue));


        stmt = block.getStmts().get(3);
        assertTrue(stmt instanceof Return);
        assertNull(((Return) stmt).getExpression());
    }

    @Test
    void shouldParseInitialisationOfBools() {
        Source source = new StringSource("void function(int a) { bool b = true, c = false, d = true; return; }");
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
        assertEquals(1, args.size());

        Declaration declaration = args.get(0);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());

        Block block = funcDef.getBlock();
        assertEquals(4, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("b", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof TrueExpression);


        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("c", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof FalseExpression);


        stmt = block.getStmts().get(2);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof BoolType);
        assertEquals("d", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof TrueExpression);


        stmt = block.getStmts().get(3);
        assertTrue(stmt instanceof Return);
        assertNull(((Return) stmt).getExpression());
    }

    @Test
    void shouldParseInitialisationOfApostropheStrings() {
        Source source = new StringSource("void function(int a) { string b = 'Ala ma kota', c = 'Alek ma psa', " +
                                            "d = 'Ola ma rybki'; return; }");
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
        assertEquals(1, args.size());

        Declaration declaration = args.get(0);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());

        Block block = funcDef.getBlock();
        assertEquals(4, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof StringType);
        assertEquals("b", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof StringValue);
        assertEquals("Ala ma kota", ((StringValue)((Initialisation) stmt).getExpression()).getValue());


        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof StringType);
        assertEquals("c", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof StringValue);
        assertEquals("Alek ma psa", ((StringValue)((Initialisation) stmt).getExpression()).getValue());


        stmt = block.getStmts().get(2);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof StringType);
        assertEquals("d", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof StringValue);
        assertEquals("Ola ma rybki", ((StringValue)((Initialisation) stmt).getExpression()).getValue());


        stmt = block.getStmts().get(3);
        assertTrue(stmt instanceof Return);
        assertNull(((Return) stmt).getExpression());
    }

    @Test
    void shouldParseInitialisationOfDoubleQuoteStrings() {
        Source source = new StringSource("void function(int a) { string b = \"Ala ma kota\", c = \"Alek ma psa\", " +
                "d = \"Ola ma rybki\"; return; }");
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
        assertEquals(1, args.size());

        Declaration declaration = args.get(0);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());

        Block block = funcDef.getBlock();
        assertEquals(4, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof StringType);
        assertEquals("b", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof StringValue);
        assertEquals("Ala ma kota", ((StringValue)((Initialisation) stmt).getExpression()).getValue());


        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof StringType);
        assertEquals("c", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof StringValue);
        assertEquals("Alek ma psa", ((StringValue)((Initialisation) stmt).getExpression()).getValue());


        stmt = block.getStmts().get(2);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof StringType);
        assertEquals("d", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof StringValue);
        assertEquals("Ola ma rybki", ((StringValue)((Initialisation) stmt).getExpression()).getValue());


        stmt = block.getStmts().get(3);
        assertTrue(stmt instanceof Return);
        assertNull(((Return) stmt).getExpression());
    }
}
