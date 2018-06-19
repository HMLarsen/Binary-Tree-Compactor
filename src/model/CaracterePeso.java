package model;


/**
 *
 * @author hugo
 */
public class CaracterePeso implements Comparable<CaracterePeso> {

    private char caractere;
    private int peso;

    public CaracterePeso(char caractere) {
        this.caractere = caractere;
        this.peso = 1;
    }

    public char getCaractere() {
        return caractere;
    }

    public void setCaractere(char caractere) {
        this.caractere = caractere;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    @Override
    public int compareTo(CaracterePeso c) {
        return this.peso - c.peso;
    }

    @Override
    public String toString() {
        return "''" + caractere + "'', peso " + peso;
    }

}
