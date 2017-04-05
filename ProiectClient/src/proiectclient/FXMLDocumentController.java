package proiectclient;

import classes.Asigurare;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField textId;

    @FXML
    private TextArea out;

    @FXML
    private Label label;

    @FXML
    private void handleButtonActionId(ActionEvent event) {
        try {
            Socket s = new Socket("localhost", 1990);
            ObjectOutputStream fout = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream fin = new ObjectInputStream(s.getInputStream());
            int codAsigurare = Integer.parseInt(textId.getText().trim());
            fout.writeObject(codAsigurare);
            Asigurare a = (Asigurare) fin.readObject();
            out.appendText(" Informatiile despre codul " + codAsigurare + " sunt " + "\n");
            
            out.appendText("Cod: "+a.getCod_asigurare()+" Id asigurat: "+a.getId_asigurat()+" Id asigutator: "+a.getId_asigurator()+" Valoare: "+a.getValoare()+" Data emiterii: "+new SimpleDateFormat("dd.MM.yyyy").format(a.getDataEmiterii()));

        } catch (Exception ex) {
            out.appendText(ex.toString());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
