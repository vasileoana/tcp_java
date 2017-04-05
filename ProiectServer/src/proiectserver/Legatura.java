
package proiectserver;

import classes.Asigurare;
import classes.TipAsigurare;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javafx.scene.control.TextArea;


public class Legatura extends Thread implements Closeable {
private Socket s;
private TextArea out;
private ObjectOutputStream fout;
private ObjectInputStream fin;

    public Legatura(Socket s, TextArea out) throws IOException {
        this.s = s;
        this.out = out;
        this.fout = new ObjectOutputStream(s.getOutputStream());
        this.fin = new ObjectInputStream(s.getInputStream());
    }

    @Override
    public void run() {
      
        try {
            int codAsigurare=(int)fin.readObject();
            out.appendText(codAsigurare + "\n");
            Statement s=ProiectServer.c.createStatement();
            ResultSet rezultat=s.executeQuery("SELECT * from asigurare where cod_asigurare="+codAsigurare);
            if(rezultat.next())
            {   int cod=rezultat.getInt(1);
                double valoare=rezultat.getDouble(2);
                Date data=rezultat.getDate(3);
                int idAsigurat=rezultat.getInt(4);
                int idAsigurator=rezultat.getInt(5);
                Asigurare a=new Asigurare(cod, valoare, data, idAsigurat, idAsigurator, TipAsigurare.BUNURI);
                //out.appendText(cod + valoare + "\n");
                fout.writeObject(a);
                //out.appendText(a.toString());
            }
            else {
                fout.writeObject(null);
            }
            
        }
        catch(Exception ex)
        {
            out.appendText(ex+"\n");
        }
        
    }

    
    
    
    @Override
    public void close() throws IOException {
fout.close();
fin.close();
    }





}
