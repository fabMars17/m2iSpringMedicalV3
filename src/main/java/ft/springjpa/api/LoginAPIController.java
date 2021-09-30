package ft.springjpa.api;

import ft.springjpa.entities.User;
import ft.springjpa.entities.Ville;
import ft.springjpa.repositories.UserRepository;
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
@RequestMapping("/api/login")
public class LoginAPIController {

    @Autowired
    private UserRepository ur;

    @PostMapping(path = "", produces = "application/json")
    ResponseEntity<User> checkLogin(@RequestBody User urs) {
        System.out.println( "email reçu" + urs.getEmail() );

        try{
            User user = ur.findByEmail( urs.getEmail() );
            user.setPassword("");
            return ResponseEntity.ok() // ok => 200
                    .body(user);

        }catch ( Exception e ){
            System.out.println("Je suis ici : Erreur Login Api exception");
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST ,  "probleme de Password depuis Angular"); // KO : 400e.getMessage()
        }
    }

}
