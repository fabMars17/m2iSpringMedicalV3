package ft.springjpa.api;


import ft.springjpa.entities.Patient;
import ft.springjpa.entities.Ville;
import ft.springjpa.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController()
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/patient")
public class PatientAPIController {
    @Autowired
    PatientService ps;

    @GetMapping(path="", produces = "application/json")
    List<Patient> all() {
        return (List<Patient>) ps.getList();
    }

    @GetMapping(path="/{id}", produces = "application/json")
    Patient get( @PathVariable("id") int id ) {
        return ps.find(id);
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<Patient> add( @RequestBody Patient patient ) {
        try{
            Patient createPatient = ps.addPatient( patient.getNom() , patient.getPrenom() , patient.getEmail() , patient.getTelephone() , patient.getVille().getId() );
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createPatient.getId())
                .toUri();

        return ResponseEntity.created(uri) // created => HTTP 201
                .body(createPatient);

    }catch ( Exception e ){
        System.out.println("Je suis ici");
        throw new ResponseStatusException( HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @PutMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Patient> edit( @RequestBody Patient patient , @PathVariable("id") int id ) {
        try{
            Patient updatedPatient  = ps.editPatient(id, patient.getNom(), patient.getPrenom() ,  patient.getEmail(), patient.getTelephone(), patient.getVille().getId());

            return ResponseEntity.ok() // OK => HTTP 200
                    .body(updatedPatient);

        }catch ( Exception e ){
            System.out.println("Je suis ici");
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST , e.getMessage()  );
        }

    }



    @DeleteMapping(path = "/{id}", produces = "application/json")
    void deletePatient(@PathVariable(name = "id") int id) {
        ps.delete(id);
    }
}
