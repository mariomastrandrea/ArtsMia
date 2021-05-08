/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;

public class FXMLController 
{	
	private Model model;

    @FXML 
    private ResourceBundle resources;

    @FXML 
    private URL location;

    @FXML
    private ComboBox<Integer> boxLUN;

    @FXML 
    private Button btnCalcolaComponenteConnessa; 

    @FXML 
    private Button btnCercaOggetti; 

    @FXML 
    private Button btnAnalizzaOggetti; 

    @FXML 
    private TextField txtObjectId; 
    private final int MAX_CHARS_ID_OBJECT = 7;

    @FXML 
    private TextArea txtResult; 

    
    @FXML
    void doAnalizzaOggetti(ActionEvent event) 
    {
    	this.model.creaGrafo();
    	
    	int numVertices = this.model.getNumVertices();
    	int numEdges = this.model.getNumEdges();
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("Grafo creato!\n");
    	sb.append("#Vertici: ").append(numVertices).append("\n");
    	sb.append("#Archi: ").append(numEdges);
    	
    	this.txtResult.setText(sb.toString());
    	
    	this.txtObjectId.setDisable(false);
    	
    	this.boxLUN.setDisable(true);
    	this.btnCercaOggetti.setDisable(true);
    }
    
    @FXML
    void handleObjectIdTyping(KeyEvent event) 
    {
    	if(this.txtObjectId.getText().isBlank())
    	{
    		this.btnCalcolaComponenteConnessa.setDisable(true);
    	}
    	else
    	{
    		this.btnCalcolaComponenteConnessa.setDisable(false);
    	}
    }

    @FXML
    void doCalcolaComponenteConnessa(ActionEvent event) 
    {
    	String objectIdInput = this.txtObjectId.getText();
    	int objectId = -1;
    	
    	try
    	{
    		objectId = Integer.parseInt(objectIdInput);
    	}
    	catch (NumberFormatException nfe) 
    	{
    		this.txtResult.setText("Errore: formato input non corretto. Inserire un numero intero");
			return;
		}
    	
    	Collection<ArtObject> connectedComponent = this.model.computeConnectedComponentOf(objectId);
    	
    	if(connectedComponent == null)
    	{
    		this.txtResult.setText("Errore: non esiste alcun vertice con id = " + objectId);
    		return;
    	}
    	
    	if(connectedComponent.size() == 1)
    	{
    		this.txtResult.setText("L'oggetto con id = " + objectId + " è un punto isolato!");
    		return;
    	}
    	
    	this.txtResult.setText("Calcolata la componente connessa dell'oggetto id = " + objectId + "\n");
    	this.txtResult.appendText("#Vertici: " + connectedComponent.size());
    	
    	this.boxLUN.setDisable(false);
    	this.boxLUN.setValue(null);
    }
    
    @FXML
    void handleBoxLUNSelection(ActionEvent event) 
    {
    	if(this.boxLUN.getValue() != null)
    		this.btnCercaOggetti.setDisable(false);
    	else
    		this.btnCercaOggetti.setDisable(true);
    }

    @FXML
    void doCercaOggetti(ActionEvent event) 
    {
    	//TODO: implementare metodo
    	
    }  

    @FXML 
    void initialize() 
    {
        assert boxLUN != null : "fx:id=\"boxLUN\" was not injected: check your FXML file 'Scene_artsMia.fxml'.";
        assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Scene_artsMia.fxml'.";
        assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Scene_artsMia.fxml'.";
        assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Scene_artsMia.fxml'.";
        assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Scene_artsMia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene_artsMia.fxml'.";
        
        this.txtObjectId.setTextFormatter(new TextFormatter<>(change -> 
        {        	
        	String text = change.getText();
        	if(text == null || text.isEmpty())
        		return change;
        	
        	int alreadyPresentChars = this.txtObjectId.getText().length();
        	int maxNumCharsLeft = MAX_CHARS_ID_OBJECT - alreadyPresentChars;
        	
        	if(text.length() > maxNumCharsLeft)
        		text = text.substring(0, maxNumCharsLeft);
        	
        	if(!text.matches("[\\d]+"))
        		text = text.replaceAll("\\D", "");
        	
        	change.setText(text);
        	        	
        	return change;
        }));
    }
    
    public void setModel(Model model) 
    {
    	this.model = model;
    }
}
