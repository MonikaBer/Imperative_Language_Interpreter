package project.lexer;

import org.junit.jupiter.api.Test;
import project.source.Source;
import project.source.StringSource;
import project.token.DoubleToken;
import project.token.IntToken;
import project.token.StringToken;
import project.token.Token;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleMultipleTokensTests {

    @Test
    void shouldRecogniseMultipleIdTokens() {
        Source source = new StringSource("\n\t var1 \n\t var2 \n\t var3 \t\n\n\t");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("var1", stringToken.getValue());
        assertEquals(3, lexer.getToken().getPosition());
        assertEquals(2, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("var2", stringToken.getValue());
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(2, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("var3", stringToken.getValue());
        assertEquals(19, lexer.getToken().getPosition());
        assertEquals(2, lexer.getToken().getPositionAtLine());
        assertEquals(3, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(28, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(5, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseMultipleIdTokensWithWhitespaces() {
        Source source = new StringSource("var1           var2 \n \t \r var3");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("var1", stringToken.getValue());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("var2", stringToken.getValue());
        assertEquals(15, lexer.getToken().getPosition());
        assertEquals(15, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("var3", stringToken.getValue());
        assertEquals(26, lexer.getToken().getPosition());
        assertEquals(5, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(30, lexer.getToken().getPosition());
        assertEquals(9, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseMultipleApostropheTextTokensWithWhitespacesAndComments() {
        Source source = new StringSource("#aaa\n  \t \n 'txt1' \n\r 'txt2' \r\r\r 'txt3'   \n \r\r \t\t");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("txt1", stringToken.getValue());
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("txt2", stringToken.getValue());
        assertEquals(21, lexer.getToken().getPosition());
        assertEquals(2, lexer.getToken().getPositionAtLine());
        assertEquals(3, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("txt3", stringToken.getValue());
        assertEquals(32, lexer.getToken().getPosition());
        assertEquals(13, lexer.getToken().getPositionAtLine());
        assertEquals(3, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(48, lexer.getToken().getPosition());
        assertEquals(6, lexer.getToken().getPositionAtLine());
        assertEquals(4, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseMultipleDoubleQuoteTextTokens() {
        Source source = new StringSource("\"txt1\" \"txt2\" \"txt3\"");
        Lexer lexer = new Lexer(source);

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
    void shouldRecogniseMultipleIntNumberTokens() {
        Source source = new StringSource("\n 10 0 12 01");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        BigInteger bigInteger = new BigInteger("10");
        assertEquals(0, bigInteger.compareTo(intToken.getValue()));
        assertEquals(2, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        bigInteger = new BigInteger("0");
        assertEquals(0, bigInteger.compareTo(intToken.getValue()));
        assertEquals(5, lexer.getToken().getPosition());
        assertEquals(4, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        bigInteger = new BigInteger("12");
        assertEquals(0, bigInteger.compareTo(intToken.getValue()));
        assertEquals(7, lexer.getToken().getPosition());
        assertEquals(6, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        bigInteger = new BigInteger("0");
        assertEquals(0, bigInteger.compareTo(intToken.getValue()));
        assertEquals(10, lexer.getToken().getPosition());
        assertEquals(9, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        bigInteger = new BigInteger("1");
        assertEquals(0, bigInteger.compareTo(intToken.getValue()));
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(10, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(12, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseMultipleDoubleNumberTokensWithWhitespaces() {
        Source source = new StringSource("\n\n34.17 \n\n 34.0 \n\n 34.05 \n\n 38.07080\n\n\n");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        BigDecimal bigDouble = new BigDecimal("34.17");
        assertEquals(0, bigDouble.compareTo(doubleToken.getValue()));
        assertEquals(2, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        doubleToken = (DoubleToken) lexer.getToken();
        bigDouble = new BigDecimal("34.0");
        assertEquals(0, bigDouble.compareTo(doubleToken.getValue()));
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(4, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        doubleToken = (DoubleToken) lexer.getToken();
        bigDouble = new BigDecimal("34.05");
        assertEquals(0, bigDouble.compareTo(doubleToken.getValue()));
        assertEquals(19, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(6, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        doubleToken = (DoubleToken) lexer.getToken();
        BigDecimal bigDecimal = new BigDecimal("38.07080");
        assertEquals(0, bigDecimal.compareTo(doubleToken.getValue()));
        assertEquals(28, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(8, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(39, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(11, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseMultipleDoubleNumberTokens() {
        Source source = new StringSource("0.0  0.10  0.01  0.00100");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        BigDecimal bigDouble = new BigDecimal("0.0");
        assertEquals(0, bigDouble.compareTo(doubleToken.getValue()));
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        doubleToken = (DoubleToken) lexer.getToken();
        bigDouble = new BigDecimal("0.1");
        assertEquals(0, bigDouble.compareTo(doubleToken.getValue()));
        assertEquals(5, lexer.getToken().getPosition());
        assertEquals(5, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        doubleToken = (DoubleToken) lexer.getToken();
        bigDouble = new BigDecimal("0.01");
        assertEquals(0, bigDouble.compareTo(doubleToken.getValue()));
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        doubleToken = (DoubleToken) lexer.getToken();
        bigDouble = new BigDecimal("0.001");
        assertEquals(0, bigDouble.compareTo(doubleToken.getValue()));        assertEquals(17, lexer.getToken().getPosition());
        assertEquals(17, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseMultipleTrueTokens() {
        Source source = new StringSource("\ntrue\ntrue\ntrue\n");
        Lexer lexer = new Lexer(source);

        for (int i = 1, j= 1; i < 16; i += 5, ++j) {
            assertEquals(Token.TokenType.TRUE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            assertEquals(0, lexer.getToken().getPositionAtLine());
            assertEquals(j, lexer.getToken().getLineNr());
            lexer.nextToken();
        }

        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(16, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(4, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseMultipleFalseTokens() {
        Source source = new StringSource("false false false");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 18; i += 6) {
            assertEquals(Token.TokenType.FALSE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleIfTokens() {
        Source source = new StringSource("\n if \n if \n if \n ");
        Lexer lexer = new Lexer(source);

        for (int i = 2, j = 1; i < 17; i += 5, ++j) {
            assertEquals(Token.TokenType.IF, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            assertEquals(1, lexer.getToken().getPositionAtLine());
            assertEquals(j, lexer.getToken().getLineNr());
            lexer.nextToken();
        }

        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(17, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(4, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseMultipleElseTokens() {
        Source source = new StringSource("else else else");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 15; i += 5) {
            assertEquals(Token.TokenType.ELSE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleSwitchTokens() {
        Source source = new StringSource("switch switch switch");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 21; i += 7) {
            assertEquals(Token.TokenType.SWITCH, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleCaseTokens() {
        Source source = new StringSource("case case case");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 15; i += 5) {
            assertEquals(Token.TokenType.CASE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleDefaultTokens() {
        Source source = new StringSource("default default default");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 24; i += 8) {
            assertEquals(Token.TokenType.DEFAULT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleWhileTokens() {
        Source source = new StringSource("while while while");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 18; i += 6) {
            assertEquals(Token.TokenType.WHILE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleAssignTokens() {
        Source source = new StringSource("\n = \n = \n = \n ");
        Lexer lexer = new Lexer(source);

        for (int i = 2, j = 1; i < 14; i += 4, ++j) {
            assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            assertEquals(1, lexer.getToken().getPositionAtLine());
            assertEquals(j, lexer.getToken().getLineNr());
            lexer.nextToken();
        }

        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(14, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(4, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseMultipleReturnTokens() {
        Source source = new StringSource("return return return");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 21; i += 7) {
            assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleLParenthTokens() {
        Source source = new StringSource("( ( (");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleRParenthTokens() {
        Source source = new StringSource(") ) )");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleLBraceTokens() {
        Source source = new StringSource("{ { {");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.L_BRACE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleRBraceTokens() {
        Source source = new StringSource("} } }");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.R_BRACE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleIntTokens() {
        Source source = new StringSource("int int int");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 12; i += 4) {
            assertEquals(Token.TokenType.INT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleDoubleTokens() {
        Source source = new StringSource("double double double");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 21; i += 7) {
            assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleBoolTokens() {
        Source source = new StringSource("bool bool bool");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 15; i += 5) {
            assertEquals(Token.TokenType.BOOL, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleStringTokens() {
        Source source = new StringSource("string string string");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 21; i += 7) {
            assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleVoidTokens() {
        Source source = new StringSource("void void void");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 15; i += 5) {
            assertEquals(Token.TokenType.VOID, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleStructTokens() {
        Source source = new StringSource("struct struct struct");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 21; i += 7) {
            assertEquals(Token.TokenType.STRUCT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleEqTokens() {
        Source source = new StringSource("== == ==");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            assertEquals(Token.TokenType.EQ, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleNeqTokens() {
        Source source = new StringSource("!= != !=");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            assertEquals(Token.TokenType.NEQ, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleGeqtTokens() {
        Source source = new StringSource(">= >= >=");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            assertEquals(Token.TokenType.GEQT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleGtTokens() {
        Source source = new StringSource("> > >");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.GT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleLeqtTokens() {
        Source source = new StringSource("<= <= <=");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            assertEquals(Token.TokenType.LEQT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleLtTokens() {
        Source source = new StringSource("< < <");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.LT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultiplePlusTokens() {
        Source source = new StringSource("+ + +");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.PLUS, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleMinusTokens() {
        Source source = new StringSource("- - -");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.MINUS, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultiplePostincTokens() {
        Source source = new StringSource("++ ++ ++");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            assertEquals(Token.TokenType.INC, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultiplePostdecTokens() {
        Source source = new StringSource("-- -- --");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            assertEquals(Token.TokenType.DEC, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleMulTokens() {
        Source source = new StringSource("* * *");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.MUL, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleDivTokens() {
        Source source = new StringSource("/ / /");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.DIV, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleModTokens() {
        Source source = new StringSource("% % %");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.MOD, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleAlternativeTokens() {
        Source source = new StringSource("|| || ||");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            assertEquals(Token.TokenType.ALTERNATIVE, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleConjunctionTokens() {
        Source source = new StringSource("&& && &&");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 9; i += 3) {
            assertEquals(Token.TokenType.CONJUNCTION, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleNegationTokens() {
        Source source = new StringSource("! ! !");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.NEGATION, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleSemicolonTokens() {
        Source source = new StringSource("; ; ;");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleDotTokens() {
        Source source = new StringSource(". . .");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.DOT, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleColonTokens() {
        Source source = new StringSource(": : :");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.COLON, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

    @Test
    void shouldRecogniseMultipleCommaTokens() {
        Source source = new StringSource(", , ,");
        Lexer lexer = new Lexer(source);

        for (int i = 0; i < 6; i += 2) {
            assertEquals(Token.TokenType.COMMA, lexer.getToken().getType());
            assertEquals(i, lexer.getToken().getPosition());
            lexer.nextToken();
        }
    }

}
