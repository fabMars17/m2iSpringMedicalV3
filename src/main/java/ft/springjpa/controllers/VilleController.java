package ft.springjpa.controllers;


import ft.springjpa.entities.User;
import ft.springjpa.entities.Ville;
import ft.springjpa.repositories.UserRepository;
import ft.springjpa.repositories.VilleRepository;
import ft.springjpa.services.PatientService;
import ft.springjpa.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/ville")
public class VilleController {

    @Autowired
    private final VilleService vs;

    public VilleController(VilleService vs) {
        this.vs = vs;
    }

    @GetMapping("/list")
    public String list(Model m){
        m.addAttribute("lv",vs.getList());
        return "list_ville.html";
    }

    @GetMapping("/add")
    public String addGet( Model model ) {
        model.addAttribute("v" , new Ville());
        model.addAttribute("action", "ajout");
        return "add_edit_ville";
    }

    //Update add new ville
    @PostMapping("/add")
    public String addPost( HttpServletRequest request ) {
        String nom = request.getParameter("nom");
        String cp = request.getParameter("codepostal");

        vs.add(nom,Integer.parseInt(cp));

        return "redirect:/list?success";
    }

    //$ pour afficher le messge qui suit
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {

        model.addAttribute("v", vs.getVille(id));
        //model.addAttribute( "action" , "mise à jour" );
        return "/add_edit_ville";
    }

    // Update
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable String id, HttpServletRequest request) {

        String nom = request.getParameter("nom");
        String cp = request.getParameter("codepostal");

        //vs.updateVille(nom, cp, id);

        return "redirect:/ville/list";
    }

    // Delete
    @Secured("ROLE_ADMIN")
    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/delete/{id}")
    public String deletePatient(@PathVariable String id) {
        try {
            vs.delete(Integer.parseInt(id));
        }
        catch( Exception e ){
            System.out.println(e.getMessage());
        }

        return "redirect:/ville/list";
    }
}
