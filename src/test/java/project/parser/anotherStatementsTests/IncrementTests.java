package project.parser.anotherStatementsTests;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.Block;
import project.program.content.statements.Increment;
import project.program.content.statements.Statement;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.StructFieldExpression;
import project.program.content.types.VoidType;
import project.source.Source;
import project.source.StringSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncrementTests {

    @Test
    void shouldParseSimpleIncrementStatement() {
        Source source = new StringSource("void function() { ++a; }");
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
        assertTrue(stmt instanceof Increment);
        assertTrue(((Increment)stmt).getExpression() instanceof Identifier);
        assertEquals("a", ((Identifier) ((Increment)stmt).getExpression()).getName());
    }

    @Test
    void shouldParseIncrementStatement() {
        Source source = new StringSource("void function() { ++student.a; }");
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
        assertTrue(stmt instanceof Increment);
        assertTrue(((Increment) stmt).getExpression() instanceof StructFieldExpression);
        StructFieldExpression structFieldExpression = (StructFieldExpression) ((Increment) stmt).getExpression();
        assertEquals("student", structFieldExpression.getStructVarName().getName());
        assertTrue(structFieldExpression.getFieldName() instanceof Identifier);
        assertEquals("a", ((Identifier) structFieldExpression.getFieldName()).getName());
    }
}
