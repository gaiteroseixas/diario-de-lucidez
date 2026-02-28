package com.diario;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class CriptografiaService {

    // No futuro, coloque esta chave nas Environment Variables do Render
    private static final String CHAVE_MESTRA = "cRiPtOfIa5290kKLoRtQ2614kjrosnhH"
    		+ ""; 
    private static final String ALGORITMO = "AES";

    public String criptografar(String texto) {
        try {
            SecretKeySpec key = new SecretKeySpec(CHAVE_MESTRA.getBytes(), ALGORITMO);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] criptografado = cipher.doFinal(texto.getBytes());
            return Base64.getEncoder().encodeToString(criptografado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar sonho", e);
        }
    }

    public String descriptografar(String textoCriptografado) {
        try {
            SecretKeySpec key = new SecretKeySpec(CHAVE_MESTRA.getBytes(), ALGORITMO);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodificado = Base64.getDecoder().decode(textoCriptografado);
            return new String(cipher.doFinal(decodificado));
        } catch (Exception e) {
            return "Erro ao ler sonho: " + e.getMessage();
        }
    }
}