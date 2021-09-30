package ft.springjpa.services;

import ft.springjpa.entities.Patient;
import ft.springjpa.entities.Ville;
import ft.springjpa.repositories.PatientRepository;
import ft.springjpa.repositories.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository pr;
    private final VilleRepository vr;

    public PatientService(PatientRepository pr, VilleRepository vr) {
        this.pr = pr;
        this.vr = vr;
    }

    public Iterable<Patient> getList() {
        return pr.findAll();
    }

    public void checkPatientEmail(String email ) throws Exception {
        Iterable<Patient> prf = getList();
        for(Patient p: prf) {
            if (p.getEmail() == email) {
                throw new Exception("Email already exist !");
            }
        }
    }

    private void  checkPatient( String nom, String prenom, String telephone , String email ) throws Exception {
        if( prenom.length() < 2 ){
            throw new Exception("Invalid value pour prénom");
        }

        if( nom.length() < 2 ){
            throw new Exception("Invalid value pour nom");
        }

        if( telephone.length() < 2 ){
            throw new Exception("Invalid value pour telephone");
        }

        if( email.length() < 2 ){
            throw new Exception("Invalid value pour email");
        }
    }

    public Patient addPatient(String nom, String prenom, String email, String telephone, int ville) throws Exception{

        checkPatient( nom, prenom, telephone , email );

            Patient p = new Patient();
            p.setNom(nom);
            p.setPrenom(prenom);
            p.setEmail(email);
            //p.setPhoto("");
            p.setTelephone(telephone);

            Ville villeP = new Ville();
            villeP.setId(  ville  );
            p.setVille( villeP );
            //p.setVille( ville  );

            pr.save( p );
            return p;

    }

    public Patient editPatient(int idp, String nom, String prenom, String email, String telephone , int ville ) throws Exception {
        checkPatient( nom, prenom, telephone , email );

        Patient p = pr.findById(idp).get();
        p.setNom(nom);
        p.setPrenom(prenom);
        p.setEmail(email);
        p.setTelephone(telephone);
        Ville villeP = new Ville();
        villeP.setId( ville );
        p.setVille( villeP );
        pr.save( p );
        return p;
    }

    public Patient find(int id) {
        return pr.findById( id ).get();
    }

    public void delete(int id) {
        pr.deleteById(id);
    }
}
