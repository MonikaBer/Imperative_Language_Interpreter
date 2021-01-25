package project.parser;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.Block;
import project.program.content.statements.Empty;
import project.program.content.statements.Statement;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.Initialisation;
import project.program.content.statements.declarations.OnlyDeclaration;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.IntValue;
import project.program.content.types.DoubleType;
import project.program.content.types.IntType;
import project.program.content.types.VoidType;
import project.source.Source;
import project.source.StringSource;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProgramTests {

    @Test
    void shouldParseProgram() {
        Source source = new StringSource("int a; " +
                                         "int b = 10; " +
                                         "struct Student { int a; double b; } " +
                                         "void function() { ; }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(2, declarations.size());
        assertEquals(1, funcDefs.size());
        assertEquals(1, structDefs.size());


        Declaration declaration = declarations.get(0);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());


        declaration = declarations.get(1);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("b", declaration.getId().getName());

        assertTrue(((Initialisation) declaration).getExpression() instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) declaration).getExpression()).getValue()));
        BigInteger expectedValue = new BigInteger("10");
        assertEquals(0, expectedValue.compareTo(actualValue));


        StructDef structDef = structDefs.get(0);
        assertEquals("Student", structDef.getId().getName());

        ArrayList<Declaration> body = structDef.getBody();
        assertEquals(2, body.size());

        declaration = body.get(0);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());

        declaration = body.get(1);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof DoubleType);
        assertEquals("b", declaration.getId().getName());


        FuncDef funcDef = funcDefs.get(0);
        assertTrue(funcDef.getRetType() instanceof VoidType);
        assertEquals("function", funcDef.getId().getName());

        ArrayList<Declaration> args = funcDef.getArgs();
        assertNull(args);

        Block block = funcDef.getBlock();
        assertEquals(1, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof Empty);
    }
}
