import java.security.MessageDigest;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.nio.charset.StandardCharsets;

public class Hashes {
    public int npass = 0;
    private final char[] CHARSET = "abcdefABCDEF1234567890!".toCharArray();
    private final int MAX_LEN = 6;

    // Util: convierte byte[] a hex (compatible Java 8)
    private String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    // SHA-512 de pw + salt -> hex
    public String getSHA512AmbSalt(String pw, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] bytes = md.digest((pw + salt).getBytes(StandardCharsets.UTF_8));
        return toHex(bytes);
    }

    // PBKDF2 (HmacSHA1) del pw con salt -> hex (compatible Java 8)
    public String getPBKDF2AmbSalt(String pw, String salt) throws Exception {
        String algorithm = "PBKDF2WithHmacSHA1";
        int iterations = 10000; // razonable por defecto
        int keyLength = 160; // bits (SHA-1 -> 160 bits)

        byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);
        KeySpec spec = new PBEKeySpec(pw.toCharArray(), saltBytes, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
        byte[] bytes = factory.generateSecret(spec).getEncoded();
        return toHex(bytes);
    }

    public String getInterval(long t1, long t2) {
        long diff = t2 - t1;
        long millis = diff % 1000;
        long totalSeconds = diff / 1000;
        long seconds = totalSeconds % 60;
        long totalMinutes = totalSeconds / 60;
        long minutes = totalMinutes % 60;
        long totalHours = totalMinutes / 60;
        long hours = totalHours % 24;
        long days = totalHours / 24;
        return String.format("%d dias / %d horas / %d minutos / %d segundos / %d millis",
                days, hours, minutes, seconds, millis);
    }

    public String forcaBruta(String alg, String hash, String salt) throws Exception {
        AtomicBoolean found = new AtomicBoolean(false);
        AtomicReference<String> result = new AtomicReference<>(null);
        char[] buffer = new char[MAX_LEN];
        for (int len = 1; len <= MAX_LEN && !found.get(); len++) {
            generateRecursive(alg, hash, salt, buffer, 0, len, found, result);
        }
        return result.get();
    }

    private void generateRecursive(String alg, String hashObj, String salt, char[] buffer,
            int pos, int targetLen,
            AtomicBoolean found, AtomicReference<String> result) throws Exception {
        if (found.get())
            return;
        if (pos == targetLen) {
            String candidate = new String(buffer, 0, targetLen);
            npass++;
            String candidateHash;
            if ("SHA-512".equalsIgnoreCase(alg)) {
                candidateHash = getSHA512AmbSalt(candidate, salt);
            } else {
                candidateHash = getPBKDF2AmbSalt(candidate, salt);
            }
            if (candidateHash.equalsIgnoreCase(hashObj)) {
                found.set(true);
                result.set(candidate);
            }
            return;
        }
        for (char c : CHARSET) {
            if (found.get())
                return;
            buffer[pos] = c;
            generateRecursive(alg, hashObj, salt, buffer, pos + 1, targetLen, found, result);
        }
    }

    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruasn1kdfjz";
        String pw = "aaaBf!";

        Hashes h = new Hashes();
        String[] aHashes = {
                h.getSHA512AmbSalt(pw, salt),
                h.getPBKDF2AmbSalt(pw, salt)
        };
        String pwTrobat;

        String[] algorismes = { "SHA-512", "PBKDF2" };
        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("===============================\n");
            System.out.printf("Algoritmo: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
            System.out.printf("-------------------------------\n");
            System.out.printf("-- Inicio de fuerza bruta ---\n");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass: %s\n", pwTrobat);
            System.out.printf("Provadas: %d\n", h.npass);
            System.out.printf("Tiempo: %s\n", h.getInterval(t1, t2));
            System.out.printf("-------------------------------\n\n");

            h.npass = 0; // reiniciar para siguiente algoritmo
        }
    }
}
