package ch.fhnw;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by pwg on 07.03.17.
 *
 * Definitionen
 *  r = 4           -> Anzahl Runden
 *  n = 4           ->
 *  m = 4           ->
 *  m*n = 16        -> länge der eingabe "Block"
 *  SBox            -> done
 *  BitPermutation  -> done
 *  s = 32          -> Schlüssel gesamtgrösse
 *  K(k,i)          -> Rundenschlüssel 16 ab position 4i
 *  yRAND           -> Die Randomnummer (RAND oder y-1)
 *
 */

public class SPN {
  String key;
  String chiffre;
  String yRAND;
  String[] y;  // beginnt erst bei chiffre zeichen 15 also ohne y-1(yRAND)
  String[] z;  // nach Folie 1.52 im Skript, output aus generateZ()
  String[] x;  // wollen wir am schluss


  public SPN(String key, String chiffre) {
    this.key = key;
    this.chiffre = chiffre;
    yRAND = chiffre.substring(0,15);
    y = chiffre.substring(16,chiffre.length()).split("(?<=\\G.{16})");
    generateZ();
  }

  private void generateZ(){
//  y.length() mal müssen sie schritte auf Folie 1.29 durchgegangen werden:
//  yRAND muss pro z um eins erhöht werden!
//    TODO: initialer Weissschritt x = x (+) K(k,0)
//    TODO: i=1 bis r-1 mal:
//            1. Sbox
//            2. bitpermutation
//            3. rundenschlüsseladdition x = x (+) K(k,i)
//    TODO: Abschliessende verkürzte Runde
//            1. Sbox
//            2. rundenschlüsseladdition x = x (+) K(k,i)
  }

  private String addOne(String x){
//      TODO: eins hinzuzählen
      return new String("");
  }


  private String sBox(String x) {
      if(!(x.length()%4==0)){
          throw new IllegalArgumentException("Input must be dividable by 4");
      }

      StringBuilder out = new StringBuilder();
      HashMap<Integer, Integer> thebox = new HashMap<Integer, Integer>(){{
        put(0,14);
        put(1,4);
        put(2,13);
        put(3,1);
        put(4,2);
        put(5,15);
        put(6,11);
        put(7,8);
        put(8,3);
        put(9,10);
        put(10,6);
        put(11,12);
        put(12,5);
        put(13,9);
        put(14,0);
        put(15,7);
      }};

      for (int i = 0; i < x.length(); i += 4) {
          int a = Integer.parseInt(x.substring(i, i+4),2);
            out.append(String.format("%4s",Integer.toBinaryString(thebox.get(a))).replace(' ', '0'));
      }
    return String.valueOf(out);
  }


  private String bitPermutation(String x) {
      if (x.length() != 16) {
          throw new IllegalArgumentException("Length does not match");
      }

      StringBuilder out = new StringBuilder("0000000000000000");
      int[] a = {0,4,8,12,1,5,9,13,2,6,10,14,3,7,11,15};
      for (int i = 0; i < 16; i++) {
          if(x.charAt(i) == '1'){
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
