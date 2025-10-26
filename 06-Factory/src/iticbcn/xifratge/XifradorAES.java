package iticbcn.xifratge;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.util.Arrays;

public class XifradorAES implements Xifrador {

    public static final String ALGORITME_XIFRAT = "AES";
    public static final String ALGORITME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int LONGITUD_IV = 16;
    private byte[] iv = new byte[LONGITUD_IV];

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            byte[] bytesMsg = msg.getBytes("UTF-8");
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            MessageDigest digest = MessageDigest.getInstance(ALGORITME_HASH);
            byte[] claveHash = digest.digest(clau.getBytes("UTF-8"));
            SecretKeySpec key = new SecretKeySpec(claveHash, ALGORITME_XIFRAT);

            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

            byte[] cifrat = cipher.doFinal(bytesMsg);

            byte[] resultat = new byte[LONGITUD_IV + cifrat.length];
            System.arraycopy(iv, 0, resultat, 0, LONGITUD_IV);
            System.arraycopy(cifrat, 0, resultat, LONGITUD_IV, cifrat.length);

            return new TextXifrat(resultat);
        } catch (Exception e) {
            System.err.println("Error en xifrat AES: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            byte[] dades = xifrat.getBytes();
            byte[] ivExtra = Arrays.copyOfRange(dades, 0, LONGITUD_IV);
            IvParameterSpec ivSpec = new IvParameterSpec(ivExtra);
            byte[] partCifrada = Arrays.copyOfRange(dades, LONGITUD_IV, dades.length);

            MessageDigest digest = MessageDigest.getInstance(ALGORITME_HASH);
            byte[] claveHash = digest.digest(clau.getBytes("UTF-8"));
            SecretKeySpec key = new SecretKeySpec(claveHash, ALGORITME_XIFRAT);

            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

            byte[] descifrat = cipher.doFinal(partCifrada);
            return new String(descifrat, "UTF-8");
        } catch (Exception e) {
            System.err.println("Error en desxifrat AES: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }
}
