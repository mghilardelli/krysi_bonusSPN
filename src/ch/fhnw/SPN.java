package ch.fhnw;

import javafx.beans.property.IntegerProperty;

/**
 * Created by pwg on 07.03.17.
 */
public class SPN {
  String key;
  String chiffre;
  String key0;
  String key1;
  String key2;
  String key3;


  public SPN(String key, String chiffre) {
    this.key = key;
    this.chiffre = chiffre;
    /*
    System.out.println(sBox("AE012"));
    System.out.println("60E4D");

    System.out.println(bitPermutation("Wasserfall123456"));
    System.out.println("Wasserfall123456");
    System.out.println(getKey("abcdefghijoklmnopqrsteufvwxyzabcdefghijoklmnopqrsteufvwxyz",0));
    System.out.println(getKey("abcdefghijoklmnopqrsteufvwxyzabcdefghijoklmnopqrsteufvwxyz",1));
    System.out.println(getKey("abcdefghijoklmnopqrsteufvwxyzabcdefghijoklmnopqrsteufvwxyz",2));


   */
    key0=getKey(key,0);
    key1=getKey(key,1);
    key2=getKey(key,2);
    key3=getKey(key,3);
    String testchiffre = "0001001010001111";
    String testkey = "00010001001010001000110000000000";
/*
    String decrypted = decrypt(testchiffre, testkey);
    System.out.println(decrypted);
    System.out.println("1010111010110100");


    String encrypted = encrypt(decrypted,testkey);
    System.out.println(encrypted);
    System.out.println(testchiffre);
*/
    String[] arr = new String[8];

    for (int i = 0; i <= chiffre.length() - 16; i += 16) {
      arr[i / 16] = chiffre.substring(i, i + 16);
    }

    String[] keyArr = new String[8];

    keyArr[0] = "0000010011010010";
    keyArr[1] = "0000010011010010";
    keyArr[2] = "0000010011010011";
    keyArr[3] = "0000010011010100";
    keyArr[4] = "0000010011010101";
    keyArr[5] = "0000010011010110";
    keyArr[6] = "0000010011010111";
    keyArr[7] = "0000010011011000";

    String[] deKeyArr = new String[8];

    for (int i = 0; i < keyArr.length; i++) {
    deKeyArr[i]=decrypt(keyArr[i],key);
    }
    for(int i=1; i<arr.length;i++){
      arr[i]=xor(arr[i],deKeyArr[i]);
    }


    String temp="";
    for(String str:arr){
      temp+=str;
    }

    String[] ans= new String[16];
    for (int i = 0; i <= temp.length() - 8; i += 8) {
      ans[i / 8] = temp.substring(i, i + 8);
    }

    for (String str:ans
        ) {
      System.out.println(str+":" + (char) Integer.parseUnsignedInt(str,2));

    }
    System.out.println();
    for (String str:ans
         ) {
      System.out.print((char)Integer.parseInt(str,2));

    }



  }

  private String decrypt(String plain, String testkey) {
    String testchiffre = plain;
    testchiffre = weissschritt(testchiffre, getKey(testkey, 0));
    testchiffre = regularRound(testchiffre, getKey(testkey, 1));
    testchiffre = regularRound(testchiffre, getKey(testkey, 2));
    testchiffre = regularRound(testchiffre, getKey(testkey, 3));
    testchiffre = shortRound(testchiffre, getKey(testkey, 4));
    return testchiffre;
  }

  private String encrypt(String plain, String testkey) {
    String testchiffre = plain;
    testchiffre = reverseShortRound(testchiffre, getKey(testkey, 4));
    testchiffre = reverseRegularRound(testchiffre, getKey(testkey, 3));
    testchiffre = reverseRegularRound(testchiffre, getKey(testkey, 2));
    testchiffre = reverseRegularRound(testchiffre, getKey(testkey, 1));
    testchiffre = weissschritt(testchiffre, getKey(testkey, 0));
    return testchiffre;
  }

  private String shortRound(String plain, String key) {
    String temp = sBox(plain);
    temp = xor(temp, key);
    return temp;
  }

  private String reverseShortRound(String plain, String key) {
    String temp = xor(plain, key);
    temp = reverseSBox(temp);
    return temp;
  }

  private String regularRound(String plain, String key) {
    String temp = sBox(plain);
    temp = bitPermutation(temp);
    temp = xor(temp, key);
    return temp;
  }

  private String reverseRegularRound(String plain, String key) {
    String temp = xor(plain, key);
    temp = bitPermutation(temp);
    temp = reverseSBox(temp);
    return temp;
  }

  private String weissschritt(String plain, String key) {
    return xor(plain, key);
  }

  private String sBox(String x) {
    String temp = "";
    for (int i = 0; i <= x.length() - 4; i += 4) {
      temp += sBoxHelper(x.substring(i, i + 4));
    }

    return temp;
  }


  private String sBoxHelper(String x) {
    switch (x) {
      case "0000":
        return "1110";
      case "0001":
        return "0100";
      case "0010":
        return "1101";
      case "0011":
        return "0001";
      case "0100":
        return "0010";
      case "0101":
        return "1111";
      case "0110":
        return "1011";
      case "0111":
        return "1000";
      case "1000":
        return "0011";
      case "1001":
        return "1010";
      case "1010":
        return "0110";

      case "1011":
        return "1100";
      case "1100":
        return "0101";
      case "1101":
        return "1001";

      case "1110":
        return "0000";
      case "1111":
        return "0111";


      default:
        throw new IllegalArgumentException("illegal Selection");
    }
  }


  private String reverseSBox(String x) {
    String temp = "";
    for (int i = 0; i <= x.length() - 4; i += 4) {
      temp += reverseSBoxHelper(x.substring(i, i + 4));
    }

    return temp;
  }


  private String reverseSBoxHelper(String x) {
    switch (x) {
      case "1110":
        return "0000";
      case "0100":
        return "0001";
      case "1101":
        return "0010";
      case "0001":
        return "0011";
      case "0010":
        return "0100";
      case "1111":
        return "0101";
      case "1011":
        return "0110";
      case "1000":
        return "0111";
      case "0011":
        return "1000";
      case "1010":
        return "1001";
      case "0110":
        return "1010";

      case "1100":
        return "1011";
      case "0101":
        return "1100";
      case "1001":
        return "1101";

      case "0000":
        return "1110";
      case "0111":
        return "1111";


      default:
        throw new IllegalArgumentException("illegal Selection");
    }
  }

  private String bitPermutation(String x) {

    char[] temp = x.toCharArray();

    swop(temp, 0, 0);
    swop(temp, 1, 4);
    swop(temp, 2, 8);
    swop(temp, 3, 12);
    swop(temp, 5, 5);
    swop(temp, 6, 9);
    swop(temp, 7, 13);
    swop(temp, 10, 10);
    swop(temp, 11, 14);
    swop(temp, 15, 15);

    return String.valueOf(temp);

  }

  private void swop(char[] arr, int i, int j) {
    char temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
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

  private String getKey(String key, int i) {
    assert (key.length() >= 4 * i + 16);
    return key.substring(4 * i, 4 * i + 16);
  }

}
