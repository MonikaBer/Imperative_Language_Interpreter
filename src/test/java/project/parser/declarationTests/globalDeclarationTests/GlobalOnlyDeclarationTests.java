package project.parser.declarationTests.globalDeclarationTests;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.OnlyDeclaration;
import project.program.content.types.*;
import project.source.Source;
import project.source.StringSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GlobalOnlyDeclarationTests {

    @Test
    void shouldParseOnlyDeclarationOfInt() {
        Source source = new StringSource("int a;");
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
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());
    }

    @Test
    void shouldParseOnlyDeclarationOfInts() {
        Source source = new StringSource("int a, b, c;");
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
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());

        declaration = declarations.get(1);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("b", declaration.getId().getName());

        declaration = declarations.get(2);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("c", declaration.getId().getName());
    }

    @Test
    void shouldParseOnlyDeclarationOfDouble() {
        Source source = new StringSource("double a;");
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
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof DoubleType);
        assertEquals("a", declaration.getId().getName());
    }

    @Test
    void shouldParseOnlyDeclarationOfDoubles() {
        Source source = new StringSource("double a, b, c;");
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
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof DoubleType);
        assertEquals("a", declaration.getId().getName());

        declaration = declarations.get(1);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof DoubleType);
        assertEquals("b", declaration.getId().getName());

        declaration = declarations.get(2);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof DoubleType);
        assertEquals("c", declaration.getId().getName());
    }

    @Test
    void shouldParseOnlyDeclarationOfBool() {
        Source source = new StringSource("bool a;");
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
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("a", declaration.getId().getName());
    }

    @Test
    void shouldParseOnlyDeclarationOfBools() {
        Source source = new StringSource("bool a, b, c;");
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
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("a", declaration.getId().getName());

        declaration = declarations.get(1);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("b", declaration.getId().getName());

        declaration = declarations.get(2);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("c", declaration.getId().getName());
    }

    @Test
    void shouldParseOnlyDeclarationOfString() {
        Source source = new StringSource("string a;");
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
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("a", declaration.getId().getName());
    }

    @Test
    void shouldParseOnlyDeclarationOfStrings() {
        Source source = new StringSource("string a, b, c;");
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
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("a", declaration.getId().getName());

        declaration = declarations.get(1);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("b", declaration.getId().getName());

        declaration = declarations.get(2);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("c", declaration.getId().getName());

    }

    @Test
    void shouldParseOnlyDeclarationOfStructVar() {
        Source source = new StringSource("Student a, b, c;");
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
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof StructType);
        assertEquals("Student", ((StructType)declaration.getType()).getId().getName());
        assertEquals("a", declaration.getId().getName());

        declaration = declarations.get(1);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof StructType);
        assertEquals("Student", ((StructType)declaration.getType()).getId().getName());
        assertEquals("b", declaration.getId().getName());

        declaration = declarations.get(2);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof StructType);
        assertEquals("Student", ((StructType)declaration.getType()).getId().getName());
        assertEquals("c", declaration.getId().getName());
    }
}
