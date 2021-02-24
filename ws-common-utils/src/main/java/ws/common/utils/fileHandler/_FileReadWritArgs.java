package ws.common.utils.fileHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class _FileReadWritArgs implements FileReadWritArgs {
    private FileCharacters fileCharacters;

    public _FileReadWritArgs(FileCharacters fileCharacters) {
        this.fileCharacters = fileCharacters;
    }

    @Override
    public FileCharacters getFileCharacters() {
        return fileCharacters;
    }

    @Override
    public boolean callContentWriteBefore() throws Exception {
        return true;
    }

    @Override
    public void callContentWrite(BufferedWriter bufWrite, String content) throws Exception {

    }

    @Override
    public boolean callContentReadBefore() throws Exception {
        return true;
    }

    @Override
    public Object callContentRead(BufferedReader bufRead, Object... args) throws Exception {
        return null;
    }
}
