package ft.springjpa.controllers;


import ft.springjpa.repositories.PatientRepository;
import ft.springjpa.repositories.VilleRepository;
import ft.springjpa.entities.Patient;
import ft.springjpa.entities.Ville;
import ft.springjpa.services.PatientService;
import ft.springjpa.services.VilleService;
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

    //private final PatientRepository pr;
    //private final VilleRepository vr;

    private final PatientService ps;
    private final VilleService vs;

    public PatientController(PatientService ps, VilleService vs) {
        this.ps = ps;
        this.vs = vs;
    }

    @GetMapping("/dash")
    public String getDashBoard() {

        return "dashboard.html";
    }

    @GetMapping("/frag")
    public String getHome() {
        return "/fragments/header.html";
    }

    //@Secured("ROLE_ADMIN")
    @GetMapping("/list")
    public String list(Model m){
        m.addAttribute("lp", ps.getList());
        m.addAttribute("lv", vs.getList());
        return "list.html";
    }

    @GetMapping("/check")
    public String check(@PathVariable String email) throws Exception {
        try{
            ps.checkPatientEmail(email);
        }catch( Exception e ){

        }
        return "redirect:/patient/check?email";
    }

    @GetMapping("/add")
    public String addGet( Model model ){
        model.addAttribute("p" , new Patient());
        model.addAttribute("action", "ajout");
        model.addAttribute( "lv" , vs.getList() );
        //List<Ville> lv = (List<Ville>) vr.findAll();
        //model.addAttribute( "lv" , lv );
        return "add_edit.html";
    }

    //Update add new patient
    @PostMapping("/add")
    public String addPost( HttpServletRequest request ) throws Exception {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("mail");
        String ville = request.getParameter("ville");

        ps.addPatient(nom, prenom, email, telephone, Integer.parseInt(ville));

        return "redirect:/list?success";
    }

    //$ pour afficher le messge qui suit
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model){
        try{
            //model.addAttribute( "actionP" , "maj patient" );
            model.addAttribute( "lv" , vs.getList() );
            model.addAttribute("p", ps.find(Integer.parseInt(id)));
            return "/add_edit";
        }catch ( Exception e ){
            return "redirect:/patient?error="+e.getMessage();
        }
    }

    // Update
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable String id, HttpServletRequest request) {

        try{
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String telephone = request.getParameter("telephone");
            String email = request.getParameter("mail");
            String ville = request.getParameter("ville");

            ps.editPatient(Integer.parseInt(id), nom, prenom, email, telephone, Integer.parseInt(ville));
            return "redirect:/patient/list";
        }catch( Exception e ){
            return "patient/add_edit";
        }

    }

    // Delete
    @RequestMapping("/delete/{id}")
    public String deletePatient(@PathVariable int id) {
        try{
            ps.delete(id);
            //return "redirect:/patient?success";
            return "redirect:/patient/list";
        }catch( Exception e ){
            return "patient?error="+e.getMessage();
        }
        //pr.deleteById(Integer.parseInt(id));

    }
}
