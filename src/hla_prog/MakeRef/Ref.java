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
private String[] genes;
private int[][]pos; //[gene][pos]
private String[][] id; //[gene][L1]
private String[][][] id2; // [gene][L1][L2]
private String[][] seq1; //[gene][L1];
private String[][][]seq2; //[gene][L1][L2]

    public Ref(CriarRef config) {
        this.genes = config.genes;
        this.pos = config.getPos();
        this.id = config.gerId();
        this.id2 = config.getId2(id);
        this.seq1 = config.getSeq1(id);
        System.out.println("hla_prog.MakeRef.Ref.<init>()");
    }
    
    
    
    
    
}
