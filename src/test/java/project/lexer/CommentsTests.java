package project.lexer;

import org.junit.jupiter.api.Test;
import project.source.Source;
import project.source.StringSource;
import project.token.IntToken;
import project.token.StringToken;
import project.token.Token;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentsTests {

    @Test
    void shouldRecogniseHashCommentWithEndOfLine() {
        Source source = new StringSource(" #aaa \n ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(8, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseHashCommentWithoutEndOfLine() {
        Source source = new StringSource(" #aaa ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(6, lexer.getToken().getPosition());
        assertEquals(6, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseSlashCommentWithEndOfLine() {
        Source source = new StringSource(" //aaa\n ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(8, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseSlashCommentWithoutEndOfLine() {
        Source source = new StringSource(" //aaa ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(7, lexer.getToken().getPosition());
        assertEquals(7, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseSlashCommentWithStarInside() {
        Source source = new StringSource(" //*aaa\n bb ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("bb", stringToken.getValue());
        assertEquals(9, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(12, lexer.getToken().getPosition());
        assertEquals(4, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseSlashCommentWithMultilinearCommentInside() {
        Source source = new StringSource(" // /*aaa*/ bb\n    cc ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("cc", stringToken.getValue());
        assertEquals(19, lexer.getToken().getPosition());
        assertEquals(4, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(22, lexer.getToken().getPosition());
        assertEquals(7, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseSlashCommentWithHashCommentInside() {
        Source source = new StringSource(" // #/*aaa*/ bb\n   cc ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("cc", stringToken.getValue());
        assertEquals(19, lexer.getToken().getPosition());
        assertEquals(3, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(22, lexer.getToken().getPosition());
        assertEquals(6, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseMultilinearComment() {
        Source source = new StringSource("  /*aaa*/  ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseMultilinearCommentWithStarsInside() {
        Source source = new StringSource("  /**aaa**/ aa ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(12, lexer.getToken().getPosition());
        assertEquals(12, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(15, lexer.getToken().getPosition());
        assertEquals(15, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseMultipleIdTokensAmongCommentsAndWhitespaces() {
        Source source = new StringSource(" #aaa\n /*aaa*/ //aaa\n  1  #aaa\n /*aaa*/ //aaa\n  2  #aaa\n /*aaa*/ //aaa\n ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(1, intToken.getValue());
        assertEquals(23, lexer.getToken().getPosition());
        assertEquals(2, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(2, intToken.getValue());
        assertEquals(48, lexer.getToken().getPosition());
        assertEquals(2, lexer.getToken().getPositionAtLine());
        assertEquals(4, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(72, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(6, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseAaa() {
        Source source = new StringSource("/* a");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(4, lexer.getToken().getPosition());
        assertEquals(4, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());


    }
}
