package iticbcn.xifratge;

import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {

    private char[] minuscules = "abcdefghijklmnopqrstuvwxyzçñáàéèíìóòúùü".toCharArray();
    private char[] majuscules = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇÑÁÀÉÈÍÌÓÒÚÙÜ".toCharArray();
    private Random random;

    public XifradorPolialfabetic() {
        random = new Random();
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        long desplaçament;
        try {
            desplaçament = Long.parseLong(clau);
        } catch (Exception e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }

        String resultat = "";
        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            boolean maj = Character.isUpperCase(c);
            char lletra = Character.toUpperCase(c);

            int idx = indexOf(minuscules, lletra);
            if (idx != -1) {
                int shift = (int) ((desplaçament + random.nextInt(5)) % minuscules.length);
                resultat += minuscules[(idx + shift) % minuscules.length];
            } else {
                idx = indexOf(majuscules, lletra);
                if (idx != -1) {
                    int shift = (int) ((desplaçament + random.nextInt(5)) % majuscules.length);
                    resultat += majuscules[(idx + shift) % majuscules.length];
                } else {
                    resultat += c;
                }
            }
        }

        return new TextXifrat(resultat.getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        long desplaçament;
        try {
            desplaçament = Long.parseLong(clau);
        } catch (Exception e) {
            throw new ClauNoSuportada("La clau de Polialfabètic ha de ser un String convertible a long");
        }

        return new String(xifrat.getBytes());
    }

    private int indexOf(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c)
                return i;
        }
        return -1;
    }
}
