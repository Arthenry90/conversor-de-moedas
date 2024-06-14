package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import model.DadosMoeda;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Classe responsável por consumir a API, converter os dados para um record,
 * converter o valor informado, e devolver o valor convertido
 */
public class LeitorApi {

    private final Gson gson = new Gson();

    /**
     * Aqui deve-se utilizar uma chave de api,
     * disponível no site: https://www.exchangerate-api.com
     */
    private final String apiKey = System.getenv("EXCHANGE_RATE_API_KEY");

    private final String urlApi = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/";

    /**
     * Deve-se informar os parâmetros para realizar a conversão
     *
     * @param escolha
     * @param valor
     * @return retorna o valor já convertido
     */
    public Double callLeitorApi(String escolha, double valor) {

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlApi + escolha + "/" + valor))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao enviar a solicitação para a API", e);
        }

        // Verificação do status da resposta
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new RuntimeException("Erro na resposta da API: " + response.body());
        }

        // Verificação do conteúdo da resposta antes da conversão
        JsonObject jsonResponse;
        try {
            jsonResponse = gson.fromJson(response.body(), JsonObject.class);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Resposta JSON inválida: " + response.body(), e);
        }

        // Verificar se a resposta contém um erro
        if (jsonResponse.has("error")) {
            String errorMessage = jsonResponse.get("error").getAsString();
            throw new RuntimeException("Erro na resposta da API: " + errorMessage);
        }

        // Conversão para o objeto DadosMoeda
        try {
            DadosMoeda dadosMoeda = gson.fromJson(jsonResponse, DadosMoeda.class);
            return dadosMoeda.resultadoDaConversao();
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Erro na conversão para DadosMoeda: " + jsonResponse, e);
        }
    }
}