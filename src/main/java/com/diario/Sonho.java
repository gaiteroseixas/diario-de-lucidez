package com.diario;

import jakarta.persistence.*; // Simplificando os imports
import org.springframework.format.annotation.DateTimeFormat; 

@Entity 
public class Sonho {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT") 
    private String relato; 

    private String corFundo;
    private Integer lucidez; 
    
    // ANOTAÇÃO ADICIONADA AQUI:
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.time.LocalDate dataRegistro = java.time.LocalDate.now();
    
    // --- NOVO CAMPO DE RELACIONAMENTO ---
    @ManyToOne 
    @JoinColumn(name = "usuario_id", nullable = true) 
    private Usuario usuario;
    // ------------------------------------

    // Categorias de Sinais de Sonho
    private String personagens;
    private String lugares;
    private String simbolos;
    private String objetosAcoes;
    
    // Consciência Interna
    private String pensamentos, emocoes, sensacoes, percepcoes; 
    // Ação
    private String acaoEgo, acaoPersonagem, acaoObjeto;        
    // Forma
    private String formaEgo, formaPersonagem, formaObjeto, formaCenario; 
    // Contexto
    private String papelEgo, lugarCenario, tempoCenario, situacaoBizarra; 

    public Sonho() {
    }

    // --- GETTER E SETTER DO NOVO CAMPO ---
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    // ------------------------------------
    
    public java.time.LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(java.time.LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    // Truque mágico: Devolve a data já no padrão brasileiro para a tela (ex: 25/02/2026)
    public String getDataFormatada() {
        if (this.dataRegistro == null) return "Data Desconhecida";
        return this.dataRegistro.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // --- SEUS GETTERS E SETTERS ORIGINAIS ABAIXO ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getRelato() { return relato; }
    public void setRelato(String relato) { this.relato = relato; }

    public String getCorFundo() { return corFundo; }
    public void setCorFundo(String corFundo) { this.corFundo = corFundo; }

    public Integer getLucidez() { return lucidez; }
    public void setLucidez(Integer lucidez) { this.lucidez = lucidez; }

    public String getPersonagens() { return personagens; }
    public void setPersonagens(String personagens) { this.personagens = personagens; }

    public String getLugares() { return lugares; }
    public void setLugares(String lugares) { this.lugares = lugares; }

    public String getSimbolos() { return simbolos; }
    public void setSimbolos(String simbolos) { this.simbolos = simbolos; }

    public String getObjetosAcoes() { return objetosAcoes; }
    public void setObjetosAcoes(String objetosAcoes) { this.objetosAcoes = objetosAcoes; }

    public String getPensamentos() { return pensamentos; }
    public void setPensamentos(String pensamentos) { this.pensamentos = pensamentos; }
    public String getEmocoes() { return emocoes; }
    public void setEmocoes(String emocoes) { this.emocoes = emocoes; }
    public String getSensacoes() { return sensacoes; }
    public void setSensacoes(String sensacoes) { this.sensacoes = sensacoes; }
    public String getPercepcoes() { return percepcoes; }
    public void setPercepcoes(String percepcoes) { this.percepcoes = percepcoes; }
    public String getAcaoEgo() { return acaoEgo; }
    public void setAcaoEgo(String acaoEgo) { this.acaoEgo = acaoEgo; }
    public String getAcaoPersonagem() { return acaoPersonagem; }
    public void setAcaoPersonagem(String acaoPersonagem) { this.acaoPersonagem = acaoPersonagem; }
    public String getAcaoObjeto() { return acaoObjeto; }
    public void setAcaoObjeto(String acaoObjeto) { this.acaoObjeto = acaoObjeto; }
    public String getFormaEgo() { return formaEgo; }
    public void setFormaEgo(String formaEgo) { this.formaEgo = formaEgo; }
    public String getFormaPersonagem() { return formaPersonagem; }
    public void setFormaPersonagem(String formaPersonagem) { this.formaPersonagem = formaPersonagem; }
    public String getFormaObjeto() { return formaObjeto; }
    public void setFormaObjeto(String formaObjeto) { this.formaObjeto = formaObjeto; }
    public String getFormaCenario() { return formaCenario; }
    public void setFormaCenario(String formaCenario) { this.formaCenario = formaCenario; }
    public String getPapelEgo() { return papelEgo; }
    public void setPapelEgo(String papelEgo) { this.papelEgo = papelEgo; }
    public String getLugarCenario() { return lugarCenario; }
    public void setLugarCenario(String lugarCenario) { this.lugarCenario = lugarCenario; }
    public String getTempoCenario() { return tempoCenario; }
    public void setTempoCenario(String tempoCenario) { this.tempoCenario = tempoCenario; }
    public String getSituacaoBizarra() { return situacaoBizarra; }
    public void setSituacaoBizarra(String situacaoBizarra) { this.situacaoBizarra = situacaoBizarra; }
}