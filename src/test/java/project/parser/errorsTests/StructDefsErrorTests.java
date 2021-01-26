package project.parser.errorsTests;

import org.junit.jupiter.api.Test;
import project.exceptions.SyntaxError;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.source.Source;
import project.source.StringSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StructDefsErrorTests {

    @Test
    void shouldRecogniseStructDefErr() {
        Source source = new StringSource("struct");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No name of struct type (in struct definition)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(6, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseStructDefErr2() {
        Source source = new StringSource("struct N");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No '{' (after name of struct type in struct definition)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(8, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseStructDefErr3() {
        Source source = new StringSource("struct N {");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No fields (in struct definition)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(10, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseStructDefErr4() {
        Source source = new StringSource("struct N {}");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No fields (in struct definition)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(10, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseStructDefErr5() {
        Source source = new StringSource("struct N { 2 }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No fields (in struct definition)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(11, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseStructDefErr6() {
        Source source = new StringSource("struct N { int }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No identifier (after type in declaration)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(15, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseStructDefErr8() {
        Source source = new StringSource("struct N { int a. }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No ';' (at the end of declaration statement)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(16, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseStructDefErr7() {
        Source source = new StringSource("struct N { int b; double }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No identifier (after type in declaration)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(25, ex.getPositionAtLine());
        }
    }
}
