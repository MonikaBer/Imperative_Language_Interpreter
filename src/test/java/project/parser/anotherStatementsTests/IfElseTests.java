package project.parser.anotherStatementsTests;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.*;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.LesserEqualExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.IntValue;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.TrueExpression;
import project.program.content.types.VoidType;
import project.source.Source;
import project.source.StringSource;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IfElseTests {

    @Test
    void shouldParseSimpleIfElseStatement() {
        Source source = new StringSource("void function() { if (a) ; else ; }");
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
        assertTrue(stmt instanceof IfElse);

        assertTrue(((IfElse)stmt).getCondition() instanceof Identifier);
        assertEquals("a", ((Identifier) ((IfElse)stmt).getCondition()).getName());

        assertTrue(((IfElse)stmt).getIfStmt() instanceof Empty);

        assertTrue(((IfElse)stmt).getElseStmt() instanceof Empty);
    }

    @Test
    void shouldParseSimpleTrueIfElseStatement() {
        Source source = new StringSource("void function() { if (true) ;  else ; }");
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
        assertTrue(stmt instanceof IfElse);

        assertTrue(((IfElse)stmt).getCondition() instanceof TrueExpression);

        assertTrue(((IfElse)stmt).getIfStmt() instanceof Empty);

        assertTrue(((IfElse)stmt).getElseStmt() instanceof Empty);
    }

    @Test
    void shouldParseIfElseStatement() {
        Source source = new StringSource("void function() { if (a <= 10) { ++a; --b; } else { --a; ++b; } }");
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
        assertTrue(stmt instanceof IfElse);
        assertTrue(((IfElse)stmt).getCondition() instanceof LesserEqualExpression);
        assertTrue(((LesserEqualExpression) ((IfElse)stmt).getCondition()).getLeftOperand() instanceof Identifier);
        assertEquals("a", ((Identifier) ((LesserEqualExpression) ((IfElse)stmt).getCondition()).getLeftOperand()).getName());

        assertTrue(((LesserEqualExpression) ((IfElse)stmt).getCondition()).getRightOperand() instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) ((LesserEqualExpression) ((IfElse)stmt).getCondition()).getRightOperand()).getValue()));
        BigInteger expectedValue = new BigInteger("10");
        assertEquals(0, expectedValue.compareTo(actualValue));


        assertTrue(((IfElse)stmt).getIfStmt() instanceof Block);

        Statement stmt1 = ((Block) ((IfElse)stmt).getIfStmt()).getStmts().get(0);
        assertTrue(stmt1 instanceof Increment);
        assertTrue(((Increment)stmt1).getExpression() instanceof Identifier);
        assertEquals("a", ((Identifier) ((Increment)stmt1).getExpression()).getName());


        Statement stmt2 = ((Block) ((IfElse)stmt).getIfStmt()).getStmts().get(1);
        assertTrue(stmt2 instanceof Decrement);
        assertTrue(((Decrement)stmt2).getExpression() instanceof Identifier);
        assertEquals("b", ((Identifier) ((Decrement)stmt2).getExpression()).getName());


        assertTrue(((IfElse)stmt).getElseStmt() instanceof Block);

        stmt1 = ((Block) ((IfElse)stmt).getElseStmt()).getStmts().get(0);
        assertTrue(stmt1 instanceof Decrement);
        assertTrue(((Decrement)stmt1).getExpression() instanceof Identifier);
        assertEquals("a", ((Identifier) ((Decrement)stmt1).getExpression()).getName());


        stmt2 = ((Block) ((IfElse)stmt).getElseStmt()).getStmts().get(1);
        assertTrue(stmt2 instanceof Increment);
        assertTrue(((Increment)stmt2).getExpression() instanceof Identifier);
        assertEquals("b", ((Identifier) ((Increment)stmt2).getExpression()).getName());
    }
}
