package ft.springjpa.controllers;


import ft.springjpa.repositories.PatientRepository;
import ft.springjpa.repositories.UserRepository;
import ft.springjpa.repositories.VilleRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("content")
public class ContentController {
    private final PatientRepository pr;
    private final VilleRepository vr;
    private final UserRepository ur;

    public ContentController(PatientRepository pr, VilleRepository vr, UserRepository ur) {
        this.pr = pr;
        this.vr = vr;
        this.ur = ur;
    }

    @RequestMapping("")
    public String loadContent() {
        return "website";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("content1")
    public String getContent1(Model m) {
        m.addAttribute("lp",pr.findAll());
        m.addAttribute("lv", vr.findAll());
        //m.addAttribute("user",ur.findAll());
        return "/fragments/listpart.html :: content1";
        //return "/fragments/listpart.html";
    }

    @RequestMapping("content2")
    public String getContent2() {
        return "/fragments/header.html :: headerfrag";
    }
}
