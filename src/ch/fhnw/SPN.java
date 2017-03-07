package ch.fhnw;

import java.util.HashMap;

/**
 * Created by pwg on 07.03.17.
 */
public class SPN {
  String key;
  String chiffre;

  public SPN(String key, String chiffre) {
    this.key = key;
    this.chiffre = chiffre;
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
      //TODO: implement bitpermutation


    return new String("");
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
