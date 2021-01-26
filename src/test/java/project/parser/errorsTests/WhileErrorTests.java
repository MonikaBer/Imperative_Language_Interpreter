package project.parser.errorsTests;

import org.junit.jupiter.api.Test;
import project.exceptions.SyntaxError;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.source.Source;
import project.source.StringSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhileErrorTests {

    @Test
    void shouldRecogniseIncorrectWhile() {
        Source source = new StringSource("void function() { while(1 {} }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            assertEquals(0, ex.getLineNr());
            assertEquals(26, ex.getPositionAtLine());
            String expectedDesc = "No ')' (after condition)";
            assertEquals(expectedDesc, ex.getDesc());
        }
    }

    @Test
    void shouldRecogniseIncorrectWhile2() {
        Source source = new StringSource("void function() { while(1) { }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "Incorrect statement in block";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(30, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseIncorrectWhile3() {
        Source source = new StringSource("void function() { while(1) } }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No statement (after right parenthesis)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(27, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseIncorrectWhile4() {
        Source source = new StringSource("void function() { while1) } }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "Wrong usage of identifier";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(24, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseIncorrectWhile5() {
        Source source = new StringSource("void function() { while 1) } }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No '(' (after 'while' keyword)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(24, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseIncorrectWhile6() {
        Source source = new StringSource("void function() {while (1) { 2 + 2; } }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "Incorrect statement in block";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(29, ex.getPositionAtLine());
        }
    }
}
