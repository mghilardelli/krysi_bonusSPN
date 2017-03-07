package ch.fhnw;

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
    return new String("");
  }

  private String bitPermutation(String x) {
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
