package project.source;

import java.io.IOException;

public interface ISource {

    boolean isEOT();
    char getChar();
    int getPosition();
    void advance() throws IOException;
}
