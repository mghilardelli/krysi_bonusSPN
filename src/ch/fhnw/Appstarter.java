package ch.fhnw;

public class Appstarter {

    static final byte[] key = "00111010100101001101011000111111".getBytes();
    static final byte[] chiffre = "00000100110100100000101110111000000000101000111110001110011111110110000001010001010000111010000000010011011001110010101110110000".getBytes();

    public static void main(String[] args) {


        for (byte b:key) {
            System.out.print(Integer.toBinaryString(b));}

        }
}
