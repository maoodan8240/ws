package ws.common.utils.general;

import java.util.UUID;

public class UuidGenerator {

    public UuidGenerator() {
    }

    public static String newUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

}