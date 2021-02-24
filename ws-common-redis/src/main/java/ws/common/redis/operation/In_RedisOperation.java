package ws.common.redis.operation;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class In_RedisOperation {
    private RedisParams.Params params;

    public In_RedisOperation(RedisParams.Params params) {
        this.params = params;
    }

    public RedisParams.Params getParams() {
        return params;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append("RedisDataStructureOp", params.getOp().getClass().getPackage() + "." + params.getOp().getClass().getName());
        builder.append("RedisDataStructureOpNickName", params.getOp().getNickName());
        builder.append("params", params);
        return builder.toString();
    }
}
