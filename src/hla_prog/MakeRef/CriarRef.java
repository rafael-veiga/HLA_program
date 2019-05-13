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
        int tam = genes.length;
        int[][] res = new int[tam][];

        for (int g = 0; g < tam; g++) {
            int pos_ini = this.bancos[g].getPosIni();
            String aRef = this.bancos[g].getsequencias()[0];
            aRef= aRef.replace("\\|", "");
            int tam2 = aRef.length();
            res[g] = new int[tam2];

            b = 1;
            c = 1;
            res[g][0] = pos_ini++;
            for (a = 1; a < tam2; a++) {
               // if()

            }

        }
        return null;
    }
}
