/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hla_prog.MakeRef;

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

/**
 *
 * @author rafael.veiga
 */
public class CriarRef {

    // private boolean inver[];
    // private boolean comp[];
    public String genes[];
    public Ref_banco bancos[];
    public String seq[][];//[2][num genes] // 0=genoma 1 = alelo referencia
    // private int pos[];
    //private int numGenesClassic;

    public String[][] gerId() {
        ArrayList<String>[] lista = new ArrayList[genes.length];
        int tam = genes.length;
        for (int g = 0; g < tam; g++) {
            String id[] = this.bancos[g].getID();
            lista[g] = new ArrayList();
            int tam2 = id.length;
            int a;
            for (int i = 0; i < tam2; i++) {
                a = id[i].indexOf(':');
                String s = id[i].substring(0, a);
                if (!lista[g].contains(s)) {
                    lista[g].add(new String(s));
                }

            }
        }

        String[][] res = new String[tam][];
        for (int g = 0; g < tam; g++) {
            res[g] = new String[lista[g].size()];
            lista[g].toArray(res[g]);
        }

        return res;
    }

    public String[][][] getId2(String[][] id) {
        ArrayList<String>[][] lista = new ArrayList[id.length][];
        int tam = id.length;
        for (int g = 0; g < tam; g++) {
            int tam2 = id[g].length;
            lista[g] = new ArrayList[tam2];
            for (int h = 0; h < tam2; h++) {
                lista[g][h] = new ArrayList();
                String ind[] = this.bancos[g].getID();
                int tamInd = ind.length;
                for (int i = 0; i < tamInd; i++) {
                    String aux[] = ind[i].split(":");
                    if (aux[0].contentEquals(id[g][h])) {
                        if (!lista[g][h].contains(aux[1])) {
                            lista[g][h].add(aux[1]);
                        }
                    }
                }
            }

        }
        String[][][] res = new String[tam][][];
        for (int g = 0; g < tam; g++) {
            int tam2 = lista[g].length;
            res[g] = new String[tam2][];
            for (int h = 0; h < tam2; h++) {
                res[g][h] = new String[lista[g][h].size()];
                lista[g][h].toArray(res[g][h]);
            }
        }

        return res;
    }

    public CriarRef() {
        File pasta = new File("config");
        File arqs[] = pasta.listFiles();
        int b, a;
        ArrayList<Boolean> invertido = new ArrayList();
        ArrayList<Boolean> complemento = new ArrayList();
        ArrayList<String> genesLista = new ArrayList();
        ArrayList<Integer> posicao = new ArrayList();
        ArrayList<String> sequencias = new ArrayList();
        try {

            BufferedReader config = new BufferedReader(new FileReader(arqs[0]));
            String lin = config.readLine();
            if (!lin.matches("﻿classic")) {
                System.err.println("Error format config file : example: \"config/GRCh37p13.txt\"");
                System.exit(1);
            }
            lin = config.readLine();

            while (!lin.matches("no_classic")) {
                //classic
                b = lin.indexOf('=');
                a = lin.indexOf(' ', (b + 2));
                String snum = lin.substring(b + 1, a).trim();
                sequencias.add(new String(lin.substring(a + 1).trim()));
                genesLista.add(new String(lin.substring(0, b).trim()));
                invertido.add(lin.substring(b).contains("i"));
                complemento.add(snum.contains("c"));
                snum = snum.replace("i", "").replace("c", "");
                posicao.add(Integer.parseInt(snum));
                lin = config.readLine();
            }
            int numGenesClassic = posicao.size();
            if (!lin.matches("no_classic")) {
                System.err.println("Error format config file : example: \"config/GRCh37p13.txt\"");
                System.exit(1);
            }
            lin = config.readLine();
            while (lin != null) {
                //no classic
                b = lin.indexOf('=');
                a = lin.indexOf(' ', (b + 2));
                String snum = lin.substring(b + 1, a).trim();
                sequencias.add(new String(lin.substring(a + 1).trim()));
                genesLista.add(new String(lin.substring(0, b).trim()));
                invertido.add(lin.substring(b).contains("i"));
                complemento.add(snum.contains("c"));
                snum = snum.replace("i", "").replace("c", "");
                posicao.add(Integer.parseInt(snum));
                lin = config.readLine();
            }

            config.close();
            int tam = genesLista.size();
            this.bancos = new Ref_banco[tam];
            this.genes = new String[tam];
            this.seq = new String[2][tam];
            for (int i = 0; i < numGenesClassic; i++) {
                this.genes[i] = genesLista.get(i);
                System.out.print("load " + this.genes[i] + "_gen.txt : ");
                this.bancos[i] = new Ref_banco(new File("ref_aleles_classics/" + this.genes[i] + "_gen.txt"));
                System.out.print("Done\n");
                if (complemento.get(i)) {
                    this.bancos[i].sequenciaComplementar();
                }
                if (invertido.get(i)) {
                    this.bancos[i].inverter();
                }

            }
            for (int i = numGenesClassic; i < tam; i++) {
                this.genes[i] = genesLista.get(i);
                System.out.print("load " + this.genes[i] + "_gen.txt : ");
                this.bancos[i] = new Ref_banco(new File("ref_aleles_no_classics/" + this.genes[i] + "_gen.txt"));
                System.out.print("Done\n");
                if (complemento.get(i)) {
                    this.bancos[i].sequenciaComplementar();
                }
                if (invertido.get(i)) {
                    this.bancos[i].inverter();
                }

            }

            for (int i = 0; i < tam; i++) {
                a = sequencias.get(i).indexOf(';');
                this.seq[0][i] = new String(sequencias.get(i).substring(0, a));
                this.seq[1][i] = new String(sequencias.get(i).substring(a + 1));
                this.bancos[i].setPosIni(posicao.get(i));
            }

        } catch (FileNotFoundException ex) {
            System.err.println("Error no found config file : example: \"config/GRCh37p13.txt\"");
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("Error can not open config file : example: \"config/GRCh37p13.txt\"");
            System.exit(1);
        }

    }

