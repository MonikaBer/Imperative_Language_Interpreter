package project.parser.declarationTests.declarationInFuncDefTests;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.Block;
import project.program.content.statements.Return;
import project.program.content.statements.Statement;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.Initialisation;
import project.program.content.statements.declarations.OnlyDeclaration;
import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.IntValue;
import project.program.content.types.IntType;
import project.program.content.types.VoidType;
import project.source.Source;
import project.source.StringSource;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MixDeclarationInFuncDefTests {

    @Test
    void shouldParseMixDeclarationOfInts() {
        Source source = new StringSource("void function(int a) { int b = 2, c, d = 4, e; return; }");
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
        assertEquals(1, args.size());

        Declaration declaration = args.get(0);
        assertTrue(declaration instanceof OnlyDeclaration);
        assertTrue(declaration.getType() instanceof IntType);
        assertEquals("a", declaration.getId().getName());

        Block block = funcDef.getBlock();
        assertEquals(5, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof IntType);
        assertEquals("b", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof IntValue);

        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) stmt).getExpression()).getValue()));
        BigInteger expectedValue = new BigInteger("2");
        assertEquals(0, expectedValue.compareTo(actualValue));


        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof IntType);
        assertEquals("c", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(2);
        assertTrue(stmt instanceof Initialisation);
        assertTrue(((Initialisation)stmt).getType() instanceof IntType);
        assertEquals("d", ((Initialisation)stmt).getId().getName());
        assertTrue(((Initialisation) stmt).getExpression() instanceof IntValue);

        actualValue = new BigInteger(String.valueOf(((IntValue)((Initialisation) stmt).getExpression()).getValue()));
        expectedValue = new BigInteger("4");
        assertEquals(0, expectedValue.compareTo(actualValue));


        stmt = block.getStmts().get(3);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof IntType);
        assertEquals("e", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(4);
        assertTrue(stmt instanceof Return);
        assertNull(((Return) stmt).getExpression());
    }
}
