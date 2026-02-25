package com.diario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class SonhoController {

    @Autowired
    private SonhoRepository sonhoRepository;

    @PostMapping("/salvarSonho")
    public String salvar(Sonho sonho, HttpSession session) {
        // Recupera o usuário que logou (precisamos garantir que o LoginController salve ele aqui)
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado != null) {
            // Define o dono do sonho antes de salvar no PostgreSQL
            sonho.setUsuario(usuarioLogado);
            sonhoRepository.save(sonho);
            return "redirect:/home";
        }

        return "redirect:/login";
    }
}