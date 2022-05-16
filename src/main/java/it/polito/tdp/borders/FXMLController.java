
package it.polito.tdp.borders;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	Integer anno = Integer.parseInt(txtAnno.getText());
    	
    	if(anno < 1920 || anno > 2016 || anno == null) {
    		txtResult.appendText("Inserire un anno compreso tra il 1920 e il 2016!\n");
    	}
    	
    	model.creaGrafo(anno);
    	
    	Map<Country, Integer> countriesMap = model.getCountry(anno);
    	countriesMap.remove(null);	//Chiedere perché l'elemento 0 della mappa esce null
    	
    	for(Country c : countriesMap.keySet()) {
//    		if(c != null)
    		txtResult.appendText(c.getNome() + " " + countriesMap.get(c) + "\n");
    	}
//    	txtResult.appendText(countriesMap.size() + "");
    	int componentiConnesse = model.componentiConnesse();
    	txtResult.appendText("Componenti connesse: " + componentiConnesse + "\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
