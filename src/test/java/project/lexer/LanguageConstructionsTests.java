package project.lexer;

import org.junit.jupiter.api.Test;
import project.source.Source;
import project.source.StringSource;
import project.token.DoubleToken;
import project.token.IntToken;
import project.token.StringToken;
import project.token.Token;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LanguageConstructionsTests {

    @Test
    void shouldRecogniseMainFunction() {
        Source source = new StringSource("int main() {\n\treturn 0;\n}\n");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.INT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("main", stringToken.getValue());
        assertEquals(4, lexer.getToken().getPosition());
        assertEquals(4, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(8, lexer.getToken().getPosition());
        assertEquals(8, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(9, lexer.getToken().getPosition());
        assertEquals(9, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_BRACE, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(14, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(0, intToken.getValue());
        assertEquals(21, lexer.getToken().getPosition());
        assertEquals(8, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(22, lexer.getToken().getPosition());
        assertEquals(9, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_BRACE, lexer.getToken().getType());
        assertEquals(24, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(26, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(3, lexer.getToken().getLineNr());
    }


    @Test
    void shouldRecogniseSwitchInstruction() {
        Source source = new StringSource("switch (a) {\n\tcase 1: \t\treturn 1;\n\tcase 2: \t\treturn 2;\n\tdefault: \t\treturn 0;\n}\n");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.SWITCH, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(7, lexer.getToken().getPosition());
        assertEquals(7, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("a", stringToken.getValue());
        assertEquals(8, lexer.getToken().getPosition());
        assertEquals(8, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(9, lexer.getToken().getPosition());
        assertEquals(9, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_BRACE, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.CASE, lexer.getToken().getType());
        assertEquals(14, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(1, intToken.getValue());
        assertEquals(19, lexer.getToken().getPosition());
        assertEquals(6, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.COLON, lexer.getToken().getType());
        assertEquals(20, lexer.getToken().getPosition());
        assertEquals(7, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(24, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(1, intToken.getValue());
        assertEquals(31, lexer.getToken().getPosition());
        assertEquals(18, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(32, lexer.getToken().getPosition());
        assertEquals(19, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.CASE, lexer.getToken().getType());
        assertEquals(35, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(2, intToken.getValue());
        assertEquals(40, lexer.getToken().getPosition());
        assertEquals(6, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.COLON, lexer.getToken().getType());
        assertEquals(41, lexer.getToken().getPosition());
        assertEquals(7, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(45, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(2, intToken.getValue());
        assertEquals(52, lexer.getToken().getPosition());
        assertEquals(18, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(53, lexer.getToken().getPosition());
        assertEquals(19, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.DEFAULT, lexer.getToken().getType());
        assertEquals(56, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(3, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.COLON, lexer.getToken().getType());
        assertEquals(63, lexer.getToken().getPosition());
        assertEquals(8, lexer.getToken().getPositionAtLine());
        assertEquals(3, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(67, lexer.getToken().getPosition());
        assertEquals(12, lexer.getToken().getPositionAtLine());
        assertEquals(3, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(0, intToken.getValue());
        assertEquals(74, lexer.getToken().getPosition());
        assertEquals(19, lexer.getToken().getPositionAtLine());
        assertEquals(3, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(75, lexer.getToken().getPosition());
        assertEquals(20, lexer.getToken().getPositionAtLine());
        assertEquals(3, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_BRACE, lexer.getToken().getType());
        assertEquals(77, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(4, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(79, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(5, lexer.getToken().getLineNr());
    }


    @Test
    void shouldRecogniseStructDefinition() {
        Source source = new StringSource("struct Address {\n\tint albumNr;\n\tstring name;\n\tAddress address;\n}");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.STRUCT, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("Address", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());
        assertEquals(7, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_BRACE, lexer.getToken().getType());
        assertEquals(15, lexer.getToken().getPosition());
        assertEquals(15, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT, lexer.getToken().getType());
        assertEquals(18, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("albumNr", stringToken.getValue());
        assertEquals( 22, lexer.getToken().getPosition());
        assertEquals(5, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(29, lexer.getToken().getPosition());
        assertEquals(12, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
        assertEquals(32, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("name", stringToken.getValue());
        assertEquals( 39, lexer.getToken().getPosition());
        assertEquals(8, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(43, lexer.getToken().getPosition());
        assertEquals(12, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("Address", stringToken.getValue());
        assertEquals(46, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(3, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("address", stringToken.getValue());
        assertEquals(54, lexer.getToken().getPosition());
        assertEquals(9, lexer.getToken().getPositionAtLine());
        assertEquals(3, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(61, lexer.getToken().getPosition());
        assertEquals(16, lexer.getToken().getPositionAtLine());
        assertEquals(3, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_BRACE, lexer.getToken().getType());
        assertEquals(63, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(4, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(64, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(4, lexer.getToken().getLineNr());
    }


    @Test
    void shouldRecogniseFunctionDefinition() {
        Source source = new StringSource("double getDouble(double aa = 0.310, double bb = 0.020) {\n\treturn aa;\n}\n");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

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
        assertEquals(55, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(58, lexer.getToken().getPosition());
        assertEquals(1, lexer.getToken().getPositionAtLine());
        assertEquals(1, lexer.getToken().getLineNr());

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
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(2, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(71, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(3, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseDoubleDeclaration() {
        Source source = new StringSource("double abc;");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("abc", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());
        assertEquals(7, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());
        assertEquals(10, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
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
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("student", stringToken.getValue());
        assertEquals(8, lexer.getToken().getPosition());
        assertEquals(8, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(15, lexer.getToken().getPosition());
        assertEquals(15, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

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
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("abc", stringToken.getValue());
        assertEquals(4, lexer.getToken().getPosition());
        assertEquals(4, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(8, lexer.getToken().getPosition());
        assertEquals(8, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(12345, intToken.getValue());
        assertEquals(10, lexer.getToken().getPosition());
        assertEquals(10, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(15, lexer.getToken().getPosition());
        assertEquals(15, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(16, lexer.getToken().getPosition());
        assertEquals(16, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }


    @Test
    void shouldRecogniseDoubleAssignment() {
        Source source = new StringSource("double abc = 0.570;");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("abc", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());
        assertEquals(7, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(0.57, doubleToken.getValue());
        assertEquals(13, lexer.getToken().getPosition());
        assertEquals(13, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(18, lexer.getToken().getPosition());
        assertEquals(18, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(19, lexer.getToken().getPosition());
        assertEquals(19, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }


    @Test
    void shouldRecogniseBoolAssignment() {
        Source source = new StringSource("bool abc = true;");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.BOOL, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("abc", stringToken.getValue());
        assertEquals(5, lexer.getToken().getPosition());
        assertEquals(5, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(9, lexer.getToken().getPosition());
        assertEquals(9, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.TRUE, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(15, lexer.getToken().getPosition());
        assertEquals(15, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(16, lexer.getToken().getPosition());
        assertEquals(16, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }


    @Test
    void shouldRecogniseDoubleQuoteStringAssignment() {
        Source source = new StringSource("string abc = \"Hello\";");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("abc", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());
        assertEquals(7, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("Hello", stringToken.getValue());
        assertEquals(13, lexer.getToken().getPosition());
        assertEquals(13, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(20, lexer.getToken().getPosition());
        assertEquals(20, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(21, lexer.getToken().getPosition());
        assertEquals(21, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }


    @Test
    void shouldRecogniseApostropheStringAssignment() {
        Source source = new StringSource("string abc = 'Hello';");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("abc", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());
        assertEquals(7, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("Hello", stringToken.getValue());
        assertEquals(13, lexer.getToken().getPosition());
        assertEquals(13, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(20, lexer.getToken().getPosition());
        assertEquals(20, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(21, lexer.getToken().getPosition());
        assertEquals(21, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
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
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(8, lexer.getToken().getPosition());
        assertEquals(8, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(9, lexer.getToken().getPosition());
        assertEquals(9, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.COMMA, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(11.24, doubleToken.getValue());
        assertEquals(13, lexer.getToken().getPosition());
        assertEquals(13, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.COMMA, lexer.getToken().getType());
        assertEquals(18, lexer.getToken().getPosition());
        assertEquals(18, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("bb", stringToken.getValue());
        assertEquals(20, lexer.getToken().getPosition());
        assertEquals(20, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.COMMA, lexer.getToken().getType());
        assertEquals(22, lexer.getToken().getPosition());
        assertEquals(22, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(22, intToken.getValue());
        assertEquals(24, lexer.getToken().getPosition());
        assertEquals(24, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(26, lexer.getToken().getPosition());
        assertEquals(26, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(27, lexer.getToken().getPosition());
        assertEquals(27, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(28, lexer.getToken().getPosition());
        assertEquals(28, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }


    @Test
    void shouldRecogniseIfElseInstruction() {
        Source source = new StringSource("if (aa == 1.5010) return aa; else return -1.010;");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.IF, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(3, lexer.getToken().getPosition());
        assertEquals(3, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(4, lexer.getToken().getPosition());
        assertEquals(4, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EQ, lexer.getToken().getType());
        assertEquals(7, lexer.getToken().getPosition());
        assertEquals(7, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(1.501, doubleToken.getValue());
        assertEquals(10, lexer.getToken().getPosition());
        assertEquals(10, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(16, lexer.getToken().getPosition());
        assertEquals(16, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(18, lexer.getToken().getPosition());
        assertEquals(18, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(25, lexer.getToken().getPosition());
        assertEquals(25, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(27, lexer.getToken().getPosition());
        assertEquals(27, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ELSE, lexer.getToken().getType());
        assertEquals(29, lexer.getToken().getPosition());
        assertEquals(29, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.RETURN, lexer.getToken().getType());
        assertEquals(34, lexer.getToken().getPosition());
        assertEquals(34, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.MINUS, lexer.getToken().getType());
        assertEquals(41, lexer.getToken().getPosition());
        assertEquals(41, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(1.010, doubleToken.getValue());
        assertEquals(42, lexer.getToken().getPosition());
        assertEquals(42, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(47, lexer.getToken().getPosition());
        assertEquals(47, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(48, lexer.getToken().getPosition());
        assertEquals(48, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }


    @Test
    void shouldRecogniseWhileInstruction() {
        Source source = new StringSource("while (aa <= 50) aa++;");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.WHILE, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(6, lexer.getToken().getPosition());
        assertEquals(6, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());
        assertEquals(7, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.LEQT, lexer.getToken().getType());
        assertEquals(10, lexer.getToken().getPosition());
        assertEquals(10, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(50, intToken.getValue());
        assertEquals(13, lexer.getToken().getPosition());
        assertEquals(13, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(15, lexer.getToken().getPosition());
        assertEquals(15, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(17, lexer.getToken().getPosition());
        assertEquals(17, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.POSTINC, lexer.getToken().getType());
        assertEquals(19, lexer.getToken().getPosition());
        assertEquals(19, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(21, lexer.getToken().getPosition());
        assertEquals(21, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(22, lexer.getToken().getPosition());
        assertEquals(22, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }


    @Test
    void shouldRecogniseMultipleConditions() {
        Source source = new StringSource("if ((11 < 22 && 30 != -15) || aa == 0.010) aa--;");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.IF, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(3, lexer.getToken().getPosition());
        assertEquals(3, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.L_PARENTH, lexer.getToken().getType());
        assertEquals(4, lexer.getToken().getPosition());
        assertEquals(4, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        assertEquals(11, intToken.getValue());
        assertEquals(5, lexer.getToken().getPosition());
        assertEquals(5, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.LT, lexer.getToken().getType());
        assertEquals(8, lexer.getToken().getPosition());
        assertEquals(8, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(22, intToken.getValue());
        assertEquals(10, lexer.getToken().getPosition());
        assertEquals(10, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.CONJUNCTION, lexer.getToken().getType());
        assertEquals(13, lexer.getToken().getPosition());
        assertEquals(13, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(30, intToken.getValue());
        assertEquals(16, lexer.getToken().getPosition());
        assertEquals(16, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.NEQ, lexer.getToken().getType());
        assertEquals(19, lexer.getToken().getPosition());
        assertEquals(19, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.MINUS, lexer.getToken().getType());
        assertEquals(22, lexer.getToken().getPosition());
        assertEquals(22, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        intToken = (IntToken) lexer.getToken();
        assertEquals(15, intToken.getValue());
        assertEquals(23, lexer.getToken().getPosition());
        assertEquals(23, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(25, lexer.getToken().getPosition());
        assertEquals(25, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ALTERNATIVE, lexer.getToken().getType());
        assertEquals(27, lexer.getToken().getPosition());
        assertEquals(27, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(30, lexer.getToken().getPosition());
        assertEquals(30, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EQ, lexer.getToken().getType());
        assertEquals(33, lexer.getToken().getPosition());
        assertEquals(33, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        assertEquals(0.01, doubleToken.getValue());
        assertEquals(36, lexer.getToken().getPosition());
        assertEquals(36, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.R_PARENTH, lexer.getToken().getType());
        assertEquals(41, lexer.getToken().getPosition());
        assertEquals(41, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("aa", stringToken.getValue());
        assertEquals(43, lexer.getToken().getPosition());
        assertEquals(43, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.POSTDEC, lexer.getToken().getType());
        assertEquals(45, lexer.getToken().getPosition());
        assertEquals(45, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(47, lexer.getToken().getPosition());
        assertEquals(47, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(48, lexer.getToken().getPosition());
        assertEquals(48, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }


    @Test
    void shouldRecogniseStringWithWhitespaces() {
        Source source = new StringSource("string str = \"Ala ma kota\";");
        Lexer lexer = new Lexer(source);

        lexer.nextToken();
        assertEquals(Token.TokenType.STRING, lexer.getToken().getType());
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ID, lexer.getToken().getType());
        StringToken stringToken = (StringToken) lexer.getToken();
        assertEquals("str", stringToken.getValue());
        assertEquals(7, lexer.getToken().getPosition());
        assertEquals(7, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.ASSIGN, lexer.getToken().getType());
        assertEquals(11, lexer.getToken().getPosition());
        assertEquals(11, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.TEXT, lexer.getToken().getType());
        stringToken = (StringToken) lexer.getToken();
        assertEquals("Ala ma kota", stringToken.getValue());
        assertEquals(13, lexer.getToken().getPosition());
        assertEquals(13, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.SEMICOLON, lexer.getToken().getType());
        assertEquals(26, lexer.getToken().getPosition());
        assertEquals(26, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(27, lexer.getToken().getPosition());
        assertEquals(27, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }
}
