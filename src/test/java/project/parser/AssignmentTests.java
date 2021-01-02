package project.parser;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.Assignment;
import project.program.content.statements.Block;
import project.program.content.statements.Statement;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.OnlyDeclaration;
import project.program.content.statements.expressions.structExpressions.StructFieldExpression;
import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.*;
import project.program.content.types.*;
import project.source.Source;
import project.source.StringSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AssignmentTests {

    @Test
    void shouldParseAssignmentToInt() {
        Source source = new StringSource("void function(int a) { int b; b = 1; }");
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
        assertEquals(2, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof IntType);
        assertEquals("b", ((OnlyDeclaration)stmt).getId().getName());

        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof Assignment);
        assertTrue(((Assignment)stmt).getId() instanceof Identifier);
        Identifier id = (Identifier) ((Assignment)stmt).getId();
        assertEquals("b", id.getName());

        assertTrue(((Assignment) stmt).getExpression() instanceof IntValue);
        BigInteger actualValue = new BigInteger(String.valueOf(((IntValue)((Assignment)stmt).getExpression()).getValue()));
        BigInteger expectedValue = new BigInteger("1");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseAssignmentToDouble() {
        Source source = new StringSource("void function(int a) { double b; b = 10.01; }");
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
        BigDecimal actualValue = new BigDecimal(String.valueOf(((DoubleValue)((Assignment)stmt).getExpression()).getValue()));
        BigDecimal expectedValue = new BigDecimal("10.01");
        assertEquals(0, expectedValue.compareTo(actualValue));
    }

    @Test
    void shouldParseTrueBoolAssignment() {
        Source source = new StringSource("void function(int a) { bool b; b = true; }");
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
        assertEquals(2, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof BoolType);
        assertEquals("b", ((OnlyDeclaration)stmt).getId().getName());

        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof Assignment);
        assertTrue(((Assignment)stmt).getId() instanceof Identifier);
        Identifier id = (Identifier) ((Assignment)stmt).getId();
        assertEquals("b", id.getName());

        assertTrue(((Assignment) stmt).getExpression() instanceof TrueExpression);
    }

    @Test
    void shouldParseFalseBoolAssignment() {
        Source source = new StringSource("void function(int a) { bool b; b = false; }");
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
        assertEquals(2, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof BoolType);
        assertEquals("b", ((OnlyDeclaration)stmt).getId().getName());

        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof Assignment);
        assertTrue(((Assignment)stmt).getId() instanceof Identifier);
        Identifier id = (Identifier) ((Assignment)stmt).getId();
        assertEquals("b", id.getName());

        assertTrue(((Assignment) stmt).getExpression() instanceof FalseExpression);
    }

    @Test
    void shouldParseApostropheStringAssignment() {
        Source source = new StringSource("void function(int a) { string b; b = 'Ala ma kota'; }");
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
        assertEquals(2, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof StringType);
        assertEquals("b", ((OnlyDeclaration)stmt).getId().getName());

        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof Assignment);
        assertTrue(((Assignment)stmt).getId() instanceof Identifier);
        Identifier id = (Identifier) ((Assignment)stmt).getId();
        assertEquals("b", id.getName());

        assertTrue(((Assignment) stmt).getExpression() instanceof StringValue);
        assertEquals("Ala ma kota", ((StringValue)((Assignment)stmt).getExpression()).getValue());
    }

    @Test
    void shouldParseDoubleQuoteStringAssignment() {
        Source source = new StringSource("void function(int a) { string b; b = \"Ala ma kota\"; }");
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
        assertEquals(2, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof StringType);
        assertEquals("b", ((OnlyDeclaration)stmt).getId().getName());

        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof Assignment);
        assertTrue(((Assignment)stmt).getId() instanceof Identifier);
        Identifier id = (Identifier) ((Assignment)stmt).getId();
        assertEquals("b", id.getName());

        assertTrue(((Assignment) stmt).getExpression() instanceof StringValue);
        assertEquals("Ala ma kota", ((StringValue)((Assignment)stmt).getExpression()).getValue());
    }

    @Test
    void shouldParseStructVarToStructVarAssignment() {
        Source source = new StringSource("void function(int a) { Student student1; Student student2; student1 = student2; }");
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
        assertEquals(3, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof StructType);
        assertEquals("Student", ((StructType) ((OnlyDeclaration)stmt).getType()).getId().getName());
        assertEquals("student1", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof StructType);
        assertEquals("Student", ((StructType) ((OnlyDeclaration)stmt).getType()).getId().getName());
        assertEquals("student2", ((OnlyDeclaration)stmt).getId().getName());


        stmt = block.getStmts().get(2);
        assertTrue(stmt instanceof Assignment);
        assertTrue(((Assignment)stmt).getId() instanceof Identifier);
        assertEquals("student1", ((Identifier) ((Assignment)stmt).getId()).getName());
        assertTrue(((Assignment) stmt).getExpression() instanceof Identifier);
        assertEquals("student2", ((Identifier)((Assignment)stmt).getExpression()).getName());
    }

    @Test
    void shouldParseAssignmentToSimpleStructField() {
        Source source = new StringSource("void function(int a) { Student student; student.name = 'Ala'; }");
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
        assertEquals(2, block.getStmts().size());

        Statement stmt = block.getStmts().get(0);
        assertTrue(stmt instanceof OnlyDeclaration);
        assertTrue(((OnlyDeclaration)stmt).getType() instanceof StructType);
        assertEquals("Student", ((StructType) ((OnlyDeclaration)stmt).getType()).getId().getName());
        assertEquals("student", ((OnlyDeclaration)stmt).getId().getName());

        stmt = block.getStmts().get(1);
        assertTrue(stmt instanceof Assignment);
        assertTrue(((Assignment)stmt).getId() instanceof StructFieldExpression);
        StructFieldExpression id = (StructFieldExpression) ((Assignment)stmt).getId();
        assertEquals("student", ((Identifier)id.getStructVarName()).getName());
        assertEquals("name", id.getFieldName().getName());

        assertTrue(((Assignment) stmt).getExpression() instanceof StringValue);
        assertEquals("Ala", ((StringValue)((Assignment)stmt).getExpression()).getValue());
    }

