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
    void shouldRecogniseSingleIdToken() {
        Source source = new StringSource("variableName");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.ID, lexer.getToken().getType());

        StringToken stringToken = (StringToken) lexer.getToken();

        assertEquals("variableName", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleTextToken() {
        Source source = new StringSource("\"some text\"");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());

        StringToken stringToken = (StringToken) lexer.getToken();

        assertEquals("some text", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleIntNumberToken() {
        Source source = new StringSource("2578");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());

        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(2578, intToken.getValue());

        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleDoubleNumberToken() {
        Source source = new StringSource("34.17");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());

        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(34.17, doubleToken.getValue());

        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleTrueToken() {
        Source source = new StringSource("true");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.TRUE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleFalseToken() {
        Source source = new StringSource("false");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.FALSE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleIfToken() {
        Source source = new StringSource("if");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.IF, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleElseToken() {
        Source source = new StringSource("else");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.ELSE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleSwitchToken() {
        Source source = new StringSource("switch");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.SWITCH, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleCaseToken() {
        Source source = new StringSource("case");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.CASE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleDefaultToken() {
        Source source = new StringSource("default");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.DEFAULT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleWhileToken() {
        Source source = new StringSource("while");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.WHILE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleAssignToken() {
        Source source = new StringSource("=");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleReturnToken() {
        Source source = new StringSource("return");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleLParenthToken() {
        Source source = new StringSource("(");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleRParenthToken() {
        Source source = new StringSource(")");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleLBraceToken() {
        Source source = new StringSource("{");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.L_BRACE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleRBraceToken() {
        Source source = new StringSource("}");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.R_BRACE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleIntToken() {
        Source source = new StringSource("int");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.INT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleDoubleToken() {
        Source source = new StringSource("double");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleBoolToken() {
        Source source = new StringSource("bool");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.BOOL, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleStringToken() {
        Source source = new StringSource("string");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleVoidToken() {
        Source source = new StringSource("void");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.VOID, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleStructToken() {
        Source source = new StringSource("struct");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.STRUCT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleEqToken() {
        Source source = new StringSource("==");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.EQ, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleNeqToken() {
        Source source = new StringSource("!=");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.NEQ, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleGeqtToken() {
        Source source = new StringSource(">=");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.GEQT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleGtToken() {
        Source source = new StringSource(">");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.GT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleLeqtToken() {
        Source source = new StringSource("<=");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.LEQT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleLtToken() {
        Source source = new StringSource("<");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.LT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSinglePlusToken() {
        Source source = new StringSource("+");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.PLUS, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleMinusToken() {
        Source source = new StringSource("-");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.MINUS, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSinglePostincToken() {
        Source source = new StringSource("++");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.POSTINC, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSinglePostdecToken() {
        Source source = new StringSource("--");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.POSTDEC, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleMulToken() {
        Source source = new StringSource("*");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.MUL, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleDivToken() {
        Source source = new StringSource("/");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.DIV, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleModToken() {
        Source source = new StringSource("%");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.MOD, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleAlternativeToken() {
        Source source = new StringSource("||");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.ALTERNATIVE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleConjuctionToken() {
        Source source = new StringSource("&&");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.CONJUNCTION, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleNegationToken() {
        Source source = new StringSource("!");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.NEGATION, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleSemicolonToken() {
        Source source = new StringSource(";");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleDotToken() {
        Source source = new StringSource(".");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.DOT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleColonToken() {
        Source source = new StringSource(":");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.COLON, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleCommaToken() {
        Source source = new StringSource(",");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.COMMA, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleEOTToken() {
        Source source = new StringSource("");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenAt() {
        Source source = new StringSource("@");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenDolar() {
        Source source = new StringSource("$");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenPeak() {
        Source source = new StringSource("^");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenAnd() {
        Source source = new StringSource("&");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenOr() {
        Source source = new StringSource("|");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenLSquareBracket() {
        Source source = new StringSource("[");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenRSquareBracket() {
        Source source = new StringSource("]");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenBackSlash() {
        Source source = new StringSource("\\");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleUndefinedTokenQuestionMark() {
        Source source = new StringSource("?");
        Lexer lexer = new Lexer(source);
        lexer.nextToken();

        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }
}
