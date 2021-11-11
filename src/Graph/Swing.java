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
<<<<<<< HEAD
	ArrayList<ArrayList<JTextField>> tf = new ArrayList<ArrayList<JTextField>>();
	ArrayList<ArrayList<JButton>> bt = new ArrayList<ArrayList<JButton>>();
=======
>>>>>>> eea68cc4e9008e4f6b62252d4d15bc893856cf0c
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
    		//�巡��
    	    if (dragging){
    		    float mx = e.getX();
			    float my = e.getY();
			    nodePos.get(nodeNum).SetXPos(mx - offsetX);
			    nodePos.get(nodeNum).SetYPos(my - offsetY);
    	    }
    	}
        
        @Override
        public void mousePressed(MouseEvent e){
        	// �巡�� üũ
    	    float mx = e.getX();
    	    float my = e.getY();
    	    for (int i = 1; i < nodePos.size(); i++) {
    	    	if (nodePos.get(i) == null)
    	    		continue;
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
    	    
    	    
    		// ��� �߰�
    		if (toolBar.isClickedInsertNode) {
    			
        	    graph.GetPath().add(new ArrayList<Integer>());
        	    graph.GetDist().add(Float.POSITIVE_INFINITY);
        	    graph.GetAdj().add(new ArrayList<Node>());
        	    
        	    ui.tf.add(new ArrayList<JTextField>());
        	    ui.bt.add(new ArrayList<JButton>());
        	    ui.jPanel.add(new ArrayList<JPanel>());
        	    ui.arr.add(new ArrayList<Integer>());
        	    
        	    nodePos.add(new Circle(mx, my));
        	    
        	    toolBar.jButton.get(0).setBackground(Color.darkGray);
        	    toolBar.jButton.get(0).setForeground(Color.white);
        	    
        	    panel.repaint();
    			
    			toolBar.isClickedInsertNode = false;
    		}
    		
    		// ���� �߰�
    		if (toolBar.isClickedInsertEdge) {
    			SelectNode(mx, my);
    			
    			if (count == 2) {
    				Connect();
    			}
    			
    		}
    		
    		// �ִ� �Ÿ� ���ϱ�
    		if (toolBar.isClickedGetShortestPath) {
    			SelectNode(mx, my);
    			
    			if (count == 2) {
    				GetShortestPath();
    			}
    		}
    		
    		// ���� ����
    		if (toolBar.isClickedDeleteEdge) {
    			SelectNode(mx, my);
    			
    			if (count == 2) {
    				Disconnect();
    			}
    		}
    		
    		// ��� ����
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
				
				ui.tf.get(node1).add(new JTextField());
				ui.bt.get(node1).add(new JButton(node1 + " - " + node2));
				
				ui.tf.get(node2).add(new JTextField());
				ui.bt.get(node2).add(new JButton(node2 + " - " + node1));
				
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
			
			for (int i = 0; i < adj.get(node1).size(); i++) {
				if (adj.get(node1).get(i).GetTargetNode() == node2) {
					ui.remove(ui.panelPane);
					ui.uiPanel.remove(ui.jPanel.get(node1).get(i));
					ui.panelPane = new JScrollPane(ui.uiPanel);
    				
    				ui.panelPane.getVerticalScrollBar().setUnitIncrement(16);
    				ui.panelPane.setPreferredSize(new Dimension(300, 800));
    				
    				ui.add(ui.panelPane);
    				ui.revalidate();
    				ui.repaint();
				}
			}
			
			for (int i = 0; i < adj.get(node2).size(); i++) {
				if (adj.get(node2).get(i).GetTargetNode() == node1) {
					ui.remove(ui.panelPane);
					ui.uiPanel.remove(ui.jPanel.get(node2).get(i));
					ui.panelPane = new JScrollPane(ui.uiPanel);
    				
    				ui.panelPane.getVerticalScrollBar().setUnitIncrement(16);
    				ui.panelPane.setPreferredSize(new Dimension(300, 800));
    				
    				ui.add(ui.panelPane);
    				ui.revalidate();
    				ui.repaint();
				}
			}
			
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
    			if (nodePos.get(i) != null)
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
				if (nodePos.get(i) != null)
	    		    if (mx > nodePos.get(i).GetXPos() - 50 && mx < nodePos.get(i).GetXPos() + 50 && my > nodePos.get(i).GetYPos() - 50 && my < nodePos.get(i).GetYPos() + 50){
	    		    	curNode = i;
	    		    }
    		}
			
			graph.GetPath().set(curNode, null);
			graph.GetDist().set(curNode, null);
			
			if (nodePos.get(curNode) != null) {
				for (int i = 0; i < adj.get(curNode).size(); i++) {
					for (int j  = 0; j < adj.get(adj.get(curNode).get(i).GetTargetNode()).size(); j++) {
						if (adj.get(adj.get(curNode).get(i).GetTargetNode()).get(j).GetTargetNode() == curNode) {
							adj.get(adj.get(curNode).get(i).GetTargetNode()).remove(j);
							
							ui.remove(ui.panelPane);
							ui.uiPanel.remove(ui.jPanel.get(curNode).get(i));
							ui.uiPanel.remove(ui.jPanel.get(adj.get(curNode).get(i).GetTargetNode()).get(j));
							ui.panelPane = new JScrollPane(ui.uiPanel);
		    				
		    				ui.panelPane.getVerticalScrollBar().setUnitIncrement(16);
		    				ui.panelPane.setPreferredSize(new Dimension(300, 800));
		    				
		    				ui.add(ui.panelPane);
		    				ui.revalidate();
		    				ui.repaint();
						}
					}
				}
				
				adj.get(curNode).clear();
				adj.set(curNode, null);
				nodePos.set(curNode, null);
			}
    	    
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
    
    public class UI extends JPanel implements ActionListener, KeyListener{
    	
    	JPanel uiPanel;
    	ArrayList<ArrayList<JPanel>> jPanel = new ArrayList<>();
    	JScrollPane panelPane = new JScrollPane(uiPanel);
    	ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
    	
    	ArrayList<ArrayList<JTextField>> tf = new ArrayList<>();
    	ArrayList<ArrayList<JButton>> bt = new ArrayList<>();
    	
    	public UI(int size) {
    		uiPanel = new JPanel();
    		uiPanel.setLayout(new BoxLayout(uiPanel, BoxLayout.Y_AXIS));
    		uiPanel.setBackground(Color.WHITE);
    		
    		arr.add(null);
    		tf.add(null);
    		bt.add(null); 
    		jPanel.add(null);
<<<<<<< HEAD
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
=======
    		
    		for(int i = 1; i < adj.size(); i++) {
    			arr.add(new ArrayList<Integer>());
    			jPanel.add(new ArrayList<JPanel>());
    			tf.add(new ArrayList<JTextField>());
    			bt.add(new ArrayList<JButton>());
    			for (int j = 0; j < adj.get(i).size(); j++) {
    				jPanel.get(i).add(new JPanel());
    				tf.get(i).add(new JTextField(String.valueOf(adj.get(i).get(j).GetValue())));
        			bt.get(i).add(new JButton(i + " - " + adj.get(i).get(j).GetTargetNode()));
    			}
    		}
    		
    		for(int i = 1; i < adj.size(); i++) {
    			for (int j = 0; j < adj.get(i).size(); j++) {
    				if(!overlapCheck(i, adj.get(i).get(j).GetTargetNode())){
    					continue;
    				}
    				
    				InsertTextFieldAndButton(i, adj.get(i).get(j).GetTargetNode());
>>>>>>> eea68cc4e9008e4f6b62252d4d15bc893856cf0c
    			}
    		}
    	}
    	@Override
		public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
    		/*for(int i=0;i<count;i++) {//adj.size�� ���߿� ��ư�� ������ �ٲٱ�
    			if(e.getSource()==bt.get(i)) {
    				String length=tf.get(i).getText();
    				System.out.println(length);
    				float value=Float.valueOf(tf.get(i).getText());
    				adj.get(i).get(j).SetValue(value);
    			}
    		}*/
=======
>>>>>>> eea68cc4e9008e4f6b62252d4d15bc893856cf0c
    		for(int i = 1; i < adj.size(); i++) {
    			for (int j = 0; j < adj.get(i).size(); j++) {
    				if (tf.get(i).get(j).getText().isEmpty())
    					continue;
<<<<<<< HEAD
    				//���⼭ �ߺ� Ȯ���ϱ�
    				if(e.getSource()==bt.get(i).get(j)) {
    					float value=Float.valueOf(tf.get(i).get(j).getText());
=======
    				
    				if(e.getSource()==bt.get(i).get(j)) {
    					float value = Float.parseFloat(tf.get(i).get(j).getText());
>>>>>>> eea68cc4e9008e4f6b62252d4d15bc893856cf0c
    					
    					System.out.println(value);
        				adj.get(i).get(j).SetValue(value);
        				
        				for (int k = 0; k < adj.get(adj.get(i).get(j).GetTargetNode()).size(); k++) {
        					if (adj.get(adj.get(i).get(j).GetTargetNode()).get(k).GetTargetNode() == i)
        						adj.get(adj.get(i).get(j).GetTargetNode()).get(k).SetValue(value);
        				}
    				}
    			}
    		}
    	}
    	
<<<<<<< HEAD
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

=======
    	public void InsertTextFieldAndButton(int curNode, int targetNode) {
    		jPanel.get(curNode).add(new JPanel());
    		jPanel.get(targetNode).add(new JPanel());
>>>>>>> eea68cc4e9008e4f6b62252d4d15bc893856cf0c
    		
    		for (int i = 0; i < adj.get(curNode).size(); i++) {
    			if (adj.get(curNode).get(i).GetTargetNode() == targetNode) {
    				System.out.println(jPanel.get(curNode).get(i));
    				jPanel.get(curNode).get(i).add(tf.get(curNode).get(i));
    				jPanel.get(curNode).get(i).add(bt.get(curNode).get(i));
    				
    				jPanel.get(curNode).get(i).setMinimumSize(new Dimension(200, 60));
    				bt.get(curNode).get(i).setPreferredSize(new Dimension(70, 50));
    				tf.get(curNode).get(i).setPreferredSize(new Dimension(150, 50));
    				
    				tf.get(curNode).get(i).setHorizontalAlignment(JTextField.CENTER);
    				// ���ڸ� ����
    				tf.get(curNode).get(i).addKeyListener(new KeyAdapter() {
    					public void keyTyped(KeyEvent evt) {
    						if (!Character.isDigit(evt.getKeyChar()) && !(evt.getKeyChar() == '.')) {
    							evt.consume();
    						}
    					}
    				});
    				
    				bt.get(curNode).get(i).addActionListener(this);
    				
    				remove(panelPane);
    				
    				uiPanel.add(jPanel.get(curNode).get(i));
    				panelPane = new JScrollPane(uiPanel);
    				
    				panelPane.getVerticalScrollBar().setUnitIncrement(16);
    				panelPane.setPreferredSize(new Dimension(300, 800));
    				
    				add(panelPane);
    				revalidate();
    				repaint();
    			}
    		}
    		
    		for (int i = 0; i< adj.get(targetNode).size(); i++) {
    			if (adj.get(targetNode).get(i).GetTargetNode() == curNode) {
    				jPanel.get(targetNode).get(i).add(tf.get(targetNode).get(i));
    				jPanel.get(targetNode).get(i).add(bt.get(targetNode).get(i));
    			}
    		}
    	}
    	
    	public boolean overlapCheck(int startNode,int endNode) {//���� �ߺ��̸� true
        	if(arr.get(endNode).contains(startNode) && arr.get(endNode).contains(endNode)) {
        		return true;
        	}
        	else {
        		arr.get(startNode).add(startNode);
        		arr.get(startNode).add(endNode);
        		return false;
        	}
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
        	
        	Graphics2D drawEdge = (Graphics2D) buffG;//��
        	DrawEdge(drawEdge);
        	
    		Graphics2D drawNode = (Graphics2D) buffG;//���
    		DrawNode(drawNode);
        	
            Graphics2D drawNodeNumber = (Graphics2D) buffG;//����
            DrawNodeNumber(drawNodeNumber);
            
            Graphics2D DrawNodeLength=(Graphics2D) buffG;
            DrawNodeLength(drawNodeNumber);
            
            g.drawImage(buffImg, 0, 0, this);
            repaint();
        }
        
        public void DrawNode(Graphics2D g) {
        	for (int i = 1; i < nodePos.size(); i++) {
        		if (nodePos.get(i) == null)
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
        			if (nodePos.get(i).equals(null))
        				continue;
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
        			if(!Swing.this.ui.overlapCheck(i, adj.get(i).get(j).GetTargetNode()))
        				continue;
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
    		
    		createButton("�� +", 60, 40);
    		createButton("�� +", 60, 40);
    		createButton("GetShortestPath", 200, 40);
    		createButton("�� -", 60, 40);
    		createButton("�� -", 60, 40);
    		
    		jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    		
    		for (int i = 0; i < count; i++) {
	    		jPanel.add(jButton.get(i));
	    		add(jPanel);
    		}
    	}

		@Override
		public void actionPerformed(ActionEvent e) {
			actionReset();
			
			// ��� �߰� ��ư�� ������ ��
			if (e.getSource() == jButton.get(0)) {
				jButton.get(0).setBackground(Color.white);
				jButton.get(0).setForeground(Color.darkGray);
				isClickedInsertNode = true;
			}
			// ���� �߰� ��ư�� ������ ��
			if (e.getSource() == jButton.get(1)) {
				jButton.get(1).setBackground(Color.white);
				jButton.get(1).setForeground(Color.darkGray);
				isClickedInsertEdge = true;
			}
			
			// �ִ� ��� ���ϱ�
			if (e.getSource() == jButton.get(2)) {
				jButton.get(2).setBackground(Color.white);
				jButton.get(2).setForeground(Color.darkGray);
				isClickedGetShortestPath = true;
			}
			
			// ���� ���� ��ư ������ ��
			if (e.getSource() == jButton.get(3)) {
				jButton.get(3).setBackground(Color.white);
				jButton.get(3).setForeground(Color.darkGray);
				isClickedDeleteEdge = true;
			}
			
			// ��� ���� ��ư �÷��� ��
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
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

