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
private String[][] seqL1;
private String[][][] seqL2;
    public Ref(CriarRef config) {
        this.genes = config.genes;
        this.pos = config.getPos();
    }
    
    
    
    
    
}
