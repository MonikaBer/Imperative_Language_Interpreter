package project.source;

public class StringSource extends Source {

    private final String source;

    public StringSource(String source) {
        this.source = source;
        advance();
    }

    @Override
    public void advance() {
        if (EOT)
            return;

        advancePosition();

        if (position < source.length()) {
            character = source.charAt(position);
            return;
        }

        EOT = true;
        character = 0x03;
    }
}
