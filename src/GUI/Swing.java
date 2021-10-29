package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Swing extends JFrame{
	  public Swing() {
	        setTitle("Drawing a Circle");
	        setSize(1280 ,920);
	        setVisible(true);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);

	    }

	    @Override
	    public void paint(Graphics g) {
	    	Random random = new Random();
	    	Circle[] circle = new Circle[10];
	    	
	    	for(int i = 0; i < 10; i++) {
	    		circle[i] = new Circle(/*(float)(random.nextInt(1180)), (float)(random.nextInt(820))*/ i * 50 , i * 50);
	    	}
	    	
	        Graphics2D g2d = (Graphics2D) g;
	        for (int i = 0; i < 10; i++) {
	        	Shape circleShape = new Ellipse2D.Double(circle[i].xPos, circle[i].yPos, 100, 100);
	        	g2d.draw(circleShape);
	        }
	        	
	    }
	public static void main(String[] args) { 
			new Swing(); 
		}
}
