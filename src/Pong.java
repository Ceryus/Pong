//Name -
//Date -
//Class -
//Lab  - 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;

public class Pong extends Canvas implements KeyListener, Runnable
{
	private int leftScore, rightScore;
	private Ball ball;
	private Paddle leftPaddle;
	private Paddle rightPaddle;
	private boolean[] keys;
	private BufferedImage back;
	private Dimension size;


	public Pong(int w, int h)
	{
		//set up all variables related to the game
		size = new Dimension(w-50, h-50);
		ball = new Ball((int)size.getWidth()/2,(int)size.getHeight()/2, 5,5,Color.red,1,1);
		
		rightPaddle = new Paddle((int)size.getWidth()-10, (int)size.getHeight()/2, 10, 50, Color.black, 2);
		leftPaddle = new Paddle((int)size.getWidth()-((int)size.getWidth()-10),(int)size.getHeight()/2, 10, 50, Color.black, 2);
		ball.setXSpeed(2);
		ball.setYSpeed(1);


		keys = new boolean[4];

    
    	setBackground(Color.WHITE);
		setVisible(true);
		
		new Thread(this).start();
		
		addKeyListener(this);		//starts the key thread to log key strokes
	}
	
   public void update(Graphics window){
	   paint(window);
   }

   public void paint(Graphics window)
   {
	   size = this.getSize();
		//set up the double buffering to make the game animation nice and smooth
		Graphics2D twoDGraph = (Graphics2D)window;

		//take a snap shop of the current screen and same it as an image
		//that is the exact same width and height as the current screen
		if(back==null)
		   back = (BufferedImage)(createImage(getWidth(),getHeight()));

		//create a graphics reference to the back ground image
		//we will draw all changes on the background image
		Graphics graphToBack = back.createGraphics();


		ball.moveAndDraw(graphToBack);
		leftPaddle.draw(graphToBack);
		rightPaddle.draw(graphToBack);


		//see if ball hits left wall or right wall
		if(!(ball.getX()<=(int)size.getWidth()-10 && ball.getX()>=(int)size.getWidth()-((int)size.getWidth()-10)))
		{
			ball.setXSpeed(0);
			ball.setYSpeed(0);
			Color c = graphToBack.getColor();
			graphToBack.setColor(Color.white);
			graphToBack.fillRect(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
			graphToBack.setColor(c);
			ball.setPos((int)size.getWidth()/2, (int)size.getHeight()/2);
			
			ball.setXSpeed((int)Math.ceil(Math.random()*2));
			ball.setYSpeed((int)Math.ceil(Math.random()*2));
		}

		
		//see if the ball hits the top or bottom wall 
		if(ball.getX() < (int)size.getWidth()-((int)size.getWidth()-10))
		{
			setRightScore(getRightScore()+1);
		}

		if(ball.getX() > (int)size.getWidth()-10)
		{
			setLeftScore(getLeftScore()+1);
		}
		
		if(!(ball.getY() >= size.getHeight()-(size.getHeight()-15) && ball.getY() <= size.getHeight() - 15))
		{
			ball.setYSpeed(-ball.getYSpeed());
		}

		if
		(
			(ball.getX() >=	leftPaddle.getX()+leftPaddle.getWidth()-10 && ball.getX() <= leftPaddle.getX()+leftPaddle.getWidth()) &&
			(ball.getY() >= leftPaddle.getY() && ball.getY() <= leftPaddle.getY()+leftPaddle.getHeight())	
		)
		{
			if(ball.getX() <= leftPaddle.getX()+leftPaddle.getWidth()-Math.abs(ball.getXSpeed()))
			{
				ball.setYSpeed(-ball.getYSpeed());
			}
			else 
			{
				ball.setXSpeed(-ball.getXSpeed());
			}
		}
		
		if
		(
				((ball.getX() >= rightPaddle.getX()-2 && ball.getX() <= rightPaddle.getX()+5)) &&
				(ball.getY() >= rightPaddle.getY() && ball.getY() <= rightPaddle.getY()+rightPaddle.getHeight())
		)
		{
			if(ball.getX() >= rightPaddle.getX()+Math.abs(ball.getXSpeed()))
			{
				ball.setYSpeed(-ball.getYSpeed());
			}
			else 
			{
				ball.setXSpeed(-ball.getXSpeed());
			}
		}
		//see if the ball hits the left paddle
		
		
		
		//see if the ball hits the right paddle
		
		
		


		//see if the paddles need to be moved
		if(keys[0] == true)
		{
			leftPaddle.moveDownAndDraw(graphToBack);
		}
		if(keys[1] == true)
		{
			leftPaddle.moveUpAndDraw(graphToBack);
		}
		if(keys[2] == true)
		{
			rightPaddle.moveDownAndDraw(graphToBack);
		}
		if(keys[3] == true)
		{
			rightPaddle.moveUpAndDraw(graphToBack);
		}














		
		twoDGraph.drawImage(back, null, 0, 0);
	}

	public void keyPressed(KeyEvent e)
	{
		switch(toUpperCase(e.getKeyChar()))
		{
			case 'W' : keys[0]=true; break;
			case 'Z' : keys[1]=true; break;
			case 'I' : keys[2]=true; break;
			case 'M' : keys[3]=true; break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(toUpperCase(e.getKeyChar()))
		{
			case 'W' : keys[0]=false; break;
			case 'Z' : keys[1]=false; break;
			case 'I' : keys[2]=false; break;
			case 'M' : keys[3]=false; break;
		}
	}

	public void keyTyped(KeyEvent e){}
	public int getLeftScore()
	{
		return leftScore;
	}
	
	public int getRightScore()
	{
		return rightScore;
	}
	
	public void setRightScore(int rightScore)
	{
		this.rightScore = rightScore;
	}
	
	public void setLeftScore(int leftScore)
	{
		this.leftScore = leftScore;
	}
	
   public void run()
   {
   	try
   	{
   		while(true)
   		{
   		   Thread.currentThread().sleep(8);
            repaint();
         }
      }catch(Exception e)
      {
      }
  	}	
}