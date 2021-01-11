package project.parser.errorsTests;

import org.junit.jupiter.api.Test;
import project.exceptions.SyntaxError;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.source.Source;
import project.source.StringSource;

import static org.junit.jupiter.api.Assertions.*;

public class AssignmentErrorTests {

    @Test
    void shouldRecogniseIncorrectStatementInBlock() {
        Source source = new StringSource("void function() { 1 = ; }");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();
        Parser parser = new Parser(lexer);

        try {
            Program program = parser.parseProgram();
        } catch (SyntaxError ex){
            assertEquals(0, ex.getLineNr());
            assertEquals(18, ex.getPositionAtLine());
            String expectedDesc = "Incorrect statement in block";
            assertEquals(expectedDesc, ex.getDesc());
        }
    }
}
