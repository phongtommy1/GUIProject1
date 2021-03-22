/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pointandclick;

/**
 *
 * @author phong
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Pong extends JFrame {
        static JLabel score;
        static JButton quit;
	PongSet gameEngine = new PongSet();	
        
        Action escape = new AbstractAction()  //use Action class to create AbstractAction for escape key
        {
            public void actionPerformed(ActionEvent e)
             {
                new Menu().setVisible(true);
                System.exit(0);  //if ESC is pressed, exit the program
                
            }
         };
    
        Action f1 = new AbstractAction()  //use Action class to create AbstractAction for F1 key
        {
            public void actionPerformed(ActionEvent e)
            {
                new ProjectDisplay().setVisible(true);  //display the project display frame if the user presses F1
            }
        };
        
	public Pong() {
		setTitle("Pong");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
                setResizable(false);
   
                getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");  //create InputMap with KeyStroke for ESC
                getRootPane().getActionMap().put("escape", escape);  //create the ActionMap for the escape Action (give it the AbstractAction that was created earlier)
        
                getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "f1");  //create InputMap with KeyStroke for F1
                getRootPane().getActionMap().put("f1", f1); 
                
                score = new JLabel("Player A: 0" + "  Player B: 0", JLabel.CENTER);
                add(score, BorderLayout.SOUTH);	
		add(gameEngine);
  
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					gameEngine.start();
					break;
				case KeyEvent.VK_UP:
					gameEngine.moveB(-20);
					break;
				case KeyEvent.VK_DOWN:
					gameEngine.moveB(+20);
					break;
				case KeyEvent.VK_W:
					gameEngine.moveA(-20);
					break;
				case KeyEvent.VK_S:
					gameEngine.moveA(20);
					break;
				}
			}
		});
                dispose();
	
	}	
}
class ScoreLabel extends JFrame {
	
	public ScoreLabel(int aScore, int bScore){
		setTitle("Pong");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		
		JLabel score = new JLabel("Player A: " + aScore + "  Player B: " + bScore, JLabel.CENTER);
		score.setOpaque(true);	
		add(score, BorderLayout.SOUTH);		
  
	}
}
class PongSet extends JPanel {
	public JPanel dateTimePanel = new JPanel();  //panel to hold labels for date and time
        public JLabel dateLabel = new JLabel();
        public JLabel timeLabel = new JLabel();
        
	String message = "Press A to start";

	int aY;
	
	
	public void moveA(int d) {
		aY += d;
		
		// Player A left paddle cannot leave the screen from top or bottom
		if (aY <= paddleSize/2){
			aY = paddleSize/2;
		} else if (aY + paddleSize >= getHeight() + paddleSize/2){
			aY = getHeight() - paddleSize/2;
		} 
	}
	

	int bY;

	
	public void moveB(int d) {
		bY += d;

		if (bY <= paddleSize/2){
			bY = paddleSize/2;
		} else if (bY + paddleSize >= getHeight() + paddleSize/2){
			bY = getHeight() - paddleSize/2;
		}
	}
		

	static int paddleSize = 75;
		
	static int aScore;
	
	static int bScore;


	double ballX;
	
	double ballY;

	
	double dX = 10.0;
	
	double dY = 10.0;	

	int ballR = 10;
		
	boolean running = false;
	
	public PongSet() {
		super();
                
                dateTimePanel.setBounds(500,50,80,40);
                dateTimePanel.setBorder(BorderFactory.createLineBorder(Color.black));
                dateTimePanel.add(dateLabel);
                dateTimePanel.add(timeLabel);
                add(dateTimePanel);
                
                showDate();
                showTime();
		Timer timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (running) {
					updateGame();
				}
			}
		});
		timer.start();
	}
        public void showDate()
        {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        dateLabel.setText(dateFormat.format(date));
        }
        
        public void showTime()
        {
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Date date = new Date();
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
                timeLabel.setText(timeFormat.format(date));
            }
        }).start();
        }
	private void updateGame() {
		ballX += dX;
		ballY += dY;
		
		if (ballY + ballR > getHeight()) {
			double out = ballY + ballR - getHeight();
			ballY = getHeight() - ballR - out;  
			dY = -dY;
		}
		if (ballY - ballR < 0) {
			double out = ballR - ballY;
			ballY = ballR + out;
			dY = -dY;
		}
		
		if ((ballX - ballR < 15) && (ballY < aY + paddleSize/2) && (ballY > aY - paddleSize/2)) {
			dX = -dX;
		} else if (ballX - ballR < 0) { // If out of bounds, score increases for player B
			bScore+= 10;
                        Pong.score.setText("Player A: " + aScore + "  Player B: " + bScore);
			running = false;
		}
		
		if ((ballX + ballR > getWidth() - 15) && (ballY < bY + paddleSize/2) && (ballY > bY - paddleSize/2)){
			dX = -dX;
		} else if (ballX + ballR > getWidth()){ // If out of bounds, score increases for player A
			aScore+=10;
                        Pong.score.setText("Player A: " + aScore + "  Player B: " + bScore);
			running = false;
		}
                // Check if either player has reached 100 
		checkScore();
		/** Repaint after the move */
		repaint();
		
	}
    public  void checkScore() {
        if(aScore == 100){
            JOptionPane.showMessageDialog(null,
             "Player A won!!");
            new Menu().setVisible(true);
    
        }
        else if (bScore == 100){ 
                JOptionPane.showMessageDialog(null,
                "Player B won!!"); 
                new Menu().setVisible(true);
              
        }
    }

	public void start() {
		if (!running) {
			running = true;
			aY = bY = getHeight() / 2;
			ballX = getWidth() / 2;
			ballY = getHeight() / 2;
			repaint();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (running) {
			// Draw the ball
			int x = (int)(ballX - ballR);
			int y = (int)(ballY - ballR);
			int size = 2*ballR;
			g.setColor(Color.RED);
			g.fillOval(x, y, size, size);
			
			// Draw the paddles
			g.setColor(Color.BLUE);
			g.fillRect(5, aY - paddleSize/2, 15, paddleSize);
			g.setColor(Color.BLUE);
			g.fillRect(getWidth() - 20, bY - paddleSize/2, 15, paddleSize);

		} else {
			// If not running display a message 
			String message = "Press A to start";
			g.setColor(Color.BLACK);
			int h = g.getFontMetrics().getHeight();
			int w = g.getFontMetrics().stringWidth(message);
			g.drawString(message, getWidth()/2 - w/2, getHeight()/2 - h/2);

		}
	}

}


