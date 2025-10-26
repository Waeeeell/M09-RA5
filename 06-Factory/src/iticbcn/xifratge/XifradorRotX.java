package iticbcn.xifratge;

public class XifradorRotX implements Xifrador {

    char[] minuscules = "abcdefghijklmnopqrstuvwxyzçñáàéèíìóòúùü".toCharArray();
    char[] majuscules = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇÑÁÀÉÈÍÌÓÒÚÙÜ".toCharArray();

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        int desplaçament;
        try {
            desplaçament = Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }

        if (desplaçament < 0 || desplaçament > 40) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }

        String resultat = xifraRot13(msg, desplaçament);
        return new TextXifrat(resultat.getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        int desplaçament;
        try {
            desplaçament = Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }

        if (desplaçament < 0 || desplaçament > 40) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }

        return desxifraRot13(new String(xifrat.getBytes()), desplaçament);
    }

    public String xifraRot13(String cadena, int desplacament) {
        String resultat = "";

        for (int i = 0; i < cadena.length(); i++) {
            char lletra = cadena.charAt(i);
            boolean trobat = false;

            for (int j = 0; j < minuscules.length; j++) {
                if (lletra == minuscules[j]) {
                    int rota13 = (j + desplacament) % minuscules.length;
                    resultat += minuscules[rota13];
                    trobat = true;
                    break;
                }
            }

            if (!trobat) {
                for (int k = 0; k < majuscules.length; k++) {
                    if (lletra == majuscules[k]) {
                        int rota13 = (k + desplacament) % majuscules.length;
                        resultat += majuscules[rota13];
                        trobat = true;
                        break;
                    }
                }
            }

            if (!trobat)
                resultat += lletra;
        }

        return resultat;
    }

    public String desxifraRot13(String cadena, int desplacament) {
        String resultat = "";

        for (int i = 0; i < cadena.length(); i++) {
            char lletra = cadena.charAt(i);
            boolean trobat = false;

            for (int j = 0; j < minuscules.length; j++) {
                if (lletra == minuscules[j]) {
                    int desRota13 = (j - desplacament + minuscules.length) % minuscules.length;
                    resultat += minuscules[desRota13];
                    trobat = true;
                    break;
                }
            }

            if (!trobat) {
                for (int e = 0; e < majuscules.length; e++) {
                    if (lletra == majuscules[e]) {
                        int desRota13 = (e - desplacament + majuscules.length) % majuscules.length;
                        resultat += majuscules[desRota13];
                        trobat = true;
                        break;
                    }
                }
            }

            if (!trobat)
                resultat += lletra;
        }

        return resultat;
    }
}
