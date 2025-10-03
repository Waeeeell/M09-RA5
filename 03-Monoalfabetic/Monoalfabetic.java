
//es te que fer servir Collections.shuffle() 
import java.util.ArrayList;
import java.util.Collections;

public class Monoalfabetic {

    char[] alfabetOriginal = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇÑÁÀÉÈÍÌÓÒÚÙÜ".toCharArray();

    public static void main(String[] args) {

    }

    public char[] permutaAlfabet(String cadena) {

        ArrayList<Character> Alfabet = new ArrayList<>(); // creo la list de chars

        for (int j = 0; j < alfabetOriginal.length; j++) {
            Alfabet.add(alfabetOriginal[j]); // amb for empleno el list amb l'array de char

        }
        Collections.shuffle(Alfabet); // aqui es on permuto l'alfabet

        char[] permutat = new char[Alfabet.size()]; // creo un nou array de char

        for (int j = 0; j < Alfabet.size(); j++) {
            permutat[j] = Alfabet.get(j); // empleno el noy array, amb el list permutat
        }

        return permutat;
    }

    public String xifraMonoAlfa(String cadena, char[] permutat) {
        String resultat = "";

        for (int i = 0; i < alfabetOriginal.length; i++){
            char lletra = alfabetOriginal[i];
               
                for (int j = 0; j < permutat.length; j++){
                    boolean esMajuscula = false;
                    if (){ //tema mayusculas y minusculas vaya lio, falta por hacer

                    }
                }
        }
        return resultat;
    }

    public String desxifraMonoAlfa(String cadena) {
        String resultat = "";
        return resultat;
    }
}