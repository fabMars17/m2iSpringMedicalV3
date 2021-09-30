package ft.springjpa.api;

import ft.springjpa.entities.Ville;
import ft.springjpa.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/ville")
public class VilleAPIController {

        @Autowired
        VilleService vs;

        @GetMapping(path = "", produces = "application/json")
        Iterable<Ville> getAllVilleApi() {
            return vs.getList();
        }

        @GetMapping(path = "/{id}", produces = "application/json")
        Ville getVilleByIdApi(@PathVariable(name = "id") String id) {
            return vs.find(Integer.parseInt(id));
        }

    @PostMapping(path = "", produces = "application/json")
    ResponseEntity<Ville> addVilleApi(@RequestBody Ville ville) {
        try{
            Ville createVille = vs.add(ville.getNom() , ville.getCodepostal() );

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createVille.getId())
                    .toUri();

            return ResponseEntity.created(uri) // created => HTTP 201
                    .body(createVille);

        }catch ( Exception e ){
            System.out.println("Je suis ici");
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

        @PutMapping(path = "/{id}", produces = "application/json")
        Ville updateVilleApi(@PathVariable(name = "id") int id, @RequestBody Ville ville ) {
            return vs.edit(id, ville.getNom(), ville.getCodepostal());
        }

        @DeleteMapping(path = "/{id}", produces = "application/json")
        void deleteVille(@PathVariable(name = "id") int id) {
            vs.delete(id);
        }

}
