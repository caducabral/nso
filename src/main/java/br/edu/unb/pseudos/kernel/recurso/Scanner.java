package br.edu.unb.pseudos.kernel.recurso;

import java.util.concurrent.ThreadLocalRandom;

public class Scanner extends RecursoGenerico{
    private final Integer id;

    /**
    *  Método construtor do Scanner.
    * 
    */
    public Scanner() {
        this.id = ThreadLocalRandom.current().nextInt(0, 101);
        this.livre = true;
    }

    public Integer getId() {
        return id;
    }
}

