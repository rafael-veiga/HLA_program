/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hla_prog;

import hla_prog.MakeRef.Ref;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
                a = linha.indexOf(' ')+1;
                b = linha.indexOf(' ',a);
                idList.add(new String(linha.substring(a, b)));
                a = linha.indexOf(' ',++b)+1;
                a = linha.indexOf(' ',a)+1;
                a = linha.indexOf(' ',a)+1;
                a = linha.indexOf(' ',a)+1;
                this.setSeq(seqList, linha.substring(a),this.pos.length);
                
               

                linha = ler.readLine();
            }
           

          //  long estimatedTime = System.currentTimeMillis() - startTime;
            ler.close();
            int tam = idList.size();
            int tam2 = pos.length;
            this.seq = new char[2][tam][tam2];
            this.id  = new String[tam];
            for(int i=0;i<tam;i++){
                id[i] = idList.get(i);
                for(int j=0;j<tam2;j++){
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
    
    private void setSeq(ArrayList<Character[]> lista[], String linha,int tam){
        int a=0;
        Character[] seq1 = new Character[tam];
        Character[] seq2 = new Character[tam];
        for(int i=0;i<tam;i++){
          seq1[i] = linha.charAt(a);
          a = a+2;
          seq2[i] = linha.charAt(a);
          a = a+2;
        }
        lista[0].add(seq1);
        lista[1].add(seq2);
    } 

}
