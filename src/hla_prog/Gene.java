/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hla_prog;

import hla_prog.MakeRef.Ref;
import java.util.ArrayList;

/**
 *
 * @author rafael.veiga
 */
public class Gene {

    public char base1[][];//[ind][snp]
    public char base2[][];//[ind][snp]
    public int indexPos[];
    public final int minPos;
    public final int maxPos;
    //private final Ref ref;

    public Gene(Ref ref, int index) {

        int[] val = getMinMax(index, ref);
        this.minPos = val[0];
        this.maxPos = val[1];
        //this.ref = ref;

    }

    public void getSNPs(ArrayList<Character[]> seqList[], ArrayList<Integer> posLista, int indexGene,Ref ref) {
        ArrayList<Integer> ipos = new ArrayList();
        int valor;
        for (int i = 0; i < posLista.size(); i++) {
            valor = posLista.get(i);
            if (valor >= this.minPos && valor <= this.maxPos) {
                ipos.add(i);
            }
        }
        valor = ipos.size();
        indexPos = new int[valor];
        int tam = seqList[0].size();
        base1 = new char[tam][valor];
        base2 = new char[tam][valor];
        for(int i=0;i<valor;i++){
            indexPos[i] = ref.getIndexPos(posLista.get(ipos.get(i)), indexGene);
            for(int ind =0;ind<seqList.length;ind++){
                base1[ind][i] = seqList[0].get(ind)[i];
                base2[ind][i] = seqList[1].get(ind)[i];
            }
        }
        
        
        
    }

    private int[] getMinMax(int g, Ref ref) {
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
}
