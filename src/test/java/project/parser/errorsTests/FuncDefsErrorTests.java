package project.parser.errorsTests;

import org.junit.jupiter.api.Test;
import project.exceptions.SyntaxError;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.source.Source;
import project.source.StringSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuncDefsErrorTests {

    @Test
    void shouldRecogniseFuncDefErr() {
        Source source = new StringSource("void func");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No ';' (at the end of list of declarations)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(9, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseFuncDefErr2() {
        Source source = new StringSource("void func(");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No ')' (after args in function definition)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(10, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseFuncDefErr3() {
        Source source = new StringSource("int func()");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No block statement (after args in function definition)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(10, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseFuncDefErr4() {
        Source source = new StringSource("int func(int)");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No identifier (after type in declaration)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(12, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseFuncDefErr5() {
        Source source = new StringSource("int func(int { 2 }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No identifier (after type in declaration)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(13, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseFuncDefErr6() {
        Source source = new StringSource("int func() { return a }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No ';' (after expression in return statement)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(22, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseFuncDefErr8() {
        Source source = new StringSource("int func() { return a. }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No field name (after '.' in struct type variable";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(23, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseFuncDefErr7() {
        Source source = new StringSource("int func() { abc; return a;}");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "Wrong usage of identifier";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(16, ex.getPositionAtLine());
        }
    }
}
