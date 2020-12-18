package project.source;

import java.io.IOException;

public interface ISource {

    boolean isEOT();
    char getChar();
    int getPosition();
    int getLineNr();
    int getPositionAtLine();
    void advance() throws IOException;
}
