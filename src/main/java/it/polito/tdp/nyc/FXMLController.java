package it.polito.tdp.nyc;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.nyc.model.Coppia;
import it.polito.tdp.nyc.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="clPeso"
    private TableColumn<?, ?> clPeso; // Value injected by FXMLLoader

    @FXML // fx:id="clV1"
    private TableColumn<?, ?> clV1; // Value injected by FXMLLoader

    @FXML // fx:id="clV2"
    private TableColumn<?, ?> clV2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBorough"
    private ComboBox<String> cmbBorough; // Value injected by FXMLLoader

    @FXML // fx:id="tblArchi"
    private TableView<?> tblArchi; // Value injected by FXMLLoader

    @FXML // fx:id="txtDurata"
    private TextField txtDurata; // Value injected by FXMLLoader

    @FXML // fx:id="txtProb"
    private TextField txtProb; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAnalisiArchi(ActionEvent event) {
    	if(model.isGraphLoaded()) {
    		for (Coppia c : model.getTable()) {
    			this.txtResult.appendText(c.toString()+"\n");
    		}
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String borgo = this.cmbBorough.getValue();
    	if(borgo!=null) {
    		model.creaGrafo(borgo);
    		this.txtResult.appendText("Grafo creato con successo. Vertici "+model.getGrafo().vertexSet().size()+" e archi "+model.getGrafo().edgeSet().size()+ "\n");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	String d = this.txtDurata.getText();
    	String p = this.txtProb.getText();
    	try {
    		int D = Integer.parseInt(d);
    		double P = Double.parseDouble(p);
    		model.Simula(P, D);
    		for(String s : model.getMapC().keySet()) {
    			int i = model.getMapC().get(s);
    			if(i>0)
    			this.txtResult.appendText(s+ " "+i+"\n");
    		}
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("Dati inserito in modo scorretto.\n");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clPeso != null : "fx:id=\"clPeso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clV1 != null : "fx:id=\"clV1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clV2 != null : "fx:id=\"clV2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBorough != null : "fx:id=\"cmbBorough\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblArchi != null : "fx:id=\"tblArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDurata != null : "fx:id=\"txtDurata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtProb != null : "fx:id=\"txtProb\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.setCombo();
    }
    
    public void setCombo() {
    	for(String b : model.getBorghi()) {
    		this.cmbBorough.getItems().add(b);
    	}
    }

}
