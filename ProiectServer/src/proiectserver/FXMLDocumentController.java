package proiectserver;

import classes.Asigurat;
import classes.Asigurator;
import com.sun.jmx.remote.internal.Unmarshal;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class FXMLDocumentController implements Initializable {

    public ServerSocket socket;

    @FXML
    private TextArea out;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Thread t = new Thread(() -> {
            try {
                if (socket == null) {
                    socket = new ServerSocket(1990);
                }
                Socket s = socket.accept();
                Legatura leg = new Legatura(s, out);
                leg.start();
            } catch (Exception ex) {
                out.appendText(ex + "\n");
            }
        });
        out.clear();

        out.appendText("Conexiune deschisa!");
        t.start();

    }

    @FXML
    private void handleButtonStop(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleButtonCitire(ActionEvent event) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Angajati.class);
            Unmarshaller unm=jc.createUnmarshaller();
            Angajati a=(Angajati)unm.unmarshal(new File("Asiguratori.xml"));
            out.clear();
            out.appendText(a.toString());
        }
        catch(Exception ex)
        {
            
        }
    }

    private List<Asigurator> asiguratori = new ArrayList<>();

    @FXML
    private void handleButtonWrite(ActionEvent event) throws SQLException, JAXBException, IOException {
        try {
            Statement s = ProiectServer.c.createStatement();
            ResultSet rez = s.executeQuery("SELECT * FROM ASIGURATOR");
            while (rez.next()) {
                int id_asig = rez.getInt(1);
                String nume = rez.getString(2);
                Asigurator a = new Asigurator(id_asig, nume);
                asiguratori.add(a);

            }
            Angajati angajati = new Angajati(asiguratori, "Axasig SC");
            JAXBContext jc = JAXBContext.newInstance(Angajati.class);

            Marshaller mh = jc.createMarshaller();
            mh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            mh.marshal(angajati, new File("Asiguratori.xml"));
            SchemaOutputResolver schema = new SchemaOutputResolver() {
                @Override
                public Result createOutput(String string, String string1) throws IOException {
                    return new StreamResult(string1);
                }
            };
            jc.generateSchema(schema);
        } catch (Exception ex) {
            out.appendText(ex + "\n");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
