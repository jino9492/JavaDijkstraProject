package Graph;

import javax.swing.*;
import javax.swing.event.AncestorListener;

import java.util.ArrayList;
import java.awt.*;import java.awt.dnd.DragSourceAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.event.*;


public class Swing extends JFrame implements ActionListener{
<<<<<<< HEAD
	protected Graph graph;
	protected Circle[] nodePos;
	protected ArrayList<Node>[] adj;
	protected int size;
	
=======
	private int i;
	private JPanel jp=new JPanel();
	private Graph graph;
	private Circle[] nodePos;
	private ArrayList<Node>[] adj;
	private int size;
	private int count;
>>>>>>> 9e94fb2db32bf493c3c1a6cea09c816f07c1c36b
	JTextField[] tf=new JTextField[11];
	JButton[] bt=new JButton[110];
	
	
  	public Swing(int size) {
<<<<<<< HEAD
        setTitle("Dijkstra Project");
=======
  		JPanel jp=new JPanel();
        setTitle("Drawing a Circle");
>>>>>>> 9e94fb2db32bf493c3c1a6cea09c816f07c1c36b
        setSize(1280 ,920);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createBufferStrategy(2);
        
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
    	
<<<<<<< HEAD
    	DragShapes draggable = new DragShapes();
    	
    	addMouseMotionListener(draggable);
    	addMouseListener(draggable);
=======
    	SetTextFiled();//텍스트 필드
>>>>>>> 9e94fb2db32bf493c3c1a6cea09c816f07c1c36b
    }

    @Override
    public void paint(Graphics g) {
    	super.paint(g);
    	
    	Graphics2D drawEdge = (Graphics2D) g;//줄
    	DrawEdge(drawEdge);
    	
		Graphics2D drawNode = (Graphics2D) g;//노드
		DrawNode(drawNode);
    	
        Graphics2D drawNodeNumber = (Graphics2D) g;//숫자
        DrawNodeNumber(drawNodeNumber);
        
<<<<<<< HEAD
        SetTextFiled();//텍스트 필드
=======
>>>>>>> 9e94fb2db32bf493c3c1a6cea09c816f07c1c36b
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
<<<<<<< HEAD
				g.draw(curLine);
				lines.add(curLine);
				g.draw(curLine);
=======
    			if(!lines.contains(curLine)) {
    				g.draw(curLine);
    				lines.add(curLine);
    				g.draw(curLine);
    			}
>>>>>>> 9e94fb2db32bf493c3c1a6cea09c816f07c1c36b
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
<<<<<<< HEAD
    
    public void actionPerformed(ActionEvent e) {
=======
    public void actionPerformed(ActionEvent e)
    {
>>>>>>> 9e94fb2db32bf493c3c1a6cea09c816f07c1c36b
    	if(e.getSource()==bt[1]) {
    		System.out.println(tf[1].getText());
    	}
<<<<<<< HEAD
	}

    
    
    public static void main(String[] args) {
    	new Swing(6);
	}
    
    public class DragShapes implements MouseListener, MouseMotionListener{

    	private boolean dragging; 
    	private float offsetX;
    	private float offsetY;
    	private int nodeNum;
    	
    	@Override
    	public void mouseDragged(MouseEvent ev){
    	    if (dragging){
    		    float mx = ev.getX();
			    float my = ev.getY();
			    nodePos[nodeNum].SetXPos(mx - offsetX);
			    nodePos[nodeNum].SetYPos(my - offsetY);
			    repaint();
    	    }
    	}
        
        @Override
        public void mousePressed(MouseEvent ev){
    	    float mx = ev.getX();
    	    float my = ev.getY();
    	    for (int i = 1; i < size + 1; i++) {
    		    if (mx > nodePos[i].GetXPos() - 50 && mx < nodePos[i].GetXPos() + 50 && my > nodePos[i].GetYPos() - 50 && my < nodePos[i].GetYPos() + 50){
    		        dragging = true;
    		        nodeNum = i;
    		        offsetX  = mx - nodePos[i].GetXPos();
    		        offsetY = my - nodePos[i].GetYPos();
    		    }
    	    }
        }
        
        @Override
        public void mouseReleased(MouseEvent ev){
        	dragging = false;
        }

    	@Override
    	public void mouseMoved(MouseEvent e) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void mouseClicked(MouseEvent e) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void mouseEntered(MouseEvent e) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void mouseExited(MouseEvent e) {
    		// TODO Auto-generated method stub
    		
    	}
    }
=======
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
    	}
    }
    public static void main(String[] args) { 
		new Swing(6); 
	}
	
>>>>>>> 9e94fb2db32bf493c3c1a6cea09c816f07c1c36b
}
