package Graph;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TestCase extends JFrame{

	public static void main(String[] args) throws IOException{
		Graph g = new Graph(6);
		g.ConnectNodes(1, 2, 2);
		g.ConnectNodes(1, 3, 5);
		g.ConnectNodes(1, 4, 1);
		
		g.ConnectNodes(2, 1, 2);
		g.ConnectNodes(2, 3, 3);
		g.ConnectNodes(2, 4, 2);
		
		g.ConnectNodes(3, 1, 5);
		g.ConnectNodes(3, 2, 3);
		g.ConnectNodes(3, 4, 3);
		g.ConnectNodes(3, 5, 1);
		g.ConnectNodes(3, 6, 5);
		
		g.ConnectNodes(4, 1, 1);
		g.ConnectNodes(4, 2, 2);
		g.ConnectNodes(4, 3, 3);
		g.ConnectNodes(4, 5, 1);
		
		g.ConnectNodes(5, 3, 1);
		g.ConnectNodes(5, 4, 1);
		g.ConnectNodes(5, 6, 2);
		
		g.ConnectNodes(6, 3, 5);
		g.ConnectNodes(6, 5, 2);
		
		Scanner stdIn = new Scanner(System.in);
		
		System.out.print("StartNode : ");
		int startNode = stdIn.nextInt();
		
		System.out.print("EndNode : ");
		int endNode = stdIn.nextInt();
		
		ArrayList<Integer> shortestPath = g.GetShortestPath(startNode, endNode);
		float shortestDist = g.GetShortestDistance(startNode, endNode);
		
		System.out.print("ShortestPath : " + shortestPath + " / ShortestDistacnce : " + shortestDist);
	}

}
