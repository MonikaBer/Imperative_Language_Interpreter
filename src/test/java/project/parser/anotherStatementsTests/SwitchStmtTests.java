package project.parser.anotherStatementsTests;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.*;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.IntValue;
import project.program.content.statements.switchStmt.Case;
import project.program.content.statements.switchStmt.Switch;
import project.program.content.types.VoidType;
import project.source.Source;
import project.source.StringSource;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SwitchStmtTests {

    @Test
    void shouldParseSimpleSwitchStatement() {
        Source source = new StringSource("void function() { switch (a) { case 1: ; case 2: ; default: ; } }");
        Lexer lexer = new Lexer(source);
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
        assertTrue(stmt instanceof Switch);
        assertTrue(((Switch)stmt).getExpression() instanceof Identifier);
        assertEquals("a", ((Identifier) ((Switch)stmt).getExpression()).getName());


        ArrayList<Case> cases = ((Switch)stmt).getCases();

        assertTrue(cases.get(0).getExpression() instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) cases.get(0).getExpression()).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));
        assertTrue(cases.get(0).getStmt() instanceof Empty);

        assertTrue(cases.get(1).getExpression() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) cases.get(1).getExpression()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
        assertTrue(cases.get(1).getStmt() instanceof Empty);

        Statement defaultStmt = ((Switch) stmt).getDefaultStmt();
        assertTrue(defaultStmt instanceof Empty);
    }

    @Test
    void shouldParseSwitchStatement() {
        Source source = new StringSource("void function() { switch (a) { case 1: {++b;} case 2: {++c;} default: {++d;} } }");
        Lexer lexer = new Lexer(source);
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
        assertTrue(stmt instanceof Switch);
        assertTrue(((Switch)stmt).getExpression() instanceof Identifier);
        assertEquals("a", ((Identifier) ((Switch)stmt).getExpression()).getName());


        ArrayList<Case> cases = ((Switch)stmt).getCases();

        assertTrue(cases.get(0).getExpression() instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) cases.get(0).getExpression()).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));
        assertTrue(cases.get(0).getStmt() instanceof Block);
        assertTrue(((Block) cases.get(0).getStmt()).getStmts().get(0) instanceof Increment);
        assertTrue(((Increment) ((Block) cases.get(0).getStmt()).getStmts().get(0)).getExpression() instanceof Identifier);
        assertEquals("b", ((Identifier) ((Increment) ((Block) cases.get(0).getStmt()).getStmts().get(0)).getExpression()).getName());

        assertTrue(cases.get(1).getExpression() instanceof IntValue);
        actualValue = new BigInteger(String.valueOf(((IntValue) cases.get(1).getExpression()).getValue()));
        expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));
        assertTrue(cases.get(1).getStmt() instanceof Block);
        assertTrue(((Block) cases.get(1).getStmt()).getStmts().get(0) instanceof Increment);
        assertTrue(((Increment) ((Block) cases.get(1).getStmt()).getStmts().get(0)).getExpression() instanceof Identifier);
        assertEquals("c", ((Identifier) ((Increment) ((Block) cases.get(1).getStmt()).getStmts().get(0)).getExpression()).getName());

        Statement defaultStmt = ((Switch) stmt).getDefaultStmt();
        assertTrue(defaultStmt instanceof Block);
        assertTrue(((Block) defaultStmt).getStmts().get(0) instanceof Increment);
        assertTrue(((Increment) ((Block) defaultStmt).getStmts().get(0)).getExpression() instanceof Identifier);
        assertEquals("d", ((Identifier) ((Increment) ((Block) defaultStmt).getStmts().get(0)).getExpression()).getName());
    }
}
