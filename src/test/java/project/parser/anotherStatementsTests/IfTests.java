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

public class IfTests {

    @Test
    void shouldParseSimpleIfStatement() {
        Source source = new StringSource("void function() { if (a) ; }");
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
        assertTrue(stmt instanceof If);
        assertFalse(stmt instanceof IfElse);
        assertTrue(((If)stmt).getCondition() instanceof Identifier);
        assertEquals("a", ((Identifier) ((If)stmt).getCondition()).getName());

        assertTrue(((If)stmt).getIfStmt() instanceof Empty);
    }

    @Test
    void shouldParseSimpleTrueIfStatement() {
        Source source = new StringSource("void function() { if (true) ; }");
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
        assertTrue(stmt instanceof If);
        assertFalse(stmt instanceof IfElse);
        assertTrue(((If)stmt).getCondition() instanceof TrueExpression);

        assertTrue(((If)stmt).getIfStmt() instanceof Empty);
    }

    @Test
    void shouldParseIfStatement() {
        Source source = new StringSource("void function() { if (a <= 10) { ++a; --b; } }");
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
        assertTrue(stmt instanceof If);
        assertFalse(stmt instanceof IfElse);
        assertTrue(((If)stmt).getCondition() instanceof LesserEqualExpression);
        assertTrue(((LesserEqualExpression) ((If)stmt).getCondition()).getLeftOperand() instanceof Identifier);
        assertEquals("a", ((Identifier) ((LesserEqualExpression) ((If)stmt).getCondition()).getLeftOperand()).getName());

        assertTrue(((LesserEqualExpression) ((If)stmt).getCondition()).getRightOperand() instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) ((LesserEqualExpression) ((If)stmt).getCondition()).getRightOperand()).getValue()));
        BigInteger expectedValue = new BigInteger("10");
        assertEquals(0, expectedValue.compareTo(actualValue));


        assertTrue(((If)stmt).getIfStmt() instanceof Block);

        Statement stmt1 = ((Block) ((If)stmt).getIfStmt()).getStmts().get(0);
        assertTrue(stmt1 instanceof Increment);
        assertTrue(((Increment)stmt1).getExpression() instanceof Identifier);
        assertEquals("a", ((Identifier) ((Increment)stmt1).getExpression()).getName());


        Statement stmt2 = ((Block) ((If)stmt).getIfStmt()).getStmts().get(1);
        assertTrue(stmt2 instanceof Decrement);
        assertTrue(((Decrement)stmt2).getExpression() instanceof Identifier);
        assertEquals("b", ((Identifier) ((Decrement)stmt2).getExpression()).getName());
    }
}
