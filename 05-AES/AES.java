
/*
 * 
 */
import javax.crypto.*;
import java.security.*;

public class AES {

    public static final String ALGORITME_XIFRAT = "AES";
    public static final String ALGORITME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC(PKCS5padding)";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "8611mWael";

    public static void main(String[] args) {
        String msgs[] = {
                "Lorem ipsum dicet",
                "Hola Andrés cómo está tu cuñado",
                "Àgora ïlla Ôtto "
        };

        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];
            byte[] bXifrats = null;
            String desxifrat = "";

            try {
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desXifraAES(bXifrats, CLAU);
            } catch (Exception e) {
                System.err.println("Error de xifrat: "
                        + e.getLocalizedMessage());
            }
            System.out.println("-----------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("DEC: " + desxifrat);
        }

    }

    public static byte[] xifraAES(String msg String password) throws Exception{
        //obtenir els bytes de l'String
        //genera IvParameterSpec
        //Genera hash
        //Encrypt
        //Combinar IV i part xifrada
        //return iv+msgxifrat
    }

    public String desXifraAES(byte[] bMsgXifrat, String password) throws Exception {
        // extreure l'IV
        // Extreure la part xifrada
        // fer hash de la clau
        // Desxifrar
        // return String desxifrat
    }

}