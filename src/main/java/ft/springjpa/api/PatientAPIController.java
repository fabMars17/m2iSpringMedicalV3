package ft.springjpa.api;


import ft.springjpa.entities.Patient;
import ft.springjpa.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController()
@RequestMapping("/ws/patient")
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

    /*@PostMapping(path="", produces = "application/json")
    Patient postme( @RequestBody Patient patient ) throws Exception {
        return ps.addPatient( patient.getNom() , patient.getPrenom() , patient.getTelephone() , patient.getEmail() , patient.getVille().getId() );
    }*/

    // Create
    @PostMapping(path="/", consumes = "application/json")
    public ResponseEntity<Patient> create(@RequestBody Patient patient) throws Exception {
        Patient createdPatient = ps.addPatient( patient.getNom() , patient.getPrenom() , patient.getTelephone() , patient.getEmail() , patient.getVille().getId() );
        if (createdPatient == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdPatient.getId())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdPatient);
        }
    }
}
