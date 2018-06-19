package model;

/**
 *
 * @author hugo
 */
public class CaractereCodificacao {

    private char letra;
    private int ascii;
    private String binario;

    public CaractereCodificacao(char letra, String binario) {
        this.letra = letra;
        this.ascii = (int) letra;
        this.binario = binario;
    }

    public CaractereCodificacao(int ascii, String binario) {
        this.letra = (char) ascii;
        this.ascii = ascii;
        this.binario = binario;
    }

    public char getLetra() {
        return letra;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }

    public int getAscii() {
        return ascii;
    }

    public void setAscii(int ascii) {
        this.ascii = ascii;
    }

    public String getBinario() {
        return binario;
    }

    public void setBinario(String binario) {
        this.binario = binario;
    }

}
