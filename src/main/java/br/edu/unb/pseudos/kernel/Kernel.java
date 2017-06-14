package br.edu.unb.pseudos.kernel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import br.edu.unb.pseudos.kernel.processo.Gerenciador;
import br.edu.unb.pseudos.kernel.processo.Processo;

public class Kernel {
    private final String boot;
    private Output output;
    private Processador CPU;
    private br.edu.unb.pseudos.kernel.processo.Gerenciador gerenteProcesso;
    private br.edu.unb.pseudos.kernel.memoria.Gerenciador gerenteMemoria;
    private br.edu.unb.pseudos.kernel.recurso.Gerenciador gerenteRecurso;

    /**
    *  Costrutor default caso nao seja passado nenhum parametro alem do nome do arquivo.
    * 
     * @param arquivo caminho para o arquivo
    */
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
    
    /**
    * Método para ler o arquivo deentrada e transforma-lo em uma lista de processos
    * 
    */
    private void lerArquivo() {
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + this.boot)))) {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.boot))))) {
            this.gerenteProcesso.setProcessos(
                    br.lines().map(this.gerenteProcesso.mapearItens).collect(Collectors.toList())
             );
        } catch(IOException ex ) {
            this.output.mostrar(5, "Arquivo '" + this.boot + "' nao encontrado ou com erros fatais");
            this.output.mostrar(4, "O pseudo.OS sera encerrado");
        }
    }
    
    /**
    * Varre a lista de bloqueados e verifica qual está com os pre-requisitos para ir para a fila de pronto
    * 
    */
    private void escalonarBloqueados() {
        for (int i = 0; i < this.gerenteProcesso.getBloqueados().size(); i++) {
            Processo p = this.gerenteProcesso.getBloqueados().get(i);
            if ( p.getPrioridade() == 0 && (p.getBlocosMemoria() > this.gerenteMemoria.getTempoReal().getTAMANHO())) {
                this.gerenteProcesso.getBloqueados().remove(p);
            } else if (p.getPrioridade() == 0 && (p.getBlocosMemoria() > this.gerenteMemoria.getTempoReal().getTAMANHO())) {
                this.gerenteProcesso.getBloqueados().remove(p);
                this.output.mostrar(5, "PROCESSO " + p.getId() + "CANCELADO POR FALTA DE MEMORIA");
            } else {
                if (this.gerenteRecurso.verificarRecursos(p)
                    && this.gerenteMemoria.verificarMemoria(p)
                    && p.getTempoInicializacao() < this.CPU.getClock()) {
                    this.gerenteProcesso.addFilaPronto(p);
                    this.gerenteProcesso.getBloqueados().remove(p);
                    break;
                }
            }
            
        }
    }

    /**
    * Métodos que inicializa o kernel e roda a logica do programa
    * 
    */
    public void start() {
        this.lerArquivo();
        this.output.mostrar(1, "");
        this.output.mostrar(3, "INICIANDO PROCESSAMENTO");
        this.output.mostrar(1, "");
        
        this.gerenteProcesso.ordenarEspera();
        
        for(int i = 0; i < this.gerenteProcesso.getProcessos().size(); i++){
            Processo p = this.gerenteProcesso.getProcessos().get(i);
            if (this.gerenteRecurso.verificarRecursos(p) 
                    && this.gerenteMemoria.verificarMemoria(p)
                    && p.getTempoInicializacao() < this.CPU.getClock()) {
                this.gerenteProcesso.addFilaPronto(p);
            } else {
                this.gerenteProcesso.addFilaBloqueado(p);
            }
        }
        while (!this.gerenteProcesso.getFim()) {
            this.gerenteProcesso.obterProximo();
            if(this.gerenteProcesso.getProcesso() != null ) {
                this.gerenteRecurso.alocarRecursos(this.gerenteProcesso.getProcesso());
                this.gerenteMemoria.alocarMemoria(this.gerenteProcesso.getProcesso());
                
                this.output.mostrar(0, "dispatcher => \n" + 
                    "PID: " + String.valueOf(this.gerenteProcesso.getProcesso().getId()) + "\n" +
                    "offset: " + String.valueOf(this.gerenteProcesso.getProcesso().getOffset()) + "\n" +
                    "blocks: " + String.valueOf(this.gerenteProcesso.getProcesso().getBlocosMemoria()) + "\n" +
                    "priority: " + String.valueOf(this.gerenteProcesso.getProcesso().getPrioridade()) + "\n" +
                    "time: " + String.valueOf(this.gerenteProcesso.getProcesso().getTempoProcessador()) + "\n" +
                    "printers: " + String.valueOf(this.gerenteProcesso.getProcesso().getImpressora()) + "\n" +
                    "scanners: " + String.valueOf(this.gerenteProcesso.getProcesso().getScanner()) + "\n" +
                    "modems: " + String.valueOf(this.gerenteProcesso.getProcesso().getModem()) + "\n" +
                    "drives: " + String.valueOf(this.gerenteProcesso.getProcesso().getSata())
                );
                
                this.gerenteProcesso.getProcesso().setProcessando(true);
                this.CPU.setInicioProcessamento();

                int count = 1;
                while (this.gerenteProcesso.getProcesso().getProcessando()) {
                    this.CPU.processar(this.gerenteProcesso.getProcesso());
                    this.output.mostrar(0, "Instrução: " + count);
                    count++;
                }
                
                this.output.mostrar(0, "return SIGINT \n");

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
        }
        
        this.output.mostrar(1, "");
        this.output.mostrar(3, "FIM DO PROCESSAMENTO");
    }
    
    
}
