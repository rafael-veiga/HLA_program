/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hla_prog.MakeRef;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author rafael.veiga
 */
public class Ref_banco {

    private int pos_ini;
    private Dados dados;

    public Ref_banco(File arq_ref) {

        try {
            BufferedReader ler = new BufferedReader(new FileReader(arq_ref));
            String linha = ler.readLine();
            int pos = 0;
            int count = 1;

            //pass lines star with #
            while (linha.startsWith("#")) {
                linha = ler.readLine();
                count++;
            }
            boolean tag = true;
            while (linha != null) {
                ArrayList<String> id = new ArrayList();
                ArrayList<String> linhas = new ArrayList();
                while (linha.trim().length() < 2) {
                    count++;
                    linha = ler.readLine();
                }
                // get pos
                if (linha.contains("gDNA")) {
                    String aux[] = linha.trim().split("[ ]+");
                    pos = Integer.parseInt(aux[1]);
                    if (tag) {
                        this.pos_ini = pos;
                    }
                } else {
                    if(linha.contains("Please see")){
                        return;
                    }
                    System.err.println("Error, file " + arq_ref.getAbsolutePath() + " do not contain gDNA!!!");
                    System.exit(1);
                }
                linha = ler.readLine();
                count++;
                while (linha.trim().length() < 2) {
                    count++;
                    linha = ler.readLine();
                }
                //get sequence names 
                int a;
                while (linha.trim().length() > 2) {
                    a = linha.indexOf(' ', 2);

                    String lin = new String(linha.substring(a).replace(" ", ""));
                    linhas.add(lin);
                    if (tag) {
                        String reg = new String(linha.substring(0, a).trim());
                        id.add(reg);
                    }
                    linha = ler.readLine();

                }
                if (tag) {
                    this.dados = new Dados(this.pos_ini, id, linhas);
                    tag = false;
                } else {
                    this.dados.add(pos, linhas);
                }

            }

            ler.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Error, file " + arq_ref.getAbsolutePath() + " could not be opened!!!");
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("Error, file " + arq_ref.getAbsolutePath() + " access error!!!");
            System.exit(1);
        } catch (NumberFormatException ex) {
            System.err.println("Error, file " + arq_ref.getAbsolutePath() + " number format error!!!");
            System.exit(1);
        }

    }
    
    public String getSequenciaReferencia(){
       StringBuilder seq = new StringBuilder();
       
       int tam = this.dados.sequencia[0].length();
       for(int i=0;i<tam;i++){
        if(this.dados.sequencia[0].charAt(i)!= '.' && this.dados.sequencia[0].charAt(i)!= '|' && this.dados.sequencia[0].charAt(i)!= '*'){
            
                seq.append(this.dados.sequencia[0].charAt(i));
            
        }   
       }
        
        return seq.toString();
    }
public void inverter(){
    int tam = this.dados.sequencia.length;
    StringBuilder res;
    int tam2 = this.dados.sequencia[0].length();
    for(int i=0;i<tam;i++){
        res = new StringBuilder(tam2);
        for(int j=0;j<tam2;j++){
            //char c = this.dados.sequencia[i].charAt((tam2-1)-j);
            res.append(this.dados.sequencia[i].charAt((tam2-1)-j));
        }
        this.dados.sequencia[i] = res.toString();
    }
    
}
    
  public void sequenciaComplementar(){
      int tam = this.dados.sequencia.length;
      StringBuilder res;
      for(int i=0;i<tam;i++){
          res = new StringBuilder(this.dados.sequencia[i]);
          int tam2 = res.length();
          for(int j=0;j<tam2;j++){
              res.setCharAt(j, complementar(res.charAt(j)));
          }
          this.dados.sequencia[i] = res.toString();
      }
  }  
    
  private char complementar(char c){
      char res;
   switch (c){
       case 'A':
           res = 'T';
           break;
       case 'T':
           res = 'A';
           break;
       case 'C':
           res = 'G';
           break;
       case 'G':
           res = 'C';
           break;
        default:
            res = c;
            break;
   }   
      
    return res;  
  }
    private class Dados {

        public int pos_ini;
        public String[] sequencia;
        public String[] id;

        public Dados(int pos_ini, ArrayList<String> id, ArrayList<String> seq) {
            this.pos_ini = pos_ini;
            this.sequencia = new String[seq.size()];
            seq.toArray(sequencia);
            this.id = new String[id.size()];
            id.toArray(this.id);
        }

        public void add(int pos, ArrayList<String> seq) {
            
            int tam = this.id.length;
            for (int i = 0; i < tam; i++) {
                sequencia[i] += seq.get(i);
            }
        }

    }

}
