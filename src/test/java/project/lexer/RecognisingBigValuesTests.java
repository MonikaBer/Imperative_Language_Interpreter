package project.lexer;

import org.junit.jupiter.api.Test;
import project.source.Source;
import project.source.StringSource;
import project.token.DoubleToken;
import project.token.IntToken;
import project.token.Token;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecognisingBigValuesTests {

    @Test
    void shouldRecogniseBigInt() {
        Source source = new StringSource("214748364733355");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.INT_NUMBER, lexer.getToken().getType());
        IntToken intToken = (IntToken) lexer.getToken();
        BigInteger bigInteger = new BigInteger("214748364733355");
        assertEquals(0, bigInteger.compareTo(intToken.getValue()));
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());

        lexer.nextToken();
        assertEquals(Token.TokenType.EOT, lexer.getToken().getType());
        assertEquals(15, lexer.getToken().getPosition());
        assertEquals(15, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }

    @Test
    void shouldRecogniseBigDouble() {
        Source source = new StringSource("111111111100000000002222222223333333333444444444455.00000000001111111111222222222233333333334444444444");
        Lexer lexer = new Lexer(source);

        assertEquals(Token.TokenType.DOUBLE_NUMBER, lexer.getToken().getType());
        DoubleToken doubleToken = (DoubleToken) lexer.getToken();
        BigDecimal bigDouble = new BigDecimal("111111111100000000002222222223333333333444444444455.00000000001111111111222222222233333333334444444444");
        assertEquals(0, bigDouble.compareTo(doubleToken.getValue()));
        assertEquals(0, lexer.getToken().getPosition());
        assertEquals(0, lexer.getToken().getPositionAtLine());
        assertEquals(0, lexer.getToken().getLineNr());
    }
}
