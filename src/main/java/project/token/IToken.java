package project.token;

public interface IToken {
    Token.TokenType getType();
    int getPosition();
    int getLineNr();
    int getPositionAtLine();
}
