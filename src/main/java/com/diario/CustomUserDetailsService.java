package com.diario; // ou com.diario.service, dependendo de onde você colocar

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Busca o usuário no banco de dados usando o e-mail digitado no login
        Usuario usuario = usuarioRepository.findByEmail(email);

        // 2. Se não achar, devolve erro
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email);
        }

        // 3. Se achar, empacota os dados no formato que o Spring Security exige
        return new User(
            usuario.getEmail(),
            usuario.getSenha(), // A senha criptografada que está no banco
            new ArrayList<>()   // Lista de permissões (vazia por enquanto)
        );
    }
}