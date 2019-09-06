/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hla_prog;

import hla_prog.Banco;
import hla_prog.LeitorResults;
import hla_prog.MakeRef.LeitorDados;
import hla_prog.MakeRef.LeitorDados;
import hla_prog.MakeRef.Ref;
import hla_prog.MakeRef.Ref;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author rafael
 */
public class Janela extends javax.swing.JFrame {
    
    private StringBuilder saidaTex;
    private File arqPed = null;
    private File arqMap = null;
    private Banco banco = null;
    private Ref ref = null;
    private String[][] resultTable;
    private String[] columTable;
    
    public Janela() {
        File pasta = new File("data");
        File arqs[] = pasta.listFiles();
        String genomas[] = new String[arqs.length];
        //System.out.println(jSelectGenome.getItemCount());
        for (int i = 0; i < arqs.length; i++) {
            genomas[i] = arqs[i].getName();
        }
        initComponents();
        jSelectGenoma.setModel(new javax.swing.DefaultComboBoxModel<>(genomas));
        jSelectGenoma.setSelectedIndex(0);
        ref = LeitorDados.load(jSelectGenoma.getItemAt(0));
        saidaTex = new StringBuilder("Loaded reference data: " + ref.genomeVersion + "\n");
        
        janela_saida.setText(saidaTex.toString());
        
        BotaoExecutar.setEnabled(false);
        jMenuSaveResult.setEnabled(false);
        ViewResults.setEnabled(false);
        jMenuExportCSV.setEnabled(false);
        janela_saida.setEditable(false);
        // ref =  LeitorDados.load(genomas[1]);
        // saidaTex.append("Done\n");
        // janela_saida.setText(saidaTex.toString());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        janela_saida = new javax.swing.JTextArea();
        label_map = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSelectGenoma = new javax.swing.JComboBox<>();
        BotaoExecutar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        label_ped = new javax.swing.JLabel();
        ViewResults = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuOpenPed = new javax.swing.JMenuItem();
        jMenuOpenMap = new javax.swing.JMenuItem();
        jMenuSaveResult = new javax.swing.JMenuItem();
        jMenuLoadReasult = new javax.swing.JMenuItem();
        jMenuExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuExportCSV = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        janela_saida.setColumns(20);
        janela_saida.setRows(5);
        jScrollPane1.setViewportView(janela_saida);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("File Map:");

        jSelectGenoma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jSelectGenoma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSelectGenomaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSelectGenoma, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(label_map, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(label_map, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSelectGenoma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        BotaoExecutar.setText("Execute");
        BotaoExecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoExecutarActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("File Ped:");

        ViewResults.setText("View Results");
        ViewResults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewResultsActionPerformed(evt);
            }
        });

        jMenuFile.setText("File");

