package Graph;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Graph{
	private int size;
	private ArrayList<Float> dist; // ��� �ִ� ����� �Ÿ�
	private ArrayList<ArrayList<Integer>> path; // ��� �ִ� ���
	private ArrayList<ArrayList<Node>> adj; // �׷��� ����
	
	public Graph(int size) {
		this.size = size;
		
		dist = new ArrayList<Float>();
		for(int i = 0; i < size + 1; i++)
			dist.add(Float.POSITIVE_INFINITY);
		
		path = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < size + 1; i++)
			path.add(new ArrayList<Integer>());
		
		adj = new ArrayList<ArrayList<Node>>();
		for(int i = 0; i < size + 1; i++)
			adj.add(new ArrayList<Node>());
	}
	
	private void CheckAllShortestPath(int startNode){ // startNode���� ��� �������� �ִ� ��� �� �Ÿ��� ���
		dist.set(startNode, (float) 0);
		path.get(startNode).add(startNode);
		
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.add(new Node(startNode, dist.get(startNode)));
		
		while(!pq.isEmpty()) {
			int current = pq.peek().GetTargetNode();
			float distance = pq.peek().GetValue();
			pq.poll();
			
			if (dist.get(current) < distance) 
				continue;
			
			for(int i = 0; i < adj.get(current).size(); i++) {
				int next = adj.get(current).get(i).GetTargetNode();
				float nextDist = distance + adj.get(current).get(i).GetValue();
				
				if (nextDist < dist.get(next)) { // �ֱ��� �ּڰ��� ������ ������ ū��?
					dist.set(next, nextDist);
					
					path.get(next).clear(); // ���� ��θ� �����
					path.get(next).addAll(path.get(current));
					path.get(next).add(next);
					
					pq.add(new Node(next, nextDist));
				}
			}
		}
	}
	
	public void ConnectNodes(int thisNode, int targetNode, float value) throws IllegalArgumentException{ // ���� 1������ ������
		if (thisNode * targetNode * value != 0)
			adj.get(thisNode).add(new Node(targetNode, value));
		else
			throw new IllegalArgumentException("This args are can not be 0.");
	}
	
	public void ConnectNodes(int thisNode, int targetNode) throws IllegalArgumentException{ // �ӽ�
		if (thisNode * targetNode != 0)
			adj.get(thisNode).add(new Node(targetNode, 0));
		else
			throw new IllegalArgumentException("This args are can not be 0.");
	}
	
	public ArrayList<Integer> GetShortestPath(int startNode, int endNode) { // �ִ� ��� ���ϱ�
		CheckAllShortestPath(startNode);
		return path.get(endNode);
	}
	
	public float GetShortestDistance(int startNode, int endNode) { // �ִ� ����� �Ÿ� ���ϱ�
		CheckAllShortestPath(startNode);
		return dist.get(endNode);
	}
	
	public ArrayList<ArrayList<Node>> GetAdj(){
		return adj;
	}
	
	public ArrayList<ArrayList<Integer>> GetPath(){
		return path;
	}
	
	public ArrayList<Float> GetDist(){
		return dist;
	}
}
