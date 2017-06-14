/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unb.pseudos.kernel.recurso;

import br.edu.unb.pseudos.kernel.processo.Processo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cadu
 */
public class Gerenciador {
    private List<Scanner> scanners;
    private List<Impressora> impressoras;
    private List<Modem> modems;
    private List<Sata> satas;

    public Gerenciador(int scanners, int impressoras, int modems, int sata) {
        this.inicializarImpressoras(scanners);
        this.inicializarScanners(impressoras);
        this.inicializarModems(modems);
        this.inicializarSatas(sata);
    }
    
    private void inicializarImpressoras(int qtd) {
        this.impressoras = new ArrayList<>();
        for (int i = 0; i < qtd; i++) {
            this.impressoras.add(new Impressora());
        }
    }
    
    private void inicializarScanners(int qtd) {
        this.scanners = new ArrayList<>();
        for (int i = 0; i < qtd; i++) {
            this.scanners.add(new Scanner());
        }
    }
    
    private void inicializarModems(int qtd) {
        this.modems = new ArrayList<>();
        for (int i = 0; i < qtd; i++) {
            this.modems.add(new Modem());
        }
    }
    
    private void inicializarSatas(int qtd) {
        this.satas = new ArrayList<>();
        for (int i = 0; i < qtd; i++) {
            this.satas.add(new Sata());
        }
    }
    
    
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
    
    private Boolean verificarImpressora() {
        for(Impressora i : this.impressoras) {
            if(i.getLivre()) {
                return i.getLivre(); 
            }
        }
        return false;
    }
    
    private void alocarImpressora() {
        for(Impressora i : this.impressoras) {
            if(i.getLivre()) {
                i.setLivre(Boolean.FALSE);
            }
            break;
        }
    }
    
    private void liberarImpressora() {
        for(Impressora i : this.impressoras) {
            if(!i.getLivre()) {
                i.setLivre(Boolean.TRUE);
            }
            break;
        }
    }
    
    private Boolean verificarScanner() {
        for(Scanner i : this.scanners) {
            if(i.getLivre()) {
                return i.getLivre(); 
            }
        }
        return false;
    }
    
    private void alocarScanner() {
        for(Scanner i : this.scanners) {
            if(i.getLivre()) {
                i.setLivre(Boolean.FALSE);
            }
            break;
        }
    }
    
    private void liberarScanner() {
        for(Scanner i : this.scanners) {
            if(!i.getLivre()) {
                i.setLivre(Boolean.TRUE);
            }
            break;
        }
    }
    
    private Boolean verificarSata() {
        for(Sata i : this.satas) {
            if(i.getLivre()) {
                return i.getLivre(); 
            }
        }
        return false;
    }
    
    private void alocarSata() {
        for(Sata i : this.satas) {
            if(i.getLivre()) {
                i.setLivre(Boolean.FALSE);
            }
            break;
        }
    }
    
    private void liberarSata() {
        for(Sata i : this.satas) {
            if(!i.getLivre()) {
                i.setLivre(Boolean.TRUE);
            }
            break;
        }
    }
    
    private Boolean verificarModem() {
        for(Modem i : this.modems) {
            if(i.getLivre()) {
                return i.getLivre(); 
            }
        }
        return false;
    }
    
    private void alocarModem() {
        for(Modem i : this.modems) {
            if(i.getLivre()) {
                i.setLivre(Boolean.FALSE);
            }
            break;
        }
    }
    
    private void liberarModem() {
        for(Modem i : this.modems) {
            if(!i.getLivre()) {
                i.setLivre(Boolean.TRUE);
            }
            break;
        }
    }
    
    
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
