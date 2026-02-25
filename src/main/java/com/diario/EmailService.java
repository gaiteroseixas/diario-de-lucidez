package com.diario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailBoasVindas(String emailDestino, String nomeUsuario) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        
        mensagem.setTo(emailDestino);
        mensagem.setSubject("Bem-vindo ao Diário de Lucidez! 🌙");
        mensagem.setText("Olá, " + nomeUsuario + "!\n\n"
                + "Que bom ter você conosco. Seu registro no Diário de Lucidez foi concluído com sucesso.\n\n"
                + "A partir de agora, você tem um espaço seguro para catalogar suas jornadas oníricas, "
                + "e aumentar a frequência de lucidez nos seus sonhos e na sua vida!!\n\n"
                + "Bons sonhos e muita lucidez!\n"
                + "Equipe Diário de Lucidez");

        mailSender.send(mensagem);
    }
}