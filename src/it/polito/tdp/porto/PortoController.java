package it.polito.tdp.porto;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;
    
    private Model model;

    @FXML
    void handleCoautori(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Author author = boxPrimo.getValue();
    	
    	if(author == null){
    		txtResult.setText("ERROR: Insert an author.");
    		return;
    	}
    	
    	List<Author> coauthors = model.getCoAuthors(author);
    	
    	for(Author a : coauthors){
    		txtResult.appendText(a.toString()+"\n");
    	}
    	
    	for(Author a : boxPrimo.getItems()){
    		if(!a.equals(author) && !coauthors.contains(a)){
    			boxSecondo.getItems().add(a);
    		}
    	}

    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Author a1 = boxPrimo.getValue();
    	Author a2 = boxSecondo.getValue();
    	
    	if(a1 == null || a2 == null){
    		txtResult.setText("ERROR.");
    		return;
    	}
    	
    	List<Paper> sequence = model.showSequence(a1, a2);
    	
    	for(Paper p : sequence){
    		txtResult.appendText(p.toString()+"\n");
    	}
    	
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }

	public void setModel(Model model) {

		this.model = model;
		
		List<Author> authors = model.getAllAuthors();
		
		Collections.sort(authors);
				
		boxPrimo.getItems().addAll(authors);
		
		model.createGraph();
		
	}
}