    public void createFasta(String arq) {
        try {
            BufferedReader ler = new BufferedReader(new FileReader(arq));
            int tam = genes.length;
            String lin = ler.readLine();
            boolean tag = false;
            while (lin != null) {
                if (lin.startsWith(">")) {
                    ArrayList<String> listaGenes = new ArrayList();
                    int indexGene = 0;
                    for (int i = 0; i < tam; i++) {
                        if (lin.contains(">HLA-" + genes[i])) {
                            listaGenes.add(lin);
                            indexGene = i;
                        }
                    }
                    if (listaGenes.size() > 0) {
                        for (int j = 0; j < listaGenes.size(); j++) {
                            BufferedWriter saida = new BufferedWriter(new FileWriter(lin.substring(1).split(";")[0] + ".fasta"));
                            saida.append(this.bancos[indexGene].getSequenciaReferencia());
                            saida.append(lin);
                            lin = ler.readLine();
                            while (lin != null && (!lin.startsWith(">"))) {
                                saida.append("\n" + lin);

                                lin = ler.readLine();
                            }
                            tag = true;

                            saida.close();
                            listaGenes = new ArrayList();
                        }
                    } else {
                        tag = false;
                    }
                }
                if (!tag) {
                    lin = ler.readLine();
                    tag = false;
                }
            }

            ler.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CriarRef.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CriarRef.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int[][] getPos() {
        int a, b, c;//a = Aref, b = Aref_aln, c = genoma_aln
        char a1, a2, a3;
        int tam = genes.length;
        int[][] res = new int[tam][];

        for (int g = 0; g < tam; g++) {
            int pos_ini = this.bancos[g].getPosIni();
            String aRef = this.bancos[g].getsequencias()[0];
            String aRef_aln = this.seq[0][g];
            String geno_aln = this.seq[1][g];
            aRef = aRef.replaceAll("\\|", "");
            int tam2 = aRef.length();
            res[g] = new int[tam2];

            b = 1;
            c = 1;
            res[g][0] = pos_ini++;
            System.out.println("" + g);
            for (a = 1; a < tam2;) {
                a1 = aRef.charAt(a);
                a2 = aRef_aln.charAt(b);
                a3 = geno_aln.charAt(c);
//                if ( g == 2 && a==676) {
//                    System.out.println(aRef.substring(a, a + 30));
//                    System.out.println(aRef_aln.substring(b, b + 30));
//                    System.out.println(geno_aln.substring(c, c + 30));
//                }
                if (a1 == '.') {
                    if (a3 != '-' && a2 != '-') {
                        res[g][a] = 0;
                        a++;
                        //b++;
                        //c++;
                        continue;
                    }
                    if (a3 != '-' && a2 == '-') {
                        res[g][a] = pos_ini++;
                        a++;

                        b++;
                        continue;
                    }
                    if (a3 == '-' && a2 != '-') {
                        res[g][a] = 0;
                        //c++;

                        a++;
                        continue;
                    }

                }

                if (a2 == '-') {
                    b++;
                    c++;
                    pos_ini++;
                    continue;
                }

                if (a3 == '-') {
                    res[g][a] = 0;
                    b++;
                    c++;
                    a++;
                    continue;
                }

                res[g][a] = pos_ini++;
                a++;
                b++;
                c++;

            }
        }
        return res;

    }

    public String[][] getSeq1(String[][] id) {
        int tam = id.length;
        String res[][] = new String[tam][];
        for (int g = 0; g < tam; g++) {
            res[g] = new String[id[g].length];
            String nome[] = this.bancos[g].getID();
            String seq[] = this.bancos[g].getsequencias();

            for (int h = 0; h < id[g].length; h++) {
                ArrayList<String> lista = new ArrayList();
                lista.add(seq[0]);
                for (int i = 0; i < nome.length; i++) {
                    String aux[] = nome[i].split(":");
                    if (aux[0].contentEquals(id[g][h])) {
                        lista.add(seq[i]);
                        //lista.add(nome[i]);
                    }
                }
             res[g][h] = processSeq(lista);
                

            }

        }

        return res;
    }
    
    
    private String processSeq(ArrayList<String> lista){
        StringBuilder res = new StringBuilder();
        String ref = lista.get(0);
        ref = ref.replaceAll("\\|", "");
        int tam = lista.size();
        ArrayList<String> nova = new ArrayList();
        for(int i=0;i<tam;i++){
            nova.add(lista.get(i).replaceAll("\\|", ""));
        }
        int tam2 = ref.length();
        for(int c=0;c<tam2;c++){
            
        }
        
        return null;
    }

    private char compCaracter(ArrayList<Character> c) {
        //AT = 1
        //AC = 2
        //AG = 3
        //TC = 4
        //TG = 5
        //CG = 6
        //TCG = 7
        //ACG = 8
        //ATG = 9
        //ATC = 10

        return ' ';
    }

}
