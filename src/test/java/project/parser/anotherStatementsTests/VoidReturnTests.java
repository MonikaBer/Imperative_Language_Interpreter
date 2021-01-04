package project.parser.anotherStatementsTests;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.*;
import project.program.content.statements.declarations.Declaration;
import project.program.content.types.VoidType;
import project.source.Source;
import project.source.StringSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class VoidReturnTests {

    @Test
    void shouldParseSimpleVoidReturn() {
        Source source = new StringSource("void function() { return; }");
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
        assertTrue(stmt instanceof VoidReturn);
    }
}
