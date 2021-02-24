package ws.common.network.server.implement;

import ws.common.network.server.interfaces.CodeToMessagePrototype;
import ws.common.network.server.interfaces.NetworkContext;

public class _NetworkContext implements NetworkContext {
    // 协议号-协议原型
    private CodeToMessagePrototype codeToMessagePrototype;

    @Override
    public CodeToMessagePrototype getCodeToMessagePrototype() {
        return codeToMessagePrototype;
    }

    @Override
    public void setCodeToMessagePrototype(CodeToMessagePrototype codeToMessagePrototype) {
        this.codeToMessagePrototype = codeToMessagePrototype;
    }
}