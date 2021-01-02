package project.parser.declarationTests.declarationInStructDefTests;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.Initialisation;
import project.program.content.statements.declarations.OnlyDeclaration;
import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.IntValue;
import project.program.content.types.IntType;
import project.source.Source;
import project.source.StringSource;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MixDeclarationInStructDefTests {

    @Test
    void shouldParseMixDeclarationOfInts() {
        Source source = new StringSource("struct Student { int a = 1; int b; int c = 3; int d; }");
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
        assertEquals(4, body.size());

        Declaration stmt = body.get(0);
        assertTrue(stmt instanceof Initialisation);
        assertTrue((stmt).getType() instanceof IntType);
        assertEquals("a", stmt.getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof IntValue);

        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) stmt).getExpression()).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));


        stmt = body.get(1);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue((stmt).getType() instanceof IntType);
        assertEquals("b", stmt.getId().getName());


        stmt = body.get(2);
        assertTrue(stmt instanceof Initialisation);
        assertTrue((stmt).getType() instanceof IntType);
        assertEquals("c", stmt.getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof IntValue);

        actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) stmt).getExpression()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));


        stmt = body.get(3);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue((stmt).getType() instanceof IntType);
        assertEquals("d", stmt.getId().getName());
    }
}
