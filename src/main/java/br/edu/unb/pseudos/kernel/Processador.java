/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unb.pseudos.kernel;

import br.edu.unb.pseudos.kernel.processo.Processo;

/**
 *
 * @author Cadu
 */
public class Processador {
    private Integer clock;
    private Boolean ocupado;
    private Integer inicio;

    public Processador() {
        this.clock = 1;
        this.ocupado = false;
        this.inicio = -1;
    }
    
    public Integer getClock() {
        return this.clock;
    }
    
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
    
    public void setInicioProcessamento() {
        this.inicio = this.clock;
    }
    
    public void iterar() {
        this.clock++;
    }
}
