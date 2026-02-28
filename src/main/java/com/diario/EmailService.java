package com.diario; // Verifique se o nome do seu pacote é esse mesmo

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import java.util.Map;
import java.util.List;

@Service
public class EmailService {

    // Cole a chave longa que você copiou no site do Brevo aqui embaixo
	private final String API_KEY = System.getenv("BREVO_API_KEY");
    
    // Coloque o mesmo e-mail que você usou para criar a conta no Brevo
    private final String SEU_EMAIL = "diariodelucidez@gmail.com"; 

    private final String URL_BREVO = "https://api.brevo.com/v3/smtp/email";

    @Async
    public void enviarEmailBoasVindas(String emailDestino, String nomeUsuario) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            
            // 1. Prepara a "credencial" para a porta da API
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", API_KEY);
            headers.set("accept", "application/json");

            // 2. Monta o pacote de dados do e-mail (Remetente, Destinatário, Assunto e Texto)
            Map<String, Object> corpoRequisicao = Map.of(
                "sender", Map.of("name", "Diário da Lucidez", "email", SEU_EMAIL),
                "to", List.of(Map.of("email", emailDestino, "name", nomeUsuario)),
                "subject", "Bem-vindo(a) ao Diário da Lucidez! 🌙",
                "htmlContent", "<html><body><h2 style='color: #03e9f4;'>Olá, " + nomeUsuario + "!</h2><p>Sua jornada onírica acaba de começar. Prepare-se para registrar e explorar os seus sonhos.</p></body></html>"
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(corpoRequisicao, headers);
            
            // 3. Dispara a mensagem pela internet normal (fura o bloqueio da porta 587!)
            restTemplate.postForEntity(URL_BREVO, request, String.class);
            
            System.out.println("SUCESSO: E-mail de boas-vindas enviado para " + emailDestino);

        } catch (Exception e) {
            System.out.println("FALHA: Não foi possível enviar o e-mail pela API do Brevo.");
            System.out.println("Motivo: " + e.getMessage());
        }
    }
}