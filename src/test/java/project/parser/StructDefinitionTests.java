package project.parser;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.OnlyDeclaration;
import project.program.content.types.DoubleType;
import project.program.content.types.IntType;
import project.source.Source;
import project.source.StringSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StructDefinitionTests {

    @Test
    void shouldParseStructDefWithTwoSimpleFields() {
        Source source = new StringSource("struct Student { int a; double b; }");
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
        assertEquals(2, body.size());

        Declaration declaration = body.get(0);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());

        declaration = body.get(1);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof DoubleType);
        assertEquals("b", declaration.getId().getName());
    }
}
