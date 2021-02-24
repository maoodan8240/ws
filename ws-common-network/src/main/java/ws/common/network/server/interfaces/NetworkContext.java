package ws.common.network.server.interfaces;

public interface NetworkContext {

    /**
     * 获取 协议号-协议原型
     * 
     * @return
     */
    CodeToMessagePrototype getCodeToMessagePrototype();

    /**
     * 设置 协议号-协议原型
     * 
     * @param codeToMessagePrototype
     */
    void setCodeToMessagePrototype(CodeToMessagePrototype codeToMessagePrototype);

}