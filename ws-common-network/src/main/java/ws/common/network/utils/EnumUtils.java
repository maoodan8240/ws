package ws.common.network.utils;

import java.util.Objects;

public class EnumUtils {

    /**
     *  gameServer.features.standalone.extp.itemIo.gm.ItemIoGmSupport$ItemIoGmSupportEnum.Op
     * Op.toString() > Op
     * Op.getClass().toString() > class  gameServer.features.standalone.extp.itemIo.gm.ItemIoGmSupport$ItemIoGmSupportEnum
     * Op.getClass().getName().toString() >  gameServer.features.standalone.extp.itemIo.gm.ItemIoGmSupport$ItemIoGmSupportEnum
     * Op.getClass().getSimpleName().toString() > ItemIoGmSupportEnum
     * Op.getClass().getTypeName().toString() >  gameServer.features.standalone.extp.itemIo.gm.ItemIoGmSupport$ItemIoGmSupportEnum
     * Op.getClass().getCanonicalName().toString() >  gameServer.features.standalone.extp.itemIo.gm.ItemIoGmSupport.ItemIoGmSupportEnum
     * Op.getClass().getPackage().toString() > package  gameServer.features.standalone.extp.itemIo.gm
     *
     *
     *  protos.ItemBagProtos$Sm_ItemBag$Action.RESP_EXTEND
     * Op.toString() > RSEP_EXTEND
     * Op.getClass().toString() > class  protos.ItemBagProtos$Sm_ItemBag$Action
     * Op.getClass().getName().toString() >  protos.ItemBagProtos$Sm_ItemBag$Action
     * Op.getClass().getSimpleName().toString() > Action
     * Op.getClass().getTypeName().toString() >  protos.ItemBagProtos$Sm_ItemBag$Action
     * Op.getClass().getCanonicalName().toString() >  protos.ItemBagProtos.Sm_ItemBag.Action
     * Op.getClass().getPackage().toString() > package  protos
     *
     *  gameServer.features.standalone.extp.itemIo.gm.ItemIoGmSupportEnum.Op
     * Op.toString() > Op
     * Op.getClass().toString() > class  gameServer.features.standalone.extp.itemIo.gm.ItemIoGmSupportEnum
     * Op.getClass().getName().toString() >  gameServer.features.standalone.extp.itemIo.gm.ItemIoGmSupportEnum
     * Op.getClass().getSimpleName().toString() > ItemIoGmSupportEnum
     * Op.getClass().getTypeName().toString() >  gameServer.features.standalone.extp.itemIo.gm.ItemIoGmSupportEnum
     * Op.getClass().getCanonicalName().toString() >  gameServer.features.standalone.extp.itemIo.gm.ItemIoGmSupportEnum
     * Op.getClass().getPackage().toString() > package  gameServer.features.standalone.extp.itemIo.gm
     *
     */

    /**
     * Protobuf Message中的Action toString
     *
     * @param action
     * @return
     */
    public static String protoActionToString(Enum<?> action) {
        Objects.requireNonNull(action);
        return enumPartToString(enumWholeToString(action), 2);
    }

    /**
     * 枚举类的名字和当前枚举值组成的字符串
     */
    public static String enumClassNameAndValueToString(Enum<?> action) {
        Objects.requireNonNull(action);
        return enumPartToString(enumWholeToString(action), 1);
    }

    private static String enumWholeToString(Enum<?> action) {
        return action.getClass().getCanonicalName().toString() + "." + action.toString();
    }

    private static String enumPartToString(String whole, int... idx) {
        StringBuffer sb = new StringBuffer();
        String[] parts = whole.split("\\.");
        for (int i : idx) {
            sb.append(parts[parts.length - 1 - i]).append(".");
        }
        sb.append(parts[parts.length - 1]);
        return sb.toString();
    }

}
