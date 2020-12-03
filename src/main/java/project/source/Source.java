package project.source;

public abstract class Source implements ISource {

    protected boolean EOT;
    protected char character;
    protected int position;

    public Source() {
        EOT = false;
    }

    @Override
    public boolean isEOT() {
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
