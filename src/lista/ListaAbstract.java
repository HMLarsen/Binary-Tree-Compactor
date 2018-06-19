package lista;

/**
 *
 * @author hugo
 */
public abstract class ListaAbstract<T> {

    private T[] info;
    private int tamanho;
    private static final int TAMANHO_PADRAO = 10;

    public ListaAbstract() {
        info = ((T[]) new Object[TAMANHO_PADRAO]);
        tamanho = 0;
    }

    public abstract void inserir(T valor);

    public abstract int buscar(T valorBuscar);

    public void exibir() {
        String str = "";

        for (int i = 0; i < tamanho; i++) {
            str += info[i] + ", ";
        }

        System.out.println(String.copyValueOf(str.toCharArray(), 0, str.length() - 2));
    }

    public void retirar(T valor) {
        int posicao = buscar(valor);

        if (posicao > -1) {
            for (int i = posicao; i < tamanho - 1; i++) {
                info[i] = info[i + 1];
            }

            tamanho--;
        }
    }

    public void liberar() {
        info = ((T[]) new Object[TAMANHO_PADRAO]);
        tamanho = 0;
    }

    public T obterElemento(int posicao) throws IndexOutOfBoundsException {
        if (posicao < tamanho) {
            return info[posicao];
        } else {
            throw new IndexOutOfBoundsException("Posição inexistente ou não ocupada!");
        }
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    protected T[] getInfo() {
        return this.info;
    }

    protected void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getTamanho() {
        return this.tamanho;
    }

    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < tamanho; i++) {
            if (i == (tamanho - 1)) {
                str += info[i];
            } else {
                str += info[i] + ", ";
            }
        }

        return str;
    }

    protected void redimensionar() {
        T[] novo = ((T[]) new Object[tamanho + TAMANHO_PADRAO]);

        for (int i = 0; i < tamanho; i++) {
            novo[i] = info[i];
        }

        info = novo;
    }
}
