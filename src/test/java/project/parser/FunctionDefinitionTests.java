package project.parser;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.*;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.Initialisation;
import project.program.content.statements.declarations.OnlyDeclaration;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.DoubleValue;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.IntValue;
import project.program.content.types.DoubleType;
import project.program.content.types.IntType;
import project.program.content.types.VoidType;
import project.source.Source;
import project.source.StringSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionDefinitionTests {

    @Test
    void shouldParseVoidFuncDefWithoutArgs() {
        Source source = new StringSource("void function() { int b; return; }");
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
        assertEquals(2, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof IntType);
        assertEquals("b", ((OnlyDeclaration)stmt).getId().getName());

        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof VoidReturn);
    }

    @Test
    void shouldParseVoidFuncDefWithOneArg() {
        Source source = new StringSource("void function(int a) { int b; return; }");
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
        assertEquals(1, args.size());

        Declaration declaration = args.get(0);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());

        Block block = funcDef.getBlock();
        assertEquals(2, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof IntType);
        assertEquals("b", ((OnlyDeclaration)stmt).getId().getName());

        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof VoidReturn);
    }

    @Test
    void shouldParseVoidFuncDefWithOneDefaultArg() {
        Source source = new StringSource("void function(int a = 1) { double b; b = 10.01; }");
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
        assertEquals(1, args.size());

        Declaration declaration = args.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());

        assertTrue(((Initialisation) declaration).getExpression() instanceof IntValue);
        BigInteger actualIntValue = new BigInteger(String.valueOf(((IntValue)((Initialisation)declaration).getExpression()).getValue()));
        BigInteger expectedIntValue = new BigInteger("1");
        assertEquals(0, expectedIntValue.compareTo(actualIntValue));

        Block block = funcDef.getBlock();
        assertEquals(2, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof DoubleType);
        assertEquals("b", ((OnlyDeclaration)stmt).getId().getName());

        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof Assignment);
        assertTrue(((Assignment)stmt).getId() instanceof Identifier);
        Identifier id = (Identifier) ((Assignment)stmt).getId();
        assertEquals("b", id.getName());

        assertTrue(((Assignment) stmt).getExpression() instanceof DoubleValue);
        BigDecimal actualDoubleValue = new BigDecimal(String.valueOf(((DoubleValue)((Assignment)stmt).getExpression()).getValue()));
        BigDecimal expectedDoubleValue = new BigDecimal("10.01");
        assertEquals(0, expectedDoubleValue.compareTo(actualDoubleValue));
    }
}
