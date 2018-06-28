import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FloodIt {
	public static void main(String[] args) throws IOException{
		
	}
	
	private static void configInstances(List<Vertex> vertex, List<Integer> color, List<Edge> edge) {
		File f = new File("instance.txt");
		Scanner s = null;
		try {
			 s = new Scanner(f);
		}
		catch(FileNotFoundException ex) {
			System.out.println("Arquivo não encontrado " + ex.getStackTrace());
		}

		//Instancias no formato
		//numeroVertices, numeroArestas, numeroCores, verticePivo
		Integer n = s.nextInt(), m = s.nextInt(), c = s.nextInt(), p = s.nextInt();
	}
}

class Edge{
	private Integer origin;
	private Integer destination;
	
}

class Vertex {
	private Integer color;
}
