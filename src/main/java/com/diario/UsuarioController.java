package com.diario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
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
    public String cadastrarUsuario(Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);
        emailService.enviarEmailBoasVindas(usuario.getEmail(), usuario.getNome());
        return "redirect:/login";
    }

    // --- AS ROTAS QUE FALTAVAM PARA O LOGIN FUNCIONAR ---

    // 3. Mostra a sua tela de login customizada
    @GetMapping("/login")
    public String exibirLogin() {
        return "login"; // Deve ter um arquivo login.html na pasta templates
    }

    // 4. Para onde o Spring redireciona após o login dar certo
    @GetMapping("/home")
    public String exibirHome() {
        return "home"; // Deve ter um arquivo home.html na pasta templates
    }
}