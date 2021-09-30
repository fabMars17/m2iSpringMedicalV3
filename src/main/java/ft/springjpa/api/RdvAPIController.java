package ft.springjpa.api;

import ft.springjpa.entities.Patient;
import ft.springjpa.entities.Rdv;
import ft.springjpa.services.RdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController()
@RequestMapping("/api/rdv")
public class RdvAPIController {
    @Autowired
    RdvService rs;

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<Iterable<Rdv>> getallRDVApi() {
        try {
            return ResponseEntity.ok().body(rs.getList());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Rdv> getRdvByIdApi(@PathVariable(name = "id") int id) {
        Rdv rendezvousGet = rs.find(id);
        if (rendezvousGet == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(rendezvousGet);
        }
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<Rdv> addRdvApi(@RequestBody Rdv rdv) {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

        String dateFormatStr = dateParser.format( rdv.getDate() );

        try{
            Rdv createRdv = rs.addRdv(dateFormatStr, rdv.getDuree()+"", rdv.getType(), rdv.getNote(), rdv.getPatient().getId());

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createRdv.getId())
                    .toUri();

            return ResponseEntity.created(uri) // created => HTTP 201
                    .body(createRdv);
        }catch( Exception e ){
            System.out.println(e.getMessage());
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }
/*
    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Rdv> updateRdvApi(@PathVariable(name = "id") int id, @RequestBody Rdv rdv) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

        String dateFormatStr = formatter.format( rdv.getDateheure() );

        try{
            RdvEntity rdvUpdate = rs.editRdv( rdv.getId() ,  rdv.getPatient().getId() , ""+ rdv.getDuree(),  rdv.getType(), dateFormatStr , rdv.getNote());
            URI uir = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(rdvUpdate).toUri();
            return ResponseEntity.created(uir).body(rdvUpdate);

        }catch ( Exception e ){
            System.out.println(e.getMessage());
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }
*/
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteRdv(@PathVariable(name = "id")int id) {
        try {
            rs.delete(id);
            return ResponseEntity.ok()
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
