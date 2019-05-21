/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hla_prog;

import hla_prog.MakeRef.Ref;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafael.veiga
 */
public class Banco {

    private int pos[];  //[snps]
    private String id[]; //[ind]
    private char seq[][][];// [2][ind][snps]
    private Ref ref;

    public Banco(String arqPed, String arqMap, Ref ref) {
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
            this.pos = new int[posLista.size()];
            for (int i = 0; i < posLista.size(); i++) {
                this.pos[i] = posLista.get(i);
            }

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
                this.setSeq(seqList, linha.substring(a), this.pos.length);

                linha = ler.readLine();
            }

            //  long estimatedTime = System.currentTimeMillis() - startTime;
            ler.close();
            int tam = idList.size();
            int tam2 = pos.length;
            this.seq = new char[2][tam][tam2];
            this.id = new String[tam];
            for (int i = 0; i < tam; i++) {
                id[i] = idList.get(i);
                for (int j = 0; j < tam2; j++) {
                    this.seq[0][i][j] = seqList[0].get(i)[j];
                    this.seq[1][i][j] = seqList[1].get(i)[j];
                }
            }
            System.out.println("");
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

    public void execute(String arqOut) {
        try {
            BufferedWriter saida = new BufferedWriter(new FileWriter(arqOut));
            saida.append("reg");
            ArrayList<Integer> snpList[] = new ArrayList[this.ref.genes.length];
            int tamSnp = this.pos.length;
            int tamGene = this.ref.genes.length;
            for (int g = 0; g < tamGene; g++) {
                snpList[g] = new ArrayList();
                int extremos[] = this.getMinMax(g);

                for (int i = 0; i < tamSnp; i++) {
                    if (pos[i] >= extremos[0] && pos[i] <= extremos[1]) {
                        snpList[g].add(i);
                    }
                }

            }
            for (int g = 0; g < tamGene; g++) {
                if (snpList[g].size() > 0) {
                    saida.append(";" + this.ref.genes[g]);
                } else {
                    System.out.println("no snp in gene: " + this.ref.genes[g]);
                }

            }
            saida.append("\n");

            int tamInd = this.id.length;
            for (int ind = 0; ind < tamInd; ind++) {
                saida.append(this.id[ind]);
                for (int g = 0; g < tamGene; g++) {
                    if (snpList[g].size() > 0) {
                        String s = this.getPheno(snpList[g], g, ind);
                    }
                }
                saida.append("\n");

            }

            saida.close();
        } catch (IOException ex) {
            System.err.println("file error! is not possible to write a output file!");
            System.exit(1);
        }

    }

    private int[] getMinMax(int g) {
        int value[] = new int[2];
        value[0] = ref.pos[g][0];
        value[1] = ref.pos[g][0];
        for (int v : ref.pos[g]) {
            if (v != 0) {
                if (value[0] > v) {
                    value[0] = v;
                }
                if (value[1] < v) {
                    value[1] = v;
                }
            }
        }

        return value;
    }

    private String getPheno(ArrayList<Integer> snpList, int gene, int ind) {
        int tamL1 = this.ref.seq1[gene].length;
        StringBuilder res = new StringBuilder();
        ArrayList<ArrayList<Integer>> l1 = new ArrayList();
        for (int snp : snpList) {
            char c1 = this.seq[0][ind][snp];
            char c2 = this.seq[1][ind][snp];
            int pos = this.pos[snp];
            int indexPos = this.ref.getIndexPos(pos, gene);
            if (indexPos >= 0) {

                l1.add(L1(c1, c2, indexPos, gene));

            }
        }

        double valor[] = getConsesus(l1, snpList.size(), gene);
        ArrayList<Integer> lista = new ArrayList();
        
        
        
        return null;
    }

    private ArrayList<Integer> getBestL1(double[] res) {

        return null;
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
