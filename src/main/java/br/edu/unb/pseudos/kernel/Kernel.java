/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unb.pseudos.kernel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import br.edu.unb.pseudos.kernel.processo.Gerenciador;
import br.edu.unb.pseudos.kernel.processo.Processo;

/**
 *
 * @author Cadu
 */
public class Kernel {
    private final String boot;
    private Output output;
    private Processador CPU;
    private br.edu.unb.pseudos.kernel.processo.Gerenciador gerenteProcesso;
    private br.edu.unb.pseudos.kernel.memoria.Gerenciador gerenteMemoria;
    private br.edu.unb.pseudos.kernel.recurso.Gerenciador gerenteRecurso;

    /* Construtor default */
    public Kernel(String arquivo) {
        this.output = new Output();
        this.CPU = new Processador();
        this.gerenteProcesso = new br.edu.unb.pseudos.kernel.processo.Gerenciador(1000);
        this.gerenteMemoria = new br.edu.unb.pseudos.kernel.memoria.Gerenciador(1024, 64);
        this.gerenteRecurso = new br.edu.unb.pseudos.kernel.recurso.Gerenciador(1, 2, 1, 2);
        this.boot = arquivo;
        this.lerArquivo();
    }
    
    /**
    * Método construtor do kernel com informacoes passadas por parametro
    * 
     * @param output estado do objeto de saida para usuario 
     * @param arquivo nome do arquivo com processos
     * @param processadores quantidade de processadores
     * @param maxProcessos quantidade maxima de processadores
     * @param memoria quantidade de memorias
     * @param memoriaSys tamanho da memoria para o processos de tempo real
     * @param modem quantidade de processadores
     * @param impressora quantidade de impressoras
     * @param scanner quantidade de scanners
     * @param sata quantidade de satas
    */
    public Kernel(Output output, String arquivo, Integer processadores, Integer maxProcessos, Integer memoria, Integer memoriaSys ,Integer scanner, Integer impressora, Integer modem, Integer sata) {
        this.output = output;
        this.output.mostrar(3, "Inicializando o kernel ... ");
        this.CPU = new Processador();
        this.output.mostrar(2, "Gerenciador de Processos Iniciado com Sucesso! ");
        this.gerenteProcesso = new br.edu.unb.pseudos.kernel.processo.Gerenciador(maxProcessos);
        this.output.mostrar(2, "Gerenciador de Processos Iniciado com Sucesso! ");
        this.gerenteMemoria = new br.edu.unb.pseudos.kernel.memoria.Gerenciador(memoria, memoriaSys);
        this.output.mostrar(2, "Gerenciador de Memória Iniciado com Sucesso! ");
        this.gerenteRecurso = new br.edu.unb.pseudos.kernel.recurso.Gerenciador(scanner, impressora, modem, sata);
        this.output.mostrar(2, "Gerenciador de Recursos Iniciado com Sucesso! ");
        //this.processador = new Processador();
        //this.output.mostrar(2, "Processador está disponível para uso! ");
        this.boot = arquivo;
    }

    public Gerenciador getGerenteProcesso() {
        return gerenteProcesso;
    }

    public void setGerenteProcesso(Gerenciador gerenteProcesso) {
        this.gerenteProcesso = gerenteProcesso;
    }

    public br.edu.unb.pseudos.kernel.memoria.Gerenciador getGerenteMemoria() {
        return gerenteMemoria;
    }

    public void setGerenteMemoria(br.edu.unb.pseudos.kernel.memoria.Gerenciador gerenteMemoria) {
        this.gerenteMemoria = gerenteMemoria;
    }

    public br.edu.unb.pseudos.kernel.recurso.Gerenciador getGerenteRecurso() {
        return gerenteRecurso;
    }

    public void setGerenteRecurso(br.edu.unb.pseudos.kernel.recurso.Gerenciador gerenteRecurso) {
        this.gerenteRecurso = gerenteRecurso;
    }

