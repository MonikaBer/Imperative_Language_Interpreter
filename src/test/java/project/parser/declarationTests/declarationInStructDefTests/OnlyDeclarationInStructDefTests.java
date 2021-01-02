package project.parser.declarationTests.declarationInStructDefTests;

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

import static org.junit.jupiter.api.Assertions.*;

public class OnlyDeclarationInStructDefTests {

    @Test
    void shouldParseOnlyDeclarationOfInts() {
        Source source = new StringSource("struct Student { int a; int b; int c; }");
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
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());

        declaration = body.get(1);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("b", declaration.getId().getName());

        declaration = body.get(2);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("c", declaration.getId().getName());
    }

    @Test
    void shouldParseOnlyDeclarationOfDoubles() {
        Source source = new StringSource("struct Student { double a; double b; double c; }");
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
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof DoubleType);
        assertEquals("a", declaration.getId().getName());

        declaration = body.get(1);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof DoubleType);
        assertEquals("b", declaration.getId().getName());

        declaration = body.get(2);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof DoubleType);
        assertEquals("c", declaration.getId().getName());
    }

    @Test
    void shouldParseOnlyDeclarationOfBools() {
        Source source = new StringSource("struct Student { bool a; bool b; bool c; }");
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
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("a", declaration.getId().getName());

        declaration = body.get(1);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("b", declaration.getId().getName());

        declaration = body.get(2);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof BoolType);
        assertEquals("c", declaration.getId().getName());
    }

    @Test
    void shouldParseOnlyDeclarationOfStrings() {
        Source source = new StringSource("struct Student { string a; string b; string c; }");
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
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("a", declaration.getId().getName());

        declaration = body.get(1);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("b", declaration.getId().getName());

        declaration = body.get(2);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof StringType);
        assertEquals("c", declaration.getId().getName());
    }

    @Test
    void shouldParseOnlyDeclarationOfStructVars() {
        Source source = new StringSource("struct Student { Address a; Address b; Address c; }");
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
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof StructType);
        assertEquals("Address", ((StructType)declaration.getType()).getId().getName());
        assertEquals("a", declaration.getId().getName());

        declaration = body.get(1);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof StructType);
        assertEquals("Address", ((StructType) declaration.getType()).getId().getName());
        assertEquals("b", declaration.getId().getName());

        declaration = body.get(2);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof StructType);
        assertEquals("Address", ((StructType) declaration.getType()).getId().getName());
        assertEquals("c", declaration.getId().getName());
    }
}
