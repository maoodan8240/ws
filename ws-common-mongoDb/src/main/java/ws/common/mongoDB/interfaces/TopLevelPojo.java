package ws.common.mongoDB.interfaces;

import java.io.Serializable;

public interface TopLevelPojo extends Serializable {

    String getOid();

    void setOid(String oid);
}
