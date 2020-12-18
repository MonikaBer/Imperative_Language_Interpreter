package project.lexer;

public class Buffer {

    private StringBuilder buffer;
    private int idx;

    public Buffer() {
        buffer = new StringBuilder();
        idx = 0;
    }

    public void append(char character) {
        if (idx < buffer.length())
            return;
        buffer.append(character);
        ++idx;
    }

    public void clear() {
        buffer.setLength(0);
        idx = 0;
    }

    public void clearReadPart() {
        String newStr = buffer.substring(idx, buffer.length());
        buffer.setLength(0);
        idx = 0;
        buffer.append(newStr);
    }

    public void resetIdx() {
        idx = 0;
    }

    public char getChar() {
        if (idx == buffer.toString().length())
            return 0x03;

        return buffer.charAt(idx);
    }

    public void incrementIdx() {
        ++idx;
    }

    public int getIdx() {
        return idx;
    }

    public int length() {
        return buffer.toString().length();
    }

    public String get() {
        return buffer.toString();
    }
}
