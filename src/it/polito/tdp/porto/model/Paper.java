package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

public class Paper {

	private int eprintid;
	private String title;
	private String issn;
	private String publication;
	private String type;
	private String types;
	private List<Author> authors;

	public Paper(int eprintid, String title, String issn, String publication, String type, String types) {
		this.eprintid = eprintid;
		this.title = title;
		this.issn = issn;
		this.publication = publication;
		this.type = type;
		this.types = types;
		this.authors = new ArrayList<Author>();
	}
	
	/**
	 * @param eprintid
	 */
	public Paper(int eprintid) {
		super();
		this.eprintid = eprintid;
	}

	public int getEprintid() {
		return eprintid;
	}

	public void setEprintid(int eprintid) {
		this.eprintid = eprintid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getPublication() {
		return publication;
	}

	public void setPublication(String pubblication) {
		this.publication = pubblication;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	@Override
	public String toString() {
		return title;
	}
	
	public void addAuthor(Author a){
		
		if(a == null){
			return;
		}
		
		this.authors.add(a);
		
	}
	
	public List<Author> getAuthors(){
		return this.authors;
	}

}
