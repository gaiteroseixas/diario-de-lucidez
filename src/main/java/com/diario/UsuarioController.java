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
        // 1. Criptografia: Transforma a senha real em um código seguro
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        // 2. Persistência: Agora o banco salva apenas o código, não a senha real
        usuarioRepository.save(usuario);

        // 3. Notificação: Envia o e-mail (que já configuramos no Brevo)
        emailService.enviarEmailBoasVindas(usuario.getEmail(), usuario.getNome());

        return "redirect:/login";
    }
}