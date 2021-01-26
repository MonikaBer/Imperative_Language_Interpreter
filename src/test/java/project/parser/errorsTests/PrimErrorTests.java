package project.parser.errorsTests;

import org.junit.jupiter.api.Test;
import project.exceptions.SyntaxError;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.source.Source;
import project.source.StringSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimErrorTests {

    @Test
    void shouldRecogniseGlobalNumber() {
        Source source = new StringSource("1 = ;");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "Unknown construction";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(0, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseGlobalSwitch() {
        Source source = new StringSource("switch = ;");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "Unknown construction";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(0, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseGlobalIf() {
        Source source = new StringSource("if = ;");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "Unknown construction";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(0, ex.getPositionAtLine());
        }
    }

    @Test
    void shouldRecogniseGlobalIdentifier() {
        Source source = new StringSource("a = ;");
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            String expectedDesc = "Unknown construction";
            assertEquals(expectedDesc, ex.getDesc());
            assertEquals(0, ex.getLineNr());
            assertEquals(2, ex.getPositionAtLine());
        }
    }
}
