import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.util.Arrays;

public class AES {

    public static final String ALGORITME_XIFRAT = "AES";
    public static final String ALGORITME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int LONGITUD_IV = 16;
    private static byte[] iv = new byte[LONGITUD_IV];
    private static final String CLAU = "8611mWael";

    public static void main(String[] args) {
        String mensajes[] = {
                "Lorem ipsum dicet",
                "Hola Andrés cómo está tu cuñado",
                "Àgora ïlla Ôtto "
        };

        for (int i = 0; i < mensajes.length; i++) {
            String mensaje = mensajes[i];
            byte[] mensajeCifrado = null;
            String mensajeDescifrado = "";

            try {
                mensajeCifrado = cifrarAES(mensaje, CLAU);
                mensajeDescifrado = descifrarAES(mensajeCifrado, CLAU);
            } catch (Exception e) {
                System.err.println("Error de cifrado: " + e.getMessage());
            }

            System.out.println("-----------------------");
            System.out.println("Msg: " + mensaje);
            System.out.println("Enc: " + new String(mensajeCifrado));
            System.out.println("Dec: " + mensajeDescifrado);
        }
    }

    public static byte[] cifrarAES(String mensaje, String password) throws Exception {
        byte[] bytesMensaje = mensaje.getBytes("UTF-8");

        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        MessageDigest digest = MessageDigest.getInstance(ALGORITME_HASH);
        byte[] claveHash = digest.digest(password.getBytes("UTF-8"));

        SecretKeySpec claveSecreta = new SecretKeySpec(claveHash, ALGORITME_XIFRAT);

        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, claveSecreta, ivSpec);

        byte[] mensajeCifrado = cipher.doFinal(bytesMensaje);

        byte[] resultado = new byte[LONGITUD_IV + mensajeCifrado.length];
        System.arraycopy(iv, 0, resultado, 0, LONGITUD_IV);
        System.arraycopy(mensajeCifrado, 0, resultado, LONGITUD_IV, mensajeCifrado.length);

        return resultado;
    }

    public static String descifrarAES(byte[] mensajeCifrado, String password) throws Exception {
        byte[] ivExtraido = Arrays.copyOfRange(mensajeCifrado, 0, LONGITUD_IV);
        IvParameterSpec ivSpec = new IvParameterSpec(ivExtraido);

        byte[] parteCifrada = Arrays.copyOfRange(mensajeCifrado, LONGITUD_IV, mensajeCifrado.length);

        MessageDigest digest = MessageDigest.getInstance(ALGORITME_HASH);
        byte[] claveHash = digest.digest(password.getBytes("UTF-8"));

        SecretKeySpec claveSecreta = new SecretKeySpec(claveHash, ALGORITME_XIFRAT);

        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, claveSecreta, ivSpec);

        byte[] mensajeDescifrado = cipher.doFinal(parteCifrada);

        return new String(mensajeDescifrado, "UTF-8");
    }
}
