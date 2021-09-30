package ft.springjpa.services;

import ft.springjpa.entities.Patient;
import ft.springjpa.entities.Rdv;
import ft.springjpa.repositories.PatientRepository;
import ft.springjpa.repositories.RdvRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Service
public class RdvService {

    private final RdvRepository rr;
    private final PatientRepository pr;

    public RdvService(RdvRepository rr, PatientRepository pr) {
        this.rr = rr;
        this.pr = pr;
    }

    public Iterable<Rdv> getList() {
        return rr.findAll();
    }

    public Rdv addRdv(String date, String duree, String type, String note, int patient) throws Exception{

        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

        Date dateFormat = dateParser.parse(date);

        Rdv r = new Rdv();
        r.setDate(dateFormat);
        r.setDuree(Integer.parseInt(duree));
        r.setType(type);
        r.setNote(note);

        Patient rdvP = new Patient();
        rdvP.setId( patient );

        r.setPatient( rdvP );

        rr.save( r );

        return r;

    }

    public void delete( int id ) {
        rr.deleteById(id);
    }

    public Rdv find(int id) {
        return rr.findById( id ).get();
    }
}
