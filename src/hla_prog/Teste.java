/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hla_prog;

import hla_prog.MakeRef.CriarRef;
import hla_prog.MakeRef.LeitorDados;
import hla_prog.MakeRef.Ref;
import java.io.File;
import javax.swing.JTextArea;

/**
 *
 * @author rafael.veiga
 */
public class Teste {

    public static void main(String args[]) {
        // gerar ref
        File pasta = new File("data");
        File arqs[] = pasta.listFiles();
//        for (File arq : arqs) {
//            CriarRef criador = new CriarRef(arq);
//            Ref ref2 = new Ref(criador);
//            Leitor.save(ref2);
//        }

        // criador.createFasta("C:/Users/rafael.veiga/Documents/NetBeansProjects/saida/");
         File arqPed = new File("C:/Users/rafael.veiga/Documents/NetBeansProjects/All_Harmonizado5M_chr6Out.ped");
          File arqMap = new File("C:/Users/rafael.veiga/Documents/NetBeansProjects/All_Harmonizado5M_chr6Out.map");
        // File arqPed = new File("C:/Users/rafael.veiga/Documents/NetBeansProjects/All_5M_chr6.ped");
        // File arqMap = new File("C:/Users/rafael.veiga/Documents/NetBeansProjects/All_5M_chr6.map");
         Ref ref = LeitorDados.load(arqs[0].getName());
         StringBuilder saidaTex = new StringBuilder();
         javax.swing.JTextArea janela_saida = new JTextArea();
         Banco b = new Banco(arqPed, arqMap, ref, janela_saida, saidaTex);
         b.execute();
         b.printDebug("C:/Users/rafael.veiga/Documents/NetBeansProjects/saida.csv",1);
         System.err.println("C:/Users/rafael.veiga/Documents/NetBeansProjects/saida.csv");
    }

}
