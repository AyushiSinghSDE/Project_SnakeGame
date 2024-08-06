package SnakeGame;

import javax.swing.JFrame;

public class SnakeGame extends JFrame{

	public SnakeGame()
	{	
		setTitle("Snake Game");
		
		add(new board());
		pack();
		
		//setSize(300, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String args[])
	{
		new SnakeGame();
	}
}
