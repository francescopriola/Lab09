package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Graph<Country, DefaultEdge> grafoGraph;
	private BordersDAO dao;
	private Map<Integer, Country> idMap;
	private List<Set<Country>> compSet;

	public Model() {
		dao = new BordersDAO();
		idMap = new HashMap<>(dao.loadAllCountries());
	}
	
	public void creaGrafo(int anno) {
		grafoGraph = new SimpleGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(grafoGraph, dao.getCountryWithBorders(idMap, anno).values());
		
		List<Border> archi = dao.getCoppieAdiacenti(anno);
		for (Border c : archi) {
			grafoGraph.addEdge(this.idMap.get(c.getC1()), this.idMap.get(c.getC2()));

		}
		
//		System.out.println("#Vertici: " + grafoGraph.vertexSet().size());
//		System.out.println("#Archi: " + grafoGraph.edgeSet().size());
	}
	
	public Map<Country, Integer> getCountry(int anno){
		Map<Integer, Country> vertexMap = new HashMap<Integer, Country>();
		
		for(Country c : this.grafoGraph.vertexSet()) {
			vertexMap.put(c.getCountryId(), c);
		}
		
		Map<Country, Integer>  countriesMap = new HashMap<>(dao.getGradoVertice(vertexMap, anno));
		return countriesMap;
	}
	
	public int componentiConnesse() {
		int componentiConnesse = 0;
		ConnectivityInspector<Country, DefaultEdge> inspector = new ConnectivityInspector<>(grafoGraph);
		compSet = inspector.connectedSets();
		
		for(Set<Country> c : compSet)
			componentiConnesse += c.size();
		return componentiConnesse;
	}

}
