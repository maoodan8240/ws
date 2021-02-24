package ws.common.utils.fileHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public interface FileReadWritArgs {

    /**
     * 获取读写文件配置对象
     * 
     * @return
     */
    FileCharacters getFileCharacters();

    /**
     * 调用写入文件前的前置控制方法
     * 
     * @return
     */
    boolean callContentWriteBefore() throws Exception;

    /**
     * 建立bufWrite后调用的方法
     * 
     * @param bufWrite
     */
    void callContentWrite(BufferedWriter bufWrite, String content) throws Exception;

    /**
     * 调用读取文件前的前置控制方法
     * 
     * @return
     */
    boolean callContentReadBefore() throws Exception;

    /**
     * 建立bufRead后调用的方法
     * 
     * @param bufRead
     */
    Object callContentRead(BufferedReader bufRead, Object... args) throws Exception;

}
