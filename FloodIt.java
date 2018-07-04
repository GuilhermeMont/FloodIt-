import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;
import com.sun.javafx.collections.MappingChange.Map;

public class FloodIt {
	public static void main(String[] args) throws IOException{
		ArrayList<Vertex> all_vertexes = new ArrayList<Vertex>();
		ArrayList<Edge> all_edges = new ArrayList<Edge>();
		
		preProcessing(all_vertexes, all_edges);	
		Graph g = new Graph(all_edges, all_vertexes);
		Flood f = new Flood();
		f.floodIt(g,all_vertexes.get(4));
		
//		g.printGraph();
		
	}
	
	private static void preProcessing(List<Vertex> list_vertex, List<Edge> list_edge) {
		final String path = "resourses/1.txt";
		File f = new File(path);
		Scanner s = null;
		try {
			 s = new Scanner(f);
		}
		catch(FileNotFoundException ex) {
			System.out.println("Arquivo n�o encontrado");
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
	private boolean flooded;
	private LinkedList<Edge> adjacencies = new LinkedList<Edge>();
	
	Vertex(){}
	
	public Vertex(Integer flag, Integer color) {
		this.flag = flag;
		this.color = color;
		this.flooded = false;
	}

	public Integer getFlag() {
		return flag;
	}

	public boolean isFlooded(){
		return this.flooded;
	}
	public void setFlood (boolean cond){
		this.flooded = true; 
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
		return "Vertice [r�tulo = " + flag + ", cor = " + color + ", adjac�ncias = " +  adjacencies +"]";
	}
}

class Region {
	private HashSet<Vertex> regionVertexes = new HashSet<Vertex>();
	private Integer color;
	
	Region() {}
	
	Region(Region r ){
		this.color = r.getColor();
		this.regionVertexes = r.getRegionVertexes();
	}
	
	Region(Integer color) {  
		this.color = color;
	}
	
	public int getColor(){
		return this.color;
	}	

	public HashSet<Vertex> getRegionVertexes() {
		return regionVertexes;
	}

	public void setRegionVertexes(HashSet<Vertex> regionVertexes) {
		this.regionVertexes = regionVertexes;
	}
	
	public boolean addAllVertexesToRegion (HashSet<Vertex> regionVertexes){
		 return this.regionVertexes.addAll(regionVertexes);
	}
	
	public void addVertexToRegion(Vertex v){
		if (!regionVertexes.contains(v))
			regionVertexes.add(v);
	}
	
	public Set<Integer> getAllFlagsFromRegion(Region g){
		Set<Integer> flags = new HashSet<Integer>();
		for (Vertex v : g.getRegionVertexes()){
			flags.add(v.getFlag());
		}
		return flags;
	}
		
	public boolean checkAdjacence (Region r , Region g){
		Set<Integer> flags = getAllFlagsFromRegion(g);
		System.out.println("Vertices da minha regiao");
		for (Vertex v : r.getRegionVertexes()){
			for (Edge e : v.getAdjacencies()){
			if (flags.contains(e.getOrigin()) || flags.contains(e.getDestination())){
				return true;
			}
			else {
				return false;
			}
		  }
		}
		return false;
	}
	
	public int getRegionSize(){
		return regionVertexes.size();
	}
	
	@Override
	public String toString() {
		return "Region [Vertices da regiao = " + regionVertexes + ", cor = " + color + "]";
	}
}

class Graph {
	
	private HashSet<Vertex> all_vertexes = new HashSet<Vertex>();
	private HashSet<Region> all_regions = new HashSet<Region>();
	
	public Graph() { 	}
	
	public Graph (ArrayList<Edge> list_edge, ArrayList<Vertex> list_vertex) {
		//Construcao das adjacencias 
		for (int i = 0; i < list_vertex.size(); i++) {
			for (int j = 0; j < list_edge.size(); j++) {
				if (list_edge.get(j).getOrigin() == list_vertex.get(i).getFlag()
						|| list_edge.get(j).getDestination() == list_vertex.get(i).getFlag()) {
					list_vertex.get(i).getAdjacencies().add(list_edge.get(j));
				}
			}
			this.getAll_vertexes().add(list_vertex.get(i));
		}
		
		
		//Construcao das regioes de cores
		
//		for (Vertex v : list_vertex){
////			System.out.println(v.isFlooded());
//			if (v.isFlooded() == false){
//				// se o vertice ainda nao foi inundado , crie uma nova regiao
//				Region r = new Region(v.getColor());
//				for (Edge e : v.getAdjacencies()){
//					Vertex adVertex = list_vertex.get(e.getDestination()-1);
//					if(v.getColor() == adVertex.getColor()){
//							r.addVertexToRegion(v);
//							v.setFlood(true);
//							r.addVertexToRegion(adVertex);
//							adVertex.setFlood(true);
//							
//							list_vertex.set(e.getDestination()-1 , adVertex);
//
//					}
//					
//				}
//				if (!r.getRegionVertexes().isEmpty()){
//					all_regions.add(r);
//				}
//				
//			}
//		}
		
		
			
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
	
	public Vertex getVertex (int id_vertex){
		try{
			for (Vertex v : all_vertexes){
				if (v.getFlag() == id_vertex){
//					System.out.println("VERTEX FOUNDED ! ");
					return v;
				}
			}
			return new Vertex();
		}
		catch (Exception e){
			System.out.println("vertice nao existente");
			return new Vertex();
		}
		
		
	}
	
	public int getNumberOfRegions(){
		int numberOfRegions=0;
		for (Region r : all_regions){
			numberOfRegions++;
		}
		return numberOfRegions;
	}
		
	@SuppressWarnings("finally")
	public Region discoverVertexRegion (Vertex v){
		try{
			for (Region r : all_regions){
				if (r.getRegionVertexes().contains(v)){
					return r;
				}
			}
		}
		catch (Exception e){
			System.out.println("Regiao nao encontrado");
			
		}
		finally{
			Region c = new Region(-1);
			c.addVertexToRegion(v);
			return c;
		}
		
		
		
	}
		
	public int Colors (List<Vertex> list_vertex){
		HashMap<Integer, Integer> cores = new HashMap<Integer,Integer>();
		for (Vertex v : list_vertex){
			if(cores.containsKey(v.getColor())){
				cores.replace(v.getColor(), cores.get(v.getColor()) + 1);
			}
			else{
				cores.put(v.getColor(), 1);
			}
		}
		int numberOfColors = cores.size();
		return numberOfColors;
	}
	
	public void printRegions(){
		for (Region r : all_regions) {
			System.out.println(r);
		}
	}

	public void printGraph() {
		for (Vertex v : all_vertexes) {
			System.out.println(v);
		}
		
		for (Region r : all_regions) {
			System.out.println(r);
		}
	}
	
	
	
}

class Flood {
	
	Flood(){};
	
	public int getMaxColor (HashMap<Integer,Integer> cores ){
		
		int max =0;
		int key =0;
		
		for (Integer i : cores.keySet()){
				if (cores.get(i) > max){
					max = cores.get(i);
					key = i;
		
				}	
		}
		return key;
	}
	
	public void floodIt (Graph g ,Vertex pivot){
		
		Region x = new Region (pivot.getColor());
		x.addVertexToRegion(pivot);
		

		while (x.getRegionVertexes().size() != g.getAll_vertexes().size()){
			HashMap<Integer, Integer> cores = new HashMap<Integer,Integer>();
			
			for(Vertex v : x.getRegionVertexes()){
				for (Edge e : v.getAdjacencies()){
					if(e.getDestination() == g.getVertex(e.getDestination()).getFlag()){
					Vertex k  = g.getVertex(e.getDestination());
					if(x.getRegionVertexes().contains(k) == false){
						if(cores.containsKey(k.getColor())){
							cores.replace(k.getColor(), cores.get(k.getColor()) + 1);
						}
						else{
							cores.put(k.getColor(), 1);
						}
					}
				}
			  }
			}
			
			
			int color = getMaxColor(cores);
			Iterator<Vertex> iter = x.getRegionVertexes().iterator();
			Region n = new Region(color);
			while(iter.hasNext()){
				for (Edge e : iter.next().getAdjacencies()){
					Vertex k  = g.getVertex(e.getDestination());
					if (k.getColor() == color){
						n.addVertexToRegion(k);
					}
				}
			}
			
			x.addAllVertexesToRegion(n.getRegionVertexes());
			cores.clear();			
			System.out.println(x);
	}
		
			
}
}


