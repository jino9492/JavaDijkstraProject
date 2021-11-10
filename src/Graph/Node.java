package Graph;

public class Node implements Comparable<Node>{
	private int targetNodeNum;
	private float value;
	
	public Node(int targetNodeNum, float value) {
		this.targetNodeNum = targetNodeNum;
		this.value = value;
	}
	
	public int GetTargetNode() {
		return targetNodeNum;
	}
	
	public float GetValue() {
		return value;
	}
	
	public void SetValue(float value) {
		this.value = value;
	}
	
	@Override
	public int compareTo(Node node) {
		return this.value >= node.value ? 1 : -1;
	}
}
