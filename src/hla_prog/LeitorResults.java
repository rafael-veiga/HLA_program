/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hla_prog;

import hla_prog.MakeRef.Ref;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author rafael.veiga
 */
public class LeitorResults {
     public static void save(Banco obj, File arq){
        FileOutputStream f;
        ObjectOutputStream o;
        try {
            f = new FileOutputStream(arq);
            o = new ObjectOutputStream(f);
            o.writeObject(obj);
            o.close();
            f.close();
            
        } catch (FileNotFoundException ex) {
            System.err.println("can not write file: data/data.dat");
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("can not write file: data/data.dat");
            System.exit(1);
        }
			
    }
    
    public static Banco load(File arq){
       
        try {
           FileInputStream f = new FileInputStream(arq);
            ObjectInputStream o = new ObjectInputStream(f); 
           Banco res = (Banco)o.readObject();
           o.close();
           f.close();
            return res;
        } catch (FileNotFoundException ex) {
            System.err.println("can not load file: data/" + arq);
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("can not load file: data/" + arq);
            System.exit(1);
        } catch (ClassNotFoundException ex) {
           System.err.println("can not load file: data/" + arq);
            System.exit(1);
        }
	return null;		
    }  
}
