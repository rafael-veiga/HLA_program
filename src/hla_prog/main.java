/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hla_prog;

//import hla_prog.MakeRef.CriarRef;
import hla_prog.MakeRef.Leitor;
//import hla_prog.MakeRef.Ref;
//import java.io.File;
//import hla_prog.MakeRef.Ref_banco;

/**
 *
 * @author rafael.veiga
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
       // CriarRef ref = new CriarRef();
       // Ref nRef = new Ref(ref);
       // Leitor.save(nRef);
       Banco b = new Banco("C:/Users/rafael.veiga/Documents/bancos/epigen5M_chr6.ped", "C:/Users/rafael.veiga/Documents/bancos/epigen5M_chr6.map",Leitor.load() );
        b.execute("C:/Users/rafael.veiga/Documents/bancos/saida.csv");
//        Ref data =Leitor.load();
//        System.out.println("");
        //ref.createFasta("C:/Users/rafael.veiga/Desktop/HLA/descricao/referencia_37.txt");
  //   ref.createFasta("F:/HLA/descricao/referencia_37.txt");
//        String arq_in = null;
//        String arq_out = null;
//        String arq_ref = null;
//        if (args.length > 0) {
//            for (int i = 0; i < args.length; i++) {
//                if (args[i].matches("-in")) {
//                    if (i + 1 < args.length) {
//                        arq_in = args[i + 1];
//                    } else {
//                        System.out.println("Program to identify HLA phenotypes based on genomic data\n"
//                                + "Help\n"
//                                + "parametros:\n"
//                                + "-in : genomic database in the format ... \n"
//                                + "-ref : reference bank construction in genomic nucleotide sequence format from http://hla.alleles.org\n"
//                                + "-out : output file name\n"
//                                + "Example: java -jar HLA_prog.jar -in snps.raw -out saida");
//                        System.exit(0);
//                    }
//
//                }
//                if (args[i].matches("-ref")) {
//                    if (i + 1 < args.length) {
//                        arq_ref = args[i + 1];
//                    } else {
//                        System.out.println("Program to identify HLA phenotypes based on genomic data\n"
//                                + "Help\n"
//                                + "parametros:\n"
//                                + "-in : genomic database in the format ... \n"
//                                + "-ref : reference bank construction in genomic nucleotide sequence format from http://hla.alleles.org\n"
//                                + "-out : output file name\n"
//                                + "Example: java -jar HLA_prog.jar -in snps.raw -out saida");
//                        System.exit(0);
//                    }
//
//                }
//                if (args[i].matches("-out")) {
//                    if (i + 1 < args.length) {
//                        arq_out = args[i + 1];
//                    } else {
//                        System.out.println("Program to identify HLA phenotypes based on genomic data\n"
//                                + "Help\n"
//                                + "parametros:\n"
//                                + "-in : genomic database in the format ... \n"
//                                + "-ref : reference bank construction in genomic nucleotide sequence format from http://hla.alleles.org\n"
//                                + "-out : output file name\n"
//                                + "Example: java -jar HLA_prog.jar -in snps.raw -out saida");
//                        System.exit(0);
//                    }
//
//                }
//
//            }
//        } else {
//            System.out.println("Program to identify HLA phenotypes based on genomic data\n"
//                    + "Help\n"
//                    + "parametros:\n"
//                    + "-in : genomic database in the format ... \n"
//                    + "-ref : reference bank construction in genomic nucleotide sequence format from http://hla.alleles.org\n"
//                    + "-out : output file name\n"
//                    + "Example: java -jar HLA_prog.jar -in snps.raw -out saida");
//        }
//
//        if (arq_ref != null) {
//            File pasta = new File("ref_new");
//            File file = new File(arq_ref);
//            if (file.exists()) {
//                if (pasta.exists()) {
//                    pasta.delete();
//                }
//                pasta.mkdir();
//
//            }else{
//                System.err.println("reference file not found!!! " + file);
//                System.exit(1);
//            }
//        
       
    }

}
