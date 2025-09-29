public class RotX {

    char[] minuscules = "abcdefghijklmnopqrstuvwxyzçñáàéèíìóòúùü".toCharArray();
    char[] majuscules = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇÑÁÀÉÈÍÌÓÒÚÙÜ".toCharArray();

    public static void main(String[] Args) {

        int desplacament = 0; // inicio desplacamiento en 0
        String[] cadena = {

                "ABC",
                "XZY",
                "Hola, Mr calçot",
                "Perdó, per tu què és?"

        };
    }

    public String xifraRot13(String cadena, int desplacament) {
        String resultat = "";
        // String aux = cadena; //un string auxiliar por si acaso la nescesito

        for (int i = 0; i < cadena.length(); i++) { // recorro cel texto que quiero cifrar
            char lletra = cadena.charAt(i);
            boolean trobat = false;

            for (int j = 0; j < minuscules.length; j++) { // recorremos minuscular
                if (lletra == minuscules[j]) { // si la letra esta en el array d minusculas
                    int rota13 = (j + desplacament) % minuscules.length; // cojo la posicion de la letra j puede ser
                                                                         // cualquier
                    // numero y le sumo 13
                    resultat += minuscules[rota13]; // esa suma anterior da un numero que sera la posicion d la
                                                    // letra que toca
                    trobat = true;
                    break;
                }
            }

            if (!trobat) {
                for (int k = 0; k < majuscules.length; k++) {
                    if (lletra == majuscules[k]) {
                        trobat = true;
                        int rota13 = (k + desplacament) % majuscules.length;
                        resultat += majuscules[rota13];
                        break;
                    }
                }
            }

            if (!trobat) { // si no sale en ningun array puede ser numero, espacio, signo... lo ponemos
                resultat += lletra;
            }
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
                    trobat = true;
                    int desRota13 = (j - desplacament + minuscules.length) % minuscules.length; // para que no de
                                                                                                // negativo,
                    // siempre sumamos la longitud del
                    // array
                    resultat += minuscules[desRota13];
                    break;
                }
            }

            if (!trobat) {
                for (int e = 0; e < majuscules.length; e++) {
                    if (lletra == majuscules[e]) {
                        trobat = true;
                        int desRota13 = (e - desplacament + majuscules.length) % majuscules.length;
                        resultat += majuscules[desRota13];
                        break;
                    }
                }
            }

            if (!trobat) { // si no esta en loa array, puede ser espacio, nuemro, algun signo. añadimos yya

                resultat += lletra;
            }
        }

        return resultat;
    }

    public String forcaBrutaRotX(String cadenaXifrada) {

    }
}