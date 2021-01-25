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

public class WhileTests {

    @Test
    void shouldParseSimpleWhileStatement() {
        Source source = new StringSource("void function() { while (a) ; }");
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
        assertTrue(stmt instanceof While);
        assertTrue(((While)stmt).getCondition() instanceof Identifier);
        assertEquals("a", ((Identifier) ((While)stmt).getCondition()).getName());

        assertTrue(((While)stmt).getStmt() instanceof Empty);
    }

    @Test
    void shouldParseSimpleTrueWhileStatement() {
        Source source = new StringSource("void function() { while (true) ; }");
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
        assertTrue(stmt instanceof While);
        assertTrue(((While)stmt).getCondition() instanceof TrueExpression);

        assertTrue(((While)stmt).getStmt() instanceof Empty);
    }

    @Test
    void shouldParseWhileStatement() {
        Source source = new StringSource("void function() { while (a <= 10) { ++a; --b; } }");
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
        assertTrue(stmt instanceof While);
        assertTrue(((While)stmt).getCondition() instanceof LesserEqualExpression);
        assertTrue(((LesserEqualExpression) ((While)stmt).getCondition()).getLeftOperand() instanceof Identifier);
        assertEquals("a", ((Identifier) ((LesserEqualExpression) ((While)stmt).getCondition()).getLeftOperand()).getName());

        assertTrue(((LesserEqualExpression) ((While)stmt).getCondition()).getRightOperand() instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue) ((LesserEqualExpression) ((While)stmt).getCondition()).getRightOperand()).getValue()));
        BigInteger expectedValue = new BigInteger("10");
        assertEquals(0, expectedValue.compareTo(actualValue));


        assertTrue(((While)stmt).getStmt() instanceof Block);

        Statement stmt1 = ((Block) ((While)stmt).getStmt()).getStmts().get(0);
        assertTrue(stmt1 instanceof Increment);
        assertTrue(((Increment)stmt1).getExpression() instanceof Identifier);
        assertEquals("a", ((Identifier) ((Increment)stmt1).getExpression()).getName());


        Statement stmt2 = ((Block) ((While)stmt).getStmt()).getStmts().get(1);
        assertTrue(stmt2 instanceof Decrement);
        assertTrue(((Decrement)stmt2).getExpression() instanceof Identifier);
        assertEquals("b", ((Identifier) ((Decrement)stmt2).getExpression()).getName());
    }
}
