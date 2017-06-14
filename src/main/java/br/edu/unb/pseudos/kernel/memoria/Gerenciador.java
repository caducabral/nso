package br.edu.unb.pseudos.kernel.memoria;

import br.edu.unb.pseudos.kernel.processo.Processo;

public class Gerenciador {
    private final Integer T_TEMPO_REAL = 0;
    private final Integer T_USUARIO = 1;
    
    private Memoria usuario;
    private Memoria tempoReal;

    public Gerenciador(Integer tamUsuario, Integer tamTr) {
        this.usuario = new Memoria(T_TEMPO_REAL, tamUsuario);
        this.tempoReal = new Memoria(T_USUARIO, tamTr);
    }
    
    /**
    * Método para verificar se há espaço na memoria de acordo com prioridade do Processo.
    * 
     * @param p Processo que será verificado 
     * @return True se há espaco na memoria ou False se não há espaco na memoria.
    */
    public Boolean verificarMemoria(Processo p) {
        if (p.getPrioridade() == 0 ) {
            return this.tempoReal.getMaiorEspaco() >= p.getBlocosMemoria();
        } else {
            return this.usuario.getMaiorEspaco() >= p.getBlocosMemoria();
        }
    }

    /**
    * Método para varrer memoria e atualizar qual o tamanho maximo que cabe na memoria.
    * 
    */
    private void atualizarMaximo() {
       int count = 0;
       int max = 0;
       int offset = 0;
       for (int i = 0; i < this.tempoReal.getBlocos().size(); i++) {
           if (this.tempoReal.getBlocos().get(i) == 0) {
               count++;
           } else {
               count = 0;
               offset = i;
           }
           if (max <= count) {
               max = count;
           }
       }
       this.tempoReal.setMaiorEspaco(max);
       this.tempoReal.setOffset(offset);
       
       count = 0;
       max = 0;
       offset = 0;
       for (int i = 0; i < this.usuario.getBlocos().size(); i++) {
           if (this.usuario.getBlocos().get(i) == 0) {
               count++;
           } else {
               count = 0;
               offset = i;
           }
           if (max <= count) {
               max = count;
           }
       }
       this.usuario.setMaiorEspaco(max);
       this.usuario.setOffset(offset);
    }
    
    /**
    * Método para liberar memoria de acordo com o tipo do processo (TR ou Usuario)
    * 
     * @param p Processo que será liberado em memoria
    */
    public void liberarMemoria(Processo p) {
        if (p.getPrioridade() == 0) {
            for (int i = p.getOffset(); i < p.getBlocosMemoria() + p.getOffset(); i++) {
                this.tempoReal.getBlocos().set(i, 0);
            }
            this.tempoReal.setOffset(p.getBlocosMemoria() + p.getOffset());
        } else {
            for (int i = p.getOffset(); i < p.getBlocosMemoria() + p.getOffset(); i++) {
                this.usuario.getBlocos().set(i, 0);
            }
            this.usuario.setOffset(p.getBlocosMemoria() + p.getOffset());
        }
        this.atualizarMaximo();
    }
    
    /**
    * Método para alocar memoria de acordo com o tipo do processo (TR ou Usuario)
    * 
     * @param p Processo que será alocado em memoria
    */
    public void alocarMemoria(Processo p) {
        if (p.getPrioridade() == 0) {
            for (int i = this.tempoReal.getOffset(); i < p.getBlocosMemoria() + this.tempoReal.getOffset(); i++) {
                this.tempoReal.getBlocos().set(i, 1);
            }
            p.setOffset(this.tempoReal.getOffset());
        } else {
            for (int i = this.usuario.getOffset(); i < p.getBlocosMemoria() + this.usuario.getOffset(); i++) {
                this.usuario.getBlocos().set(i, 1);
            }
            p.setOffset(this.usuario.getOffset());
        }
        this.atualizarMaximo();
    }

    public Memoria getUsuario() {
        return usuario;
    }
    
    public Memoria getTempoReal() {
        return tempoReal;
    }
    
}
