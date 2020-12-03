package project.source;

public class StringSource extends Source {

    private final String source;

    public StringSource(String source) {
        this.source = source;
    }

    @Override
    public void advance() {
        // save new character to character field
        // check if not EOT
        // if yes then set isEOT = true
        this.position++;
    }
}
