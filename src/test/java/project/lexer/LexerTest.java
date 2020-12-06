package project.lexer;

import org.junit.jupiter.api.Test;
import project.source.Source;
import project.source.StringSource;
import project.token.DoubleToken;
import project.token.IntToken;
import project.token.StringToken;
import project.token.Token;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    void shouldRecogniseIdToken() {
        Source source = new StringSource("variableName");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.ID, lexer.getToken().getType());

        StringToken stringToken = (StringToken) lexer.getToken();

        assertEquals("variableName", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseTextToken() {
        Source source = new StringSource("\"some text\"");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());

        StringToken stringToken = (StringToken) lexer.getToken();

        assertEquals("some text", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseIntNumberToken() {
        Source source = new StringSource("2578");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());

        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(2578, intToken.getValue());

        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseDoubleNumberToken() {
        Source source = new StringSource("34.17");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());

        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(34.17, doubleToken.getValue());

        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseTrueToken() {
        Source source = new StringSource("true");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.TRUE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseFalseToken() {
        Source source = new StringSource("false");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.FALSE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseIfToken() {
        Source source = new StringSource("if");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.IF, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseElseToken() {
        Source source = new StringSource("else");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.ELSE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSwitchToken() {
        Source source = new StringSource("switch");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.SWITCH, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseCaseToken() {
        Source source = new StringSource("case");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.CASE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseDefaultToken() {
        Source source = new StringSource("default");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.DEFAULT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseWhileToken() {
        Source source = new StringSource("while");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.WHILE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseAssignToken() {
        Source source = new StringSource("=");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseReturnToken() {
        Source source = new StringSource("return");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseLParenthToken() {
        Source source = new StringSource("(");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseRParenthToken() {
        Source source = new StringSource(")");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseLBraceToken() {
        Source source = new StringSource("{");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.L_BRACE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseRBraceToken() {
        Source source = new StringSource("}");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.R_BRACE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseIntToken() {
        Source source = new StringSource("int");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.INT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseDoubleToken() {
        Source source = new StringSource("double");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseBoolToken() {
        Source source = new StringSource("bool");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.BOOL, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseStringToken() {
        Source source = new StringSource("string");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseVoidToken() {
        Source source = new StringSource("void");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.VOID, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseStructToken() {
        Source source = new StringSource("struct");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.STRUCT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseEqToken() {
        Source source = new StringSource("==");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.EQ, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseNeqToken() {
        Source source = new StringSource("!=");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.NEQ, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseGeqtToken() {
        Source source = new StringSource(">=");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.GEQT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseGtToken() {
        Source source = new StringSource(">");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.GT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseLeqtToken() {
        Source source = new StringSource("<=");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.LEQT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseLtToken() {
        Source source = new StringSource("<");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.LT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecognisePlusToken() {
        Source source = new StringSource("+");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.PLUS, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseMinusToken() {
        Source source = new StringSource("-");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.MINUS, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecognisePostincToken() {
        Source source = new StringSource("++");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.POSTINC, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecognisePostdecToken() {
        Source source = new StringSource("--");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.POSTDEC, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseMulToken() {
        Source source = new StringSource("*");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.MUL, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseDivToken() {
        Source source = new StringSource("/");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.DIV, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseModToken() {
        Source source = new StringSource("%");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.MOD, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseAlternativeToken() {
        Source source = new StringSource("||");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.ALTERNATIVE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseConjuctionToken() {
        Source source = new StringSource("&&");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.CONJUNCTION, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseNegationToken() {
        Source source = new StringSource("!");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.NEGATION, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSemicolonToken() {
        Source source = new StringSource(";");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseDotToken() {
        Source source = new StringSource(".");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.DOT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseColonToken() {
        Source source = new StringSource(":");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.COLON, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseCommaToken() {
        Source source = new StringSource(",");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.COMMA, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseEOTToken() {
        Source source = new StringSource("");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseUndefinedToken() {
        Source source = new StringSource("@");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }
}
