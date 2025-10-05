
//es te que fer servir Collections.shuffle() 
import java.util.ArrayList;
import java.util.Collections;

public class Monoalfabetic {

    char[] alfabetOriginal = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇÑÁÀÉÈÍÌÓÒÚÙÜ".toCharArray();

    public static void main(String[] args) {
        Monoalfabetic cifrador = new Monoalfabetic();

        char[] permutat = cifrador.permutaAlfabet(new String(cifrador.alfabetOriginal));

        String original = "Soc wael, visca l'ITIC.";

        String xifrat = cifrador.xifraMonoAlfa(original, permutat);

        String desxifrat = cifrador.desxifraMonoAlfa(xifrat, permutat);

        System.out.println("Original:   " + original);
        System.out.println("Permutació: " + new String(permutat));
        System.out.println("Xifrat:     " + xifrat);
        System.out.println("Desxifrat:  " + desxifrat);
    }

    public char[] permutaAlfabet(String cadena) {
        ArrayList<Character> Alfabet = new ArrayList<>(); // creo l'arraylist d'alfabet

        for (int j = 0; j < alfabetOriginal.length; j++) { // recoro l'alfabet de chars original de 39 majuscules
            Alfabet.add(alfabetOriginal[j]); // emplenem l'arraylist amb tot l'alfabet
        }
        Collections.shuffle(Alfabet); // barregem

        char[] permutat = new char[Alfabet.size()]; // creo una nova lista de chars, ara desordenada

        for (int j = 0; j < Alfabet.size(); j++) { // recorrem l'arraylist ja plé i barrejat i emplenem el nou array
                                                   // char, ara desordenat
            permutat[j] = Alfabet.get(j);
        }

        return permutat;
    }

    public String xifraMonoAlfa(String cadena, char[] permutat) {
        String resultat = "";

        for (int i = 0; i < cadena.length(); i++) {
            char lletra = cadena.charAt(i);
            boolean esMajuscula = Character.isUpperCase(lletra); // si es majuscula, sera true
            char ConvertMaj = Character.toUpperCase(lletra); // per convertir a majuscula

            char substitut = lletra; // per si es un punt, coma, numero, per defecte sera el caracter iniccial
            boolean trobat = false;

            for (int j = 0; j < alfabetOriginal.length; j++) {
                if (alfabetOriginal[j] == ConvertMaj) {
                    substitut = permutat[j];
                    trobat = true;
                    break;
                }
            }

            // si era minúscula, convertim la seva corresponencia cifrada a minuscula
            if (trobat && !esMajuscula) {
                substitut = Character.toLowerCase(substitut);
            }

            resultat += substitut; // afegeixo lletra xifrada al result
        }
        return resultat;
    }

    public String desxifraMonoAlfa(String cadena, char[] permutat) {
        String resultat = "";

        for (int i = 0; i < cadena.length(); i++) {
            char lletra = cadena.charAt(i);
            boolean esMajuscula = Character.isUpperCase(lletra);
            char ConvertMaj = Character.toUpperCase(lletra);

            char substitut = lletra;
            boolean trobat = false;

            for (int j = 0; j < permutat.length; j++) {
                if (permutat[j] == ConvertMaj) {
                    substitut = alfabetOriginal[j];
                    trobat = true;
                    break;
                }
            }

            // si era minúscula, convertim la substituta
            if (trobat && !esMajuscula) {
                substitut = Character.toLowerCase(substitut);
            }

            resultat += substitut;
        }

        return resultat;
    }
}
