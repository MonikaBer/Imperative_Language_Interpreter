package project.source;

public interface ISource {

    boolean isEOT();
    char getChar();
    int getPosition();
    int getLineNr();
    int getPositionAtLine();
    void advance();
}
