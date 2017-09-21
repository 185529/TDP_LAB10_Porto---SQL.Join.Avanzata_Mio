package it.polito.tdp.porto.model;

public class Creation {
	
	private Paper paper;
	private Author author;
	
	/**
	 * @param paper
	 * @param author
	 */
	public Creation(Paper paper, Author author) {
		super();
		this.paper = paper;
		this.author = author;
	}

	/**
	 * @return the paper
	 */
	public Paper getPaper() {
		return paper;
	}

	/**
	 * @param paper the paper to set
	 */
	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	/**
	 * @return the author
	 */
	public Author getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	

}
