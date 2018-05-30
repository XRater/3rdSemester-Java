package impl;

public class FTPFile {

    private final boolean isDirectory;
    private final String utf;

    public FTPFile(final String utf, final boolean isDirectory) {
        this.isDirectory = isDirectory;
        this.utf = utf;
    }

    public String name() {
        return utf;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    @Override
    public String toString() {
        return name();
    }
}
