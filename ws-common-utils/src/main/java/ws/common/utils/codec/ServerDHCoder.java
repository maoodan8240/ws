package ws.common.utils.codec;

import java.nio.charset.Charset;
import java.util.Base64;

import ws.common.utils.exception.DHCoderException;

/**
 * <pre>
 * # G:5421644057436475141609648488325705128047428394380474376834667300766108262613900542681289080713724597310673074119355136085795982097390670890367185141189796
 * # P:13232376895198612407547930718267435757728527029623408872245156039757713029036368719146452186041204237350521785240337048752071462798273003935646236777459223
 * # L:384
 * # 公钥：/nMIHfMIGXBgkqhkiG9w0BAwEwgYkCQQD8poLOjhLKuibvzPcRDlJtsHiwXt7LzR60ogjzrhYXrgHzW5Gkfm32NBPF4S7QiZvNEyrNUNmRUb3EPuc3WS4XAkBnhHGyepz0TukaScUUfbGpqvJE8FpDTWSGkx0tFCcbnjUDC3H9c9oXkGmzLik1Yw4cIGI1TQ2iCmxBblC+eUykAgIBgANDAAJACKR8aFAMiHdgyxKSaKspiwb72cpMeX4+j/JH80OdSGMRVMFduLtbVDhdWTyuxtZtYQFaJG1vQrBDcaJFbue6Bw==
 * # 私钥：/nMIHSAgEAMIGXBgkqhkiG9w0BAwEwgYkCQQD8poLOjhLKuibvzPcRDlJtsHiwXt7LzR60ogjzrhYXrgHzW5Gkfm32NBPF4S7QiZvNEyrNUNmRUb3EPuc3WS4XAkBnhHGyepz0TukaScUUfbGpqvJE8FpDTWSGkx0tFCcbnjUDC3H9c9oXkGmzLik1Yw4cIGI1TQ2iCmxBblC+eUykAgIBgAQzAjEA5S/BXLgg7ZUbRf7nhTpVAm3N6wpkYKh0V+nBX9ym4Zm9C1GRAyEPtt4s58/oH/+2
 * </pre>
 */
public class ServerDHCoder {
    public static final String SERVER_PUBLIC_KEY = "/nMIHfMIGXBgkqhkiG9w0BAwEwgYkCQQD8poLOjhLKuibvzPcRDlJtsHiwXt7LzR60ogjzrhYXrgHzW5Gkfm32NBPF4S7QiZvNEyrNUNmRUb3EPuc3WS4XAkBnhHGyepz0TukaScUUfbGpqvJE8FpDTWSGkx0tFCcbnjUDC3H9c9oXkGmzLik1Yw4cIGI1TQ2iCmxBblC+eUykAgIBgANDAAJACKR8aFAMiHdgyxKSaKspiwb72cpMeX4+j/JH80OdSGMRVMFduLtbVDhdWTyuxtZtYQFaJG1vQrBDcaJFbue6Bw==";
    private static final String SERVER_PRIVATE_KEY = "/nMIHSAgEAMIGXBgkqhkiG9w0BAwEwgYkCQQD8poLOjhLKuibvzPcRDlJtsHiwXt7LzR60ogjzrhYXrgHzW5Gkfm32NBPF4S7QiZvNEyrNUNmRUb3EPuc3WS4XAkBnhHGyepz0TukaScUUfbGpqvJE8FpDTWSGkx0tFCcbnjUDC3H9c9oXkGmzLik1Yw4cIGI1TQ2iCmxBblC+eUykAgIBgAQzAjEA5S/BXLgg7ZUbRf7nhTpVAm3N6wpkYKh0V+nBX9ym4Zm9C1GRAyEPtt4s58/oH/+2";
    private static final byte[] SERVER_PRIVATE_KEY_BYTES = Base64.getDecoder().decode(SERVER_PRIVATE_KEY);

    public static String decrypt(String securityCode, String clientPublicKey) {
        try {
            byte[] securityCodeBytes = securityCode.getBytes();
            byte[] clientPublicKeyBytes = Base64.getDecoder().decode(clientPublicKey);
            byte[] secretKey = DHCoder.getSecretKey(clientPublicKeyBytes, SERVER_PRIVATE_KEY_BYTES);
            byte[] securityStrBytes = DHCoder.decrypt(securityCodeBytes, secretKey);
            return new String(securityStrBytes, Charset.forName("UTF-8"));
        } catch (Exception e) {
            String msg = String.format("DH decrypt 异常！ securityCode=%s clientPublicKey=%s", securityCode, clientPublicKey);
            throw new DHCoderException(msg, e);
        }
    }
}
