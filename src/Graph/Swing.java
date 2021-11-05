package Graph;

import javax.swing.*;

import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Random;



public class Swing extends JFrame{
	private JPanel jp = new JPanel();
	
	private Graph graph;
	private Circle[] nodePos;
	private ArrayList<Node>[] adj;
	private int size;
	ArrayList<JTextField> tf;
	
  	public Swing(int size) {
        setTitle("Drawing a Circle");
        setSize(1280 ,920);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.size = size;
        
        graph = new Graph(size);
    	
    	nodePos = new Circle[size + 1];
    	
    	nodePos[0] = null;
    	nodePos[1] = new Circle(200,150); // 1
    	nodePos[2] = new Circle(600,100); // 2
    	nodePos[3] = new Circle(800,300); // 3
    	nodePos[4] = new Circle(300,250); // 4
    	nodePos[5] = new Circle(500, 400); // 5
    	nodePos[6] = new Circle(400,600); // 6
    	
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
    	
    	adj = graph.GetAdj();
    	
    }

    @Override
    public void paint(Graphics g) {
    	Graphics2D drawEdge = (Graphics2D) g;//줄
    	DrawEdge(drawEdge);
    	
		Graphics2D drawNode = (Graphics2D) g;//노드
		DrawNode(drawNode);
    	
        Graphics2D drawNodeNumber = (Graphics2D) g;//숫자
        DrawNodeNumber(drawNodeNumber);
        
        SetTextFiled();
    }
    
    public void DrawNode(Graphics2D g) {
    	for (int i = 1; i < size + 1; i++) {
        	Shape circleShape = new Ellipse2D.Float(nodePos[i].GetXPos() - 50, nodePos[i].GetYPos() - 50, 100, 100);
        	g.setColor(Color.white);
        	g.fill(circleShape);
        	g.draw(circleShape);
        }  	
    }
    
    public void DrawEdge(Graphics2D g) {
    	g.setStroke(new BasicStroke(5));
    	g.setColor(Color.gray);
//    	ArrayList<Line2D> lines = new ArrayList<>();
    	
    	for (int i = 1; i < size + 1; i++) {
    		for (int j = 0; j < adj[i].size();j++) {
    			Line2D curLine = new Line2D.Float(nodePos[i].GetXPos(), nodePos[i].GetYPos(), nodePos[adj[i].get(j).GetTargetNode()].GetXPos(), nodePos[adj[i].get(j).GetTargetNode()].GetYPos());
//    			if(!lines.contains(curLine)) {
//    				g.draw(curLine);
//    				lines.add(curLine);
//    				System.out.println(curLine);
    			g.draw(curLine);
    			}
    		}
    	}
    
    public void DrawNodeNumber(Graphics2D g) {
    	Font font = new Font("Ariel", Font.BOLD, 24);
    	g.setColor(Color.black);
    	g.setFont(font);
    	
    	for (int i = 1; i < size + 1; i++) {
    		g.drawString(Integer.toString(i), nodePos[i].GetXPos() - 8, nodePos[i].GetYPos() + 8);
    	}
    }
    public void SetTextFiled() {
    	for(int i=1;i<size+1;i++){
    		tf.setBounds(4, 4, 340, 330);
    	}
    }
    
    public static void main(String[] args) { 
		new Swing(6); 
	}
}
