package project.parser.errorsTests;

import org.junit.jupiter.api.Test;
import project.exceptions.SyntaxError;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.source.Source;
import project.source.StringSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IfElseErrorTests {

    @Test
    void shouldRecogniseIncorrectIfElse() {
        Source source = new StringSource("void function() { if(1 {} else }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            assertEquals(0, ex.getLineNr());
            assertEquals(23, ex.getPositionAtLine());
            String expectedDesc = "No ')' (after condition)";
            assertEquals(expectedDesc, ex.getDesc());
        }
    }

    @Test
    void shouldRecogniseIncorrectIfElse2() {
        Source source = new StringSource("void function() { if(1) { } else ");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No statement (after right parenthesis)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(33, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseIncorrectIf3() {
        Source source = new StringSource("void function() { if(1) {int a=1;} else {int a=2} }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No ';' (at the end of list of declarations)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(48, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseIncorrectIf4() {
        Source source = new StringSource("void function() { if(1) a=4 else b=2;  }");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "No ';' (at the end of assignment statement)";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(28, ex.getPositionAtLine());
        }
    }
}
