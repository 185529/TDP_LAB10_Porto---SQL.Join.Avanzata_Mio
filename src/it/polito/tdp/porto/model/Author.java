package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

public class Author implements Comparable<Author> {

	private int id;
	private String lastname;
	private String firstname;
	private List<Paper> papers;
		
	public Author(int id, String lastname, String firstname) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.papers = new ArrayList<Paper>();
	}
	
	/**
	 * @param id
	 */
	public Author(int id) {
		super();
		this.id = id;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Override
	public String toString() {
		return lastname + " " + firstname;
	}

	@Override
	public int compareTo(Author other) {
		return this.getLastname().compareTo(other.getLastname());
	}
	
	public void addPaper(Paper p){
		
		if(p == null){
			return;
		}
		
		this.papers.add(p);
		
	}
	
	public List<Paper> getPapers(){
		return this.papers;
	}
	
}
