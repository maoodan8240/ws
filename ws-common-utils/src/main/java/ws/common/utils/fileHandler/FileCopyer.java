package ws.common.utils.fileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ws.common.utils.exception.FileCopyerException;

public class FileCopyer {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileCopyer.class);

    /**
     * 文件拷贝 D:\1\a.java ---> F:\1\a.java
     * 
     * @param oldPath
     *            被拷贝的文件
     * @param newPath
     *            新产生的文件，如果newPath的父目录不存在，则创建
     */
    public static void copy(String oldPath, String newPath) {
        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                inStream = new FileInputStream(oldPath); // 读入原文件
                File newf = new File(newPath.substring(0, newPath.lastIndexOf(File.separator)));
                newf.mkdirs();
                fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                LOGGER.debug("拷贝文件成功!\n    oldPath={}\n    newPath={}", oldPath, newPath);
            }
        } catch (Exception e) {
            new FileCopyerException("拷贝文件异常！oldPath=" + oldPath + " > newPath=" + newPath, e);
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (Exception e) {
                    LOGGER.error("文件关闭异常！", e);
                }
            }
        }
    }
}
