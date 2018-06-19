package arvore;

/**
 *
 * @author hlarsen
 */
public class ArvoreBinaria<T> {

    private NoArvoreBinaria<T> raiz;

    public ArvoreBinaria() {
        raiz = null;
    }

    public NoArvoreBinaria<T> getRaiz() {
        return this.raiz;
    }

    public void setRaiz(NoArvoreBinaria<T> raiz) {
        this.raiz = raiz;
    }

    public boolean estaVazia() {
        return raiz == null;
    }

    public boolean pertence(T info) {
        return pertence(raiz, info);
    }

    private boolean pertence(NoArvoreBinaria no, T info) {
        if (no == null) {
            return false;
        } else {
            return ((no.getInfo().equals(info)) || (pertence(no.getEsquerda(), info))
                    || (pertence(no.getDireita(), info)));
        }
    }

    @Override
    public String toString() {
        return obterRepresentacaoTextual(raiz);
    }

    private String obterRepresentacaoTextual(NoArvoreBinaria no) {
        if (no == null) {
            return "<>";
        }

        String retorno = "<";
        retorno += no.getInfo();
        retorno += obterRepresentacaoTextual(no.getEsquerda());
        retorno += obterRepresentacaoTextual(no.getDireita());
        retorno += ">";
        return retorno;
    }

    public int contarNos() {
        return contarNos(raiz);
    }

    private int contarNos(NoArvoreBinaria<T> no) {
        if (no == null) {
            return 0;
        }

        return contarNos(no.getEsquerda()) + contarNos(no.getDireita()) + 1;
    }

}
