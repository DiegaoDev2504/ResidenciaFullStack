import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class GeminiRequest {

    public static String montaRequisicao(String mensagem) throws IOException, InterruptedException {


        //Obtém a chave da API
        String apiKey = System.getenv("GEMINI_API_KEY");

        //Inicializa variável com a url do endpoint da API
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash-lite:generateContent";

        //Inicializa variável com o prompt para a API do Gemini
        String prompt = """
                Objetivo: Sou um desenvolvedor que está implementando uma aplicação que precisa verificar se uma mensagem é uma tentativa 
                de golpe digital ou não. O agente deve ler a mensagem recebida e analisar o seu conteúdo. Após a análise, deve informar a 
                porcentagem da propabilidade da mensagem ser uma tentativa de golpe. Para realizar a análise da mensagem deverão ser utilizados 
                apenas os critérios obtidos nas fontes de informação designidados na sessão 'fontes'. A resposta precisa ser sucinta e breve, sem maiores explicações de contexto.
                Contexto: Sou um desenvolvedor que está implementando um algoritmo para validar mensagens e identificar se ela é maliciosa.
                Expectativa: Informar uma mensagem e obter uma análise se ela é maliciosa. Ao usuário deverá ser apresentada o valor percentual 
                da probabilidade de se tratar de uma tentativa de golpe digital. A mensagem de retorno deverá ser assim: 'Essa mensagem tem 70% de 
                chance de ser uma tentativa de golpe digital'
                Fontes: 'https://www.gov.br/mcom/pt-br/noticias/noticias_alt/2026/julho/ataque-phishing-como-identificar-tentativas-de-fraude-na-internet', 
                'https://www.tjsc.jus.br/web/servidor/dicas-de-ti/-/asset_publisher/0rjJEBzj2Oes/content/conheca-os-principais-golpes-praticados-na-internet' 
                e 'https://sti.ufc.br/pt/perguntas-frequentes/seguranca-da-informacao-2/como-identificar-um-golpe-na-internet'.""";

        //Monta o body do JSON que será enviado na requisição, informando o prompt e a mensagem recebida;
        String corpoJson = """
               {
                 "systemInstruction": { "parts": [ { "text": "%s" } ] },
                 "contents": [
                   { "role": "user", "parts": [ { "text": "%s" } ] }
                 ],
                 "generationConfig": { "temperature": 0.2, "maxOutputTokens": 800 }
               }
               """.formatted(prompt, mensagem);

        //Cria o client
        HttpClient cliente = HttpClient.newBuilder().build();

        //Cria o request para chamar a API
        HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("x-goog-api-key", apiKey)
                .timeout(Duration.ofSeconds(60))
                .POST(HttpRequest.BodyPublishers.ofString(corpoJson, StandardCharsets.UTF_8))
                .build();


        HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        String jsonResposta = resposta.body();

        return Json.extrairTexto(jsonResposta);

    }
}