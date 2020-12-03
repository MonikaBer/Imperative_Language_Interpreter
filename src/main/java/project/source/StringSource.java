package project.source;

public class StringSource extends Source {

    private final String source;

    public StringSource(String source) {
        this.source = source;
    }

    @Override
    public void advance() {
        if (EOT)
            return;

        position++;

        if (position < source.length()) {
            character = source.charAt(position);
            return;
        }

        EOT = true;
    }
}
