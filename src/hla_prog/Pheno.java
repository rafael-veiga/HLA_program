/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hla_prog;

import hla_prog.MakeRef.Ref;

/**
 *
 * @author rafael.veiga
 */
public class Pheno {

    //[snps] pos snp in ref
    public double[] resultL1;//[l1]
    public int[] numL1;//[l1]
    public int[][] numL2;//[l1][l2]
    public double[][] resultL2;//[l1][l2]
    public int total;

    public Pheno(int g, int ind, Gene gene, Ref ref) {
        char a1;
        char a2;
        char cref;
        int pos;
        int count;
        int tamSnp;
        int tamL1;
        int tamL2;
        int contL1;
        int missing;
  

        tamL1 = ref.id[g].length;
        this.resultL1 = new double[tamL1];
        this.numL1 = new int[tamL1];
        this.numL2 = new int[tamL1][];
        this.resultL2 = new double[tamL1][];
        for (int l1 = 0; l1 < tamL1; l1++) {
            tamSnp = gene.indexPos.length;
            count = 0;
            contL1 = 0;
            missing = 0;
            for (int i = 0; i < tamSnp; i++) {
                a1 = gene.base1[ind][i];
                a2 = gene.base2[ind][i];
                pos = gene.indexPos[i];
                
               // System.out.println(i + " teste");
                if (pos >= 0) {
                    if (a1 == '0' || a2 == '0') {
                        missing++;
                    } else {
                        count++;
                        cref = ref.seq1[g][l1].charAt(pos);
                        if (phenoGet(a1, a2, cref)) {
                            contL1++;
                        }
                    }
                }
            }
            total = count + missing;
            numL1[l1] = contL1;
            this.resultL1[l1] = (double) contL1 / count;

            // l2
            tamL2 = ref.id2[g][l1].length;
            this.numL2[l1] = new int[tamL2];
            this.resultL2[l1] = new double[tamL2];
            for (int l2 = 0; l2 < tamL2; l2++) {
                
                count = 0;
                contL1 = 0;
                missing = 0;
                for (int i = 0; i < tamSnp; i++) {
                    a1 = gene.base1[ind][i];
                    a2 = gene.base2[ind][i];
                    pos = gene.indexPos[i];
                   
                    if (pos >= 0) {
                        if (a1 == '0' || a2 == '0') {
                            missing++;
                        } else {
                            count++;
                            cref = ref.seq2[g][l1][l2].charAt(pos);
                            if (phenoGet(a1, a2, cref)) {
                                contL1++;
                            }
                        }
                    }
                }
                numL2[l1][l2] = contL1;
                this.resultL2[l1][l2] = (double) contL1 / count;
            }

        }

    }

    private boolean phenoGet(char a1, char a2, char cref) {
        //AT = 1
        //AC = 2
        //AG = 3
        //TC = 4
        //TG = 5
        //CG = 6
        //TCG = 7
        //ACG = 8
        //ATG = 9
        //ATC = 0
        if (cref == '*' || a1 == '*' || a2 == '*') {
            return true;
        }
        if (a1 == 'A' || a2 == 'A') {
            if (cref == 'A') {
                return true;
            }
            if (cref == '1') {
                return true;
            }
            if (cref == '2') {
                return true;
            }
            if (cref == '3') {
                return true;
            }
            if (cref == '8') {
                return true;
            }
            if (cref == '9') {
                return true;
            }
            if (cref == '0') {
                return true;
            }

        }

        if (a1 == 'T' || a2 == 'T') {
            if (cref == 'T') {
                return true;
            }
            if (cref == '1') {
                return true;
            }
            if (cref == '4') {
                return true;
            }
            if (cref == '5') {
                return true;
            }
            if (cref == '7') {
                return true;
            }
            if (cref == '9') {
                return true;
            }
            if (cref == '0') {
                return true;
            }

        }

        if (a1 == 'C' || a2 == 'C') {
            if (cref == 'C') {
                return true;
            }
            if (cref == '2') {
                return true;
            }
            if (cref == '4') {
                return true;
            }
            if (cref == '6') {
                return true;
            }
            if (cref == '7') {
                return true;
            }
            if (cref == '8') {
                return true;
            }
            if (cref == '0') {
                return true;
            }

        }
        if (a1 == 'G' || a2 == 'G') {
            if (cref == 'G') {
                return true;
            }
            if (cref == '3') {
                return true;
            }
            if (cref == '5') {
                return true;
            }
            if (cref == '6') {
                return true;
            }
            if (cref == '7') {
                return true;
            }
            if (cref == '8') {
                return true;
            }
            if (cref == '9') {
                return true;
            }

        }
        return false;
    }

}
