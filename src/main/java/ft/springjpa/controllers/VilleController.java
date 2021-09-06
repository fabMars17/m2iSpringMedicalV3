package ft.springjpa.controllers;


import ft.springjpa.entities.User;
import ft.springjpa.entities.Ville;
import ft.springjpa.repositories.UserRepository;
import ft.springjpa.repositories.VilleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/ville")
public class VilleController {

    private final VilleRepository vr;

    public VilleController(VilleRepository vr) {
        this.vr = vr;
    }

    @GetMapping("/list")
    public String list(Model m){
        m.addAttribute("lv",vr.findAll());
        return "list_ville.html";
    }

    @GetMapping("/add")
    public String addGet( Model model ){
        model.addAttribute("v" , new Ville());
        model.addAttribute("action", "ajout");
        return "add_edit_ville.html";
    }

    //Update add new patient
    @PostMapping("/add")
    public String addPost( HttpServletRequest request ){
        String nom = request.getParameter("nom");
        String cp = request.getParameter("codepostal");

        try{
            Ville v = new Ville();
            v.setNom(nom);
            v.setCodepostal(Integer.parseInt(cp));

            vr.save( v );

        }catch( Exception e ){

        }
        return "redirect:/ville/list";
    }
    //$ pour afficher le messge qui suit
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model){

        Optional<Ville> v;
        v=vr.findById(Integer.parseInt(id));
        model.addAttribute("v", v.get());
        //model.addAttribute( "action" , "mise à jour" );
        return "/add_edit_ville";
    }

    // Update
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable String id, HttpServletRequest request) {

        Ville v;
        //Optional<Patient> p;
        v=vr.findById(Integer.parseInt(id)).get();

        String nom = request.getParameter("nom");
        String cp = request.getParameter("codepostal");

        try{

            v.setNom(nom);
            v.setCodepostal(Integer.parseInt(cp));

            vr.save( v );

        }catch( Exception e ){

        }
        return "redirect:/ville/list";
    }

    // Delete
    @RequestMapping("/delete/{id}")
    public String deletePatient(@PathVariable String id) {
        vr.deleteById(Integer.parseInt(id));
        return "redirect:/ville/list";
    }
}
