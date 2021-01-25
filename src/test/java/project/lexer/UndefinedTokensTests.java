package project.lexer;

import org.junit.jupiter.api.Test;
import project.source.Source;
import project.source.StringSource;
import project.token.Token;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UndefinedTokensTests {

    @Test
    void shouldRecogniseSingleUndefinedTokenAt() {
        Source source = new StringSource("\n\n @ \n\n");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(3, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenDolar() {
        Source source = new StringSource("$");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenPeak() {
        Source source = new StringSource("^");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenAnd() {
        Source source = new StringSource("&");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenOr() {
        Source source = new StringSource("|");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenLSquareBracket() {
        Source source = new StringSource("[");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenRSquareBracket() {
        Source source = new StringSource("]");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenBackSlash() {
        Source source = new StringSource("\\");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenQuestionMark() {
        Source source = new StringSource("?");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }
}
