package iticbcn.xifratge;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class FirmaDigital {

    public static byte[] signar(String missatge, PrivateKey clauPrivada) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(clauPrivada);
        signature.update(missatge.getBytes("UTF-8"));
        return signature.sign();
    }

    public static boolean verificar(String missatge, byte[] firma, PublicKey clauPublica) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(clauPublica);
        signature.update(missatge.getBytes("UTF-8"));
        return signature.verify(firma);
    }
}
