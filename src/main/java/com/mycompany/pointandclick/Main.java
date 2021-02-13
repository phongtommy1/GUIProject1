/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pointandclick;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;

/**
 *
 * @author phong
 */
public class Main extends JFrame implements WindowListener,ActionListener {
    Timer timer;
    int delay = 3000;
    JLabel t1,t2;
    
    public Main(String title){
        super(title);
        addActionListener(this);
        addWindowListener(this);
        
        t1 = new JLabel("CS245 Point and Click Game");
        t2 = new JLabel("By: The Nothing");
        Font font = new Font("Courier", Font.BOLD,24);
        Font font1 = new Font("Arial",Font.BOLD,14);
        t1.setFont(font);
        t2.setFont(font1);
        //t1.setBounds(100,0,400,100);
        //t2.setBounds(600,0,0,40);
        add(t1);
        add(t2);
        
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                new Menu().setVisible(true);
            }
        };
          
        timer = new Timer(delay, action);
        timer.setRepeats(false);
        timer.start();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main myWindow = new Main("Point and Click Game");
        myWindow.setSize(600,400);
        myWindow.setVisible(true);
       
    }
    
    public void actionPerformed(ActionEvent e){
        dispose();
        new Menu().setVisible(true);
    }
    @Override
    public void windowOpened(WindowEvent arg0) {
    }

    @Override
    public void windowClosing(WindowEvent arg0) {
        dispose();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent arg0) {
    }

    @Override
    public void windowIconified(WindowEvent arg0) {
    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
       
    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
    }
    
}
