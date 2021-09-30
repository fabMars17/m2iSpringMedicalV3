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

    public Ville find(int id) {
        return vr.findById( id ).get();
    }

    public Ville add(String nom, int cp){


            Ville v = new Ville();
            v.setNom(nom);
            v.setCodepostal(cp);

            vr.save( v );
            return v;

    }

    public Ville edit(int id, String nom, int cp) {
        Ville p = vr.findById(id).get();
        p.setNom(nom);
        p.setCodepostal(cp);
        vr.save( p );
        return p;
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

    public void delete(int id) {
        vr.deleteById(id);

    }
}
