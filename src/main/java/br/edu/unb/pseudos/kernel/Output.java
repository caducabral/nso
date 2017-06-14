package br.edu.unb.pseudos.kernel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Output {
    private static final String INFO = ">> [INFO] ";
    private static final String ERRO = ">> [ERRO] ";
    private static final String ATENCAO = ">> [ATNC] ";
    private static final String SISTEMA = ">> [SYST] ";
    private static final String DEFAULT = ">> "; 
    private static final String SEPARADOR = ">> --------------------- ";
    private static final Integer qtdTipo = 6;
    
    private List<Integer> chamadas;
           
    /**
    *  Metodo construtor da tela de saida do projeto
    * 
    */
    public Output() {
        this.chamadas = new ArrayList<>(Arrays.asList(new Integer[this.qtdTipo]));
        Collections.fill(this.chamadas, 0);
    }
    
    /**
    * Método para obter o texto de output da tela com o tipo certo. \n
    * - 0 : DEFAULT \n
    * - 1 : SEPARADOR \n
    * - 2 : SISTEMA \n
    * - 3 : INFO \n
    * - 4 : ATENCAO \n
    * - 5 : ERRO \n
    * 
    * @param tipo Tipo da chamada na tela 
    * @param msg Texto para mostrar na tela
    */
    public void mostrar(Integer tipo, String msg){
        System.out.println(this.getTipo(tipo) + msg);
    }
    
    /**
    *  Obtem tipo de mensagem da Telas
    * 
    */
    private String getTipo(Integer tipo){
        switch (tipo) {
            case 0: 
                this.chamadas.set(0, this.chamadas.get(0) + 1);
                return Output.DEFAULT;
            case 1:
                this.chamadas.set(1, this.chamadas.get(1) + 1);
                return Output.SEPARADOR;
            case 2:
                this.chamadas.set(2, this.chamadas.get(2) + 1);
                return Output.SISTEMA;
            case 3:
                this.chamadas.set(3, this.chamadas.get(3) + 1);
                return Output.INFO;
            case 4:
                this.chamadas.set(4, this.chamadas.get(4) + 1);
                return Output.ATENCAO;
            case 5:
                this.chamadas.set(5, this.chamadas.get(5) + 1);
            return Output.ERRO;
            default:
                this.chamadas.set(0, this.chamadas.get(0) + 1);
                return Output.DEFAULT;
        }
    }
    
    /**
    *  Obtem estatisticas da execucao do projeto
    * 
    */
    public void getEstatisticas() {
        
        this.mostrar(0, Output.SISTEMA + this.chamadas.get(2));
        this.mostrar(0, Output.INFO + this.chamadas.get(3));
        this.mostrar(0, Output.ATENCAO + this.chamadas.get(4));
        this.mostrar(0, Output.ERRO + this.chamadas.get(5));
    }
}
