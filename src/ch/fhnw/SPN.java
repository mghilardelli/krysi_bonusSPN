package ch.fhnw;

import java.util.HashMap;

/**
 * Created by pwg on 07.03.17.
 * <p>
 * Definitionen
 * r = 4           -> Anzahl Runden
 * n = 4           ->
 * m = 4           ->
 * m*n = 16        -> länge der eingabe "Block"
 * SBox            -> done
 * BitPermutation  -> done
 * s = 32          -> Schlüssel gesamtgrösse
 * K(k,i)          -> Rundenschlüssel 16 ab position 4i
 * yRAND           -> Die Randomnummer (RAND oder y-1)
 */

public class SPN {
    String key;
    String chiffre;
    String yRAND;
    String[] y;  // beginnt erst bei chiffre zeichen 15 also ohne y-1(yRAND)
    public String[] z;  // nach Folie 1.52 im Skript, output aus generateZ()
    String[] x;  // wollen wir am schluss
    public StringBuilder message;  // wollen wir am schluss
    public StringBuilder finalMessage;
    int r = 4;


    public SPN(String key, String chiffre) {
        //initialize all Variables
        this.key = key;
        this.chiffre = chiffre;
        yRAND = chiffre.substring(0, 16);
        y = chiffre.substring(16, chiffre.length()).split("(?<=\\G.{16})");
        z = new String[y.length];
        x = new String[y.length];
        message = new StringBuilder();
        finalMessage = new StringBuilder();

        generateZ();

        generateFinalMessage();

    }

    private void generateFinalMessage() {
        //XOR Y with output from SPN
        for (int i = 0; i < y.length; i++) {
            x[i] = xor(z[i], y[i]);
        }

        for (String s : x) {
            message.append(s);
        }

        //remove suffix "100000000"
        while(message.charAt(message.length()-1)=='0'){
            message.deleteCharAt(message.length()-1);
        }
        message.deleteCharAt(message.length()-1);

        //Convert to Text
        for (String x : message.toString().split("(?<=\\G.{8})")) {
            finalMessage.append((char) Integer.parseInt(x, 2));
        }
    }

    private void generateZ() {
        for (int i = 0; i < y.length; i++) {

            // initialer Weissschritt x = x (+) K(k,0)
            z[i] = xor(yRAND, key.substring(0, 16));

            //Reguläre Runden
            for (int j = 1; j < r; j++) {
                z[i] = sBox(z[i]);
                z[i] = bitPermutation(z[i]);
                z[i] = xor(z[i], key.substring(j * 4, (j * 4) + 16));
            }
            //  Abschliessende verkürzte Runde
            z[i] = sBox(z[i]);
            z[i] = xor(z[i], key.substring(r * 4, (r * 4) + 16));

            //erhöhe y-1 um 1
            yRAND = addOne(yRAND);
        }
    }

    public String addOne(String x) {
        //erhöht den eingegebenen String um 1
        StringBuilder out = new StringBuilder(x);
        int i = x.length() - 1;
        while (i >= 0 && x.charAt(i) == '1') {
            out.setCharAt(i, '0');
            i--;
        }
        if (i >= 0)
            out.setCharAt(i, '1');
        return out.toString();
    }


    private String sBox(String x) {
        if (!(x.length() % 4 == 0)) {
            throw new IllegalArgumentException("Input must be dividable by 4");
        }

        StringBuilder out = new StringBuilder();
        HashMap<Integer, Integer> thebox = new HashMap<Integer, Integer>() {{
            put(0, 14);
            put(1, 4);
            put(2, 13);
            put(3, 1);
            put(4, 2);
            put(5, 15);
            put(6, 11);
            put(7, 8);
            put(8, 3);
            put(9, 10);
            put(10, 6);
            put(11, 12);
            put(12, 5);
            put(13, 9);
            put(14, 0);
            put(15, 7);
        }};

        for (int i = 0; i < x.length(); i += 4) {
            int a = Integer.parseInt(x.substring(i, i + 4), 2);
            out.append(String.format("%4s", Integer.toBinaryString(thebox.get(a))).replace(' ', '0'));
        }
        return String.valueOf(out);
    }

    //Permutiert die einzlnen Bits anhand der Aufgabe
    private String bitPermutation(String x) {
        if (x.length() != 16) {
            throw new IllegalArgumentException("Length does not match");
        }

        StringBuilder out = new StringBuilder("0000000000000000");
        int[] a = {0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15};
        for (int i = 0; i < 16; i++) {
            if (x.charAt(i) == '1') {
                out.setCharAt(a[i], (char) '1');
            }
        }
        return String.valueOf(out);
    }


    //XOR sollte funktionierten: Strings müssen gleich lang sein und aus 0/1 bestehen!
    private String xor(String x, String y) {
        if (x.length() != y.length()) {
            throw new IllegalArgumentException("Length does not match");
        }

        String temp = "";
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) == y.charAt(i) && y.charAt(i) == '0' || x.charAt(i) == y.charAt(i) && y.charAt(i) == '1') {
                temp += "0";
            } else {
                temp += "1";
            }


        }
        return temp;

    }

}
