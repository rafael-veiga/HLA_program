/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hla_prog;

import hla_prog.MakeRef.Leitor;
import hla_prog.MakeRef.Ref;
import java.io.File;
import javax.swing.JTextArea;

/**
 *
 * @author rafael.veiga
 */
public class Teste {
    
    public static void main(String args[]){
        File arqPed = new File("C:/Users/rafael.veiga/Documents/NetBeansProjects/All_Harmonizado5M_chr6Out.ped");
        File arqMap = new File("C:/Users/rafael.veiga/Documents/NetBeansProjects/All_Harmonizado5M_chr6Out.map");
        Ref ref = Leitor.load();
        StringBuilder saidaTex = new StringBuilder();
        javax.swing.JTextArea janela_saida = new JTextArea();
        Banco b = new Banco(arqPed, arqMap, ref,janela_saida,saidaTex);
        b.execute();
        b.printDebug("C:/Users/rafael.veiga/Documents/NetBeansProjects/saida.csv");
       // System.err.println("C:/Users/rafael.veiga/Documents/NetBeansProjects/saida.csv");
    }
    
}
