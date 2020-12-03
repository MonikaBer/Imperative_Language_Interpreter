package project.source;

import java.io.*;

public class FileSource extends Source {

    private final int BUFF_LEN = 1048576;

    private final BufferedReader source;
    private char[] buff;
    private int offs;
    private int len;

    public FileSource(String filename) throws FileNotFoundException {
        FileReader fr = new FileReader(filename);
        this.source = new BufferedReader(fr);
        buff = new char[BUFF_LEN];
        offs = -1;
        len = -1;
    }

    @Override
    public void advance() throws IOException {
        if (EOT)
            return;

        position++;
        offs++;

        if (offs < len) {
            character = buff[offs];
            return;
        }

        offs = 0;
        len = source.read(buff, 0, BUFF_LEN);

        if (offs < len) {
            character = buff[offs];
            return;
        }

        EOT = true;
    }
}
