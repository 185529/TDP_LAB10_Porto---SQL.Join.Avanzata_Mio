package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Creation;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {
	
public Map<Integer, Author> loadAllAuthors(){
		
		final String sql = "SELECT * FROM author ORDER BY lastname ASC";
		
		Map<Integer, Author> authors = new HashMap<Integer, Author>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Author a = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				authors.put(a.getId(), a);
			}

			rs.close();
			st.close();
			conn.close();
			
			return authors;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("DB Error.");
		}
		
	}

public Map<Integer, Paper> loadAllPapers(){

	final String sql = "SELECT eprintid, title, issn, publication, \"type\", types FROM paper";

	Map<Integer, Paper> papers = new HashMap<Integer, Paper>();

	try {

		Connection conn = DBConnect.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		ResultSet rs = st.executeQuery();

		while(rs.next()) {
			Paper p = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"), rs.getString("publication"), rs.getString("type"), rs.getString("types"));
			papers.put(p.getEprintid(), p);
		}

		rs.close();
		st.close();
		conn.close();

		return papers;

	} catch (SQLException e) {
		// e.printStackTrace();
		throw new RuntimeException("DB Error.");
	}

}

	public List<Creation> loadAllCreations(Map<Integer, Author> authors, Map<Integer, Paper> papers) {
		
		final String sql = "SELECT * FROM creator";

		List<Creation> creations = new ArrayList<Creation>();

		try {

			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Creation c = new Creation(papers.get(rs.getInt("eprintid")), authors.get(rs.getInt("authorid")));
				creations.add(c);
			}

			rs.close();
			st.close();
			conn.close();

			return creations;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("DB Error.");
		}
		
	}

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Author> getCoAuthors(Author a, Map<Integer, Author> authors) {
		
		final String sql = "SELECT DISTINCT * FROM ( SELECT author.id FROM author, creator AS c1, creator AS c2 WHERE c1.eprintid = c2.eprintid AND c1.authorid = ? AND c2.authorid = author.id ) AS result WHERE id != ?";
		
		List<Author> coauthors = new ArrayList<Author>();
		
		try {

			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, a.getId());
			st.setInt(2, a.getId());
			
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				coauthors.add(authors.get(rs.getInt("id")));
			}

			rs.close();
			st.close();
			conn.close();

			return coauthors;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("DB Error.");
		}
		
	}

	

}