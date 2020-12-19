package project.lexer;

import org.junit.jupiter.api.Test;
import project.exceptions.FileSourceReadException;
import project.source.FileSource;
import project.source.Source;
import project.token.IntToken;
import project.token.StringToken;
import project.token.Token;

import java.io.IOException;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileSourceTests {

    @Test
    void shouldRecogniseNumberMinusOne() throws IOException, FileSourceReadException {
        Source source = new FileSource("test_sources/test_source");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.INT, lexer.getToken().getType());
        assertEquals(1, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        assertEquals(5, lexer.getToken().getPosition());
        assertEquals(4, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(7, lexer.getToken().getPosition());
        assertEquals(6, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.MINUS, lexer.getToken().getType());
        assertEquals(9, lexer.getToken().getPosition());
        assertEquals(8, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());
        assertEquals(9, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(10, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(13, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseNumberMinusEleven() throws IOException, FileSourceReadException {
        Source source = new FileSource("test_sources/test_source2");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.INT, lexer.getToken().getType());
        assertEquals(1, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        assertEquals(5, lexer.getToken().getPosition());
        assertEquals(4, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(7, lexer.getToken().getPosition());
        assertEquals(6, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.MINUS, lexer.getToken().getType());
        assertEquals(9, lexer.getToken().getPosition());
        assertEquals(8, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        BigInteger bigInteger = new BigInteger("11");
        assertEquals(0, bigInteger.compareTo(intToken.getValue()));
        assertEquals(10, lexer.getToken().getPosition());
        assertEquals(9, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(12, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(14, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseErrorInDoubleQuoteString() throws IOException, FileSourceReadException {
        Source source = new FileSource("test_sources/test_source3");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseErrorInSingleQuoteString() throws IOException, FileSourceReadException {
        Source source = new FileSource("test_sources/test_source4");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseSlashSlashNInDoubleQuoteString() throws IOException, FileSourceReadException {
        Source source = new FileSource("test_sources/test_source5");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("aaa\\\\n", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseSlashNInDoubleQuoteString() throws IOException, FileSourceReadException {
        Source source = new FileSource("test_sources/test_source6");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("aaa\\n", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseSlashSlashNInSingleQuoteString() throws IOException, FileSourceReadException {
        Source source = new FileSource("test_sources/test_source7");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("aaa\\\\n", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseSlashNInSingleQuoteString() throws IOException, FileSourceReadException {
        Source source = new FileSource("test_sources/test_source8");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("aaa\\n", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }
}
