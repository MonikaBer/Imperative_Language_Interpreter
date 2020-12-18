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
        lineNr = 0;
        positionAtLine = -1;
        character = '0';
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

    @Override
    public int getLineNr() { return lineNr; }

    @Override
    public int getPositionAtLine() { return positionAtLine; }

    protected void advancePosition() {
        if (character == '\n') {
            ++lineNr;
            positionAtLine = 0;
        } else {
            ++positionAtLine;
        }
        ++position;
    }
}
