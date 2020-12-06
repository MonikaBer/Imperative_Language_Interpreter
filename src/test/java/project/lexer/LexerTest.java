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

        lexer.nextToken();
        assertEquals(Token.TokenType.TRUE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.TRUE, lexer.getToken().getType());
        assertEquals(5, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.TRUE, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.FALSE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.FALSE, lexer.getToken().getType());
        assertEquals(6, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.FALSE, lexer.getToken().getType());
        assertEquals(12, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.IF, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.IF, lexer.getToken().getType());
        assertEquals(3, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.IF, lexer.getToken().getType());
        assertEquals(6, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.ELSE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ELSE, lexer.getToken().getType());
        assertEquals(5, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ELSE, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.SWITCH, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SWITCH, lexer.getToken().getType());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.SWITCH, lexer.getToken().getType());
        assertEquals(14, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.CASE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.CASE, lexer.getToken().getType());
        assertEquals(5, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.CASE, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.DEFAULT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DEFAULT, lexer.getToken().getType());
        assertEquals(8, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DEFAULT, lexer.getToken().getType());
        assertEquals(16, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.WHILE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.WHILE, lexer.getToken().getType());
        assertEquals(6, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.WHILE, lexer.getToken().getType());
        assertEquals(12, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(2, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(4, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(14, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(2, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(4, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(2, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(4, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.L_BRACE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_BRACE, lexer.getToken().getType());
        assertEquals(2, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_BRACE, lexer.getToken().getType());
        assertEquals(4, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.R_BRACE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_BRACE, lexer.getToken().getType());
        assertEquals(2, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_BRACE, lexer.getToken().getType());
        assertEquals(4, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.INT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT, lexer.getToken().getType());
        assertEquals(4, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT, lexer.getToken().getType());
        assertEquals(8, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
        assertEquals(14, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.BOOL, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.BOOL, lexer.getToken().getType());
        assertEquals(5, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.BOOL, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
        assertEquals(14, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.VOID, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.VOID, lexer.getToken().getType());
        assertEquals(5, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.VOID, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());
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

        lexer.nextToken();
        assertEquals(Token.TokenType.STRUCT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.STRUCT, lexer.getToken().getType());
        assertEquals(7, lexer.getToken().getPosition());

        lexer.nextToken();
        assertEquals(Token.TokenType.STRUCT, lexer.getToken().getType());
        assertEquals(14, lexer.getToken().getPosition());
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
}
