package Graph;

import javax.swing.*;
import javax.swing.event.AncestorListener;

import Graph.Swing.Panel;

import java.util.ArrayList;
import java.awt.*;import java.awt.dnd.DragSourceAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Swing extends JFrame implements ActionListener{
	protected Graph graph;
	protected ArrayList<Circle> nodePos;
	protected ArrayList<ArrayList<Node>> adj;
	protected int size;
	private int count;
	
	Panel panel = new Panel();
	ArrayList<JTextField> tf = new ArrayList<JTextField>();
	ArrayList<JButton> bt = new ArrayList<JButton>();
	ToolBar toolBar = new ToolBar();
	
	
  	public Swing(int size) {
        setTitle("Dijkstra Project");
        setSize(1600 ,900);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setLocationRelativeTo(null);
    	setLayout(new BorderLayout());
        
        this.size = size;
        graph = new Graph(size);
    	nodePos = new ArrayList<Circle>();
    	UI ui=new UI(size);
    	
    	nodePos.add(null);
    	nodePos.add(new Circle(200,150)); // 1
    	nodePos.add(new Circle(600,100)); // 2
    	nodePos.add(new Circle(800,300)); // 3
    	nodePos.add(new Circle(300,250)); // 4
    	nodePos.add(new Circle(500,400)); // 5
    	nodePos.add(new Circle(400,600)); // 6
    	
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
    	
    	
    	
    	add(panel, BorderLayout.CENTER);
    	//SetTextFiled();
    	add(toolBar, BorderLayout.PAGE_START);
    	add(ui,BorderLayout.EAST);
    	revalidate();
    }

    
    /*public void SetTextFiled() {
    	count=1;
    	ArrayList<Integer> listx = new ArrayList();
    	ArrayList<Integer> listy = new ArrayList();
    	for (int i = 1; i < size + 1; i++) {
    		for (int j = 0; j < adj.get(i).size();j++) {
    			int XPos=(int) (nodePos.get(i).GetXPos()+nodePos.get(adj.get(i).get(j).GetTargetNode()).GetXPos())/2-50;
    			int YPos=(int) (nodePos.get(i).GetYPos()+nodePos.get(adj.get(i).get(j).GetTargetNode()).GetYPos())/2-50;
    			int x = 0,y=0;
    			if(listx.contains(XPos))
    				x=1;
    			if(listy.contains(YPos))
    				y=1;
    			if(x*y==0) {
    				listx.add(XPos);listy.add(YPos);
    				tf.add(new JTextField("."));
    				add(tf.get(count));
    				tf.get(count).setBounds(XPos, YPos, 60, 40);
    				bt[count]=new JButton(""+count);
    				add(bt[count]);
    				bt[count].setBounds(XPos+60,YPos,40,40);
    				bt[count].addActionListener(this);
    				count++;
    			}
    		}
    	}
    	this.setVisible(true);
    }*/
    
    public void actionPerformed(ActionEvent e) {
    	for(int i=1;i<11;i++) {
    		//if(e.getSource()==bt[i]) {
    			//System.out.println(tf[i].getText());
    		}
    	}
	

    
    
    public static void main(String[] args) {
    	new Swing(6);
	}
    
    public class Events implements MouseListener, MouseMotionListener{

    	private boolean dragging; 
    	private float offsetX;
    	private float offsetY;
    	private int nodeNum;
    	
    	@Override
    	public void mouseDragged(MouseEvent e){
    		//드래그
    	    if (dragging){
    		    float mx = e.getX();
			    float my = e.getY();
			    nodePos.get(nodeNum).SetXPos(mx - offsetX);
			    nodePos.get(nodeNum).SetYPos(my - offsetY);
    	    }
    	}
        
        @Override
        public void mousePressed(MouseEvent e){
        	// 드래그 체크
    	    float mx = e.getX();
    	    float my = e.getY();
    	    for (int i = 1; i < nodePos.size(); i++) {
    		    if (mx > nodePos.get(i).GetXPos() - 50 && mx < nodePos.get(i).GetXPos() + 50 && my > nodePos.get(i).GetYPos() - 50 && my < nodePos.get(i).GetYPos() + 50){
    		        dragging = true;
    		        nodeNum = i;
    		        offsetX  = mx - nodePos.get(i).GetXPos();
    		        offsetY = my - nodePos.get(i).GetYPos();
    		    }
    	    }
        }
        
        @Override
        public void mouseReleased(MouseEvent e){
        	dragging = false;
        }

    	@Override
    	public void mouseMoved(MouseEvent e) {}

    	int edgeCount = 0;
    	ArrayList<Integer> node = new ArrayList<Integer>();
    	
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		float mx = e.getX();
    	    float my = e.getY();
    	    
    	    
    		// 노드 추가
    		if (toolBar.clickedInsertNode) {
    			
        	    graph.GetPath().add(new ArrayList<Integer>());
        	    graph.GetDist().add(Float.POSITIVE_INFINITY);
        	    graph.GetAdj().add(new ArrayList<Node>());
        	    nodePos.add(new Circle(mx, my));
        	    
        	    toolBar.jButton.get(0).setBackground(Color.darkGray);
        	    toolBar.jButton.get(0).setForeground(Color.white);
        	    
        	    panel.repaint();
    			
    			toolBar.clickedInsertNode = false;
    		}
    		
    		// 간선 추가
    		if (toolBar.clickedInsertEdge) {
    			for (int i = 1; i < nodePos.size(); i++) {
        		    if (mx > nodePos.get(i).GetXPos() - 50 && mx < nodePos.get(i).GetXPos() + 50 && my > nodePos.get(i).GetYPos() - 50 && my < nodePos.get(i).GetYPos() + 50){
        		    	if (edgeCount == 1)
        		    		if (node.get(0).equals(i))
        		    			break;
        		    	
        		    	node.add(i);
        		    	edgeCount++;
        		    }
    			}
    			
    			if (edgeCount == 2) {
    				boolean isConnected = false;
    				for (int i = 0; i < adj.get(node.get(0)).size(); i++)
    					if (adj.get(node.get(0)).get(i).GetTargetNode() == node.get(1))
    						isConnected = true;
    				
    				if (!isConnected) {
    					System.out.println(node.get(0));
	    				graph.ConnectNodes(node.get(0), node.get(1));
	    				graph.ConnectNodes(node.get(1), node.get(0));
    				}
    				
    				toolBar.jButton.get(1).setBackground(Color.darkGray);
            	    toolBar.jButton.get(1).setForeground(Color.white);
            	    
            	    panel.repaint();
    				
            	    toolBar.clickedInsertEdge = false;
    				edgeCount = 0;
    				node.clear();
    			}
    		}
    	}

    	@Override
    	public void mouseEntered(MouseEvent e) {}

    	@Override
    	public void mouseExited(MouseEvent e) {}
    }

    public class UI extends JPanel implements ActionListener{
    	JPanel uiPanel;
    	public UI(int size) {
    		count=1;
    		uiPanel=new JPanel();
    		uiPanel.setLayout(new BoxLayout(uiPanel,BoxLayout.Y_AXIS));
    		uiPanel.setBackground(Color.WHITE);
    		add(uiPanel);
    		tf.add(null);
    		bt.add(null); 
    		for(int i=1;i<size+1;i++) {
    				tf.add(new JTextField());
    				//tf.get(count).setBounds(600,50+count*50,1,4);
    				//add(tf.get(count));
    				bt.add(new JButton(i+","));
    				//bt.get(count).setBounds(650,50+count*50,1,4);
    				//add(bt.get(count));
    				//uiPanel.add(tf.get(count));
    				//uiPanel.add(bt.get(count));
    				uiPanel.add(bt.get(i));
    				uiPanel.add(tf.get(i));
    				bt.get(i).setPreferredSize(new Dimension(50, 50));
    				tf.get(i).setPreferredSize(new Dimension(50, 50));
    				bt.get(i).addActionListener(this);
    				count++;
    		}
    	}
    	@Override
		public void actionPerformed(ActionEvent e) {
    		int k=6;//간선의수
    			if(e.getSource()==bt.get(1)) {//1개빡에 안됨
    				System.out.println(tf.get(1).getText());
    			}
    	}
    }


    public class Panel extends JPanel{
    	Image buffImg;
        Graphics buffG;
        Events events;
        
    	public Panel() {
    		events = new Events();
        	addMouseMotionListener(events);
        	addMouseListener(events);
			repaint();
		}
    	
    	@Override
        public void paint(Graphics g) {
        	buffImg = createImage(getWidth(), getHeight());
        	buffG = buffImg.getGraphics();
            
            update(g);
        }
        
        @Override
        public void update(Graphics g) {
        	
        	Graphics2D drawEdge = (Graphics2D) buffG;//줄
        	DrawEdge(drawEdge);
        	
    		Graphics2D drawNode = (Graphics2D) buffG;//노드
    		DrawNode(drawNode);
        	
            Graphics2D drawNodeNumber = (Graphics2D) buffG;//숫자
            DrawNodeNumber(drawNodeNumber);
            
            
            g.drawImage(buffImg, 0, 0, this);
            repaint();
        }
        
        public void DrawNode(Graphics2D g) {
        	for (int i = 1; i < nodePos.size(); i++) {
            	Shape circleShape = new Ellipse2D.Float(nodePos.get(i).GetXPos() - 50, nodePos.get(i).GetYPos() - 50, 100, 100);
            	g.setColor(Color.white);
            	if (events.node.size() > 0) {
	            	for (int j = 0; j < events.node.size(); j++) {
	            		if (events.node.get(j).equals(i))
	            			g.setColor(Color.red);
	            	}            	
            	}
            	g.fill(circleShape);
            	g.draw(circleShape);
            }  	
        }
        
        public void DrawEdge(Graphics2D g) {
        	g.setStroke(new BasicStroke(5));
        	g.setColor(Color.gray);
        	ArrayList<Line2D> lines = new ArrayList<>();
        	
        	for (int i = 1; i < size + 1; i++) {
        		for (int j = 0; j < adj.get(i).size();j++) {
        			Line2D curLine = new Line2D.Float(nodePos.get(i).GetXPos(), nodePos.get(i).GetYPos(), nodePos.get(adj.get(i).get(j).GetTargetNode()).GetXPos(), nodePos.get(adj.get(i).get(j).GetTargetNode()).GetYPos());
    				g.draw(curLine);
    				lines.add(curLine);
    				g.draw(curLine);
        		}
        	}
        }
        
        public void DrawNodeNumber(Graphics2D g) {
        	Font font = new Font("Ariel", Font.BOLD, 24);
        	g.setColor(Color.black);
        	g.setFont(font);
        	for (int i = 1; i < nodePos.size(); i++) {
        		g.drawString(Integer.toString(i), nodePos.get(i).GetXPos() - 8, nodePos.get(i).GetYPos() + 8);
        	}
        }
    }

    public class ToolBar extends JToolBar implements ActionListener{
    	
    	int count = 0;
    	JPanel jPanel;
    	ArrayList<JButton> jButton;
    	Color defaultButtonColor = Color.darkGray;
    	
    	public boolean clickedInsertNode;
    	public boolean clickedInsertEdge;
    	
    	public ToolBar() {
    		jPanel = new JPanel();
    		jButton = new ArrayList<JButton>();
    		
    		createButton("○", 60, 40);
    		createButton("／", 60, 40);
    		
    		jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    		
    		for (int i = 0; i < count; i++) {
	    		jPanel.add(jButton.get(i));
	    		add(jPanel);
    		}
    	}

		@Override
		public void actionPerformed(ActionEvent e) {
			actionInit();
			
			// 노드 추가 버튼을 눌렀을 때
			if (e.getSource() == jButton.get(0)) {
				jButton.get(0).setBackground(Color.white);
				jButton.get(0).setForeground(Color.darkGray);
				clickedInsertNode = true;
			}
			// 간선 추가 버튼을 눌렀을 때
			if (e.getSource() == jButton.get(1)) {
				jButton.get(1).setBackground(Color.white);
				jButton.get(1).setForeground(Color.darkGray);
				clickedInsertEdge = true;
			}
		}
		
		public void actionInit() {
			for (int i = 0; i < jButton.size(); i++) {
				jButton.get(i).setBackground(Color.darkGray);
				jButton.get(i).setForeground(Color.white);
				clickedInsertNode = false;
				clickedInsertEdge = false;
			}
		}
		
		public void createButton(String CONTENT, int WIDTH, int HEIGHT) {
			jButton.add(new JButton(CONTENT));
			
			jButton.get(count).setForeground(Color.white);
			jButton.get(count).setPreferredSize(new Dimension(WIDTH, HEIGHT));
			jButton.get(count).setBackground(defaultButtonColor);
			jButton.get(count).addActionListener(this);
			
			count++;
		}
		
		public void createButton(String CONTENT, int WIDTH, int HEIGHT, Color COLOR) {
			jButton.add(new JButton(CONTENT));
			
			jButton.get(count).setPreferredSize(new Dimension(WIDTH, HEIGHT));
			jButton.get(count).setBackground(COLOR);
			
			count++;
		}
    }
}

