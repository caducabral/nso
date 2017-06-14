package br.edu.unb.pseudos.kernel.recurso;

import br.edu.unb.pseudos.kernel.processo.Processo;
import java.util.ArrayList;
import java.util.List;

public class Gerenciador {
    private List<Scanner> scanners;
    private List<Impressora> impressoras;
    private List<Modem> modems;
    private List<Sata> satas;

    /**
    *  Método construtor do Gerenciador de Recursos.
    * 
     * @param scanners numero de scanners usadas
     * @param impressoras numero de impressoras usadas
     * @param sata numero de satas usadas
     * @param modems numero de modems usadas
    */
    public Gerenciador(int scanners, int impressoras, int modems, int sata) {
        this.inicializarImpressoras(scanners);
        this.inicializarScanners(impressoras);
        this.inicializarModems(modems);
        this.inicializarSatas(sata);
    }
    
    /**
    *  Método para inicializar as impressoras.
    * 
     * @param qtd quantidade de recursos
    */
    private void inicializarImpressoras(int qtd) {
        this.impressoras = new ArrayList<>();
        for (int i = 0; i < qtd; i++) {
            this.impressoras.add(new Impressora());
        }
    }
    
    /**
    *  Método para inicializar as Scanners.
    * 
     * @param qtd quantidade de recursos
    */
    private void inicializarScanners(int qtd) {
        this.scanners = new ArrayList<>();
        for (int i = 0; i < qtd; i++) {
            this.scanners.add(new Scanner());
        }
    }
    
    /**
    *  Método para inicializar os Modems.
    * 
     * @param qtd quantidade de recursos
    */
    private void inicializarModems(int qtd) {
        this.modems = new ArrayList<>();
        for (int i = 0; i < qtd; i++) {
            this.modems.add(new Modem());
        }
    }
    
    /**
    *  Método para inicializar as satas.
    * 
     * @param qtd quantidade de recursos
    */
    private void inicializarSatas(int qtd) {
        this.satas = new ArrayList<>();
        for (int i = 0; i < qtd; i++) {
            this.satas.add(new Sata());
        }
    }
    
    /**
    *  Método para verificar se os recursos estão livres caso o processo precisa.
    * 
     * @param p processo que será verificado
     * @return True se tem recurso. False se nao
    */
    public Boolean verificarRecursos(Processo p){
        Boolean impressora = true;
        Boolean scanner = true;
        Boolean modem = true;
        Boolean sata = true;
        
        if(p.getImpressora() > 0) {
            impressora = this.verificarImpressora();
        } 
        
        if(p.getImpressora() > 0) {
            scanner = this.verificarScanner();
        }
        
        if(p.getImpressora() > 0) {
            modem = this.verificarModem();
        }
        
        if(p.getImpressora() > 0) {
            sata = this.verificarSata();
        }
        
        return impressora && scanner && modem && sata;
    }
    
    /**
    *  Método para verificar se as impressoras estao livres.
    * 
    * @return True se estiver livre. False se estiver ocupado
    */
    private Boolean verificarImpressora() {
        for(Impressora i : this.impressoras) {
            if(i.getLivre()) {
                return i.getLivre(); 
            }
        }
        return false;
    }
    
    /**
    *  Método para alocar se as impressoras estiverem livres.
    * 
    */
    private void alocarImpressora() {
        for(Impressora i : this.impressoras) {
            if(i.getLivre()) {
                i.setLivre(Boolean.FALSE);
            }
            break;
        }
    }
    
    /**
    *  Método para liberar as impressoras que estavam ocupadas.
    * 
    */
    private void liberarImpressora() {
        for(Impressora i : this.impressoras) {
            if(!i.getLivre()) {
                i.setLivre(Boolean.TRUE);
            }
            break;
        }
    }
    
    /**
    *  Método para verificar se as scanners estao livres.
    * 
    * @return True se estiver livre. False se estiver ocupado
    */
    private Boolean verificarScanner() {
        for(Scanner i : this.scanners) {
            if(i.getLivre()) {
                return i.getLivre(); 
            }
        }
        return false;
    }
    
    /**
    *  Método para alocar se as scanners estiverem livres.
    * 
    */
    private void alocarScanner() {
        for(Scanner i : this.scanners) {
            if(i.getLivre()) {
                i.setLivre(Boolean.FALSE);
            }
            break;
        }
    }
    
    /**
    *  Método para liberar as scanners que estavam ocupadas.
    * 
    */
    private void liberarScanner() {
        for(Scanner i : this.scanners) {
            if(!i.getLivre()) {
                i.setLivre(Boolean.TRUE);
            }
            break;
        }
    }
    
    /**
    *  Método para verificar se as Satas estao livres.
    * 
    * @return True se estiver livre. False se estiver ocupado
    */
    private Boolean verificarSata() {
        for(Sata i : this.satas) {
            if(i.getLivre()) {
                return i.getLivre(); 
            }
        }
        return false;
    }
    
    /**
    *  Método para alocar se as satas estiverem livres.
    * 
    */
    private void alocarSata() {
        for(Sata i : this.satas) {
            if(i.getLivre()) {
                i.setLivre(Boolean.FALSE);
            }
            break;
        }
    }
    
    /**
    *  Método para liberar as satas que estavam ocupadas.
    * 
    */
    private void liberarSata() {
        for(Sata i : this.satas) {
            if(!i.getLivre()) {
                i.setLivre(Boolean.TRUE);
            }
            break;
        }
    }
    
    /**
    *  Método para verificar se os Modems estao livres.
    * 
    * @return True se estiver livre. False se estiver ocupado
    */
    private Boolean verificarModem() {
        for(Modem i : this.modems) {
            if(i.getLivre()) {
                return i.getLivre(); 
            }
        }
        return false;
    }
    
    /**
    *  Método para alocar se os modems estiverem livres.
    * 
    */
    private void alocarModem() {
        for(Modem i : this.modems) {
            if(i.getLivre()) {
                i.setLivre(Boolean.FALSE);
            }
            break;
        }
    }
    
    /**
    *  Método para liberar as modems que estavam ocupadas.
    * 
    */
    private void liberarModem() {
        for(Modem i : this.modems) {
            if(!i.getLivre()) {
                i.setLivre(Boolean.TRUE);
            }
            break;
        }
    }
    
    /**
    *  Método para alocar os recursos do processo.
    * 
    * 
     * @param p processo para alocar os recursos
    */
    public void alocarRecursos(Processo p) {
        if(p.getImpressora()  > 0) {
            this.alocarImpressora();
        }
        if(p.getScanner()  > 0) {
            this.alocarScanner();
        }
        if(p.getModem()  > 0) {
            this.alocarModem();
        }
        if(p.getSata()  > 0) {
            this.alocarSata();
        }
    }
    
    /**
    *  Método para liberar os recursos do processo.
    * 
    * 
     * @param p processo para liberar os recursos
    */
    public void liberarRecursos(Processo p) {
        if(p.getImpressora()  > 0) {
            this.liberarImpressora();
        }
        if(p.getScanner()  > 0) {
            this.liberarScanner();
        }
        if(p.getModem()  > 0) {
            this.liberarModem();
        }
        if(p.getSata()  > 0) {
            this.liberarSata();
        }
    }
}
