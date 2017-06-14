/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unb.pseudos.kernel.processo;

/**
 *
 * @author Cadu
 */
public class Processo {
    private Integer id;
    private Integer tempoInicializacao;
    private Integer prioridade;
    private Integer tempoProcessador;
    private Integer blocosMemoria;
    private Integer impressora;
    private Integer scanner;
    private Integer modem;
    private Integer sata;
    
    private Integer offset;
    
    private Integer aging;
    private Boolean processando;

    public Processo(Integer id) {
        this.id = id;
        this.offset = 0;
    }
    
    public Processo getProcesso() {
        return this;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTempoInicializacao() {
        return tempoInicializacao;
    }

    public void setTempoInicializacao(Integer tempoInicializacao) {
        this.tempoInicializacao = tempoInicializacao;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public Integer getTempoProcessador() {
        return tempoProcessador;
    }

    public void setTempoProcessador(Integer tempoProcessador) {
        this.tempoProcessador = tempoProcessador;
    }

    public Integer getBlocosMemoria() {
        return blocosMemoria;
    }

    public void setBlocosMemoria(Integer blocosMemoria) {
        this.blocosMemoria = blocosMemoria;
    }

    public Integer getImpressora() {
        return impressora;
    }

    public void setImpressora(Integer impressora) {
        this.impressora = impressora;
    }

    public Integer getScanner() {
        return scanner;
    }

    public void setScanner(Integer scanner) {
        this.scanner = scanner;
    }

    public Integer getModem() {
        return modem;
    }

    public void setModem(Integer modem) {
        this.modem = modem;
    }

    public Integer getSata() {
        return sata;
    }

    public void setSata(Integer sata) {
        this.sata = sata;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getAging() {
        return aging;
    }

    public void setAging(Integer aging) {
        this.aging = aging;
    }

    public Boolean getProcessando() {
        return processando;
    }

    public void setProcessando(Boolean processando) {
        this.processando = processando;
    }
    
    public void envelhecer() {
        this.aging++;
    }
}
