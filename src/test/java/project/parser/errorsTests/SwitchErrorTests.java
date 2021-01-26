package project.parser.errorsTests;

import org.junit.jupiter.api.Test;
import project.exceptions.SyntaxError;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.source.Source;
import project.source.StringSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwitchErrorTests {

    @Test
    void shouldRecogniseIncorrectSwitch() {
        Source source = new StringSource("void function() { switch(1 {} }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            assertEquals(0, ex.getLineNr());
            assertEquals(27, ex.getPositionAtLine());
            String expectedDesc = "No ')' (after expression)";
            assertEquals(expectedDesc, ex.getDesc());
        }
    }

    @Test
    void shouldRecogniseIncorrectSwitch2() {
        Source source = new StringSource("void function() { switch(1) { }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No case (in switch statement)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(30, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseIncorrectSwitch3() {
        Source source = new StringSource("void function() { switch(1) } }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No '{' (after right parenthesis)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(28, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseIncorrectSwitch4() {
        Source source = new StringSource("void function() { switch1) } }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "Wrong usage of identifier";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(25, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseIncorrectSwitch5() {
        Source source = new StringSource("void function() { switch 1) } }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No '(' (after 'switch' keyword)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(25, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseIncorrectSwitch6() {
        Source source = new StringSource("void function() { switch (1) { case a: 2 + 2; } }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No statement (after colon in case)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(39, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseIncorrectSwitch7() {
        Source source = new StringSource("void function() { switch (1) { case a: b = 1; } }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No 'default' keyword (after list of cases in switch)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(46, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseIncorrectSwitch8() {
        Source source = new StringSource("void function() { switch (1) { case a: b = 1; default: } }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No statement (after colon in default)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(55, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseIncorrectSwitch9() {
        Source source = new StringSource("void function() { switch (1) {  } }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No case (in switch statement)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(32, ex.getPositionAtLine());
        }
    }
}
