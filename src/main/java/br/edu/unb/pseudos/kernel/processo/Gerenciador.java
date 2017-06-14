package br.edu.unb.pseudos.kernel.processo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class Gerenciador {
    private final Integer MAX_PID;
    private Integer pid;
    private List<Processo> processos; 
    private List<Processo> bloqueados;
    private List<Processo> prontosTR;
    private List<Processo> prioridade1; 
    private List<Processo> prioridade2; 
    private List<Processo> prioridade3; 
    
    private Processo processo; 
    
    private Boolean fim;
    
    /**
    * Método Construtor do gerenciador de Processo.
    * 
     * @param maxProcessos numero maximo de processos que podem ser criados
    */
    public Gerenciador(Integer maxProcessos) {
        this.MAX_PID = maxProcessos;
        this.pid = 0;
        this.processos = new ArrayList<>(); 
        this.prontosTR = new ArrayList<>(); 
        this.bloqueados = new ArrayList<>(); 
        this.prioridade1 = new ArrayList<>(); 
        this.prioridade2 = new ArrayList<>(); 
        this.prioridade3 = new ArrayList<>();
        this.fim = false;
    }

    public List<Processo> getProcessos() {
        return processos;
    }

    public void setProcessos(List<Processo> processos) {
        this.processos = processos;
        
    }

    public List<Processo> getProntosTR() {
        return prontosTR;
    }

    public void setProntosTR(List<Processo> prontosTR) {
        this.prontosTR = prontosTR;
    }

    public List<Processo> getBloqueados() {
        return bloqueados;
    }

    public void setBloqueados(List<Processo> bloqueados) {
        this.bloqueados = bloqueados;
    }

    public List<Processo> getPrioridade1() {
        return prioridade1;
    }

    public void setPrioridade1(List<Processo> prioridade1) {
        this.prioridade1 = prioridade1;
    }

    public List<Processo> getPrioridade2() {
        return prioridade2;
    }

    public void setPrioridade2(List<Processo> prioridade2) {
        this.prioridade2 = prioridade2;
    }

    public List<Processo> getPrioridade3() {
        return prioridade3;
    }

    public void setPrioridade3(List<Processo> prioridade3) {
        this.prioridade3 = prioridade3;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Boolean getFim() {
        return fim;
    }

    public void setFim(Boolean fim) {
        this.fim = fim;
    }
    
    public Processo criarProcesso(){
        return new Processo(this.criarPID());
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }
    
    /**
    * Retorna um ID para o Processo.
    * 
    * @return Integer numero do PID
    */
    private Integer criarPID(){
        return this.pid++;
    }
    
    /**
    *  Transforma o item do arquivo em um objeto Processo
    * 
    */
    public Function<String, Processo> mapearItens = (String line) -> {
        String[] pTemp = line.split(",");// a CSV has comma separated lines
        Processo p = criarProcesso();
        if (pTemp[0] != null && pTemp[0].trim().length() > 0 
                || pTemp[0] != null && pTemp[0].trim().length() > 0
                || pTemp[0] != null && pTemp[0].trim().length() > 0
                || pTemp[0] != null && pTemp[0].trim().length() > 0
                || pTemp[0] != null && pTemp[0].trim().length() > 0
                || pTemp[0] != null && pTemp[0].trim().length() > 0
                || pTemp[0] != null && pTemp[0].trim().length() > 0
                || pTemp[0] != null && pTemp[0].trim().length() > 0 ) {
            p.setTempoInicializacao(Integer.valueOf(pTemp[0].trim()));
            p.setPrioridade(Integer.valueOf(pTemp[1].trim()));
            p.setTempoProcessador(Integer.valueOf(pTemp[2].trim()));
            p.setBlocosMemoria(Integer.valueOf(pTemp[3].trim()));
            p.setImpressora(Integer.valueOf(pTemp[4].trim()));
            p.setScanner(Integer.valueOf(pTemp[5].trim()));
            p.setModem(Integer.valueOf(pTemp[6].trim()));
            p.setSata(Integer.valueOf(pTemp[7].trim()));
        }
        return p;
    };
    
    /**
    *  Ordena lista de processos que foram lidos do arquivo
    * 
    */
    public void ordenarEspera() {
        Collections.sort(this.processos, (Processo p1, Processo p2) -> p1.getTempoInicializacao().compareTo(p2.getTempoInicializacao()));
    }
    
    /**
    *  Obtem proximo processo da lista de processos lidos do arquivo
    * 
    */
    public void escalonarEspera() {
        this.processo = null;
        if(this.processos.size() > 0) {
            this.processo = this.processos.get(0);
            this.processos.remove(this.processo);
        }
    }
    
    /**
    *  Adiciona processo para a sua respectiva fila de processo.
    * 
     * @param p processo a ser distribuido
    */
    public void addFilaPronto(Processo p){
        switch (p.getPrioridade()){
            case 0:
                this.prontosTR.add(p);
                break;
            case 1:
                this.prioridade1.add(p);
                break;
            case 2:
                this.prioridade2.add(p);
                break;
            default:
                this.prioridade3.add(p);
        }
    }
    
    /**
    *  Adiciona processo para a sua respectiva fila de processo.
    * 
    */
    public void addFilaPronto(){
        switch (this.processo.getPrioridade()){
            case 0:
                this.prontosTR.add(this.processo);
                break;
            case 1:
                this.prioridade1.add(this.processo);
                break;
            case 2:
                this.prioridade2.add(this.processo);
                break;
            default:
                this.prioridade3.add(this.processo);
        }
    }
    
    public void addFilaBloqueado(Processo p){
        this.bloqueados.add(p);
    }
    
    public void addFilaBloqueado(){
        this.bloqueados.add(this.processo);
    }
    
    /**
    *  Atualiza idade dos processos.
    * 
    */
    public void atualizarPiroridade() {
        for (int i = 0; i < this.prioridade3.size(); i++) {
            this.prioridade3.get(i).envelhecer();
        }
        for (int i = 0; i < this.prioridade2.size(); i++) {
            this.prioridade2.get(i).envelhecer();
        }
        for (int i = 0; i < this.prioridade1.size(); i++) {
            this.prioridade1.get(i).envelhecer();
        }
    }
    
    /**
    *  Ordena por idade dos processos.
    * 
    */
    public void escalonarProntos() {
        this.atualizarPiroridade();
        
        if (this.prioridade3.size() > 0) {
            Collections.sort(this.prioridade3, Collections.reverseOrder((Processo p1, Processo p2) -> p1.getAging().compareTo(p2.getAging())));
        }
        if (this.prioridade2.size() > 0) {
            Collections.sort(this.prioridade2, Collections.reverseOrder((Processo p1, Processo p2) -> p1.getAging().compareTo(p2.getAging())));
        }
        if (this.prioridade1.size() > 0) {
            Collections.sort(this.prioridade1, Collections.reverseOrder((Processo p1, Processo p2) -> p1.getAging().compareTo(p2.getAging())));
        }
    }
    
    /**
    *  Métodos para definir o proximo processo pronto.
    * 
    */
    public void obterProximo() {
        this.processo = null;
        if (this.prontosTR.size() > 0 ) {
            this.processo = this.prontosTR.get(0);
            this.prontosTR.remove(this.processo);
        } else if (this.prioridade1.size() > 0 ) {
            this.processo = this.prioridade1.get(0);
            this.prioridade1.remove(this.processo);
        } else if (this.prioridade2.size() > 0 ) {
            this.processo = this.prioridade2.get(0);
            this.prioridade2.remove(this.processo);
        } else if (this.prioridade3.size() > 0 ) {
            this.processo = this.prioridade3.get(0);
            this.prioridade3.remove(this.processo);
        }
    }
    
}
