package iticbcn.xifratge;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.util.Base64;

public class GestorClaus {

    public static void guardaClau(Key clau, String nomFitxer) throws IOException {
        String tipus = clau.getAlgorithm().equals("RSA") ? (clau.getFormat().equals("X.509") ? "PUBLIC" : "PRIVATE")
                : "UNKNOWN";

        String base64 = Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(clau.getEncoded());
        String pem = "-----BEGIN " + tipus + " KEY-----\n" + base64 + "\n-----END " + tipus + " KEY-----\n";

        try (FileOutputStream fos = new FileOutputStream(nomFitxer)) {
            fos.write(pem.getBytes());
        }
    }

    public static void guardaParellClaus(KeyPair parell, String rutaPublica, String rutaPrivada) throws IOException {
        guardaClau(parell.getPublic(), rutaPublica);
        guardaClau(parell.getPrivate(), rutaPrivada);
    }
}
