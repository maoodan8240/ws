package ws.common.utils.fileHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ws.common.utils.exception.FileWriterException;

public class FileWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileWriter.class);

    /**
     * 写入文件
     * 
     * @param call
     */
    public static void write(FileReadWritArgs call) {
        OutputStream outStream = null;
        Writer write = null;
        BufferedWriter bufWrite = null;
        try {
            if (!call.callContentWriteBefore()) {
                return;
            }
            if (!(call.getFileCharacters() instanceof FileWriteCharacters)) {
                return;
            }
            FileWriteCharacters fileCharacters = (FileWriteCharacters) call.getFileCharacters();// 文件对象配置对象
            if (fileCharacters.getFile().isDirectory()) {
                return;
            }
            int idx = fileCharacters.getFile().getPath().lastIndexOf(File.separator);
            String newPath = fileCharacters.getFile().getPath().substring(0, idx);
            File newf = new File(newPath);
            newf.mkdirs();
            outStream = new FileOutputStream(fileCharacters.getFile(), fileCharacters.isAppendText());
            write = new OutputStreamWriter(outStream, fileCharacters.getEncode().getCode());
            bufWrite = new BufferedWriter(write);
            call.callContentWrite(bufWrite, fileCharacters.getContent());
            bufWrite.flush();
        } catch (Exception e) {
            throw new FileWriterException("写入文件异常！FileCharacters=" + call.getFileCharacters(), e);
        } finally {
            try {
                if (bufWrite != null) {
                    bufWrite.close();
                }
                bufWrite = null;
            } catch (Exception e) {
                LOGGER.error("文件关闭异常！", e);
            }
        }
    }
}
