package br.edu.unb.pseudos.kernel;

import br.edu.unb.pseudos.kernel.processo.Processo;

public class Processador {
    private Integer clock;
    private Boolean ocupado;
    private Integer inicio;

    /**
    * Método construtor do Processador.
    * 
    */
    public Processador() {
        this.clock = 1;
        this.ocupado = false;
        this.inicio = -1;
    }
    
    public Integer getClock() {
        return this.clock;
    }
    
    /**
    * Método para processar instruçoes do Processo. 
    * 
     * @param p Processo que será processado
    */
    public void processar (Processo p) {
        this.iterar();
        if (p.getTempoProcessador() <= (this.clock - this.inicio) ) {
            p.setProcessando(false);
        }
    }

    public Boolean getOcupado() {
        return ocupado;
    }

    public void setOcupado(Boolean ocupado) {
        this.ocupado = ocupado;
    }
    
    /**
    * Método para marcar o inicio do processamento do processo.
    * 
    */
    public void setInicioProcessamento() {
        this.inicio = this.clock;
    }
    
    /**
    * Método para contar incrementar o clock do processador.
    * 
    */
    public void iterar() {
        this.clock++;
    }
}
