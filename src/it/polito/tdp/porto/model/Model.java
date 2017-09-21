package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private UndirectedGraph<Author, DefaultEdge> graph;
	private Map<Integer, Author> authors;
	private Map<Integer, Paper> papers;
	private List<Creation> creations;
	private PortoDAO dao;
	
	public Model(){
		super();
		this.dao = new PortoDAO();
	}

	public List<Author> getAllAuthors() {
		
		if(authors == null){
			authors = loadAllAuthors();
		}
			
		return new ArrayList<Author>(authors.values());
		
	}

	private Map<Integer, Author> loadAllAuthors() {
		
		if(authors != null){
			return authors;
		}
		
		authors = new HashMap<Integer, Author>();
		
		authors = dao.loadAllAuthors();
		
		return authors;
		
	}
	
	private Map<Integer, Paper> loadAllPapers() {

		if(papers != null){
			return papers;
		}
		
		papers = new HashMap<Integer, Paper>();
		
		papers = dao.loadAllPapers();
		
		return papers;
		
	}
	
	private List<Creation> loadAllCreations(Map<Integer, Author> authors, Map<Integer, Paper> papers){
		
		if(creations != null){
			return creations;
		}
		
		creations = new ArrayList<Creation>();
		
		creations = dao.loadAllCreations(authors, papers);
		
		return creations;
		
	}
	
	public void createGraph(){
		
		graph = new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
		
		authors = loadAllAuthors();
		papers = loadAllPapers();
		creations = loadAllCreations(authors, papers);
		
		setAuthorPapers();
		setPaperAuthors();
		
		Graphs.addAllVertices(graph, authors.values());
		
		for(Author a1 : graph.vertexSet()){
			for(Author a2 : dao.getCoAuthors(a1, authors)){
				graph.addEdge(a1, a2);
			}
		}
		
	}

	private void setPaperAuthors() {
		
		for(Paper p : papers.values()){
			for(Creation c : creations){
				if(p.getEprintid() == c.getPaper().getEprintid()){
					p.addAuthor(authors.get(c.getAuthor().getId()));
				}
			}
		}
		
	}

	private void setAuthorPapers() {
		
		for(Author a : authors.values()){
			for(Creation c : creations){
				if(a.getId() == c.getAuthor().getId()){
					a.addPaper(papers.get(c.getPaper().getEprintid()));
				}
			}
		}
		
	}

	public List<Author> getCoAuthors(Author author) {
		
		if(graph == null){
			createGraph();
		}
		
		List<Author> coauthors = Graphs.neighborListOf(graph, author);
		
		Collections.sort(coauthors);
		
		return coauthors;
		
	}

	public List<Paper> showSequence(Author a1, Author a2) {
		
		DijkstraShortestPath<Author, DefaultEdge> sp = new DijkstraShortestPath<Author, DefaultEdge>(this.graph, a1, a2);
				
		List<Paper> sequence = new ArrayList<Paper>();
		
		for(DefaultEdge e : sp.getPathEdgeList()){
			
			Paper p = findCoPaper(graph.getEdgeSource(e), graph.getEdgeTarget(e));
			if(p != null && !sequence.contains(p)){
				sequence.add(p);
			}
				
		}
		
		return sequence;
		
	}

	private Paper findCoPaper(Author a1, Author a2) {
		
		for(Paper p : a1.getPapers()){
			if(a2.getPapers().contains(p)){
				return p;
			}
		}
		
		return null;
		
	}

}
