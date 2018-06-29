import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FloodIt {
	public static void main(String[] args) throws IOException{
		List<Vertex> all_vertexes = new ArrayList<Vertex>();
		List<Edge> all_edges = new ArrayList<Edge>();
		
		preProcessing(all_vertexes, all_edges);	
		Graph g = new Graph(all_edges, all_vertexes);
		
		g.printGraph();
	}
	
	private static void preProcessing(List<Vertex> list_vertex, List<Edge> list_edge) {
		final String path = "resources/instances.txt";
		File f = new File(path);
		Scanner s = null;
		try {
			 s = new Scanner(f);
		}
		catch(FileNotFoundException ex) {
			System.out.println("Arquivo não encontrado");
		}

		Integer n = s.nextInt(), m = s.nextInt(), c = s.nextInt(), p = s.nextInt();
		
		for(int i = 0; i < n; i++) {
			list_vertex.add(new Vertex(i, s.nextInt()));
		}
		
		for (int i = 0; i < m; i++) {
			list_edge.add(new Edge(s.nextInt(), s.nextInt()));
		}
	}
}

class Edge{
	private Integer origin;
	private Integer destination;
	
	public Edge(Integer origin, Integer destination) {
		this.origin = origin;
		this.destination = destination;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public Integer getDestination() {
		return destination;
	}

	public void setDestination(Integer destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "Aresta (origem = " + origin + ", destino = " + destination + ")";
	}
}

class Vertex {
	
	private Integer flag;
	private Integer color;
	private LinkedList<Edge> adjacencies = new LinkedList<Edge>();
	
	public Vertex(Integer flag, Integer color) {
		this.flag = flag;
		this.color = color;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public LinkedList<Edge> getAdjacencies() {
		return adjacencies;
	}

	public void setAdjacencies(LinkedList<Edge> adjacencies) {
		this.adjacencies = adjacencies;
	}

	@Override
	public String toString() {
		return "Vertice [rótulo = " + flag + ", cor = " + color + ", adjacências = " +  adjacencies +"]";
	}
}

class Graph {
	private HashSet<Vertex> all_vertexes = new HashSet<Vertex>();
	private HashSet<Region> all_regions = new HashSet<Region>();
	
	public Graph() { 	}
	
	public Graph (List<Edge> list_edge, List<Vertex> list_vertex) {
		for (int i = 0; i < list_vertex.size(); i++) {
			for (int j = 0; j < list_edge.size(); j++) {
				if (list_edge.get(j).getOrigin() == list_vertex.get(i).getFlag()) {
					list_vertex.get(i).getAdjacencies().add(list_edge.get(j));
				}
			}
			this.getAll_vertexes().add(list_vertex.get(i));
		}
		
		for (int i = 0; i < list_vertex.size(); i++) {
			for (int j = 0; j < list_vertex.get(i).getAdjacencies().size(); j++) {
				Region r = new Region();
				//vertices da mesma cor entram na mesma região
			}
		}
	}

	public HashSet<Vertex> getAll_vertexes() {
		return all_vertexes;
	}

	public void setAll_vertexes(HashSet<Vertex> all_vertexes) {
		this.all_vertexes = all_vertexes;
	}
	
	public HashSet<Region> getAll_regions() {
		return all_regions;
	}

	public void setAll_regions(HashSet<Region> all_regions) {
		this.all_regions = all_regions;
	}

	public void printGraph() {
		for (Vertex v : all_vertexes) {
			System.out.println(v);
		}
	}
}

class Region {
	private HashSet<Vertex> regionVertexes = new HashSet<Vertex>();
	private Integer color;
	
	Region() {  }

	public HashSet<Vertex> getRegionVertexes() {
		return regionVertexes;
	}

	public void setRegionVertexes(HashSet<Vertex> regionVertexes) {
		this.regionVertexes = regionVertexes;
	}
}