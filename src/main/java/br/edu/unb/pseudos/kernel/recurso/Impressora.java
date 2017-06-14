/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unb.pseudos.kernel.recurso;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Cadu
 */
public class Impressora extends RecursoGenerico{
    private final Integer id;

    public Impressora() {
        this.id = ThreadLocalRandom.current().nextInt(0, 101);
        this.livre = true;
    }

    public Integer getId() {
        return id;
    }
}