    private void lerArquivo() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + this.boot)))) {
            this.gerenteProcesso.setProcessos(
                    br.lines().map(this.gerenteProcesso.mapearItens).collect(Collectors.toList())
             );
        } catch(IOException ex ) {
            this.output.mostrar(5, "Arquivo '" + this.boot + "' nao encontrado ou com erros fatais");
            this.output.mostrar(4, "O pseudo.OS sera encerrado");
        }
    }
    
    private void escalonarBloqueados() {
        for (int i = 0; i < this.gerenteProcesso.getBloqueados().size(); i++) {
            Processo p = this.gerenteProcesso.getBloqueados().get(i);
            if (this.gerenteRecurso.verificarRecursos(p)
                    && this.gerenteMemoria.verificarMemoria(p)
                    && p.getTempoInicializacao() < this.CPU.getClock()) {
                this.gerenteProcesso.addFilaPronto(p);
                this.gerenteProcesso.getBloqueados().remove(p);
                break;
            }
        }
    }

    public void start() {
        this.lerArquivo();
        this.output.mostrar(1, "");
        this.output.mostrar(3, "INICIANDO PROCESSAMENTO");
        this.output.mostrar(1, "");
        
        this.gerenteProcesso.setCPU(this.CPU);
        
        //ORDENA PELO TEMPO INICIALIZACAO
        this.gerenteProcesso.ordenarEspera();
        
        for(int i = 0; i < this.gerenteProcesso.getProcessos().size(); i++){
//            this.gerenteProcesso.escalonarEspera();
            Processo p = this.gerenteProcesso.getProcessos().get(i);
            if (this.gerenteRecurso.verificarRecursos(p) 
                    && this.gerenteMemoria.verificarMemoria(p)
                    && p.getTempoInicializacao() < this.CPU.getClock()) {
                this.gerenteProcesso.addFilaPronto(p);
            } else {
                this.gerenteProcesso.addFilaBloqueado(p);
            }
//                this.gerenteProcesso.getProcessos().remove(this.gerenteProcesso.getProcesso());
        }
        //ENQUANTO NAO FOR FIM
        while (!this.gerenteProcesso.getFim()) {
            //if (!this.CPU.getOcupado()) {
            this.gerenteProcesso.obterProximo();
            if(this.gerenteProcesso.getProcesso() != null ) {
                this.output.mostrar(0, "dispatcher => \n" + 
                    "PID: " + String.valueOf(this.gerenteProcesso.getProcesso().getId()) + "\n" +
                    "offset: " + String.valueOf(this.gerenteProcesso.getProcesso().getOffset()) + "\n" +
                    "blocks: " + String.valueOf(this.gerenteProcesso.getProcesso().getBlocosMemoria()) + "\n" +
                    "priority: " + String.valueOf(this.gerenteProcesso.getProcesso().getPrioridade()) + "\n" +
                    "time: " + String.valueOf(this.gerenteProcesso.getProcesso().getTempoProcessador()) + "\n" +
                    "printers: " + String.valueOf(this.gerenteProcesso.getProcesso().getImpressora()) + "\n" +
                    "scanners: " + String.valueOf(this.gerenteProcesso.getProcesso().getScanner()) + "\n" +
                    "modems: " + String.valueOf(this.gerenteProcesso.getProcesso().getModem()) + "\n" +
                    "drives: " + String.valueOf(this.gerenteProcesso.getProcesso().getSata()) + "\n"
                );
                this.gerenteRecurso.alocarRecursos(this.gerenteProcesso.getProcesso());
                this.gerenteMemoria.alocarMemoria(this.gerenteProcesso.getProcesso());
                this.gerenteProcesso.getProcesso().setProcessando(true);
                this.CPU.setInicioProcessamento();

                int count = 1;
                while (this.gerenteProcesso.getProcesso().getProcessando()) {
                    this.CPU.processar(this.gerenteProcesso.getProcesso());
                    this.output.mostrar(0, "Instrução: " + count);
                    count++;
                }

                this.gerenteMemoria.liberarMemoria(this.gerenteProcesso.getProcesso());
                this.gerenteRecurso.liberarRecursos(this.gerenteProcesso.getProcesso());
                this.escalonarBloqueados();
                this.gerenteProcesso.escalonarProntos();
            } else if(this.gerenteProcesso.getBloqueados().size() > 0) {
                this.escalonarBloqueados();
                this.CPU.iterar();
            } else {
                this.gerenteProcesso.setFim(Boolean.TRUE);
            }
                
                
            //}
            /*****
                //AQUI DEVE VERIFICAR SE O PROCESSO É DE TEMPO REAL OU NAO PARA PODER HAVER PREEMPÇAO
            ******/
                      
//            if((this.CPU.getFim() < this.CPU.getClock())) {
//                this.gerenteMemoria.liberarMemoria(this.gerenteProcesso.getProcesso());
//                this.gerenteRecurso.liberarRecursos(this.gerenteProcesso.getProcesso());
//            }

            
        }
        
        this.output.mostrar(1, "");
        this.output.mostrar(3, "FIM DO PROCESSAMENTO");
    }
    
    
}
