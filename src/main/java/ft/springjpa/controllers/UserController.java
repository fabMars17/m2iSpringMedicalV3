package ft.springjpa.controllers;

import ft.springjpa.entities.Patient;
import ft.springjpa.entities.User;
import ft.springjpa.entities.Ville;
import ft.springjpa.repositories.PatientRepository;
import ft.springjpa.repositories.UserRepository;
import ft.springjpa.repositories.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository ur;

    public UserController(UserRepository ur) {
        this.ur = ur;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/list")
    public String list(Model m){
        m.addAttribute("lu",ur.findAll());
        return "list_user.html";
    }

    @GetMapping("/add")
    public String addGet( Model model ){
        model.addAttribute("u" , new User());
        model.addAttribute("action", "ajout");
        return "add_edit_user.html";
    }

    //Update add new patient
    @PostMapping("/add")
    public String addPost( HttpServletRequest request ){
        String nom = request.getParameter("nom");
        String email = request.getParameter("mail");
        String role = request.getParameter("role");
        String pass = request.getParameter("pass");

        try{
            User u = new User();
            u.setName(nom);
            u.setEmail(email);
            u.setRoles(role);
            u.setPassword(passwordEncoder.encode(pass));
            ur.save( u );

        }catch( Exception e ){

        }
        return "redirect:/user/list";
    }
    //$ pour afficher le messge qui suit
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model){

        Optional<User> u;
        u=ur.findById(Integer.parseInt(id));
        model.addAttribute("u", u.get());
        model.addAttribute("as_admin", u.get().getRoles().equals("ROLE_ADMIN"));
        //model.addAttribute( "action" , "mise à jour" );
        return "/add_edit_user";
    }

    // Update
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable String id, HttpServletRequest request) {

        User u;
        //Optional<Patient> p;
        u=ur.findById(Integer.parseInt(id)).get();

        String nom = request.getParameter("nom");
        String email = request.getParameter("mail");
        String role = request.getParameter("role");
        String pass = request.getParameter("pass");

        try{
            if (pass.length()>0){
                u.setPassword(passwordEncoder.encode(pass));
            }
            u.setName(nom);
            u.setEmail(email);
            u.setRoles(role);

            ur.save( u );

        }catch( Exception e ){

        }
        return "redirect:/user/list";
    }

    // Delete
    @RequestMapping("/delete/{id}")
    public String deletePatient(@PathVariable String id) {
        ur.deleteById(Integer.parseInt(id));
        return "redirect:/user/list";
    }
}