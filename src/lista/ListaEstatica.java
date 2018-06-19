package lista;

/**
 *
 * @author hugo
 */
public class ListaEstatica<T> extends ListaAbstract<T> {

    @Override
    public void inserir(T valor) {
        if (getInfo().length == getTamanho()) {
            redimensionar();
        }

        getInfo()[getTamanho()] = valor;
        setTamanho(getTamanho() + 1);
    }

    @Override
    public int buscar(T valorBuscar) {
        for (int i = 0; i < getTamanho(); i++) {
            if (getInfo()[i] == valorBuscar) {
                return i;
            }
        }

        return -1;
    }

}
