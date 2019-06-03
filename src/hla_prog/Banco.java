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
                a = 2;
                a = linha.indexOf('\t', a);
                a = linha.indexOf('\t', ++a);
                posLista.add(Integer.parseInt(linha.substring(++a, linha.length())));
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

//    private String getPheno(ArrayList<Integer> snpList, int gene, int ind) {
//        int tamL1 = this.ref.seq1[gene].length;
//        StringBuilder res = new StringBuilder();
//        ArrayList<ArrayList<Integer>> l1 = new ArrayList();
//        int indexPos[] = new int[snpList.size()];
//        //for (int snp : snpList) {
//        for (int i = 0; i < indexPos.length; i++) {
//
//            char c1 = this.seq[0][ind][snpList.get(i)];
//            char c2 = this.seq[1][ind][snpList.get(i)];
//            int pos = this.pos[snpList.get(i)];
//            indexPos[i] = this.ref.getIndexPos(pos, gene);
//            if (indexPos[i] >= 0) {
//
//                l1.add(L1(c1, c2, indexPos[i], gene));
//
//            }
//        }
//
//        double valor[] = getConsesus(l1, snpList.size(), gene);
//        ArrayList<Integer> lista = getBestL1(valor);
//
//        res.append(ref.id[gene][lista.get(0)] + "(" + valor[lista.get(0)] + ")");
//        for (int numL1 = 1; numL1 < lista.size(); numL1++) {
//            res.append(":" + ref.id[gene][lista.get(numL1)] + "(" + valor[lista.get(numL1)] + ")");
//        }
//
//        
//        
//        for (int numL1 = 1; numL1 < lista.size(); numL1++) {
////            ArrayList<ArrayList<Integer>> l2 = new ArrayList();
////            for (int i = 0; i < indexPos.length; i++) {
////                char c1 = this.seq[0][ind][snpList.get(i)];
////                char c2 = this.seq[1][ind][snpList.get(i)];
////                if (indexPos[i] >= 0) {
////
////                    l2.add(L2(c1, c2, indexPos[i], gene,numL1));
////                }
////            }
////            
////             valor = getConsesus2(l1, snpList.size(), gene,numL1);
////// continua
//        }
//
//        return res.toString();
//    }
    private double[] getConsesus2(ArrayList<ArrayList<Integer>> l2, int size, int gene, int numL1) {
        int tam2 = this.ref.id2[gene][numL1].length;
        int phenoCount[] = new int[tam2];
        int tam = l2.size();
        for (int i = 0; i < tam; i++) {
            for (int a : l2.get(i)) {
                phenoCount[a]++;
            }

        }

        double[] res = new double[tam2];

        for (int i = 0; i < tam2; i++) {
            res[i] = (double) phenoCount[i] / tam;
        }
        return res;
    }

    private ArrayList<Integer> L2(char c1, char c2, int indexPos, int gene, int numL1) {
        ArrayList<Integer> lista = new ArrayList();
        int tam = this.ref.seq2[gene][numL1].length;
        for (int l = 0; l < tam; l++) {
            char n = this.ref.seq2[gene][numL1][l].charAt(indexPos);
            if (n == '*') {
                lista.add(l);
                continue;
            }
            if (n == c1 || n == c2) {
                lista.add(l);
                continue;
            }
            //AT = 1
            if (n == '1' && ((c1 == 'A') || (c2 == 'A') || (c1 == 'T') || (c2 == 'T'))) {
                lista.add(l);
                continue;
            }
            //AC = 2
            if (n == '2' && ((c1 == 'A') || (c2 == 'A') || (c1 == 'C') || (c2 == 'C'))) {
                lista.add(l);
                continue;
            }
            //AG = 3
            if (n == '3' && ((c1 == 'A') || (c2 == 'A') || (c1 == 'G') || (c2 == 'G'))) {
                lista.add(l);
                continue;
            }
            //TC = 4
            if (n == '4' && ((c1 == 'T') || (c2 == 'T') || (c1 == 'C') || (c2 == 'C'))) {
                lista.add(l);
                continue;
            }
            //TG = 5
            if (n == '5' && ((c1 == 'T') || (c2 == 'T') || (c1 == 'G') || (c2 == 'G'))) {
                lista.add(l);
                continue;
            }
            //CG = 6
            if (n == '6' && ((c1 == 'C') || (c2 == 'C') || (c1 == 'G') || (c2 == 'G'))) {
                lista.add(l);
                continue;
            }
            //TCG = 7
            if (n == '7' && ((c1 != 'A') || (c2 != 'A'))) {
                lista.add(l);
                continue;
            }
            //ACG = 8
            if (n == '8' && ((c1 != 'T') || (c2 != 'T'))) {
                lista.add(l);
                continue;
            }
            //ATG = 9
            if (n == '9' && ((c1 != 'C') || (c2 != 'C'))) {
                lista.add(l);
                continue;
            }
            //ATC = 0
            if (n == '0' && ((c1 != 'G') || (c2 != 'G'))) {
                lista.add(l);
            }
        }

        return lista;
    }

    private ArrayList<Integer> getBestL1(double[] res) {
        double num1 = 0.0;
        double num2 = 0.0;

        ArrayList<Integer> list = new ArrayList();

        if (res.length == 1) {
            list.add(0);
            return list;
        }
        for (double d : res) {
            if (d > num1) {
                num2 = num1;
                num1 = d;
            }
            if (d < num1 && d > num2) {
                num2 = d;
            }

        }
        for (int i = 0; i < res.length; i++) {
            if (res[i] == num1) {
                list.add(i);
            }
        }
        if (list.size() > 1) {
            return list;
        }
        for (int i = 0; i < res.length; i++) {
            if (res[i] == num2) {
                list.add(i);
            }
        }

        return list;
    }

    private double[] getConsesus(ArrayList<ArrayList<Integer>> l1, int size, int gene) {
        int tam2 = this.ref.id[gene].length;
        int phenoCount[] = new int[tam2];
        int tam = l1.size();
        for (int i = 0; i < tam; i++) {
            for (int a : l1.get(i)) {
                phenoCount[a]++;
            }

        }

        double[] res = new double[tam2];

        for (int i = 0; i < tam2; i++) {
            res[i] = (double) phenoCount[i] / tam;
        }
        return res;
    }

    private ArrayList<Integer> L1(char c1, char c2, int indexPos, int gene) {
        ArrayList<Integer> lista = new ArrayList();
        int tam = this.ref.seq1[gene].length;
        for (int l = 0; l < tam; l++) {
            char n = this.ref.seq1[gene][l].charAt(indexPos);
            if (n == '*') {
                lista.add(l);
                continue;
            }
            if (n == c1 || n == c2) {
                lista.add(l);
                continue;
            }
            //AT = 1
            if (n == '1' && ((c1 == 'A') || (c2 == 'A') || (c1 == 'T') || (c2 == 'T'))) {
                lista.add(l);
                continue;
            }
            //AC = 2
            if (n == '2' && ((c1 == 'A') || (c2 == 'A') || (c1 == 'C') || (c2 == 'C'))) {
                lista.add(l);
                continue;
            }
            //AG = 3
            if (n == '3' && ((c1 == 'A') || (c2 == 'A') || (c1 == 'G') || (c2 == 'G'))) {
                lista.add(l);
                continue;
            }
            //TC = 4
            if (n == '4' && ((c1 == 'T') || (c2 == 'T') || (c1 == 'C') || (c2 == 'C'))) {
                lista.add(l);
                continue;
            }
            //TG = 5
            if (n == '5' && ((c1 == 'T') || (c2 == 'T') || (c1 == 'G') || (c2 == 'G'))) {
                lista.add(l);
                continue;
            }
            //CG = 6
            if (n == '6' && ((c1 == 'C') || (c2 == 'C') || (c1 == 'G') || (c2 == 'G'))) {
                lista.add(l);
                continue;
            }
            //TCG = 7
            if (n == '7' && ((c1 != 'A') || (c2 != 'A'))) {
                lista.add(l);
                continue;
            }
            //ACG = 8
            if (n == '8' && ((c1 != 'T') || (c2 != 'T'))) {
                lista.add(l);
                continue;
            }
            //ATG = 9
            if (n == '9' && ((c1 != 'C') || (c2 != 'C'))) {
                lista.add(l);
                continue;
            }
            //ATC = 0
            if (n == '0' && ((c1 != 'G') || (c2 != 'G'))) {
                lista.add(l);
            }
        }

        return lista;
    }

}
