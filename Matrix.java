public class Matrix {

    static double trace(double[][] mat) {
        double Trazo = 0;

        /*
        Amb aquest for el que feim es treure la diagonal de la traça de la matriu
         */

        for(int i = 0; i < mat.length; i++){
            Trazo += mat[i][i]; //Treiem el valor final que ens indica la traça de la matriu que retornarem
        }

        return Trazo;
    }

    static double[][] add(double[][] mat1, double[][] mat2) {
        double [][] suma = new double[mat1.length][mat1[0].length];

        /*
         * Amb aquest dos bucles for el que feim es anar llegint la array per trobar tots els seus valors i sumar-los
         */

        for(int i = 0; i < mat1.length; i++){
            for(int j = 0; j < mat1[0].length; j++){

                suma[i][j] = mat1[i][j] + mat2[i][j];//Sumam les dues matrius
            }
        }

        return suma;
    }

    static double[][] mult(double[][] mat1, double[][] mat2) {
        double [][] multiplicacioMatrius = new double[mat1.length][mat2.length];

        if(mat1[0].length == mat2.length){//Comprobam que tenguin la mateixa longitud la horitzontal de mat1[0] amb la vertical de mat2
            /*
            *   Cream tres bucles que ens permeten recorrer l'array en horitzontal, vertical y diagonalment
            *   per poder fer la multiplicació de matrius
            * */
            for(int i = 0; i < mat2.length; i++){
                for(int j = 0; j < mat2[0].length; j++){
                    for(int k = 0; k < mat1.length; k++){
                        /*
                        *   Dintre de l'array multiplicacioMatrius[][] asignarem I i J.
                        *   Anirem sumant el resultat de mat1[i][k] * mat2[k][j] per trobar el resultat final
                        * */
                        multiplicacioMatrius[i][j] += mat1[i][k] * mat2[k][j];

                    }
                }
            }

        }else {
            for (int i = 0; i < mat2.length; i++) {
                for (int j = 0; j < mat2[0].length; j++) {
                    multiplicacioMatrius[i][j] += mat2[i][j] * 5;
                }
            }
        }
        //Retornam el resultat de les multiplicacions
        return multiplicacioMatrius;
    }

    static double[][] power(double[][] mat, double p) {
        //Cream una variable auxiliar per emmagatzemar el resultat amb el valor mat per defecte
        double[][] powerUp = mat;

        /*
        *   En cas de que p sigui igual a 0 crearem 2 bucles per trobar la solució, que ha de ser o bé 0, o bé 1.
        * */
        if (p == 0) {
            for(int i = 0; i<mat.length;i++){
                for(int j = 0; j < mat[0].length; j++){

                    //Multiplicarem 0 * mat[i][j] i sumarem 1 al resultat
                    powerUp[i][j] += 0 * mat[i][j];

                    /*
                    *   En cas de que el resultat de I sigui igual que J asignarem el valor de 1 a la matriu.
                    *   En cas de que no siguin igual asignarem el valor de 0.
                    * */

                    if (i == j) {
                        powerUp[i][j] = 1;
                    }if(i != j) {
                        powerUp[i][j] = 0;
                    }
                }
            }
        }

        //En cas de que p no sigui 0 el que farem serà multiplicar powerUp[][] per p
        if (p != 0) {
            for (int k = 0; k < p - 1; k++) {
                powerUp = mult(powerUp, mat);
            }
        }
        //Retornam el resultat de la operació
        return powerUp;
    }

    static double[][] div(double[][] mat1, double[][] mat2){

        /*
        *   Comprovam que invertint les dues matrius cap sigui nula.
        *   En cas de que sigui nula el que farem serà retornar un missatge d'error.
        * */
        if(invert(mat2) == null || invert(mat1) == null){
            //Missatge d'error
            System.out.println("Les arrays no poden ser nules");
            return null;

        }else{
            /*
            *   Retornam el resultat de la multiplicació de mat1 per la matriu invertida de mat2
            * */
            return mult(mat1, invert(mat2));
        }


    }

    static double[][] submatrix(double[][] mat, int x1, int y1, int x2, int y2) {

        /*
        *   Primer hem de trobar els difernecials de X i Y, per trobar-ho el que hem de fer es restar X2 - X1 i sumar-li 1 i repetir amb Y.
        */

        int diferencialX = x2 - x1 + 1;
        int diferencialY = y2 - y1 + 1;

        //Inicialitzam una variable en la que emmagatzemarem, el resultat de la operació. La inicialitzarem amb els valors dels diferencials.
        double[][] submatriu = new double[diferencialY][diferencialX];

        for (int i = y1, y = 0; i <= y2; i++, y++) {
            for (int j = x1, x = 0; j <= x2; j++, x++) {

                //Asignarem els valors de y i x a la variable de la submatriu.
                submatriu[y][x] = mat[i][j];
            }
        }

        //Retornam la submatriu
        return submatriu;
    }

    static double[][] mult(double[][] mat, double n) {

        //Cream una array per a emmagatzemar el resultat de la multiplicació
        double[][] multiplicacioEnters = new double[mat.length][mat[0].length];

        /*
            Generam un bucle per fer la multiplicació en el que asignam la horitzontal a I, i la vertical a J.
            Emplearem la array que hem creat anteriorment per a fer la operació. mat[i][j] * n que ens donarà el resultat.
        */

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                multiplicacioEnters[i][j] = mat[i][j] * n;
            }
        }

        //Tornam el resultat
        return multiplicacioEnters;
    }

    static double[][] invert(double[][] mat) {

        //Feim servir la funcio del determinant per trobar el determinant de la matriu
        double deter = determinant(mat);

        //En cas de que sigui 0 retornarem un missatge d'error.
        if(deter == 0){
            System.out.println("Aquesta array no es pot invertir");
            return null;
        }

        //Feim una nova array per emmagatzemar els cofactors de la matriu
        double[][] inverted = cofactors(mat);

        //Invertim literalment la matriu
        inverted = transpose(inverted);

        for (int i = 0; i < inverted.length; i++) {
            for (int j = 0; j < inverted[0].length; j++) {
                //Per a trobar el valor final de la invertida el que farem serà dividir-la per el seu determinant.
                inverted[j][i] /= deter;
            }
        }

        //Retornam el resultat de invertir la matriu
        return inverted;
    }

    static double[][] cofactors (double[][] mat){
        double[][] cof = new double[mat.length][mat[0].length];

        for (int i = 0; i < mat[0].length; i++) {
            for (int j = 0; j < mat.length; j++) {

                //El determinant el trobarem empleant la funció getMinor amb els valors de (mat,i,j).
                double k = determinant(getMinor(mat, j, i));

                /*
                        Per saber si hem de fer que el valor que retorna a la seva posició es
                        positiu o negatiu el que farem serà trobar el seu residu, si el residu es 0 doncs donarem valor '+'
                        En cas de que el residu torni un resultat diferent a 0 li donarem valor '-'
                */

                if((i + j) % 2 == 0){
                    cof[j][i] = k;
                } else {
                    cof[j][i] = -k;
                }

            }
        }

        return cof;
        //Retronam la matriu cofactora de la matriu original
    }

    static double[][] getMinor(double[][] mat, int x, int y) {
        double[][] minor = new double[mat.length - 1][mat.length - 1];

        /*
            Aquesta funció ens permet trobar el valor
            mínim de cada pasada per col·lumna que realitzam.
        */

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; i != x && j < mat[i].length; j++) {
                /*
                    Comprovam que el valor que pasam a la variable "j"
                    no sigui igual que el contingut de "y" per poder operar.
                */
                if (j != y) {
                    minor[i < x ? i : i - 1][j < y ? j : j - 1] = mat[i][j];
                }
            }
        }

        //Retornam el getMinor
        return minor;
    }

    static double determinant(double[][] mat) {
        //Cream un objecte per al determinant, es a dir, una variable auxiliar on emmagatzemarem el resultat
        double getDeterminant = 0;

        //Si la longitud de la matriu es de 1 asignarem el primer valor de la mateixa, en posició horitzontal i vertical.
        if(mat.length == 1){
            return mat[0][0];
        }

        //Si la matriu es de 2x2 cridarem la funció que ens calcula la matriu de 2x2
        if(mat.length == 2){
            return determinant2x2(mat);
        }

        /*
        *   Feim un bucle que ens permet trobar el determinant de la matriu i calcularem el seu residu:
        *       - En cas de que sigui 0 el seu valor serà positiu
        *       - En cas de que sigui diferent a 0 el seu valor serà negatiu
        *
        *   Una vegada feta aquesta comprovació retornarem el determinant de la matriu
        * */

        for (int i = 0; i < mat.length; i++) {
            if (i % 2 == 0) {
                getDeterminant += mat[0][i] * determinant(getMinor(mat, 0, i));
            } else {
                getDeterminant -= mat[0][i] * determinant(getMinor(mat, 0, i));
            }
        }

        return getDeterminant;
    }

    static double determinant2x2(double[][] mat){
        double detbi = 0;

        //Feim la operació per retornar el determinant d'una matriu 2*2
        detbi = (mat[0][0] * mat[1][1]) - (mat[0][1] * mat[1][0]);


        //Retornam el valor del determinant de 2x2
        return detbi;
    }

    static double[][] transpose(double[][] mat) {
        //Cream una array auxiliar per a emmagatzemar el valor de la operació.
        double[][] Transposicio = new double[mat.length][mat[0].length];

        /*
        *   Cream un bucle per asignar valors a les files i a les col·lumnes de la matriu:
        *       - "i" ens servirà per a les files
        *       - "j" ens servirà per a les col·lumnes
        * */

        for(int i = 0; i < mat[0].length; i++){
            for (int j = 0; j < mat.length; j++) {
                //Donam la volta a la matriu.
                Transposicio[j][i] = mat[i][j];
            }
        }

        //Retornam la matriu girada.
        return Transposicio;
    }

    static boolean isOrtho(double[][] mat) {
        /*
            Amb aquesta funció el que feim es comprovar si el determinant
            de la inversa de la matriu és igual al determinant de la transposició de la mateixa matriu.
        */

        if(invert(mat) == null){

            System.out.println("No es pot invertir la array, per tant no és ortogonal");
            return false;
            /*
                En cas de que la inversa de la matriu sigui nula, avisarem
                a l'usuari de que la matriu es nula y no es pot continuar.
             */

        } else {

            return determinant(invert(mat)) == determinant(transpose(mat));
            //Retornam el valor en forma de boolean true o false

        }
    }

    static double[] cramer(double[][] mat) {
        double[] resultat = new double[mat[0].length - 1];
        double[][] cofactors = new double[mat.length][mat[0].length -1];
        double[][] incognita = new double[mat.length][1];

        /*
        *    El primer pas es trobar el determinant de la matriu, per això crearem la variable que emmagatzemarà la determinant
        *    Feim un bucle per trobar les incògnites
        */

        for (int i = 0; i < cofactors.length; i++) {
            for (int j = 0; j < cofactors[0].length; j++) {
                //Asignam el valor a l'array cofactors[][]
                cofactors[i][j] = mat[i][j];
            }
        }

        //Afegim la darrera col·lumna de la matriu original a la incògnita
        for (int i = 0; i < incognita.length; i++) {
            incognita[i][0] = mat[i][mat[0].length - 1];
        }

        //Retornam el determinant de la matriu dels cofactors
        double determinantMatriu = determinant(cofactors);

        //Comprovam que el valor dels determinants sigui diferent a 0
        if(determinantMatriu == 0) return null;
            for (int i = 0; i < cofactors.length; i++) {

                //Declaram la variable que emplearem per trobar el resultat del mètode de cramer.
                double[][] segonaMatriu = new double[cofactors.length][cofactors[0].length];

                //
                for (int j = 0; j < segonaMatriu.length; j++) {
                    for (int k = 0; k < segonaMatriu[0].length; k++) {
                        segonaMatriu[j][k] = cofactors[j][k];
                    }
                }

                //Substituim els valors de la matriu per els de la incògnita
                for (int j = 0; j < cofactors[0].length; j++) {
                    segonaMatriu[j][i] = incognita[j][0];
                }

                /*
                *   Ara per trobar el resultat el que farem serà dividir el determinant
                *   de la segona matriu entre el determinant que hem trobat abans.
                * */
                resultat[i] = determinant(segonaMatriu) / determinantMatriu;
            }
            //Retornam el resultat
            return resultat;

    }

    // Funció que mostra una matriu per pantalla
    static void printMat(double[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                System.out.printf("%06.2f ", mat[i][j]);
            }
            System.out.println();
        }
    }
}