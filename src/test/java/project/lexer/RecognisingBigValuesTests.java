package project.lexer;

// max values of int, double and string tests

import org.junit.jupiter.api.Test;
import project.source.Source;
import project.source.StringSource;
import project.token.IntToken;
import project.token.Token;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecognisingBigValuesTests {

    @Test
    void shouldRecogniseUndefinedBecauseOfTooBigInt() {
        Source source = new StringSource("2147483648");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());
        assertEquals(10, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseMaxInt() {
        Source source = new StringSource("2147483647");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(2147483647, intToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());
        assertEquals(10, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseUndefinedBecauseOfTooBigDouble() {
        Source source = new StringSource("111111111100000000002222222223333333333444444444455.00000000001111111111222222222233333333334444444444");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }
}
