        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hla_prog.MakeRef;

import java.io.Serializable;

/**
 *
 * @author rafael.veiga
 */
public class Ref implements Serializable{
public final String genomeVersion;    
public final String[] genes;
public final  int[][]pos; //[gene][pos]
public final String[][] id; //[gene][L1]
public final  String[][][] id2; // [gene][L1][L2]
public final String[][] seq1; //[gene][L1];
public final String[][][]seq2; //[gene][L1][L2]

    public Ref(CriarRef config) {
        this.genes = config.genes;
        this.pos = config.getPos();
        this.id = config.gerId();
        this.id2 = config.getId2(id);
        this.seq1 = config.getSeq1(id);
        this.seq2 = config.getSeq2(id,id2);
        this.genomeVersion = config.getNome();
    }
    
    public int getIndexPos(int pos, int gene){
        if(this.pos[gene][0]==pos){
            return 0;
        }
        if(this.pos[gene][this.pos[gene].length-1]==pos){
            return this.pos[gene].length-1; 
        }
        
        return recpos(0, this.pos[gene].length-1, gene, pos);
    }
    
    private int recpos(int a,int b, int gene, int pos){
        int m =(a+b)/2;
        if(b-a==1){
            return -1;
        }
        while(this.pos[gene][m]==0 && m!=a && m!=b){
          m++;  
        }
        if(m==a || m==b){
            return -1;
        }
        if(this.pos[gene][m]==pos){
            return m;
        }
        
        if(this.pos[gene][m]>pos){
            return recpos(a, m, gene, pos);
        }
        else{
            return recpos(m, b, gene, pos);
        }
    }
    
    
    
}
