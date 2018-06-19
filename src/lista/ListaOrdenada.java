package lista;

/**
 *
 * @author hugo
 */
public class ListaOrdenada<T extends Comparable> extends ListaAbstract<T> {

    public ListaOrdenada() {
        super();
    }

    private int getLimite() {
        Object[] info = getInfo();
        return info.length;
    }

    private int procurarPosicaoInclusao(T valor) {
        Object[] info = getInfo();
        int i;

        for (i = 0; i < getTamanho(); i++) {
            if (valor.compareTo((T) info[i]) < 0) {
                return i;
            }
        }

        return i;
    }

    private void deslocarDado(int posicao) {
        Object[] info = getInfo();

        for (int i = getTamanho(); i > posicao; i--) {
            info[i] = info[i - 1];
        }
    }

    @Override
    public void inserir(T valor) {
        if (getLimite() == getTamanho()) {
            redimensionar();
        }

        int posicao = procurarPosicaoInclusao(valor);

        if (posicao < getTamanho()) {
            deslocarDado(posicao);
        }

        Object[] info = getInfo();
        info[posicao] = valor;
        setTamanho(getTamanho() + 1);
    }

    @Override
    public int buscar(T valorBuscar) {
        Object[] info = getInfo();

        int inicio = 0;
        int fim = getTamanho() - 1;
        int meio;

        while (inicio <= fim) {
            meio = (inicio + fim) / 2;
            int comparacao = valorBuscar.compareTo((T) info[meio]);

            if (comparacao < 0) {
                fim = meio - 1;
            } else if (comparacao > 0) {
                inicio = meio + 1;
            } else {
                return meio;
            }
        }

        return -1;
    }

}
