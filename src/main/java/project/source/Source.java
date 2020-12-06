package project.source;

public abstract class Source implements ISource {

    protected boolean EOT;
    protected char character;
    protected int position;
    protected int lineNr;
    protected int positionAtLine;

    public Source() {
        EOT = false;
        position = -1;
    }

    @Override
    public boolean isEOT() {
        if (character == 0x03)
            EOT = true;
        return EOT;
    }

    @Override
    public char getChar() {
        return character;
    }

    @Override
    public int getPosition() {
        return position;
    }
}
