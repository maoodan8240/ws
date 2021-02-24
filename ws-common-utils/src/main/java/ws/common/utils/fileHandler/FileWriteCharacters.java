package ws.common.utils.fileHandler;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FileWriteCharacters extends FileCharacters {

    private boolean appendText;// 写文件时是否追加
    private String content;

    /**
     * 文件目录如果不存在，则会自动创建
     * 
     * @param file
     * @param content
     */
    public FileWriteCharacters(File file, String content) {
        this(Encode.UTF_8, file, content, true);
    }

    public FileWriteCharacters(Encode encode, File file, String content, boolean appendText) {
        super(encode, file);
        this.content = content;
        this.appendText = appendText;
    }

    public boolean isAppendText() {
        return appendText;
    }

    public void setAppendText(boolean appendText) {
        this.appendText = appendText;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append(super.toString());
        builder.append("appendText", appendText);
        builder.append("content", content);
        return builder.toString();
    }
}
