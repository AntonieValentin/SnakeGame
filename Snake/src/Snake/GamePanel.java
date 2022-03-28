package Snake;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.*;

import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	static final int latime = 600;
	static final int inaltime = 600;
	static final int unitsize = 25;
	static final int nrunits = (latime * inaltime) / unitsize;
	static final int delay = 75;
	final int x[] = new int[nrunits];
	final int y[] = new int[nrunits];
	int parti = 6;
	int nrmar;
	int marx;
	int mary;
	char directie = 'D';
	boolean alergare = false;
	Timer timer;
	Random random;
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(latime,inaltime));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new KeyAd());
		start();
	}
	
	public void start() {
		marnou();
		alergare = true;
		timer = new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		draw(g);
		if(!alergare) {
		gover(g);
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.gray);
		for(int i=0;i<inaltime/unitsize;i++) {
			g.drawLine(i*unitsize,0,i*unitsize,inaltime);
			g.drawLine(0,i*unitsize,latime,i*unitsize);
		}
		g.setColor(Color.red);
		g.fillRect(marx, mary, unitsize, unitsize);
		for(int i=0;i<parti;i++) {
			if(i == 0) {
				g.setColor(Color.green);
				g.fillRect(x[i], y[i], unitsize, unitsize);
			}
			else {
				g.setColor(Color.yellow);
				g.fillRect(x[i], y[i], unitsize, unitsize);
			}
		}
	}
	
	public void marnou() {
		marx = random.nextInt((int)(latime/unitsize))*unitsize;
		mary = random.nextInt((int)(inaltime/unitsize))*unitsize;
	}
	
	public void move() {
		for(int i=parti;i>=1;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(directie) {
		case 'S':
			y[0] = y[0] - unitsize;
			break;
		case 'J':
			y[0] = y[0] + unitsize;
			break;
		case 's':
			x[0] = x[0] - unitsize;
			break;
		case 'D':
			x[0] = x[0] + unitsize;
			break;
		}
	}
	
	public void eat() {
		if((x[0] == marx) && (y[0] == mary)) {
			parti++;
			nrmar++;
			marnou();
		}
	}
	
	public void coll() {
		
		for(int i=parti;i>=1;i--) {
			if((x[0] == x[i]) && (y[0] == y[i])){
				alergare = false;
			}
		}
		if(x[0] < 0) {
			alergare = false;
		}
		if(x[0] > latime) {
			alergare = false;
		}
		if(y[0] < 0) {
			alergare = false;
		}
		if(y[0] > inaltime) {
			alergare = false;
		}
		if(!alergare) {
			timer.stop();
		}
	}
	
	public void gover(Graphics g) {
		g.setColor(Color.green);
		g.setFont(new Font("Courier",Font.BOLD,75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (latime - metrics.stringWidth("Game Over"))/2,inaltime/2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(alergare) {
			move();
			eat();
			coll();
		}
		repaint();
		
	}
	
	public class KeyAd extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_A:
				if(directie != 'D') {
					directie = 's';
				}
				break;
			case KeyEvent.VK_D:
				if(directie != 's') {
					directie = 'D';
				}
				break;
			case KeyEvent.VK_W:
				if(directie != 'J') {
					directie = 'S';
				}
				break;
			case KeyEvent.VK_S:
				if(directie != 'S') {
					directie = 'J';
				}
				break;
			}
		}
		
	}
	
}
