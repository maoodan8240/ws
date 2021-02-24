package ws.common.utils.fileHandler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.GZIPInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ws.common.utils.exception.FileReaderException;

public class FileReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileReader.class);

    /**
     * 读取文件，支持gz压缩包的读取
     * 
     * @param call
     */
    public static Object read(FileReadWritArgs call) {
        Object objReturn = null;
        InputStream is = null;
        InputStream gzips = null;
        Reader read = null;
        BufferedReader bufRead = null;
        try {
            if (!call.callContentReadBefore())
                return objReturn;
            if (!(call.getFileCharacters() instanceof FileReadCharacters)) {
                return objReturn;
            }
            FileReadCharacters fileCharacters = (FileReadCharacters) call.getFileCharacters();// 文件对象配置对象
            if (!fileCharacters.getFile().exists() || fileCharacters.getFile().isDirectory()) {
                return null;
            }
            if (fileCharacters.getInputStream() != null && fileCharacters.getInputStream() instanceof InputStream) {
                is = fileCharacters.getInputStream();// 启用存在的InputStream
            } else {
                is = new FileInputStream(fileCharacters.getFile());
            }
            if (fileCharacters.isReadFileIsRAR()) {
                gzips = new GZIPInputStream(is);// 如果是gz压缩文件，支持读取
                read = new InputStreamReader(gzips, fileCharacters.getEncode().getCode());
            } else {
                read = new InputStreamReader(is, fileCharacters.getEncode().getCode());
            }
            bufRead = new BufferedReader(read);
            objReturn = call.callContentRead(bufRead, fileCharacters.getArgs());
        } catch (Exception e) {
            throw new FileReaderException("读取文件异常！FileCharacters=" + call.getFileCharacters(), e);
        } finally {
            try {
                if (bufRead != null) {
                    bufRead.close();
                }
                bufRead = null;
            } catch (Exception e) {
                LOGGER.error("文件关闭异常！", e);
            }
        }
        return objReturn;
    }
}
