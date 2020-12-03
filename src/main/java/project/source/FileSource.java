package project.source;

import java.io.File;

public class FileSource extends Source {

    private final File source;

    public FileSource(String filename){
        this.source = new File(filename);
    }

    @Override
    public void advance() {
        // save new character to character field
        // check if not EOT
        // if yes then set isEOT = true
        this.position++;
    }
}
