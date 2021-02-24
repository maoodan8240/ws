package ws.common.utils.fileHandler;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FileCharacters {
    private Encode encode;// 字符集
    private File file;

    public FileCharacters(Encode encode, File file) {
        this.encode = encode;
        this.file = file;
    }

    public Encode getEncode() {
        return encode;
    }

    public void setEncode(Encode encode) {
        this.encode = encode;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append("encode", encode.getCode());
        builder.append("file", file.getAbsolutePath());
        return builder.toString();
    }
}
