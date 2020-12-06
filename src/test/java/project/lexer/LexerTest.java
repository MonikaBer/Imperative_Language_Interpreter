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

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(12, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseMultipleIdTokens() {
        Source source = new StringSource("var1 var2 var3");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("var1", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("var2", stringToken.getValue());
        assertEquals(5, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("var3", stringToken.getValue());
        assertEquals(10, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(14, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseMultipleIdTokensWithWhitespaces() {
        Source source = new StringSource("var1           var2 \n \t \r var3");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("var1", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("var2", stringToken.getValue());
        assertEquals(15, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("var3", stringToken.getValue());
        assertEquals(26, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(30, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleApostropheTextToken() {
        Source source = new StringSource("'some text'");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("some text", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleApostropheTextTokenWithWhitespaces() {
        Source source = new StringSource("\n \t 'some text' \t \n \r");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("some text", stringToken.getValue());
        assertEquals(4, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(21, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleDoubleQuoteTextToken() {
        Source source = new StringSource("\"some text\"");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("some text", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleDoubleQuoteTextTokenWithWhitespaces() {
        Source source = new StringSource("\n \t \"some text\" \r  ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("some text", stringToken.getValue());
        assertEquals(4, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(19, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseMultipleApostropheTextTokens() {
        Source source = new StringSource("'txt1' 'txt2' 'txt3'");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("txt1", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("txt2", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("txt3", stringToken.getValue());
        assertEquals(14, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(20, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseMultipleApostropheTextTokensWithWhitespaces() {
        Source source = new StringSource("  \t \n 'txt1' \n\r 'txt2' \r\r\r 'txt3'   \n \r\r \t\t");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("txt1", stringToken.getValue());
        assertEquals(6, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("txt2", stringToken.getValue());
        assertEquals(16, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("txt3", stringToken.getValue());
        assertEquals(27, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(43, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseMultipleApostropheTextTokensWithWhitespacesAndComments() {
        Source source = new StringSource("#aaa\n  \t \n 'txt1' \n\r 'txt2' \r\r\r 'txt3'   \n \r\r \t\t");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("txt1", stringToken.getValue());
        assertEquals(11, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("txt2", stringToken.getValue());
        assertEquals(21, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("txt3", stringToken.getValue());
        assertEquals(32, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(48, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseMultipleDoubleQuoteTextTokens() {
        Source source = new StringSource("\"txt1\" \"txt2\" \"txt3\"");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("txt1", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("txt2", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("txt3", stringToken.getValue());
        assertEquals(14, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(20, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(4, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSingleIntNumberTokenWithWhitespacesAndComments() {
        Source source = new StringSource(" #aaa\n /*aaa*/ //aaa\n 2578  #aaa\n ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(2578, intToken.getValue());
        assertEquals(22, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(34, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseMultipleIntNumberTokens() {
        Source source = new StringSource("10 0 12 01");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(10, intToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(0, intToken.getValue());
        assertEquals(3, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(12, intToken.getValue());
        assertEquals(5, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(0, intToken.getValue());
        assertEquals(8, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(1, intToken.getValue());
        assertEquals(9, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(5, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseMultipleDoubleNumberTokens() {
        Source source = new StringSource("0.0  0.10  0.01  0.00100");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(0.0, doubleToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(0.1, doubleToken.getValue());
        assertEquals(5, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(0.01, doubleToken.getValue());
        assertEquals(11, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(0.001, doubleToken.getValue());
        assertEquals(17, lexer.getToken().getPosition());
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
    void shouldRecogniseMultipleTrueTokens() {
        Source source = new StringSource("true true true");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 15; i += 5) {
            lexer.nextToken();
            assertEquals(Token.TokenType.TRUE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleFalseTokens() {
        Source source = new StringSource("false false false");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 18; i += 6) {
            lexer.nextToken();
            assertEquals(Token.TokenType.FALSE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleIfTokens() {
        Source source = new StringSource("if if if");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            lexer.nextToken();
            assertEquals(Token.TokenType.IF, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleElseTokens() {
        Source source = new StringSource("else else else");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 15; i += 5) {
            lexer.nextToken();
            assertEquals(Token.TokenType.ELSE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleSwitchTokens() {
        Source source = new StringSource("switch switch switch");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 21; i += 7) {
            lexer.nextToken();
            assertEquals(Token.TokenType.SWITCH, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleCaseTokens() {
        Source source = new StringSource("case case case");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 15; i += 5) {
            lexer.nextToken();
            assertEquals(Token.TokenType.CASE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleDefaultTokens() {
        Source source = new StringSource("default default default");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 24; i += 8) {
            lexer.nextToken();
            assertEquals(Token.TokenType.DEFAULT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleWhileTokens() {
        Source source = new StringSource("while while while");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 18; i += 6) {
            lexer.nextToken();
            assertEquals(Token.TokenType.WHILE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleAssignTokens() {
        Source source = new StringSource("= = =");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleReturnTokens() {
        Source source = new StringSource("return return return");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 21; i += 7) {
            lexer.nextToken();
            assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleLParenthTokens() {
        Source source = new StringSource("( ( (");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleRParenthTokens() {
        Source source = new StringSource(") ) )");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleLBraceTokens() {
        Source source = new StringSource("{ { {");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.L_BRACE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleRBraceTokens() {
        Source source = new StringSource("} } }");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.R_BRACE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleIntTokens() {
        Source source = new StringSource("int int int");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 12; i += 4) {
            lexer.nextToken();
            assertEquals(Token.TokenType.INT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleDoubleTokens() {
        Source source = new StringSource("double double double");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 21; i += 7) {
            lexer.nextToken();
            assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleBoolTokens() {
        Source source = new StringSource("bool bool bool");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 15; i += 5) {
            lexer.nextToken();
            assertEquals(Token.TokenType.BOOL, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleStringTokens() {
        Source source = new StringSource("string string string");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 21; i += 7) {
            lexer.nextToken();
            assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleVoidTokens() {
        Source source = new StringSource("void void void");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 15; i += 5) {
            lexer.nextToken();
            assertEquals(Token.TokenType.VOID, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleStructTokens() {
        Source source = new StringSource("struct struct struct");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 21; i += 7) {
            lexer.nextToken();
            assertEquals(Token.TokenType.STRUCT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleEqTokens() {
        Source source = new StringSource("== == ==");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            lexer.nextToken();
            assertEquals(Token.TokenType.EQ, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleNeqTokens() {
        Source source = new StringSource("!= != !=");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            lexer.nextToken();
            assertEquals(Token.TokenType.NEQ, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleGeqtTokens() {
        Source source = new StringSource(">= >= >=");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            lexer.nextToken();
            assertEquals(Token.TokenType.GEQT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleGtTokens() {
        Source source = new StringSource("> > >");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.GT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleLeqtTokens() {
        Source source = new StringSource("<= <= <=");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            lexer.nextToken();
            assertEquals(Token.TokenType.LEQT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleLtTokens() {
        Source source = new StringSource("< < <");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.LT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultiplePlusTokens() {
        Source source = new StringSource("+ + +");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.PLUS, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleMinusTokens() {
        Source source = new StringSource("- - -");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.MINUS, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultiplePostincTokens() {
        Source source = new StringSource("++ ++ ++");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            lexer.nextToken();
            assertEquals(Token.TokenType.POSTINC, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultiplePostdecTokens() {
        Source source = new StringSource("-- -- --");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            lexer.nextToken();
            assertEquals(Token.TokenType.POSTDEC, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleMulTokens() {
        Source source = new StringSource("* * *");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.MUL, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleDivTokens() {
        Source source = new StringSource("/ / /");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.DIV, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleModTokens() {
        Source source = new StringSource("% % %");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.MOD, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleAlternativeTokens() {
        Source source = new StringSource("|| || ||");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            lexer.nextToken();
            assertEquals(Token.TokenType.ALTERNATIVE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleConjunctionTokens() {
        Source source = new StringSource("&& && &&");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            lexer.nextToken();
            assertEquals(Token.TokenType.CONJUNCTION, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleNegationTokens() {
        Source source = new StringSource("! ! !");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.NEGATION, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleSemicolonTokens() {
        Source source = new StringSource("; ; ;");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleDotTokens() {
        Source source = new StringSource(". . .");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.DOT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleColonTokens() {
        Source source = new StringSource(": : :");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.COLON, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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
    void shouldRecogniseMultipleCommaTokens() {
        Source source = new StringSource(", , ,");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            lexer.nextToken();
            assertEquals(Token.TokenType.COMMA, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
        }
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



    @Test
    void shouldRecogniseHashCommentWithEndOfLine() {
        Source source = new StringSource(" #aaa \n ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(8, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseHashCommentWithoutEndOfLine() {
        Source source = new StringSource(" #aaa ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(6, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSlashCommentWithEndOfLine() {
        Source source = new StringSource(" //aaa\n ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(8, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseSlashCommentWithoutEndOfLine() {
        Source source = new StringSource(" //aaa ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(7, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(12, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(22, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(22, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseMultilinearComment() {
        Source source = new StringSource("  /*aaa*/  ");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(15, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(2, intToken.getValue());
        assertEquals(48, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(72, lexer.getToken().getPosition());
    }



    @Test
    void shouldRecogniseMainFunction() {
        Source source = new StringSource("int main() {\n\treturn 0;\n}\n");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.INT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("main", stringToken.getValue());
        assertEquals(4, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(8, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(9, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_BRACE, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(14, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(0, intToken.getValue());
        assertEquals(21, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(22, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_BRACE, lexer.getToken().getType());
        assertEquals(24, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(26, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseSwitchInstruction() {
        Source source = new StringSource("switch (a) {\n\tcase 1: \t\treturn 1;\n\tcase 2: \t\treturn 2;\n\tdefault: \t\treturn 0;\n}\n");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.SWITCH, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("a", stringToken.getValue());
        assertEquals(8, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(9, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_BRACE, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.CASE, lexer.getToken().getType());
        assertEquals(14, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(1, intToken.getValue());
        assertEquals(19, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.COLON, lexer.getToken().getType());
        assertEquals(20, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(24, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(1, intToken.getValue());
        assertEquals(31, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(32, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.CASE, lexer.getToken().getType());
        assertEquals(35, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(2, intToken.getValue());
        assertEquals(40, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.COLON, lexer.getToken().getType());
        assertEquals(41, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(45, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(2, intToken.getValue());
        assertEquals(52, lexer.getToken().getPosition());


        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(53, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DEFAULT, lexer.getToken().getType());
        assertEquals(56, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.COLON, lexer.getToken().getType());
        assertEquals(63, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(67, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(0, intToken.getValue());
        assertEquals(74, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(75, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_BRACE, lexer.getToken().getType());
        assertEquals(77, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(79, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseStructDefinition() {
        Source source = new StringSource("struct Address {\n\tint albumNr;\n\tstring name;\n\tAddress address;\n}");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.STRUCT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("Address", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_BRACE, lexer.getToken().getType());
        assertEquals(15, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT, lexer.getToken().getType());
        assertEquals(18, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("albumNr", stringToken.getValue());
        assertEquals( 22, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(29, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
        assertEquals(32, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("name", stringToken.getValue());
        assertEquals( 39, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(43, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("Address", stringToken.getValue());
        assertEquals(46, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("address", stringToken.getValue());
        assertEquals(54, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(61, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_BRACE, lexer.getToken().getType());
        assertEquals(63, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(64, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseFunctionDefinition() {
        Source source = new StringSource("double getDouble(double aa = 0.310, double bb = 0.020) {\n\treturn aa;\n}\n");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("getDouble", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(16, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
        assertEquals(17, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(24, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(27, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(0.31, doubleToken.getValue());
        assertEquals(29, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.COMMA, lexer.getToken().getType());
        assertEquals(34, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
        assertEquals(36, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("bb", stringToken.getValue());
        assertEquals(43, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(46, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(0.02, doubleToken.getValue());
        assertEquals(48, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(53, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_BRACE, lexer.getToken().getType());
        assertEquals(55, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(58, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(65, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(67, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_BRACE, lexer.getToken().getType());
        assertEquals(69, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(71, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseDoubleDeclaration() {
        Source source = new StringSource("double abc;");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("abc", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseStructVariableInitialisation() {
        Source source = new StringSource("Student student; \n student.name = \"Jan\"; \n student.albumNr = 100200;");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("Student", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("student", stringToken.getValue());
        assertEquals(8, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(15, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("student", stringToken.getValue());
        assertEquals(19, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOT, lexer.getToken().getType());
        assertEquals(26, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("name", stringToken.getValue());
        assertEquals(27, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(32, lexer.getToken().getPosition());


        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("Jan", stringToken.getValue());
        assertEquals(34, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(39, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("student", stringToken.getValue());
        assertEquals(43, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOT, lexer.getToken().getType());
        assertEquals(50, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("albumNr", stringToken.getValue());
        assertEquals(51, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(59, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(100200, intToken.getValue());
        assertEquals(61, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(67, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(68, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseIntAssignment() {
        Source source = new StringSource("int abc = 12345;");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.INT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("abc", stringToken.getValue());
        assertEquals(4, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(8, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(12345, intToken.getValue());
        assertEquals(10, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(15, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(16, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseDoubleAssignment() {
        Source source = new StringSource("double abc = 0.570;");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("abc", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(0.57, doubleToken.getValue());
        assertEquals(13, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(18, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(19, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseBoolAssignment() {
        Source source = new StringSource("bool abc = true;");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.BOOL, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("abc", stringToken.getValue());
        assertEquals(5, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(9, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.TRUE, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(15, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(16, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseDoubleQuoteStringAssignment() {
        Source source = new StringSource("string abc = \"Hello\";");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("abc", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("Hello", stringToken.getValue());
        assertEquals(13, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(20, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(21, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseApostropheStringAssignment() {
        Source source = new StringSource("string abc = 'Hello';");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("abc", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("Hello", stringToken.getValue());
        assertEquals(13, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(20, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(21, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseFunctionCalling() {
        Source source = new StringSource("someFunc(aa, 11.24, bb, 22);");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("someFunc", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(8, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(9, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.COMMA, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(11.24, doubleToken.getValue());
        assertEquals(13, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.COMMA, lexer.getToken().getType());
        assertEquals(18, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("bb", stringToken.getValue());
        assertEquals(20, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.COMMA, lexer.getToken().getType());
        assertEquals(22, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(22, intToken.getValue());
        assertEquals(24, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(26, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(27, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(28, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseIfElseInstruction() {
        Source source = new StringSource("if (aa == 1.5010) return aa; else return -1.010;");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.IF, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(3, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(4, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EQ, lexer.getToken().getType());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(1.501, doubleToken.getValue());
        assertEquals(10, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(16, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(18, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(25, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(27, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ELSE, lexer.getToken().getType());
        assertEquals(29, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(34, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.MINUS, lexer.getToken().getType());
        assertEquals(41, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(1.010, doubleToken.getValue());
        assertEquals(42, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(47, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(48, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseWhileInstruction() {
        Source source = new StringSource("while (aa <= 50) aa++;");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.WHILE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(6, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.LEQT, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(50, intToken.getValue());
        assertEquals(13, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(15, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(17, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.POSTINC, lexer.getToken().getType());
        assertEquals(19, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(21, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(22, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseMultipleConditions() {
        Source source = new StringSource("if ((11 < 22 && 30 != -15) || aa == 0.010) aa--;");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.IF, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(3, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(4, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(11, intToken.getValue());
        assertEquals(5, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.LT, lexer.getToken().getType());
        assertEquals(8, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(22, intToken.getValue());
        assertEquals(10, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.CONJUNCTION, lexer.getToken().getType());
        assertEquals(13, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(30, intToken.getValue());
        assertEquals(16, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.NEQ, lexer.getToken().getType());
        assertEquals(19, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.MINUS, lexer.getToken().getType());
        assertEquals(22, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(15, intToken.getValue());
        assertEquals(23, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(25, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ALTERNATIVE, lexer.getToken().getType());
        assertEquals(27, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(30, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EQ, lexer.getToken().getType());
        assertEquals(33, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(0.01, doubleToken.getValue());
        assertEquals(36, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(41, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(43, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.POSTDEC, lexer.getToken().getType());
        assertEquals(45, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(47, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(48, lexer.getToken().getPosition());
    }


    @Test
    void shouldRecogniseStringWithWhitespaces() {
        Source source = new StringSource("string str = \"Ala ma kota\";");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("str", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("Ala ma kota", stringToken.getValue());
        assertEquals(13, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(26, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(27, lexer.getToken().getPosition());
    }



    // max values of int, double and string tests

    @Test
    void shouldRecogniseUndefinedBecauseOfTooBigInt() {
        Source source = new StringSource("2147483648");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());
    }

    @Test
    void shouldRecogniseUndefinedBecauseOfTooBigDouble() {
        Source source = new StringSource("111111111100000000002222222223333333333444444444455.00000000001111111111222222222233333333334444444444");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.UNDEFINED, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
    }
}
