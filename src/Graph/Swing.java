package Graph;

import javax.swing.*;
import javax.swing.event.AncestorListener;

import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.*;


public class Swing extends JFrame implements ActionListener{
	private int i;
	private JPanel jp=new JPanel();
	private Graph graph;
	private Circle[] nodePos;
	private ArrayList<Node>[] adj;
	private int size;
	private int count;
	JTextField[] tf=new JTextField[11];
	JButton[] bt=new JButton[11];
	
	
  	public Swing(int size) {
  		JPanel jp=new JPanel();
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
    	
    	SetTextFiled();//텍스트 필드
    }

    @Override
    public void paint(Graphics g) {
    	Graphics2D drawEdge = (Graphics2D) g;//줄
    	DrawEdge(drawEdge);
    	
		Graphics2D drawNode = (Graphics2D) g;//노드
		DrawNode(drawNode);
    	
        Graphics2D drawNodeNumber = (Graphics2D) g;//숫자
        DrawNodeNumber(drawNodeNumber);
        
    }
    public void DrawNode(Graphics2D g) {
    	for (int i = 1; i < size + 1; i++) {
        	Shape circleShape = new Ellipse2D.Float(nodePos[i].GetXPos() - 50, nodePos[i].GetYPos() - 50, 100, 100);
        	g.setColor(Color.white);
        	g.fill(circleShape);
        	g.draw(circleShape);
        	this.setLayout(new BorderLayout());
        	this.setVisible(true);
        }  	
    }
    
    public void DrawEdge(Graphics2D g) {
    	g.setStroke(new BasicStroke(5));
    	g.setColor(Color.gray);
    	ArrayList<Line2D> lines = new ArrayList<>();
    	
    	for (int i = 1; i < size + 1; i++) {
    		for (int j = 0; j < adj[i].size();j++) {
    			Line2D curLine = new Line2D.Float(nodePos[i].GetXPos(), nodePos[i].GetYPos(), nodePos[adj[i].get(j).GetTargetNode()].GetXPos(), nodePos[adj[i].get(j).GetTargetNode()].GetYPos());
    			if(!lines.contains(curLine)) {
    				g.draw(curLine);
    				lines.add(curLine);
    				g.draw(curLine);
    			}
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
    	count=1;
    	add(jp);
    	ArrayList<Integer> listx=new ArrayList();
    	ArrayList<Integer> listy=new ArrayList();
    	for (int i = 1; i < size + 1; i++) {
    		for (int j = 0; j < adj[i].size();j++) {
    			int XPos=(int) (nodePos[i].GetXPos()+nodePos[adj[i].get(j).GetTargetNode()].GetXPos())/2-50;
    			int YPos=(int)(nodePos[i].GetYPos()+nodePos[adj[i].get(j).GetTargetNode()].GetYPos())/2-50;
    			int x = 0,y=0;
    			if(listx.contains(XPos))
    				x=1;
    			if(listy.contains(YPos))
    				y=1;
    			if(x*y==0) {
    				System.out.println(count);
    				listx.add(XPos);listy.add(YPos);
    				tf[count]=new JTextField(".");
    				this.add(tf[count]);
    				tf[count].setBounds(XPos,YPos,60,40);
    				bt[count]=new JButton(""+count);
    				this.add(bt[count]);
    				bt[count].setBounds(XPos+60,YPos,40,40);
    				count++;
    			}
    		}
    	}
    	for(int i=1;i<11;i++) {
    		bt[i].addActionListener(this);
    	}
    	this.setLayout(new BorderLayout());
    	this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
    	for(int i=1;i<11;i++) {
    		if(e.getSource()==bt[i]) {
    			System.out.println(tf[i].getText());
    		}
    	}
    	/*if(e.getSource()==bt[1]) {
    		System.out.println(tf[1].getText());
    	}
    	if(e.getSource()==bt[2]) {
    		System.out.println(tf[2].getText());
    	}
    	if(e.getSource()==bt[3]) {
    		System.out.println(tf[3].getText());
    	}
    	if(e.getSource()==bt[4]) {
    		System.out.println(tf[4].getText());
    	}
    	if(e.getSource()==bt[5]) {
    		System.out.println(tf[5].getText());
    	}
    	if(e.getSource()==bt[6]) {
    		System.out.println(tf[6].getText());
    	}
    	if(e.getSource()==bt[7]) {
    		System.out.println(tf[7].getText());
    	}
    	if(e.getSource()==bt[8]) {
    		System.out.println(tf[8].getText());
    	}
    	if(e.getSource()==bt[9]) {
    		System.out.println(tf[9].getText());
    	}
    	if(e.getSource()==bt[10]) {
    		System.out.println(tf[10].getText());
    	}*/
    }
    public static void main(String[] args) { 
		new Swing(6); 
	}
	
}
