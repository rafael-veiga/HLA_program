/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hla_prog;

import hla_prog.MakeRef.Ref;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author rafael.veiga
 */
public class Banco {

    // private int pos[];  //[snps]
    private String id[]; //[ind]
    private Gene genes[];
    //private char seq[][][];// [2][ind][snps]
    private Ref ref;
    private Pheno fenotipo[][]; //[ind][gene]
    private JTextArea janela_saida;
    private StringBuilder saidaTex;

    public Banco(File arqPed, File arqMap, Ref ref, JTextArea janela_saida, StringBuilder saidaTex) {
        this.ref = ref;
        this.janela_saida = janela_saida;
        this.saidaTex = saidaTex;
        try {
            BufferedReader ler = new BufferedReader(new FileReader(arqMap));
            String linha = ler.readLine();
            ArrayList<Integer> posLista = new ArrayList();
            int a;
            String aux[] = linha.split("[\\s]");
            a = Integer.parseInt(aux[3]);
            if (!aux[0].matches("6")) {
                System.err.println("wrong Chomossome!!! most be 6!!!");
                System.exit(1);
            }
            posLista.add(a);
            // long startTime = System.currentTimeMillis();
            linha = ler.readLine();
            while (linha != null) {
                aux = linha.split("[\\s]");
                
                posLista.add(Integer.parseInt(aux[3]));
                linha = ler.readLine();
            }
            ArrayList<String> idList = new ArrayList();
            ArrayList<Character[]> seqList[] = new ArrayList[2];
            seqList[0] = new ArrayList();
            seqList[1] = new ArrayList();

            // long estimatedTime = System.currentTimeMillis() - startTime;
            ler.close();
//            this.pos = new int[posLista.size()];
//            for (int i = 0; i < posLista.size(); i++) {
//                this.pos[i] = posLista.get(i);
//            }

            //ped
            ler = new BufferedReader(new FileReader(arqPed));
            linha = ler.readLine();
            //   long startTime = System.currentTimeMillis();
            int b;
            while (linha != null) {
                a = linha.indexOf(' ') + 1;
                b = linha.indexOf(' ', a);
                idList.add(new String(linha.substring(a, b)));
                a = linha.indexOf(' ', ++b) + 1;
                a = linha.indexOf(' ', a) + 1;
                a = linha.indexOf(' ', a) + 1;
                a = linha.indexOf(' ', a) + 1;
                this.setSeq(seqList, linha.substring(a), posLista.size());

                linha = ler.readLine();
            }

            //  long estimatedTime = System.currentTimeMillis() - startTime;
            ler.close();
            
            //printPed("C:/Users/rafael.veiga/Documents/NetBeansProjects/printPed.csv", idList, posLista, seqList);
            int tam = idList.size();
            int tam2 = posLista.size();
//            this.seq = new char[2][tam][tam2];
//            this.id = new String[tam];
//            for (int i = 0; i < tam; i++) {
//                id[i] = idList.get(i);
//                for (int j = 0; j < tam2; j++) {
//                    this.seq[0][i][j] = seqList[0].get(i)[j];
//                    this.seq[1][i][j] = seqList[1].get(i)[j];
//                }
//            }
            int tamGene = this.ref.genes.length;
            this.fenotipo = new Pheno[idList.size()][tamGene];

            this.saidaTex.append("Database contains " + idList.size() + " individuals\nDatabase contains " + posLista.size() + " SNPs\n");
            this.janela_saida.setText(this.saidaTex.toString());
            //posLista = pos[]
            this.genes = new Gene[this.ref.genes.length];
            tam = this.genes.length;
            for (int i = 0; i < tam; i++) {
                this.genes[i] = new Gene(ref, i);
                this.genes[i].getSNPs(seqList, posLista, i, ref);
                saidaTex.append("gene " + ref.genes[i] + "\nmin: " + this.genes[i].minPos + " max: " + this.genes[i].maxPos + "\n");
                saidaTex.append("Snps in gene: " + this.genes[i].base1[0].length + "\n");
            }
            tam = idList.size();
            this.id = new String[tam];
            for (int i = 0; i < tam; i++) {
                this.id[i] = idList.get(i);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Error acess file");
            System.exit(1);
        } catch (IOException ex) {
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    private void printPed(String arq, ArrayList<String> idList,ArrayList<Integer> posLista, ArrayList<Character[]>[] seqList){
//        try {
//            BufferedWriter saida = new BufferedWriter(new FileWriter(arq));
//            saida.append(";");
//            int tamI = posLista.size();
//            int tamL = idList.size();
//            for(int i=0;i<tamI;i++){
//                if(posLista.get(i)==299115450)
//            saida.append(";"+posLista.get(i));
//            }
//            saida.newLine();
//            for(int l=0;l<tamL;l++){
//                saida.append(idList.get(l));
//                for(int i=0;i<tamI;i++){
//                     if(posLista.get(i)==29911545)
//                    saida.append(";"+seqList[0].get(l)[i]+"|"+seqList[1].get(l)[i]);
//                }
//                saida.newLine();
//            }
//            
//            
//            saida.close();
//        } catch (IOException ex) {
//            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    private void setSeq(ArrayList<Character[]> lista[], String linha, int tam) {
        int a = 0;
        Character[] seq1 = new Character[tam];
        Character[] seq2 = new Character[tam];
        for (int i = 0; i < tam; i++) {
            seq1[i] = linha.charAt(a);
            a = a + 2;
            seq2[i] = linha.charAt(a);
            a = a + 2;
        }
        lista[0].add(seq1);
        lista[1].add(seq2);
    }

    public void execute() {
        this.fenotipo = new Pheno[this.id.length][this.genes.length];
        for (int ind = 0; ind < this.id.length; ind++) {
            for (int g = 0; g < this.genes.length; g++) {
                this.fenotipo[ind][g] = new Pheno(g,ind,this.genes[g],ref);
            }
        }
    }
    
    
    public void printDebug(String arq, int gene){
        try {
            BufferedWriter saida = new BufferedWriter(new FileWriter(arq));
            saida.append("Identificador");
            int tamSNP = ref.pos[1].length;
            for(int i=0;i<tamSNP;i++){
                saida.append(";"+ref.pos[gene][i]);
            }
            saida.append("\n");
            saida.append("ref");
           for(int i=0;i<tamSNP;i++){
               saida.append(";"+ref.seq1[gene][0].charAt(i));
           }
           saida.append("\n");
           int tamId = this.id.length;
           for(int i=0;i<tamId;i++){
               saida.append(this.id[i]);
               int count=0;
               for(int s=0;s<tamSNP;s++){
                   if(count<this.genes[gene].indexPos.length){
                   int pos = this.genes[gene].indexPos[count];
                    if(s==pos){
                       saida.append(";"+this.genes[gene].base1[i][count]+"|"+this.genes[gene].base2[i][count]);
                       count++;
                       
                   }else{
                       saida.append(";-");
                   }
                   }else{
                      saida.append(";-"); 
                   }
                  
               }
               saida.append("\n");
           }
           
           
            saida.close();
                    } catch (IOException ex) {
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}


   
