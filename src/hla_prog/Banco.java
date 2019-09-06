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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author rafael.veiga
 */
public class Banco implements Serializable {

    // private int pos[];  //[snps]
    private String id[]; //[ind]
    private Gene genes[];
    //private char seq[][][];// [2][ind][snps]
    private Ref ref;
    private Pheno fenotipo[][]; //[ind][gene]
    // private JTextArea janela_saida;
    //  private StringBuilder saidaTex;

    public Banco(File arqPed, File arqMap, Ref ref, JTextArea janela_saida, StringBuilder saidaTex) {
        this.ref = ref;

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

            saidaTex.append("Database contains " + idList.size() + " individuals\nDatabase contains " + posLista.size() + " SNPs\n");
            janela_saida.setText(saidaTex.toString());
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

    public String[][] getResultTable() {
        int tamL = this.id.length;
        int tamG = this.genes.length;
        String[][] res = new String[tamL][tamG + 1];
        for (int l = 0; l < tamL; l++) {
            res[l][0] = id[l];
            for (int g = 0; g < tamG; g++) {
                String saida = "";
                int tamL1 = this.ref.id[g].length;
                double max = 0;
                for (int l1 = 0; l1 < tamL1; l1++) {
                    if (max < this.fenotipo[l][g].resultL1[l1]) {
                        max = this.fenotipo[l][g].resultL1[l1];
                    }
                }
                ArrayList<Integer> indexL1 = new ArrayList();
                for (int l1 = 0; l1 < tamL1; l1++) {
                    if (max == this.fenotipo[l][g].resultL1[l1]) {
                        indexL1.add(l1);
                    }
                }

                for (int l1 : indexL1) {
                    int tamL2 = this.ref.id2[g][l1].length;
                    double max2 = 0;
                    for (int l2 = 0; l2 < tamL2; l2++) {
                        if (max2 < this.fenotipo[l][g].resultL2[l1][l2]) {
                            max2 = this.fenotipo[l][g].resultL2[l1][l2];
                        }

                    }
                    ArrayList<Integer> indexL2 = new ArrayList();
                    for (int l2 = 0; l2 < tamL2; l2++) {
                        if (max2 == this.fenotipo[l][g].resultL2[l1][l2]) {
                            indexL2.add(l2);
                        }
                    }
                    for (int l2 : indexL2) {
                        saida = saida.concat(";" + this.ref.id[g][l1] + ":" + this.ref.id2[g][l1][l2]);
                    }

                }
                res[l][g + 1] = saida.substring(1);
            }
        }

        return res;
    }

    public String[] getCol() {
        String[] res = new String[this.genes.length + 1];
        res[0] = "ID";
        for (int i = 0; i < this.genes.length; i++) {
            res[i + 1] = this.ref.genes[i];
        }
        return res;
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
                this.fenotipo[ind][g] = new Pheno(g, ind, this.genes[g], ref);
            }
        }
    }

    public void printDebug(String arq, int gene) {
        try {
            BufferedWriter saida = new BufferedWriter(new FileWriter(arq));
            saida.append("Identificador");
            int tamSNP = ref.pos[1].length;
            for (int i = 0; i < tamSNP; i++) {
                saida.append(";" + ref.pos[gene][i]);
            }
            saida.append("\n");
            saida.append("ref");
            for (int i = 0; i < tamSNP; i++) {
                saida.append(";" + ref.seq1[gene][0].charAt(i));
            }
            saida.append("\n");
            int tamId = this.id.length;
            for (int i = 0; i < tamId; i++) {
                saida.append(this.id[i]);
                int count = 0;
                for (int s = 0; s < tamSNP; s++) {
                    if (count < this.genes[gene].indexPos.length) {
                        int pos = this.genes[gene].indexPos[count];
                        if (s == pos) {
                            saida.append(";" + this.genes[gene].base1[i][count] + "|" + this.genes[gene].base2[i][count]);
                            count++;

                        } else {
                            saida.append(";-");
                        }
                    } else {
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

    public Pheno[][] getPheno() {
        return this.fenotipo;
    }

    public void export_csv(File arq) {
        try {
            BufferedWriter saida = new BufferedWriter(new FileWriter(arq));
            String data[][] = this.getResultTable();
            String column[] = this.getCol();
            saida.append(column[0]);
            for (int c = 1; c < column.length; c++) {
                saida.append("," + column[c]);
            }
            saida.append("\n");
for(int i=0;i<this.id.length;i++){
    saida.append(data[i][0]);
    for(int g = 1;g<this.genes.length;g++){
      saida.append(","+data[i][g]);
    }
    saida.append("\n");
}
            saida.close();
        } catch (IOException ex) {
            System.err.println("can not acess file");
            System.exit(1);
        }
    }

}
