package com.diario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // Não esqueça de importar o List!

@Repository
public interface SonhoRepository extends JpaRepository<Sonho, Long> {
    
    // Este método novo permite buscar apenas os sonhos de um usuário específico
    List<Sonho> findByUsuario(Usuario usuario);

}