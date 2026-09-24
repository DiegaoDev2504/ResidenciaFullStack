import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {


        //Lê a mensagem
        String mensagem = LerMensagem.lerMensagem();

        //Chama a API para fazer a análise da mensagem
        String textoAnalise = GeminiRequest.montaRequisicao(mensagem);

        //Exibe o resultado da análise
        System.out.println();
        System.out.println("-----> Resultado da Análise <-----");
        System.out.println(textoAnalise);


    }
}