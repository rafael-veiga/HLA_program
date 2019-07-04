/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hla_prog.MakeRef;

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
public class LeitorDados {
    
    public static void save(Ref obj){
        FileOutputStream f;
        ObjectOutputStream o;
        try {
            f = new FileOutputStream(new File("data/" + obj.genomeVersion + ".dat"));
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
    
    public static Ref load(String arq){
       
        try {
           FileInputStream f = new FileInputStream(new File("data/" + arq));
            ObjectInputStream o = new ObjectInputStream(f); 
           Ref res = (Ref)o.readObject();
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
