package Graph;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Graph{
	private int size;
	private float[] dist; // 모든 최단 경로의 거리
	private ArrayList<Integer>[] path; // 모든 최단 경로
	private ArrayList<Node>[] adj; // 그래프 정보
	
	public Graph(int size) {
		this.size = size;
		
		dist = new float[size + 1];
		for(int i = 0; i < size + 1; i++)
			dist[i] = Float.POSITIVE_INFINITY;
		
		path = new ArrayList[size + 1];
		for (int i = 0; i <size + 1; i++)
			path[i] = new ArrayList<Integer>();
		
		adj = new ArrayList[size + 1];
		for(int i = 0; i < size + 1; i++)
			adj[i] = new ArrayList<Node>();
	}
	
	private void CheckAllShortestPath(int startNode){ // startNode에서 모든 노드까지의 최단 경로 및 거리를 계산
		dist[startNode] = 0;
		path[startNode].add(startNode);
		
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.add(new Node(startNode, dist[startNode]));
		
		while(!pq.isEmpty()) {
			int current = pq.peek().GetTargetNode();
			float distance = pq.peek().GetValue();
			pq.poll();
			
			if (dist[current] < distance) 
				continue;
			
			for(int i = 0; i < adj[current].size(); i++) {
				int next = adj[current].get(i).GetTargetNode();
				float nextDist = distance + adj[current].get(i).GetValue();
				
				if (nextDist < dist[next]) { // 최근의 최솟값이 현재의 값보다 큰가?
					dist[next] = nextDist;
					
					path[next].clear(); // 최종 경로만 남기기
					path[next].addAll(path[current]);
					path[next].add(next);
					
					pq.add(new Node(next, nextDist));
				}
			}
		}
	}
	
	public void ConnectNodes(int thisNode, int targetNode, float value) throws IllegalArgumentException{ // 노드는 1번부터 시작함
		if (thisNode * targetNode * value != 0)
			adj[thisNode].add(new Node(targetNode, value));
		else
			throw new IllegalArgumentException("This args are can not be 0.");
	}
	
	public ArrayList<Node>[] GetAdj(){
		return this.adj;
	}
	
	
	public ArrayList<Integer> GetShortestPath(int startNode, int endNode) { // 최단 경로 구하기
		CheckAllShortestPath(startNode);
		return path[endNode];
	}
	
	public float GetShortestDistance(int startNode, int endNode) { // 최단 경로의 거리 구하기
		CheckAllShortestPath(startNode);
		return dist[endNode];
	}
}
