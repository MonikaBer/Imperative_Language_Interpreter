package project.source;

import java.io.*;

public class FileSource extends Source {

    private final FileInputStream source;

    public FileSource(String filename) throws IOException {
        this.source = new FileInputStream(filename);
        advance();
    }

    @Override
    public void advance() throws IOException {
        if (EOT)
            return;

        advancePosition();

        int readByte = source.read();
        if (readByte != -1) {
            character = (char) readByte;
            return;
        }

        EOT = true;
        character = 0x03;
        source.close();
    }
}
