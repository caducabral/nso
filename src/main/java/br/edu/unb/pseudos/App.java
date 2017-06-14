/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unb.pseudos;

import br.edu.unb.pseudos.kernel.Kernel;
import br.edu.unb.pseudos.kernel.Output;

/**
 *
 * @author Cadu
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Output output = new Output();
        
        Integer MEMORIA = 1024;
        Integer MEMORIA_SYS = 64; 
        Integer PROCESSADOR = 1;
        Integer SCANNER = 1;
        Integer MODEM = 1;
        Integer SATA = 2;
        Integer IMPRESSORA = 2;
        Integer MAX_PROCESSOS = 1000;
        String arquivo = "processos2.txt";

        output.mostrar(2, "INFORMAÇÕES DE HARDWARE");
        output.mostrar(2, "Numero de processadores: " + PROCESSADOR.toString() );
        output.mostrar(2, "Memoria Total: " + MEMORIA.toString() );
        output.mostrar(2, "Memoria Usuário: " + MEMORIA_SYS.toString() );
        output.mostrar(2, "Scanner: " + SCANNER.toString() );
        output.mostrar(2, "Impressora: " + IMPRESSORA.toString() );
        output.mostrar(2, "Modem: " + MODEM.toString() );
        output.mostrar(2, "SATA: " + SATA.toString() );
        
        
        
        output.mostrar(0, ".______     _______. _______  __    __   _______   ______        ______        _______.\n" +
"|   _  \\   /       ||   ____||  |  |  | |       \\ /  __  \\      /  __  \\      /       |\n" +
"|  |_)  | |   (----`|  |__   |  |  |  | |  .--.  |  |  |  |    |  |  |  |    |   (----`\n" +
"|   ___/   \\   \\    |   __|  |  |  |  | |  |  |  |  |  |  |    |  |  |  |     \\   \\    \n" +
"|  |   .----)   |   |  |____ |  `--'  | |  '--'  |  `--'  |  __|  `--'  | .----)   |   \n" +
"| _|   |_______/    |_______| \\______/  |_______/ \\______/  (__)\\______/  |_______/    \n");
        
        
        
        output.mostrar(1, "");
        output.mostrar(2, "PSEUDO.OS v1.0");
        
        Kernel kernel = new Kernel(
            output,
            arquivo, 
            PROCESSADOR, 
            MAX_PROCESSOS, 
            MEMORIA - MEMORIA_SYS, 
            MEMORIA_SYS, 
            SCANNER, 
            IMPRESSORA, 
            MODEM, 
            SATA
        );
        
        kernel.start();
        
        output.mostrar(1, "");
        output.mostrar(0, "ESTATISTICAS DO SISTEMA");
        output.getEstatisticas();
        output.mostrar(1, "");
        
        output.mostrar(3, "Obrigado por usar o PSEUDO.OS v1.0");
        //TODO: Inicializar o Kernel;
        
        
        
        
        
        //TODO Iniciar a memoria;
        //TODO iniciar os recursos;
        //TODO Ler o Arquivo de Entrada;
        //
        
    }
    
}