        jMenuOpenPed.setText("Open Ped File");
        jMenuOpenPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuOpenPedActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuOpenPed);

        jMenuOpenMap.setText("Open Map File");
        jMenuOpenMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuOpenMapActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuOpenMap);

        jMenuSaveResult.setText("Save Results");
        jMenuSaveResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSaveResultActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuSaveResult);

        jMenuLoadReasult.setText("Load Results");
        jMenuLoadReasult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuLoadReasultActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuLoadReasult);

        jMenuExit.setText("Exit");
        jMenuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuExit);

        jMenuBar1.add(jMenuFile);

        jMenu2.setText("Edit");

        jMenuExportCSV.setText("Export CSV");
        jMenuExportCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuExportCSVActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuExportCSV);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BotaoExecutar)
                .addGap(31, 31, 31)
                .addComponent(ViewResults)
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_ped, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(label_ped, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotaoExecutar)
                    .addComponent(ViewResults))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotaoExecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoExecutarActionPerformed
        boolean tag = true;
        if (arqMap == null) {
            saidaTex.append("File map is empty\n");
            janela_saida.setText(saidaTex.toString());
            tag = false;
            // jMenuSaveResult.setEnabled(false);
        }
        if (arqPed == null) {
            saidaTex.append("File ped is empty\n");
            janela_saida.setText(saidaTex.toString());
            tag = false;
            // jMenuSaveResult.setEnabled(false);
        }
        
        if (tag) {
            saidaTex.append("load data files\n");
            janela_saida.setText(saidaTex.toString());
            this.banco = new Banco(arqPed, arqMap, ref, janela_saida, saidaTex);
            saidaTex.append("load data files Done\n");
            janela_saida.setText(saidaTex.toString());
            jMenuSaveResult.setEnabled(true);
            jMenuExportCSV.setEnabled(true);
            ViewResults.setEnabled(true);
            this.banco.execute();
            
        }
    }//GEN-LAST:event_BotaoExecutarActionPerformed

    private void jMenuOpenPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuOpenPedActionPerformed
        JFileChooser filechooser = new JFileChooser();
        filechooser.setCurrentDirectory(new File("."));
        filechooser.setDialogTitle("Open Ped file");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Ped file", "ped");
        filechooser.setFileFilter(filter);
        if (filechooser.showOpenDialog(jMenuOpenPed) == JFileChooser.APPROVE_OPTION) {
            arqPed = filechooser.getSelectedFile();
            label_ped.setText(arqPed.getName());
            if (arqMap != null) {
                BotaoExecutar.setEnabled(true);
            }
        } else {
            arqPed = null;
            label_ped.setText("");
        }
    }//GEN-LAST:event_jMenuOpenPedActionPerformed

    private void jMenuOpenMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuOpenMapActionPerformed
        JFileChooser filechooser = new JFileChooser();
        filechooser.setCurrentDirectory(new File("."));
        filechooser.setDialogTitle("Open Map file");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Map file", "map");
        filechooser.setFileFilter(filter);
        if (filechooser.showOpenDialog(jMenuOpenMap) == JFileChooser.APPROVE_OPTION) {
            arqMap = filechooser.getSelectedFile();
            label_map.setText(arqMap.getName());
            if (arqPed != null) {
                BotaoExecutar.setEnabled(true);
            }
        } else {
            arqMap = null;
            label_map.setText("");
        }
    }//GEN-LAST:event_jMenuOpenMapActionPerformed

    private void jMenuSaveResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSaveResultActionPerformed
        File arq = null;
        JFileChooser filechooser = new JFileChooser();
        filechooser.setCurrentDirectory(new File("."));
        filechooser.setDialogTitle("Save Result file");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("dat file", "dat");
        filechooser.setFileFilter(filter);
        if (filechooser.showSaveDialog(jMenuOpenMap) == JFileChooser.APPROVE_OPTION) {
            
            if (filechooser.getSelectedFile().getName().endsWith(".dat")) {
                arq = new File(filechooser.getSelectedFile().getPath());
            } else {
                
                arq = new File(filechooser.getSelectedFile().getPath().concat(".dat"));
            }
            LeitorResults.save(banco, arq);
            saidaTex.append("save done: " + arq.getAbsolutePath() + "\n");
            janela_saida.setText(saidaTex.toString());
            
        }
    }//GEN-LAST:event_jMenuSaveResultActionPerformed

    private void jMenuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuExitActionPerformed
        System.exit(0);// TODO add your handling code here:
    }//GEN-LAST:event_jMenuExitActionPerformed

    private void jSelectGenomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSelectGenomaActionPerformed
        int i = jSelectGenoma.getSelectedIndex();
        ref = LeitorDados.load(jSelectGenoma.getItemAt(i));
        saidaTex = new StringBuilder("Loaded reference data: " + ref.genomeVersion + "\n");
        
        janela_saida.setText(saidaTex.toString());
    }//GEN-LAST:event_jSelectGenomaActionPerformed

    private void jMenuLoadReasultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuLoadReasultActionPerformed
        
        JFileChooser filechooser = new JFileChooser();
        filechooser.setCurrentDirectory(new File("."));
        filechooser.setDialogTitle("Load result file");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("dat file", "dat");
        filechooser.setFileFilter(filter);
        if (filechooser.showOpenDialog(jMenuOpenMap) == JFileChooser.APPROVE_OPTION) {
            File arq = filechooser.getSelectedFile();
            this.banco = LeitorResults.load(arq);
            saidaTex = new StringBuilder("Loaded result data: " + arq.getAbsolutePath() + "\n");
            ViewResults.setEnabled(true);
            this.jMenuExportCSV.setEnabled(true);
            janela_saida.setText(saidaTex.toString());
            
        }
    }//GEN-LAST:event_jMenuLoadReasultActionPerformed

    private void ViewResultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewResultsActionPerformed
        JFrame f;        
        
        f = new JFrame();        
        String data[][] = this.banco.getResultTable();
        String column[] = this.banco.getCol();        
        JTable jt = new JTable(data, column);        
        jt.setBounds(30, 40, 200, 300);        
        JScrollPane sp = new JScrollPane(jt);        
        f.add(sp);        
        f.setSize(800, 400);        
        f.setVisible(true);        
    }//GEN-LAST:event_ViewResultsActionPerformed

    private void jMenuExportCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuExportCSVActionPerformed
        // TODO add your handling code here:
        File arq = null;
        JFileChooser filechooser = new JFileChooser();
        filechooser.setCurrentDirectory(new File("."));
        filechooser.setDialogTitle("Save result CSV");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV file", "csv");
        filechooser.setFileFilter(filter);
        if (filechooser.showSaveDialog(jMenuOpenMap) == JFileChooser.APPROVE_OPTION) {
            
            if (filechooser.getSelectedFile().getName().endsWith(".csv")) {
                arq = new File(filechooser.getSelectedFile().getPath());
            } else {
                
                arq = new File(filechooser.getSelectedFile().getPath().concat(".csv"));
            }
            this.banco.export_csv(arq);
            saidaTex.append("save done: " + arq.getAbsolutePath() + "\n");
            janela_saida.setText(saidaTex.toString());
            
        }
    }//GEN-LAST:event_jMenuExportCSVActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        if (args.length > 1) {
            System.out.println("rodando terminal");
            return;
        }
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Janela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Janela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Janela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Janela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Janela().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotaoExecutar;
    private javax.swing.JButton ViewResults;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuExit;
    private javax.swing.JMenuItem jMenuExportCSV;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuLoadReasult;
    private javax.swing.JMenuItem jMenuOpenMap;
    private javax.swing.JMenuItem jMenuOpenPed;
    private javax.swing.JMenuItem jMenuSaveResult;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jSelectGenoma;
    private javax.swing.JTextArea janela_saida;
    private javax.swing.JLabel label_map;
    private javax.swing.JLabel label_ped;
    // End of variables declaration//GEN-END:variables
}