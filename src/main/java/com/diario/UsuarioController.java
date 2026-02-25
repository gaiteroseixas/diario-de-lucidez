package com.diario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    // 1. Mostra a página de cadastro
    @GetMapping("/cadastro")
    public String exibirCadastro() {
        return "cadastro";
    }

    // 2. Recebe os dados, verifica duplicata, salva e dispara o e-mail
    @PostMapping("/cadastro")
    public String salvarUsuario(Usuario usuario, Model model) {
        
        // PASSO A: Busca no banco para ver se o e-mail já existe
        Usuario usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        // PASSO B: Se achou alguém, devolve o erro para a tela!
        if (usuarioExistente != null) {
            model.addAttribute("erro", "Esse e-mail já está cadastrado. Tente fazer login!");
            return "cadastro"; // Interrompe o processo e recarrega a página de cadastro
        }

        // PASSO C: Se chegou até aqui, o e-mail é novo! Salva no PostgreSQL.
        usuarioRepository.save(usuario);

        // PASSO D: Dispara o e-mail de boas-vindas
        try {
            emailService.enviarEmailBoasVindas(usuario.getEmail(), usuario.getNome());
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail: " + e.getMessage());
        }

        // Sucesso! Manda o usuário para a tela de login
        return "redirect:/login"; 
    }
}