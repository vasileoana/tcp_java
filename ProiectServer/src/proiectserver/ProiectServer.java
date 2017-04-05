
package proiectserver;

import java.sql.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.apache.derby.jdbc.ClientDataSource;


public class ProiectServer extends Application {
    public static TextArea out;
    public static Connection c;

    @Override
    public void stop() throws Exception {
       c.close();
    }

    @Override
    public void init() throws Exception {
       try { ClientDataSource client=new ClientDataSource();
        client.setServerName("localhost");
        client.setPortNumber(1527);
        client.setDatabaseName("sample");
        client.setUser("app");
        client.setPassword("app");
        c=client.getConnection();
       }
       catch(Exception ex)
       {
           out.appendText("verificati conexiunea la baza de date");
       }
        
    }
    
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
