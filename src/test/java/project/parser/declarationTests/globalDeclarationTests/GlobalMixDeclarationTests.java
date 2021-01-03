package project.parser.declarationTests.globalDeclarationTests;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.Initialisation;
import project.program.content.statements.declarations.OnlyDeclaration;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.IntValue;
import project.program.content.types.IntType;
import project.source.Source;
import project.source.StringSource;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GlobalMixDeclarationTests {

    @Test
    void shouldParseMixDeclarationOfInts() {
        Source source = new StringSource("int a = 1, b, c = 3, d;");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        ArrayList<Declaration> declarations = program.getDeclarations();
        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
        ArrayList<StructDef> structDefs = program.getStructDefs();

        assertEquals(4, declarations.size());
        assertEquals(0, funcDefs.size());
        assertEquals(0, structDefs.size());

        Declaration declaration = declarations.get(0);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof IntValue);

        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) declaration).getExpression()).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));


        declaration = declarations.get(1);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("b", declaration.getId().getName());


        declaration = declarations.get(2);
        assertTrue(declaration instanceof Initialisation);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("c", declaration.getId().getName());
        assertTrue(((Initialisation) declaration).getExpression() instanceof IntValue);

        actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) declaration).getExpression()).getValue()));
        expectedValue = new BigInteger("3");
        assertEquals(0, expectedValue.compareTo(actualValue));


        declaration = declarations.get(3);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("d", declaration.getId().getName());
    }
}
