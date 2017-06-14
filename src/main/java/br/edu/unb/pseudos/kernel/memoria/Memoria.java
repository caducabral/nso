/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unb.pseudos.kernel.memoria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Cadu
 */
public class Memoria {
    private final Integer TIPO;
    private final Integer TAMANHO;
    private Integer offset;
    private Integer maiorEspaco; 
    
    private List<Integer> blocos;
    
    public Memoria(
            Integer tipo,
            Integer tamanho
    ) {
        this.TIPO = tipo;
        this.TAMANHO = tamanho;
        this.offset = 0;
        this.maiorEspaco = this.TAMANHO;
        this.blocos = new ArrayList<>(Arrays.asList(new Integer[this.TAMANHO]));
        Collections.fill(this.blocos, 0);
    }

    public Integer getMaiorEspaco() {
        return maiorEspaco;
    }

    public void setMaiorEspaco(Integer maiorEspaco) {
        this.maiorEspaco = maiorEspaco;
    }

    public List<Integer> getBlocos() {
        return blocos;
    }

    public void setBlocos(List<Integer> blocos) {
        this.blocos = blocos;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
    
    public void zerarMemoria() {
        Collections.fill(this.blocos, 0);
    }
    
    public void atualizarOffset(Integer tamAlocacao) {
        this.offset = this.offset - tamAlocacao;
    }
}
