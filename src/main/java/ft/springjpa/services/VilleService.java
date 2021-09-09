package ft.springjpa.services;

import ft.springjpa.entities.Patient;
import ft.springjpa.entities.Ville;
import ft.springjpa.repositories.PatientRepository;
import ft.springjpa.repositories.VilleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class VilleService {
    private final VilleRepository vr;

    public VilleService( VilleRepository vr) {
        this.vr = vr;
    }

    public Iterable<Ville> getList(){
        return vr.findAll();
    }

    public Ville addVille(String nom, String cp){

        try{
            Ville v = new Ville();
            v.setNom(nom);
            v.setCodepostal(Integer.parseInt(cp));

            vr.save( v );
            return v;
        } catch( Exception e ){

        }

        return null;
    }

    public Ville updateVille(String nom, String cp, String id){

        Ville v;
        v=vr.findById(Integer.parseInt(id)).get();

        try{
            v.setNom(nom);
            v.setCodepostal(Integer.parseInt(cp));

            vr.save( v );
            return v;
        }catch( Exception e ){

        }
        return null;
    }

    public Ville getVille(String id){

        try{
            Optional<Ville> v;
            v=vr.findById(Integer.parseInt(id));

            return v.get();
        } catch( Exception e ){

        }

        return null;
    }

    public void deleteVille(String id) {
        vr.deleteById(Integer.parseInt(id));

    }
}
