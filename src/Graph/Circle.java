package Graph;

public class Circle {
	private float xPos;
	private float yPos;
	
	public Circle(float xPos, float yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public float GetXPos() {
		return xPos;
	}
	
	public float GetYPos() {
		return yPos;
	}
	
	public void SetXPos(float value) {
		this.xPos = value;
	}
	
	public void SetYPos(float value) {
		this.yPos = value;
	}
}
