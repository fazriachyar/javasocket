/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package JavaChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.*;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author fazriachyar
 */
public class JavaChat extends javax.swing.JFrame implements Runnable{
    ServerSocket server;
    Socket client;
    BufferedReader server_reader;
    BufferedWriter server_writer;
    private final DefaultListModel<String> messageListModel = new DefaultListModel<>();
    
    
    /**
     * Creates new form JavaChat
     */
    public JavaChat() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboBoxConnection = new javax.swing.JComboBox<>();
        buttonConnect = new javax.swing.JButton();
        textFieldUsername = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        listMessage = new javax.swing.JList<>();
        textFieldMessage = new javax.swing.JTextField();
        buttonSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Chat FAZRI ACHYAR ROZAQ");
        setResizable(false);

        comboBoxConnection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Server", "Client" }));
        comboBoxConnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxConnectionActionPerformed(evt);
            }
        });

        buttonConnect.setText("On");
        buttonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConnectActionPerformed(evt);
            }
        });

        textFieldUsername.setText("Username");

        jScrollPane1.setViewportView(listMessage);

        buttonSend.setText("Send");
        buttonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textFieldMessage)
                        .addGap(18, 18, 18)
                        .addComponent(buttonSend))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comboBoxConnection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonConnect, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(textFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxConnection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonConnect)
                    .addComponent(textFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSend))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxConnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxConnectionActionPerformed
        // TODO add your handling code here:
        if(comboBoxConnection.getSelectedItem().equals("Server")){
		buttonConnect.setText("ON");
		textFieldUsername.setText("Server");
	} else {
		buttonConnect.setText("Hubungkan"); 
		textFieldUsername.setText("Client");
	}
    }//GEN-LAST:event_comboBoxConnectionActionPerformed

    private void buttonConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConnectActionPerformed
        // TODO add your handling code here:
	if(buttonConnect.getText().equals("Hubungkan")){ 
		buttonConnect.setText("Putuskan");
		client_connection();
		Thread thread = new Thread(this);
		thread.start();
	} else if (comboBoxConnection.getSelectedItem().equals("Server")){
		buttonConnect.setText("OFF");
		read_connection();
		Thread thread = new Thread(this);
		thread.start();
	}
    }//GEN-LAST:event_buttonConnectActionPerformed

    private void buttonSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSendActionPerformed
        // TODO add your handling code here:
        try {
		server_writer.write(textFieldUsername.getText() + ": " + textFieldMessage.getText());
		server_writer.newLine(); 
		server_writer.flush();
	} catch (IOException e) {
		Logger.getLogger(JavaChat.class.getName()).log(Level. SEVERE, null, e);
	}
        
        listMessage.setModel(messageListModel);
	String message = "Me: " + textFieldMessage.getText();
        messageListModel.addElement(message);
	textFieldMessage.setText("");
    }//GEN-LAST:event_buttonSendActionPerformed

    private void client_connection(){
	try {
            String ip = JOptionPane.showInputDialog("Masukkan Alamat IP Server!"); 
            client = new Socket(ip, 2000); 
            comboBoxConnection.setEnabled(false);
            server_reader = new BufferedReader(new InputStreamReader(client.getInputStream())); 
            server_writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            buttonConnect.setText("Putuskan");
	} catch (UnknownHostException e) {
            System.err.println("Akses ke server gagal!");
            System.exit(-1);
	} catch (IOException e) {
            Logger.getLogger(JavaChat.class.getName()).log(Level. SEVERE, null, e);
	}
    }
    
    private void read_connection(){
	try { 
            try {
		try {
                    server = new ServerSocket(2000);
                    this.setTitle("Mohon Tunggu Sebentar ..");
		} catch (IOException e) {
                    System.err.println("Gagal membuat server"); 
                    System.exit(-1);
		}
                client = server.accept();
		this.setTitle("Terhubung ke "+client.getInetAddress());
            } catch (IOException e) {
		System.err.println("Akses ditolak");
		System.exit(-1);
            }
            server_reader = new BufferedReader(new InputStreamReader(client.getInputStream())); 
            server_writer = new BufferedWriter(new OutputStreamWriter (client.getOutputStream()));
	} catch (IOException e) {
            System.err.println("Tidak dapat membaca pesan");
            System.exit(-1);
	}
    }
    
    private void disconnected_by_client(){
	try {
            client.close();
            server_reader.close();
            server_writer.close();
            comboBoxConnection.setEnabled(true); 
            buttonConnect.setText("Hubungkan");
	} catch (IOException e) {
            Logger.getLogger(JavaChat.class.getName()).log(Level. SEVERE, null, e);
	}
    } 
    
    private void stopped_by_server(){
	try {
            server_reader.close();
            server_writer.close();
            buttonConnect.setText("ON");
            this.setTitle("Terputus");	
	} catch (IOException e) {
            Logger.getLogger(JavaChat.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                String message = server_reader.readLine();
                if (message != null) {
                    // do something with the message...
                }
                else {
                    throw new IOException();
                }
            } catch (IOException e) {
                // handle exception...
                break;
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(JavaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JavaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JavaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JavaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JavaChat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonConnect;
    private javax.swing.JButton buttonSend;
    private javax.swing.JComboBox<String> comboBoxConnection;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listMessage;
    private javax.swing.JTextField textFieldMessage;
    private javax.swing.JTextField textFieldUsername;
    // End of variables declaration//GEN-END:variables
}
