package Graph;

import javax.swing.*;

import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Random;

public class Swing extends JFrame{
	private Graph graph = new Graph(6);
	
	  public Swing() {
	        setTitle("Drawing a Circle");
	        setSize(1280 ,920);
	        setVisible(true);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);

	    }

	    @Override
	    public void paint(Graphics g) {
	    	Circle[] nodePos = new Circle[7];
	    	
	    	nodePos[0] = null;
	    	nodePos[1] = new Circle(200,150); // 1
	    	nodePos[2] = new Circle(600,100); // 2
	    	nodePos[3] = new Circle(800,300); // 3
	    	nodePos[4] = new Circle(300,250); // 4
	    	nodePos[5] = new Circle(500, 400); // 5
	    	nodePos[6] = new Circle(250,600); // 6
	    	
	    	graph.ConnectNodes(1, 2, 2);
			graph.ConnectNodes(1, 3, 5);
			graph.ConnectNodes(1, 4, 1);
			
			graph.ConnectNodes(2, 1, 2);
			graph.ConnectNodes(2, 3, 3);
			graph.ConnectNodes(2, 4, 2);
			
			graph.ConnectNodes(3, 1, 5);
			graph.ConnectNodes(3, 2, 3);
			graph.ConnectNodes(3, 4, 3);
			graph.ConnectNodes(3, 5, 1);
			graph.ConnectNodes(3, 6, 5);
			
			graph.ConnectNodes(4, 1, 1);
			graph.ConnectNodes(4, 2, 2);
			graph.ConnectNodes(4, 3, 3);
			graph.ConnectNodes(4, 5, 1);
			
			graph.ConnectNodes(5, 3, 1);
			graph.ConnectNodes(5, 4, 1);
			graph.ConnectNodes(5, 6, 2);
			
			graph.ConnectNodes(6, 3, 5);
			graph.ConnectNodes(6, 5, 2);
			
			ArrayList<Node>[] adj = graph.GetAdj();
			
			Graphics2D g2d2 = (Graphics2D) g;
			for (int i = 1; i < 6 + 1; i++) {
				for (int j = 0; j < adj[i].size(); j++) {
					g2d2.setStroke(new BasicStroke(10));
					g2d2.setColor(Color.black);
					g2d2.draw(new Line2D.Float(nodePos[i].GetXPos(), nodePos[i].GetYPos(), nodePos[adj[i].get(j).GetTargetNode()].GetXPos(), nodePos[adj[i].get(j).GetTargetNode()].GetYPos()));
				}
			}
	    	
	        Graphics2D g2d1 = (Graphics2D) g;
	        for (int i = 1; i < 6 + 1; i++) {
	        	Shape circleShape = new Ellipse2D.Float(nodePos[i].GetXPos() - 50, nodePos[i].GetYPos() - 50, 100, 100);
	        	g2d1.draw(circleShape);
	        	g2d1.setColor(Color.green);
	        	g2d1.fill(circleShape);
	        }
	        	
	    }
	public static void main(String[] args) { 
			new Swing(); 
		}
}