//    @Test
//    void shouldParseAssignmentToComplexStructField() {
//        Source source = new StringSource("void function(int a) { Student student; student.address.street = 'Akacjowa'; }");
//        Lexer lexer = new Lexer(source);
//        lexer.nextToken();
//        Parser parser = new Parser(lexer);
//        Program program = parser.parseProgram();
//
//        ArrayList<Declaration> declarations = program.getDeclarations();
//        ArrayList<FuncDef> funcDefs = program.getFuncDefs();
//        ArrayList<StructDef> structDefs = program.getStructDefs();
//
//        assertEquals(0, declarations.size());
//        assertEquals(1, funcDefs.size());
//        assertEquals(0, structDefs.size());
//
//        FuncDef funcDef = funcDefs.get(0);
//        assertTrue(funcDef.getRetType() instanceof VoidType);
//        assertEquals("function", funcDef.getId().getName());
//
//        ArrayList<Declaration> args = funcDef.getArgs();
//        assertEquals(1, args.size());
//
//        Declaration declaration = args.get(0);
//        assertTrue(declaration instanceof OnlyDeclaration);
//        assertTrue(declaration.getType() instanceof IntType);
//        assertEquals("a", declaration.getId().getName());
//
//        Block block = funcDef.getBlock();
//        assertEquals(2, block.getStmts().size());
//
//        Statement stmt = block.getStmts().get(0);
//        assertTrue(stmt instanceof OnlyDeclaration);
//        assertTrue(((OnlyDeclaration)stmt).getType() instanceof StructType);
//        assertEquals("Student", ((StructType) ((OnlyDeclaration)stmt).getType()).getId().getName());
//        assertEquals("student", ((OnlyDeclaration)stmt).getId().getName());
//
//        stmt = block.getStmts().get(1);
//        assertTrue(stmt instanceof Assignment);
//        assertTrue(((Assignment)stmt).getId() instanceof StructFieldExpression);
//        StructFieldExpression assignmentId = (StructFieldExpression) ((Assignment)stmt).getId();
//
//        StructFieldExpression id = ((StructFieldExpression)assignmentId).getStructVarName();
//        assertEquals("student", ((Identifier)id.getStructVarName()).getName());
//        assertEquals("name", id.getFieldName().getName());
//
//        assertTrue(((Assignment) stmt).getExpression() instanceof StringValue);
//        assertEquals("Ala", ((StringValue)((Assignment)stmt).getExpression()).getValue());
//    }
}
