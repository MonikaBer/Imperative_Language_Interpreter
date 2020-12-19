package project.source;

import project.exceptions.FileSourceReadException;

import java.io.*;

public class FileSource extends Source {

    private final FileInputStream source;

    public FileSource(String filename) throws IOException {
        this.source = new FileInputStream(filename);
        advance();
    }

    @Override
    public void advance() {
        if (EOT)
            return;

        advancePosition();

        try {
            int readByte = source.read();
            if (readByte != -1) {
                character = (char) readByte;
                return;
            }
        } catch (IOException ex) {
            throw new FileSourceReadException();
        }

        EOT = true;
        character = 0x03;
        try {
            source.close();
        } catch (IOException ex) {
            System.out.println("Problem with closing file source");
        }
    }
}
