package ws.common.utils.fileHandler;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FileReadCharacters extends FileCharacters {

    private boolean readFileIsRAR;// 读取的文件是否是gz等压缩文件
    private InputStream inputStream;// 读取文件时已经存在的inputStream
    private Object[] args;

    public FileReadCharacters(File file, Object... args) {
        this(Encode.UTF_8, file, false, null, args);
    }

    public FileReadCharacters(Encode encode, File file, boolean readFileIsRAR, InputStream inputStream, Object... args) {
        super(encode, file);
        this.readFileIsRAR = readFileIsRAR;
        this.inputStream = inputStream;
        this.args = args;
    }

    public boolean isReadFileIsRAR() {
        return readFileIsRAR;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setReadFileIsRAR(boolean readFileIsRAR) {
        this.readFileIsRAR = readFileIsRAR;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append(super.toString());
        builder.append("readFileIsRAR", readFileIsRAR);
        return builder.toString();
    }
}
