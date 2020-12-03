package project.source;

public interface ISource {

    boolean isEOT();
    char getChar();
    int getPosition();
    void advance();
}
