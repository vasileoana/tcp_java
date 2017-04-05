package proiectserver;

import classes.Asigurator;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Angajati {

    private List<Asigurator> angajati;
    private String denumireFirma;

    public Angajati(List<Asigurator> angajati, String denumireFirma) {
        this.angajati = angajati;
        this.denumireFirma = denumireFirma;
    }

    public Angajati() {
      angajati = new ArrayList<>();
    }

    public List<Asigurator> getAngajati() {
        return angajati;
    }

    public void setAngajati(List<Asigurator> angajati) {
        this.angajati = angajati;
    }

    public String getDenumireFirma() {
        return denumireFirma;
    }

    public void setDenumireFirma(String denumireFirma) {
        this.denumireFirma = denumireFirma;
    }

    @Override
    public String toString() {
        return "Angajati{" + "angajati=" + angajati + ", denumireFirma=" + denumireFirma + '}'+"\n";
    }
    
    

        
}
