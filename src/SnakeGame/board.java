package SnakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class board extends JPanel implements ActionListener{
	
	private int applesEaten=0;
	private int dots;
	private Image apple;
	private Image head;
	private Image dot;
	
	private final int All_Dots=900;
	private final int dot_size=10;
	
	private final int random_pos=29;
	
	private int apple_x;
	private int apple_y;
	
	private boolean Right_direction=true;
	private boolean Left_direction=false;
	private boolean Up_direction=false;
	private boolean Down_direction=false;
	
	
	private final int x[]= new int[All_Dots];
	private final int y[]= new int[All_Dots];
	
	private Timer timer;
	
	private boolean ingame=true;
	
	
	board()
	{
		addKeyListener(new TAdapter());
		
		setBackground(Color.black);
		setPreferredSize(new Dimension(300, 300));
		setFocusable(true);
		initGame();
		loadImages();
	}

	private void loadImages() 
	{
		ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
		apple=i1.getImage();
		
		ImageIcon i2= new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
		head=i2.getImage();
		
		ImageIcon i3= new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
		dot=i3.getImage();
		
	}

	private void initGame() 
	{
		dots=3;
		
		for(int i=0;i<dots;i++)
		{
			y[i]=50;
			x[i]=50-(i*dot_size);
		}
		
		locateapple();
		
		timer= new Timer(140, this);
		timer.start();
		
	}
	
	private void locateapple() 
	{
		int r= (int)(Math.random()*random_pos);
		apple_x=r*dot_size;
		
		r= (int)(Math.random()*random_pos);
		apple_y=r*dot_size;
		
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}

	private void draw(Graphics g) {
		
		if(ingame)
		{
			String scoreString="SCORE: ";
			Font font=new Font("SAN_SERIF", Font.BOLD, 14);
			FontMetrics metrices= getFontMetrics(font);
			g.setColor(Color.white);
		g.drawImage(apple, apple_x, apple_y, this);
		g.drawString(scoreString+applesEaten, (300-metrices.stringWidth(scoreString))/2, 15);
		
		for(int i=0;i<dots;i++)
		{
			if(i==0)
			{
				g.drawImage(head, x[i], y[i], this);
			}
			else 
				{
					g.drawImage(dot, x[i], y[i], this);
				}
		}
		
		Toolkit.getDefaultToolkit().sync();
		}
		else {
			{
				gameover(g);
			}
		}
		
	}

	private void gameover(Graphics g) {
		
		String msgString="GAME OVER!";
		String scoreString="SCORE: ";
		Font font=new Font("SAN_SERIF", Font.BOLD, 14);
		FontMetrics metrices= getFontMetrics(font);
		
		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(msgString, (300-metrices.stringWidth(msgString))/2, 300/2);
		g.drawString(scoreString+applesEaten, (300-metrices.stringWidth(scoreString))/2, 170);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(ingame)
		{
		move();
		checkApple();
		checkCollision();
		}
		
		repaint();
	}

	private void checkCollision() {
		
		for(int i=dots;i>0;i--)
		{
			if((i>4) && x[0]==x[i] && y[0]==y[i])
				ingame=false;
		}
		
		if(x[0]>=300)
			ingame=false;
		
		if(y[0]>=300)
			ingame=false;
		
		if(x[0]<0)
			ingame=false;
		
		if(y[0]<0)
			ingame=false;
		
		if(!ingame)
			timer.stop();
	}

	private void checkApple() {
		
		if(x[0]==apple_x && y[0]==apple_y)
		{
			dots++;
			applesEaten++;
			locateapple();
		}
		
	}

	private void move() {
		for(int i=dots;i>0;i--)
		{
			x[i]=x[i-1];
			y[i]=y[i-1];
			
		}
		
		if(Right_direction)
			x[0]=x[0]+dot_size;
		if(Left_direction)
			x[0]=x[0]-dot_size;
		if(Up_direction)
			y[0]=y[0]-dot_size;
		if (Down_direction) 
			y[0]=y[0]+dot_size;	
	}
	
	public class TAdapter extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			int key=e.getKeyCode();
			
			if(key==KeyEvent.VK_LEFT && (!Right_direction))
			{
				Left_direction=true;
				Up_direction=false;
				Down_direction=false;
			}
			
			if(key==KeyEvent.VK_RIGHT && (!Left_direction))
			{
				Right_direction=true;
				Up_direction=false;
				Down_direction=false;
			}
			
			if(key==KeyEvent.VK_UP && (!Down_direction))
			{
				Up_direction=true;
				Left_direction=false;
				Right_direction=false;
			}
			
			if(key==KeyEvent.VK_DOWN && (!Up_direction))
			{
				Down_direction=true;
				Left_direction=false;
				Right_direction=false;
			}
		}
	}
	
	
}
