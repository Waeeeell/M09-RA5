package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;

public class XifradorMonoalfabetic implements Xifrador {

    private char[] alfabetOriginal = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇÑÁÀÉÈÍÌÓÒÚÙÜ".toCharArray();
    private char[] permutat;

    public XifradorMonoalfabetic() {
        this.permutat = permutaAlfabet();
    }

    private char[] permutaAlfabet() {
        ArrayList<Character> llista = new ArrayList<>();
        for (char c : alfabetOriginal) {
            llista.add(c);
        }
        Collections.shuffle(llista);

        char[] resultat = new char[llista.size()];
        for (int i = 0; i < llista.size(); i++) {
            resultat[i] = llista.get(i);
        }
        return resultat;
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }

        String resultat = xifraMonoAlfa(msg);
        return new TextXifrat(resultat.getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }

        return desxifraMonoAlfa(new String(xifrat.getBytes()));
    }

    private String xifraMonoAlfa(String cadena) {
        String resultat = "";

        for (int i = 0; i < cadena.length(); i++) {
            char lletra = cadena.charAt(i);
            boolean maj = Character.isUpperCase(lletra);
            char may = Character.toUpperCase(lletra);
            char substitut = lletra;

            for (int j = 0; j < alfabetOriginal.length; j++) {
                if (alfabetOriginal[j] == may) {
                    substitut = permutat[j];
                    if (!maj)
                        substitut = Character.toLowerCase(substitut);
                    break;
                }
            }

            resultat += substitut;
        }

        return resultat;
    }

    private String desxifraMonoAlfa(String cadena) {
        String resultat = "";

        for (int i = 0; i < cadena.length(); i++) {
            char lletra = cadena.charAt(i);
            boolean maj = Character.isUpperCase(lletra);
            char may = Character.toUpperCase(lletra);
            char substitut = lletra;

            for (int j = 0; j < permutat.length; j++) {
                if (permutat[j] == may) {
                    substitut = alfabetOriginal[j];
                    if (!maj)
                        substitut = Character.toLowerCase(substitut);
                    break;
                }
            }

            resultat += substitut;
        }

        return resultat;
    }
}
