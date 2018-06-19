
import model.CaractereCodificacao;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import lista.ListaEstatica;

/**
 *
 * @author hugo
 */
public class Descompactador {

    public void descompactarArquivo(String arqOrigem, String arqDestino) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(arqOrigem));
        ListaEstatica<CaractereCodificacao> tabelaCodificacao = new ListaEstatica<>();

        //Obter a quantidade de linhas a ser lida para criar a tabela de codificação
        int qtdeCaracTabela = Integer.parseInt(br.readLine());
        int aux = 0;

        //Montar a tabela de codificação percorrendo até o limite de linhas
        while (br.ready()) {
            if (aux == qtdeCaracTabela) {
                break;
            }

            //Separar o código ascii do código de codificação
            String[] valores = br.readLine().split("=");
            tabelaCodificacao.inserir(new CaractereCodificacao(Integer.parseInt(valores[0]), valores[1]));
            aux++;
        }

        //Percorrer os chars da última linha
        BufferedWriter bw = new BufferedWriter(new FileWriter(arqDestino));
        String texto = br.readLine();
        String concatenacao = "";

        for (char c : texto.toCharArray()) {
            concatenacao += String.valueOf(c);

            for (int i = 0; i < tabelaCodificacao.getTamanho(); i++) {
                CaractereCodificacao t = tabelaCodificacao.obterElemento(i);

                if (t.getBinario().equals(concatenacao)) {
                    bw.write(t.getLetra());
                    concatenacao = "";
                    break;
                }
            }
        }

        br.close();
        bw.close();
    }

}
