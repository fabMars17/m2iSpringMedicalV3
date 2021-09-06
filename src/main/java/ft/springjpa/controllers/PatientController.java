package ft.springjpa.controllers;


import ft.springjpa.repositories.PatientRepository;
import ft.springjpa.repositories.VilleRepository;
import ft.springjpa.entities.Patient;
import ft.springjpa.entities.Ville;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientRepository pr;
    private final VilleRepository vr;

    public PatientController(PatientRepository pr, VilleRepository vr) {
        this.pr = pr;
        this.vr = vr;
    }

    @GetMapping("/dash")
    public String getDashBoard() {

        return "dashboard.html";
    }

    @GetMapping("/frag")
    public String getHome() {
        return "/fragments/header.html";
    }

    @Secured("ROLE_USER")
    @GetMapping("/list")
    public String list(Model m){
        m.addAttribute("lp",pr.findAll());
        m.addAttribute("lv", vr.findAll());

        return "list.html";
    }

    @GetMapping("/add")
    public String addGet( Model model ){
        model.addAttribute("p" , new Patient());
        model.addAttribute("action", "ajout");
        List<Ville> lv = (List<Ville>) vr.findAll();
        model.addAttribute( "lv" , lv );
        return "add_edit.html";
    }

    //Update add new patient
    @PostMapping("/add")
    public String addPost( HttpServletRequest request ){
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("mail");
        String ville = request.getParameter("ville");

        try{
            Patient p = new Patient();
            p.setNom(nom);
            p.setPrenom(prenom);
            p.setEmail(email);
            p.setTelephone(telephone);

            Ville villeP = new Ville();
            villeP.setId( Integer.parseInt(  ville ) );
            p.setVille( villeP );
            //p.setVille( ville  );

            pr.save( p );

        }catch( Exception e ){

        }
        return "redirect:/patient/list";
    }
    //$ pour afficher le messge qui suit
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model){
        List<Ville> lv = (List<Ville>) vr.findAll();
        model.addAttribute( "lv" , lv );

        Optional<Patient> p;
        p=pr.findById(Integer.parseInt(id));
        model.addAttribute("p", p.get());
        //model.addAttribute( "action" , "mise à jour" );
        return "/add_edit";
    }

    // Update
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable String id, HttpServletRequest request) {
        List<Ville> lv = (List<Ville>) vr.findAll();

        Patient p;
        //Optional<Patient> p;
        p=pr.findById(Integer.parseInt(id)).get();

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("mail");
        String ville = request.getParameter("ville");

        try{

            p.setNom(nom);
            p.setPrenom(prenom);
            p.setEmail(email);
            p.setTelephone(telephone);

            Ville villeP = new Ville();
            villeP.setId( Integer.parseInt(  ville ) );
            p.setVille( villeP );
            //p.setVille( ville  );

            pr.save( p );

        }catch( Exception e ){

        }
        return "redirect:/patient/list";
    }

    // Delete
    @RequestMapping("/delete/{id}")
    public String deletePatient(@PathVariable String id) {
        pr.deleteById(Integer.parseInt(id));
        return "redirect:/patient/list";
    }
}
