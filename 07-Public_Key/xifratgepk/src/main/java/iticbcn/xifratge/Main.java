package iticbcn.xifratge;

import java.io.File;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.PrivateKey;
import javax.xml.bind.DatatypeConverter;

public class Main {
    public static void main(String[] args) throws Exception {
        ClauPublica cp = new ClauPublica();
        String msg = "Missatge de prova per xifrar áéíóú àèìòù äëïöü";

        PublicKey clauPub;
        PrivateKey clauPriv;

        if (new File("clauPublica.pem").exists() && new File("clauPrivada.pem").exists()) {
            System.out.println("Claus trobades! Carregant...");
            clauPub = CarregadorClaus.carregaClauPublica("clauPublica.pem");
            clauPriv = CarregadorClaus.carregaClauPrivada("clauPrivada.pem");
        } else {
            System.out.println("No hi ha claus, generant de noves...");
            KeyPair parellClaus = cp.generaParellClausRSA();
            GestorClaus.guardaParellClaus(parellClaus, "clauPublica.pem", "clauPrivada.pem");
            clauPub = parellClaus.getPublic();
            clauPriv = parellClaus.getPrivate();
        }

        byte[] msgXifrat = cp.xifraRSA(msg, clauPub);
        System.out.println("===============================");
        System.out.println("Text xifrat: ");
        System.out.println(DatatypeConverter.printHexBinary(msgXifrat));

        String msgDesxifrat = cp.desxifraRSA(msgXifrat, clauPriv);
        System.out.println("===============================");
        System.out.println("Text desxifrat: " + msgDesxifrat);

        System.out.println("===============================");
        System.out.println("Signant missatge...");
        byte[] firma = FirmaDigital.signar(msg, clauPriv);
        System.out.println("Firma (hex): " + DatatypeConverter.printHexBinary(firma));

        System.out.println("Verificant firma...");
        boolean valida = FirmaDigital.verificar(msg, firma, clauPub);
        System.out.println("Resultat verificació: " + (valida ? "✅ Vàlida" : "❌ Invàlida"));
    }
}
