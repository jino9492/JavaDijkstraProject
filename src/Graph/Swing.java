package Graph;

import javax.swing.*;
import javax.swing.event.AncestorListener;

import Graph.Swing.Panel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.*;import java.awt.dnd.DragSourceAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	ArrayList<ArrayList<JTextField>> tf = new ArrayList<ArrayList<JTextField>>();
	ArrayList<ArrayList<JButton>> bt = new ArrayList<ArrayList<JButton>>();
	ToolBar toolBar = new ToolBar();
	UI ui;
	
  	public Swing(int size) {
        setTitle("Dijkstra Project");
        setSize(1600 ,900);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setLocationRelativeTo(null);
    	setLayout(new BorderLayout());
    	setResizable(false);
        
        this.size = size;
        graph = new Graph(size);
    	nodePos = new ArrayList<Circle>();
    	
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
    	ui=new UI(size);
    	
    	
    	add(panel, BorderLayout.CENTER);
    	//SetTextFiled();
    	add(toolBar, BorderLayout.PAGE_START);
    	add(ui,BorderLayout.EAST);
    	revalidate();
    }

    
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
    	
    	int count = 0;
    	ArrayList<Integer> node = new ArrayList<Integer>();
    	ArrayList<Integer> shortestPath = new ArrayList<Integer>();
    	
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

    	
    	
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		float mx = e.getX();
    	    float my = e.getY();
    	    
    	    
    		// 노드 추가
    		if (toolBar.isClickedInsertNode) {
    			
        	    graph.GetPath().add(new ArrayList<Integer>());
        	    graph.GetDist().add(Float.POSITIVE_INFINITY);
        	    graph.GetAdj().add(new ArrayList<Node>());
        	    nodePos.add(new Circle(mx, my));
        	    
        	    toolBar.jButton.get(0).setBackground(Color.darkGray);
        	    toolBar.jButton.get(0).setForeground(Color.white);
        	    
        	    panel.repaint();
    			
    			toolBar.isClickedInsertNode = false;
    		}
    		
    		// 간선 추가
    		if (toolBar.isClickedInsertEdge) {
    			SelectNode(mx, my);
    			
    			if (count == 2) {
    				Connect();
    			}
    			
    		}
    		
    		// 최단 거리 구하기
    		if (toolBar.isClickedGetShortestPath) {
    			SelectNode(mx, my);
    			
    			if (count == 2) {
    				GetShortestPath();
    			}
    		}
    		
    		// 간선 삭제
    		if (toolBar.isClickedDeleteEdge) {
    			SelectNode(mx, my);
    			
    			if (count == 2) {
    				Disconnect();
    			}
    		}
    		
    		// 노드 삭제
    		if (toolBar.isClickedDeleteNode) {
    			DeleteNode(mx, my);
    		}
    	}
    	
    	public void Connect() {
    		boolean isConnected = false;
			int node1 = node.get(0);
			int node2 = node.get(1);
			
			for (int i = 0; i < adj.get(node1).size(); i++)
				if (adj.get(node1).get(i).GetTargetNode() == node2)
					isConnected = true;
			
			if (!isConnected) {
				System.out.println(node1);
				graph.ConnectNodes(node1, node2);
				graph.ConnectNodes(node2, node1);
				
				ui.InsertTextFieldAndButton(node1, node2);
			}
			
			toolBar.jButton.get(1).setBackground(Color.darkGray);
    	    toolBar.jButton.get(1).setForeground(Color.white);
    	    
    	    panel.repaint();
			
    	    toolBar.isClickedInsertEdge = false;
    	    Reset();
    	}
    	
    	public void Disconnect() {
    		int node1 = node.get(0);
			int node2 = node.get(1);
			
			graph.DisconnectNodes(node1, node2);
			graph.DisconnectNodes(node2, node1);
			
			toolBar.jButton.get(3).setBackground(Color.darkGray);
    	    toolBar.jButton.get(3).setForeground(Color.white);
    	    
    	    panel.repaint();
			
    	    toolBar.isClickedDeleteEdge = false;
    	    Reset();
    	}

    	public void GetShortestPath() {
    		shortestPath.clear();
			shortestPath = graph.GetShortestPath(node.get(0), node.get(1));
			
			toolBar.jButton.get(2).setBackground(Color.darkGray);
    	    toolBar.jButton.get(2).setForeground(Color.white);
    	    
    	    panel.repaint();
    	    
    	    toolBar.isClickedGetShortestPath = false;
    	    Reset();
    	}
    	
    	public void Reset() {
    		count = 0;
    		node.clear();
    	}
    	
    	public void Reset(boolean isToolBar) {
    		if (isToolBar) {
    			count = 0;
    			node.clear();
    			shortestPath.clear();
    		}
    	}
    	
    	public void SelectNode(float mx, float my) {
    		for (int i = 1; i < nodePos.size(); i++) {
    		    if (mx > nodePos.get(i).GetXPos() - 50 && mx < nodePos.get(i).GetXPos() + 50 && my > nodePos.get(i).GetYPos() - 50 && my < nodePos.get(i).GetYPos() + 50){
    		    	if (count == 1)
    		    		if (node.get(0) == i)
    		    			break;
    		    	
    		    	node.add(i);
    		    	count++;
    		    }
			}
    	}
    	
    	public void DeleteNode(float mx, float my) {
    		int curNode = 0;
			for (int i = 1; i < nodePos.size(); i++) {
    		    if (mx > nodePos.get(i).GetXPos() - 50 && mx < nodePos.get(i).GetXPos() + 50 && my > nodePos.get(i).GetYPos() - 50 && my < nodePos.get(i).GetYPos() + 50){
    		    	curNode = i;
    		    }
    		}
			
			graph.GetPath().set(curNode, null);
			graph.GetDist().set(curNode, null);
			
			for (int i = 0; i < adj.get(curNode).size(); i++) {
				for (int j  = 0; j < adj.get(adj.get(curNode).get(i).GetTargetNode()).size(); j++) {
					if (adj.get(adj.get(curNode).get(i).GetTargetNode()).get(j).GetTargetNode() == curNode) {
						adj.get(adj.get(curNode).get(i).GetTargetNode()).remove(j);
					}
				}
			}
			
			adj.get(curNode).clear();
			adj.set(curNode, null);
    	    
    	    toolBar.jButton.get(4).setBackground(Color.darkGray);
    	    toolBar.jButton.get(4).setForeground(Color.white);
    	    
    	    panel.repaint();
			
			toolBar.isClickedInsertNode = false;
			Reset();
    	}
    	@Override
    	public void mouseEntered(MouseEvent e) {}

    	@Override
    	public void mouseExited(MouseEvent e) {}
    }
    public Boolean overlapCheck(int i,int j) {//만약 중복이면 true
    	int startNode;
    	int endNode;
    	startNode=i;
    	endNode=adj.get(i).get(j).GetTargetNode();
    	ArrayList<Pair> arr=new ArrayList<>();
    	if(!arr.contains(new Pair(endNode,startNode))) {
    		arr.add(new Pair(startNode,endNode));
    		return false;
    	}
    	return true;
    }
    class Pair{
    	int x,y;
    	Pair(int x,int y){
    		this.x=x;
    		this.y=y;
    	}
    }
    public class UI extends JPanel implements ActionListener, KeyListener{
    	
    	JPanel uiPanel;
    	ArrayList<JPanel> jPanel = new ArrayList<JPanel>();
    	JScrollPane panelPane = new JScrollPane(uiPanel);
    	public UI(int size) {
    		count = 1;
    		uiPanel = new JPanel();
    		uiPanel.setLayout(new BoxLayout(uiPanel, BoxLayout.Y_AXIS));
    		uiPanel.setBackground(Color.WHITE);
    		
    		tf.add(null);
    		bt.add(null); 
    		jPanel.add(null);
    		System.out.println("----");
    		for(int i=1;i<adj.size();i++) {
    			tf.add(new ArrayList<JTextField>());
    			bt.add(new ArrayList<JButton>());
    			for(int j=0;j<adj.get(i).size();j++) {
    				tf.get(i).add(new JTextField());
    				bt.get(i).add(new JButton(i+" - "+adj.get(i).get(j).GetTargetNode()));
    			}
    		}
    		for(int i = 1; i < adj.size(); i++) {
    			for (int j = 0; j < adj.get(i).size(); j++) {
    				if(!overlapCheck(i,j))
    					System.out.println(".");
    				InsertTextFieldAndButton();
    			}
    		}
    	}
    	@Override
		public void actionPerformed(ActionEvent e) {
    		/*for(int i=0;i<count;i++) {//adj.size는 나중에 버튼의 갯수로 바꾸기
    			if(e.getSource()==bt.get(i)) {
    				String length=tf.get(i).getText();
    				System.out.println(length);
    				float value=Float.valueOf(tf.get(i).getText());
    				adj.get(i).get(j).SetValue(value);
    			}
    		}*/
    		for(int i = 1; i < adj.size(); i++) {
    			for (int j = 0; j < adj.get(i).size(); j++) {
    				if (tf.get(i).get(j).getText().isEmpty())
    					continue;
    				//여기서 중복 확인하기
    				if(e.getSource()==bt.get(i).get(j)) {
    					float value=Float.valueOf(tf.get(i).get(j).getText());
    					
        				adj.get(i).get(j).SetValue(value);
    				}
    				count++;
    			}
    		}
    	}
    	
    	public void InsertTextFieldAndButton() {
    		jPanel.add(new JPanel());
    		tf.add(new ArrayList<JTextField>());
    		for(int i=1;i<tf.size();i++) {
    			tf.get(i).add(new JTextField());
    		}
    		bt.add(new ArrayList<JButton>());
    		for(int i=1;i<bt.size();i++) {
    			bt.get(i).add(new JButton(curNode+" - "+targetNode));
    		}

    		
    		jPanel.get(count).add(tf.get(count));
			jPanel.get(count).add(bt.get(count));
			
			jPanel.get(count).setMinimumSize(new Dimension(200, 60));
			bt.get(count).setPreferredSize(new Dimension(60, 50));
			tf.get(count).setPreferredSize(new Dimension(150, 50));
			
			tf.get(count).setHorizontalAlignment(JTextField.CENTER);
			// 숫자만 혀용
			tf.get(count).addKeyListener(new KeyAdapter() {
	            public void keyTyped(KeyEvent evt) {
	                if (!Character.isDigit(evt.getKeyChar())) {
	                    evt.consume();
	                }
	            }
	        });
			
			bt.get(count).addActionListener(this);
			
			remove(panelPane);
			
			uiPanel.add(jPanel.get(count));
			panelPane = new JScrollPane(uiPanel);
					
			panelPane.getVerticalScrollBar().setUnitIncrement(16);
			panelPane.setPreferredSize(new Dimension(300, 800));
			
			add(panelPane);
			revalidate();
			repaint();
			
			count++;
    	}
    	
		@Override
		public void keyTyped(KeyEvent e) {}
		
		@Override
		public void keyPressed(KeyEvent e) {}
		
		@Override
		public void keyReleased(KeyEvent e) {}
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
            
            Graphics2D DrawNodeLength=(Graphics2D) buffG;
            DrawNodeLength(drawNodeNumber);
            
            g.drawImage(buffImg, 0, 0, this);
            repaint();
        }
        
        public void DrawNode(Graphics2D g) {
        	for (int i = 1; i < nodePos.size(); i++) {
        		if (adj.get(i) == null)
        			continue;
            	Shape circleShape = new Ellipse2D.Float(nodePos.get(i).GetXPos() - 50, nodePos.get(i).GetYPos() - 50, 100, 100);
            	g.setColor(Color.white);
            	
            	if (!events.shortestPath.isEmpty()) {
            		for (int j = 0; j < events.shortestPath.size(); j++) {
            			if (events.shortestPath.get(j).equals(i))
            				g.setColor(Color.green);
            		}
            	}
            	
            	if (!events.node.isEmpty()) {
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
        	
        	for (int i = 1; i < adj.size(); i++) {
        		if (adj.get(i) == null)
        			continue;
        		for (int j = 0; j < adj.get(i).size();j++) {
        			Line2D curLine = new Line2D.Float(nodePos.get(i).GetXPos(), nodePos.get(i).GetYPos(), nodePos.get(adj.get(i).get(j).GetTargetNode()).GetXPos(), nodePos.get(adj.get(i).get(j).GetTargetNode()).GetYPos());
        			g.setColor(Color.gray);
        			if (!events.shortestPath.isEmpty()) {
                		for (int k = 0; k < events.shortestPath.size() - 1; k++) {
                			if ((adj.get(i).get(j).GetTargetNode() == events.shortestPath.get(k + 1) && events.shortestPath.get(k) == i) || (adj.get(i).get(j).GetTargetNode() == events.shortestPath.get(k) && events.shortestPath.get(k + 1) == i)) {
                				g.setColor(Color.green);
                				g.draw(curLine);
                			}
                			else {
                				g.draw(curLine);
                			}
                		}
                	}
        			else {        				
        				g.draw(curLine);
        			}
        		}
        	}
        }
        
        public void DrawNodeNumber(Graphics2D g) {
        	Font font = new Font("Ariel", Font.BOLD, 24);
        	g.setColor(Color.black);
        	g.setFont(font);
        	for (int i = 1; i < nodePos.size(); i++) {
        		if (adj.get(i) == null)
        			continue;
        		g.drawString(Integer.toString(i), nodePos.get(i).GetXPos() - 8, nodePos.get(i).GetYPos() + 8);
        	}
        }
        
        public void DrawNodeLength(Graphics2D g) {
        	for (int i = 1; i < adj.size(); i++) {
        		if (adj.get(i) == null)
        			continue;
        		for (int j = 0; j < adj.get(i).size();j++) {
        			//adj.get(i).get(j).GetTargetNode()
        			String value=String.valueOf(adj.get(i).get(j).GetValue());
        			float Posx=(nodePos.get(i).GetXPos()+nodePos.get(adj.get(i).get(j).GetTargetNode()).GetXPos())/2;
        			float Posy=(nodePos.get(i).GetYPos()+nodePos.get(adj.get(i).get(j).GetTargetNode()).GetYPos())/2;
        			g.drawString(value,Posx,Posy);
        		}
        	}
        }
    }

    public class ToolBar extends JToolBar implements ActionListener{
    	
    	int count = 0;
    	JPanel jPanel;
    	ArrayList<JButton> jButton;
    	Color defaultButtonColor = Color.darkGray;
    	
    	public boolean isClickedInsertNode;
    	public boolean isClickedInsertEdge;
    	public boolean isClickedGetShortestPath;
    	public boolean isClickedDeleteNode;
    	public boolean isClickedDeleteEdge;
    	
    	public ToolBar() {
    		jPanel = new JPanel();
    		jButton = new ArrayList<JButton>();
    		
    		createButton("○ +", 60, 40);
    		createButton("／ +", 60, 40);
    		createButton("GetShortestPath", 200, 40);
    		createButton("／ -", 60, 40);
    		createButton("○ -", 60, 40);
    		
    		jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    		
    		for (int i = 0; i < count; i++) {
	    		jPanel.add(jButton.get(i));
	    		add(jPanel);
    		}
    	}

		@Override
		public void actionPerformed(ActionEvent e) {
			actionReset();
			
			// 노드 추가 버튼을 눌렀을 때
			if (e.getSource() == jButton.get(0)) {
				jButton.get(0).setBackground(Color.white);
				jButton.get(0).setForeground(Color.darkGray);
				isClickedInsertNode = true;
			}
			// 간선 추가 버튼을 눌렀을 때
			if (e.getSource() == jButton.get(1)) {
				jButton.get(1).setBackground(Color.white);
				jButton.get(1).setForeground(Color.darkGray);
				isClickedInsertEdge = true;
			}
			
			// 최단 경로 구하기
			if (e.getSource() == jButton.get(2)) {
				jButton.get(2).setBackground(Color.white);
				jButton.get(2).setForeground(Color.darkGray);
				isClickedGetShortestPath = true;
			}
			
			// 간선 삭제 버튼 눌렀을 때
			if (e.getSource() == jButton.get(3)) {
				jButton.get(3).setBackground(Color.white);
				jButton.get(3).setForeground(Color.darkGray);
				isClickedDeleteEdge = true;
			}
			
			// 노드 삭제 버튼 늘렀을 때
			if (e.getSource() == jButton.get(4)) {
				jButton.get(4).setBackground(Color.white);
				jButton.get(4).setForeground(Color.darkGray);
				isClickedDeleteNode = true;
			}
		}
		
		public void actionReset() {
			for (int i = 0; i < jButton.size(); i++) {
				jButton.get(i).setBackground(Color.darkGray);
				jButton.get(i).setForeground(Color.white);
			}
			
			isClickedInsertNode = false;
			isClickedInsertEdge = false;
			isClickedGetShortestPath = false;
			isClickedDeleteEdge = false;
			isClickedDeleteNode = false;
			
			panel.events.Reset(true);
		}
		
		public void createButton(String CONTENT, int WIDTH, int HEIGHT) {
			jButton.add(new JButton(CONTENT));
			
			jButton.get(count).setForeground(Color.white);
			jButton.get(count).setPreferredSize(new Dimension(WIDTH, HEIGHT));
			jButton.get(count).setBackground(defaultButtonColor);
			jButton.get(count).addActionListener(this);
			jButton.get(count).setBorderPainted(false);
			jButton.get(count).setFocusPainted(false);
			
			count++;
		}
    }
}

