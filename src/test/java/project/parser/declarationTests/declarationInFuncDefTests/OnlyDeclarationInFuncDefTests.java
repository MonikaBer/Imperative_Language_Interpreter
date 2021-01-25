package project.parser.declarationTests.declarationInFuncDefTests;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.Block;
import project.program.content.statements.Statement;
import project.program.content.statements.VoidReturn;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.OnlyDeclaration;
import project.program.content.types.*;
import project.source.Source;
import project.source.StringSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class OnlyDeclarationInFuncDefTests {

    @Test
    void shouldParseOnlyDeclarationOfInts() {
        Source source = new StringSource("void function(int a) { int b, c, d; return; }");
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
        assertEquals(4, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof IntType);
        assertEquals("b", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof IntType);
        assertEquals("c", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(2);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof IntType);
        assertEquals("d", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(3);
        assertTrue(stmt instanceof VoidReturn);
    }

    @Test
    void shouldParseOnlyDeclarationOfDoubles() {
        Source source = new StringSource("void function(int a) { double b, c, d; return; }");
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
        assertEquals(4, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof DoubleType);
        assertEquals("b", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof DoubleType);
        assertEquals("c", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(2);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof DoubleType);
        assertEquals("d", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(3);
        assertTrue(stmt instanceof VoidReturn);
    }

    @Test
    void shouldParseOnlyDeclarationOfBools() {
        Source source = new StringSource("void function(int a) { bool b, c, d; return; }");
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
        assertEquals(4, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof BoolType);
        assertEquals("b", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof BoolType);
        assertEquals("c", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(2);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof BoolType);
        assertEquals("d", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(3);
        assertTrue(stmt instanceof VoidReturn);
    }

    @Test
    void shouldParseOnlyDeclarationOfStrings() {
        Source source = new StringSource("void function(int a) { string b, c, d; return; }");
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
        assertEquals(4, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof StringType);
        assertEquals("b", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof StringType);
        assertEquals("c", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(2);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof StringType);
        assertEquals("d", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(3);
        assertTrue(stmt instanceof VoidReturn);
    }

    @Test
    void shouldParseOnlyDeclarationOfStructVars() {
        Source source = new StringSource("void function(int a) { Student b, c, d; return; }");
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
        assertEquals(4, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof StructType);
        assertEquals("b", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof StructType);
        assertEquals("c", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(2);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof StructType);
        assertEquals("d", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(3);
        assertTrue(stmt instanceof VoidReturn);
    }
}
