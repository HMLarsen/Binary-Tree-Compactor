
import model.CaractereCodificacao;
import model.CaracterePeso;
import arvore.ArvoreBinaria;
import arvore.NoArvoreBinaria;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import lista.ListaEstatica;
import lista.ListaOrdenada;

/**
 *
 * @author hugo
 */
public class Compactador {

    public void compactarArquivo(String arqOrigem, String arqDestino) throws FileNotFoundException, IOException {
        //Primeiramente criar uma lista de caracteres que não se repetem
        ListaEstatica<Character> caracteres = new ListaEstatica<>();
        ListaEstatica<CaracterePeso> caracteresPeso = new ListaEstatica<>();

        FileInputStream inputStream = new FileInputStream(arqOrigem);
        int b = inputStream.read();

        while (b != -1) {
            char c = (char) b;

            if (caracteres.buscar(c) == -1) {
                caracteres.inserir(c);
                caracteresPeso.inserir(new CaracterePeso(c));
            } else {
                //É preciso percorrer a lista porque não dá de achar o objeto em si
                for (int j = 0; j < caracteresPeso.getTamanho(); j++) {
                    if (caracteresPeso.obterElemento(j).getCaractere() == c) {
                        caracteresPeso.obterElemento(j).setPeso(caracteresPeso.obterElemento(j).getPeso() + 1);
                        break;
                    }
                }
            }

            b = inputStream.read();
        }

        inputStream.close();

        //Criar lista ordenada de CaracterePeso
        ListaOrdenada<CaracterePeso> caracOrdenados = new ListaOrdenada<>();
        criarListaOrdenadaCaracteres(caracOrdenados, caracteresPeso);

        //Criar e juntar árvores na lista estática
        ListaEstatica<ArvoreBinaria<Object>> arvores = new ListaEstatica<>();
        criarArvoreParaCaracteres(arvores, caracOrdenados);
        juntarArvores(arvores);

        //Criar tabela de codificação
        ListaEstatica<CaractereCodificacao> tabelaCodificacao = new ListaEstatica<>();
        criarTabelaCodificacao(tabelaCodificacao, arvores, caracOrdenados);

        //Inserir a tabela no arquivo
        BufferedWriter bw = new BufferedWriter(new FileWriter(arqDestino));
        bw.write(String.valueOf(caracOrdenados.getTamanho()) + "\n");

        for (int i = 0; i < tabelaCodificacao.getTamanho(); i++) {
            CaractereCodificacao t = tabelaCodificacao.obterElemento(i);
            bw.write(t.getAscii() + "=" + t.getBinario() + "\n");
        }

        //Ler novamente as linhas e inserir a codificação no arquivo
        inputStream = new FileInputStream(arqOrigem);
        b = inputStream.read();

        while (b != -1) {
            char c = (char) b;

            for (int i = 0; i < tabelaCodificacao.getTamanho(); i++) {
                CaractereCodificacao t = tabelaCodificacao.obterElemento(i);
                if (t.getLetra() == c) {
                    bw.write(t.getBinario());
                }
            }

            b = inputStream.read();
        }

        inputStream.close();
        bw.close();
    }

    private void percorrerArvore(ListaEstatica<ArvoreBinaria<Object>> arvores, ListaEstatica<CaractereCodificacao> tabelaCodificacao, char c) {
        obterBinarioCaractere(arvores.obterElemento(0).getRaiz(), c, "", tabelaCodificacao);
    }

    private void obterBinarioCaractere(NoArvoreBinaria<Object> no, char c, String binario, ListaEstatica<CaractereCodificacao> tabelaCodificacao) {
        if (no != null) {
            if (no.getInfo().getClass() == ArvoreBinaria.class) {
                if (((ArvoreBinaria<Object>) no.getInfo()).getRaiz().getInfo().getClass() == CaracterePeso.class) {
                    if (((CaracterePeso) ((ArvoreBinaria<Object>) no.getInfo()).getRaiz().getInfo()).getCaractere() == (c)) {
                        tabelaCodificacao.inserir(new CaractereCodificacao(c, binario));
                    }
                } else {
                    obterBinarioCaractere(((ArvoreBinaria<Object>) no.getInfo()).getRaiz().getEsquerda(), c, binario + "0", tabelaCodificacao);
                    obterBinarioCaractere(((ArvoreBinaria<Object>) no.getInfo()).getRaiz().getDireita(), c, binario + "1", tabelaCodificacao);
                }
            } else {
                obterBinarioCaractere(no.getEsquerda(), c, binario + "0", tabelaCodificacao);
                obterBinarioCaractere(no.getDireita(), c, binario + "1", tabelaCodificacao);
            }
        }
    }

    public void criarListaOrdenadaCaracteres(ListaOrdenada<CaracterePeso> caracOrdenados, ListaEstatica<CaracterePeso> caracteresPeso) {
        for (int i = 0; i < caracteresPeso.getTamanho(); i++) {
            caracOrdenados.inserir(caracteresPeso.obterElemento(i));
        }
    }

    public void criarArvoreParaCaracteres(ListaEstatica<ArvoreBinaria<Object>> arvores, ListaOrdenada<CaracterePeso> caracOrdenados) {
        for (int i = 0; i < caracOrdenados.getTamanho(); i++) {
            ArvoreBinaria<Object> arvore = new ArvoreBinaria<>();
            arvore.setRaiz(new NoArvoreBinaria(caracOrdenados.obterElemento(i)));
            arvores.inserir(arvore);
        }
    }

    private void juntarArvores(ListaEstatica<ArvoreBinaria<Object>> arvores) {
        ArvoreBinaria<Object> T1;
        ArvoreBinaria<Object> T2;
        ArvoreBinaria<Object> Tr;
        int peso1;
        int peso2;

        while (arvores.getTamanho() > 1) {
            T1 = arvores.obterElemento(0);
            T2 = arvores.obterElemento(1);
            Tr = new ArvoreBinaria<>();

            if (arvores.obterElemento(0).getRaiz().getInfo().getClass() == CaracterePeso.class) {
                peso1 = ((CaracterePeso) arvores.obterElemento(0).getRaiz().getInfo()).getPeso();
            } else {
                peso1 = ((Integer) arvores.obterElemento(0).getRaiz().getInfo());
            }

            if (arvores.obterElemento(1).getRaiz().getInfo().getClass() == CaracterePeso.class) {
                peso2 = ((CaracterePeso) arvores.obterElemento(1).getRaiz().getInfo()).getPeso();
            } else {
                peso2 = ((Integer) arvores.obterElemento(1).getRaiz().getInfo());
            }

            NoArvoreBinaria<Object> no = new NoArvoreBinaria<>(peso1 + peso2,
                    new NoArvoreBinaria(T1),
                    new NoArvoreBinaria(T2));

            Tr.setRaiz(no);

            arvores.retirar(T1);
            arvores.retirar(T2);
            arvores.inserir(Tr);
        }
    }

    public void criarTabelaCodificacao(
            ListaEstatica<CaractereCodificacao> tabelaCodificacao,
            ListaEstatica<ArvoreBinaria<Object>> arvores,
            ListaOrdenada<CaracterePeso> caracOrdenados) {
        for (int i = 0; i < caracOrdenados.getTamanho(); i++) {
            percorrerArvore(arvores, tabelaCodificacao, ((CaracterePeso) caracOrdenados.obterElemento(i)).getCaractere());
        }
    }

}
