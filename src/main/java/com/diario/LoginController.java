package com.diario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private SonhoRepository sonhoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CriptografiaService criptografiaService;
    
    @GetMapping("/")
    public String exibirIndex() {
        return "index";
    }

    @GetMapping("/login")
    public String exibirLogin() {
        return "login";
    }

    @GetMapping("/home")
    public String exibirHome(Model model) {
        // 1. Pergunta ao Spring Security quem é o usuário logado (pelo E-mail)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailDoUsuario = auth.getName(); 
        
        // 2. Busca o usuário completo no banco de dados
        Usuario user = usuarioRepository.findByEmail(emailDoUsuario);

        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("nomeUsuario", user.getNome()); 

        List<Sonho> listaSonhos = sonhoRepository.findByUsuario(user);
        
        for (Sonho s : listaSonhos) {
            try {
                if (s.getRelato() != null && !s.getRelato().isEmpty()) {
                    String relatoAberto = criptografiaService.descriptografar(s.getRelato());
                    s.setRelato(relatoAberto);
                }
            } catch (Exception e) {
                // Se cair aqui, é porque o sonho é antigo e foi salvo sem criptografia. 
                // O sistema ignora e mostra o texto original normal.
            }
        }
        
        // Agrupa os sonhos pela data, do mais recente para o mais antigo
        Map<java.time.LocalDate, List<Sonho>> sonhosAgrupados = listaSonhos.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        Sonho::getDataRegistro,
                        () -> new java.util.TreeMap<>(java.util.Collections.reverseOrder()), 
                        java.util.stream.Collectors.toList()
                ));

        // Manda os grupos para a tela
        model.addAttribute("sonhosAgrupados", sonhosAgrupados);

        Map<String, Integer> mPers = new HashMap<>(), mLug = new HashMap<>(), mSimbolos = new HashMap<>();
        Map<String, Integer> mPensamentos = new HashMap<>(), mEmocoes = new HashMap<>(), 
                            mSensacoes = new HashMap<>(), mPercepcoes = new HashMap<>();
        Map<String, Integer> mAcaoEgo = new HashMap<>(), mAcaoPers = new HashMap<>(), mAcaoObj = new HashMap<>();
        Map<String, Integer> mFormaEgo = new HashMap<>(), mFormaPers = new HashMap<>(), 
                            mFormaObj = new HashMap<>(), mFormaCenario = new HashMap<>();
        Map<String, Integer> mPapelEgo = new HashMap<>(), mLugarCen = new HashMap<>(), 
                            mTempoCen = new HashMap<>(), mSituacao = new HashMap<>();
        
        int totalLucidos = 0;

        for (Sonho s : listaSonhos) {
            if (s.getLucidez() != null && s.getLucidez() > 0) totalLucidos++;
            contar(s.getPersonagens(), mPers);
            contar(s.getLugares(), mLug);
            contar(s.getSimbolos(), mSimbolos);
            contar(s.getPensamentos(), mPensamentos);
            contar(s.getEmocoes(), mEmocoes);
            contar(s.getSensacoes(), mSensacoes);
            contar(s.getPercepcoes(), mPercepcoes);
            contar(s.getAcaoEgo(), mAcaoEgo);
            contar(s.getAcaoPersonagem(), mAcaoPers);
            contar(s.getAcaoObjeto(), mAcaoObj);
            contar(s.getFormaEgo(), mFormaEgo);
            contar(s.getFormaPersonagem(), mFormaPers);
            contar(s.getFormaObjeto(), mFormaObj);
            contar(s.getFormaCenario(), mFormaCenario);
            contar(s.getPapelEgo(), mPapelEgo);
            contar(s.getLugarCenario(), mLugarCen);
            contar(s.getTempoCenario(), mTempoCen);
            contar(s.getSituacaoBizarra(), mSituacao);
        }

        // =========================================================
        // A MAGIA ACONTECE AQUI: Ordenando tudo do Maior para o Menor
        // =========================================================
        mPers = ordenarPorFrequencia(mPers);
        mLug = ordenarPorFrequencia(mLug);
        mSimbolos = ordenarPorFrequencia(mSimbolos);
        
        mPensamentos = ordenarPorFrequencia(mPensamentos);
        mEmocoes = ordenarPorFrequencia(mEmocoes);
        mSensacoes = ordenarPorFrequencia(mSensacoes);
        mPercepcoes = ordenarPorFrequencia(mPercepcoes);
        
        mAcaoEgo = ordenarPorFrequencia(mAcaoEgo);
        mAcaoPers = ordenarPorFrequencia(mAcaoPers);
        mAcaoObj = ordenarPorFrequencia(mAcaoObj);
        
        mFormaEgo = ordenarPorFrequencia(mFormaEgo);
        mFormaPers = ordenarPorFrequencia(mFormaPers);
        mFormaObj = ordenarPorFrequencia(mFormaObj);
        mFormaCenario = ordenarPorFrequencia(mFormaCenario);
        
        mPapelEgo = ordenarPorFrequencia(mPapelEgo);
        mLugarCen = ordenarPorFrequencia(mLugarCen);
        mTempoCen = ordenarPorFrequencia(mTempoCen);
        mSituacao = ordenarPorFrequencia(mSituacao);
        // =========================================================

        model.addAttribute("listaSonhos", listaSonhos);
        model.addAttribute("totalSonhos", listaSonhos.size());
        model.addAttribute("totalLucidos", totalLucidos);
        model.addAttribute("cPers", mPers);
        model.addAttribute("cLug", mLug);
        model.addAttribute("cSimbolos", mSimbolos);
        model.addAttribute("cPensamentos", mPensamentos); 
        model.addAttribute("cEmocoes", mEmocoes);
        model.addAttribute("cSensacoes", mSensacoes); 
        model.addAttribute("cPercepcoes", mPercepcoes);
        model.addAttribute("cAcaoEgo", mAcaoEgo); 
        model.addAttribute("cAcaoPers", mAcaoPers);
        model.addAttribute("cAcaoObj", mAcaoObj); 
        model.addAttribute("cFormaEgo", mFormaEgo);
        model.addAttribute("cFormaPers", mFormaPers); 
        model.addAttribute("cFormaObj", mFormaObj);
        model.addAttribute("cFormaCen", mFormaCenario); 
        model.addAttribute("cPapelEgo", mPapelEgo);
        model.addAttribute("cLugarCen", mLugarCen); 
        model.addAttribute("cTempoCen", mTempoCen);
        model.addAttribute("cSituacao", mSituacao);

        return "home";
    }

    @PostMapping("/salvar-sonho")
    public String salvarSonho(Sonho novoSonho) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailDoUsuario = auth.getName(); 
        Usuario user = usuarioRepository.findByEmail(emailDoUsuario);

        if (user != null) {
            novoSonho.setUsuario(user);
            
            // CRIPTOGRAFIA: Tranca o relato antes de ir pro banco
            try {
                if (novoSonho.getRelato() != null && !novoSonho.getRelato().isEmpty()) {
                    String relatoFechado = criptografiaService.criptografar(novoSonho.getRelato());
                    novoSonho.setRelato(relatoFechado);
                }
            } catch (Exception e) {
                System.out.println("Erro ao criptografar o sonho: " + e.getMessage());
            }

            sonhoRepository.save(novoSonho);
        }
        return "redirect:/home";
    }

    @GetMapping("/excluir-sonho/{id}")
    public String excluirSonho(@PathVariable Long id) {
        sonhoRepository.deleteById(id);
        return "redirect:/home?aba=historico";
    }
    
    @GetMapping("/editar-sonho/{id}")
    public String editarSonho(@PathVariable Long id, Model model) {
        Sonho sonho = sonhoRepository.findById(id).orElse(null);
       
        if (sonho != null) {
            try {
                if (sonho.getRelato() != null && !sonho.getRelato().isEmpty()) {
                    String relatoAberto = criptografiaService.descriptografar(sonho.getRelato());
                    sonho.setRelato(relatoAberto);
                }
            } catch (Exception e) {
                // Se der erro na descriptografia, segue a vida
            }
            
            model.addAttribute("sonhoParaEditar", sonho);
        }
         
        return exibirHome(model); 
    }

    // Método que quebra as vírgulas e conta as palavras
    private void contar(String texto, Map<String, Integer> mapa) {
        if (texto != null && !texto.isEmpty()) {
            String[] itens = texto.split(",");
            for (String item : itens) {
                String limpo = item.trim().toLowerCase();
                if (!limpo.isEmpty()) {
                    mapa.put(limpo, mapa.getOrDefault(limpo, 0) + 1);
                }
            }
        }
    }

    // NOVO MÉTODO: Organiza os itens do que aparece mais para o que aparece menos!
    private <K, V extends Comparable<? super V>> java.util.Map<K, V> ordenarPorFrequencia(java.util.Map<K, V> mapaOriginal) {
        return mapaOriginal.entrySet().stream()
                .sorted(java.util.Map.Entry.<K, V>comparingByValue().reversed())
                .collect(java.util.stream.Collectors.toMap(
                        java.util.Map.Entry::getKey,
                        java.util.Map.Entry::getValue,
                        (e1, e2) -> e1,
                        java.util.LinkedHashMap::new 
                ));
    }
}