import java.util.Scanner;

public class LerMensagem {

    public static String lerMensagem() {
    Scanner scanner = new Scanner(System.in);
    String mensagem;

    //Lê a mensagem do usuário e monta o body do JSON para enviar na requisição com o prompt de validação;
        System.out.println("----------------------------------");
        System.out.println("Bem-vindo ao Detetive Digital!");
        System.out.println("----------------------------------");
        System.out.println();


    do {
        System.out.println("Informa a mensagem que deseja verificar: ");
        mensagem = scanner.nextLine();

        if (mensagem.isBlank()) {
            System.out.println("A mensagem não pode ficar em branco.");
        }
    } while (mensagem.isBlank());

    return mensagem;

    }
}

