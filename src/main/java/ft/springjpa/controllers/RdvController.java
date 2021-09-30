package ft.springjpa.controllers;

import ft.springjpa.entities.Patient;
import ft.springjpa.entities.Rdv;
import ft.springjpa.services.PatientService;
import ft.springjpa.services.RdvService;
import org.hibernate.annotations.Parameter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/rdv")
public class RdvController {

    private final RdvService rs;
    private final PatientService ps;


    public RdvController(RdvService rs, PatientService ps) {
        this.rs = rs;
        this.ps = ps;
    }

    //@Secured("ROLE_ADMIN")
    @GetMapping("/list")
    public String list(Model m){
        /*m.addAttribute("lp", ps.getList());*/
        m.addAttribute("lr", rs.getList());
        return "list_rdv.html";
    }

    @GetMapping("/add/{id}")
    @ResponseBody
    public String addGet(@PathVariable String id,Model model){
        model.addAttribute("p", ps.find(Integer.parseInt(id)));
        /*model.addAttribute("action", "ajout");*/
        model.addAttribute( "r" ,new Rdv() );
        /*model.addAttribute( "lv" , vs.getList() );*/
        //List<Ville> lv = (List<Ville>) vr.findAll();
        //model.addAttribute( "lv" , lv );
        return "add_edit_rdv.html";
    }

    @PostMapping("/add/{id}")
    public String addPost( HttpServletRequest request, Model model ){

        String date = request.getParameter("date");
        String duree = request.getParameter("minute");
        String type = request.getParameter("type");
        String note = request.getParameter("note");
        String patient = request.getParameter("idpatient");

        try{

            rs.addRdv(date, duree, type, note, Integer.parseInt(patient));
            return "redirect:/patient/list";
        }catch( Exception e ){
            model.addAttribute("error" , e.getMessage() );
            return "add_edit_rdv.html";
        }

    }
}
