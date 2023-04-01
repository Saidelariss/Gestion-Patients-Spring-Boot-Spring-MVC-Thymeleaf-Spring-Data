package ma.ehtp.patient_mvc.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.ehtp.patient_mvc.entities.Patient;
import ma.ehtp.patient_mvc.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor   //injection des dépendances en utilisant le constructeur avec paramètres
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping(path = "/index" )
    public String patients(Model model,
                           @RequestParam(name="page" , defaultValue = "0") int page,
                           @RequestParam(name="size" , defaultValue = "5") int size,
                           @RequestParam(name="keyword" , defaultValue = "") String keyword){
        Page<Patient> pagePatients=patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listePatients",pagePatients);
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }

    @GetMapping(path="/delete")
    public String delete(Long id, String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/formPatients")
    public String formPatient(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }

    @PostMapping("/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult, int page, String keyword){
        if(bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);

        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/editPatient")
    public String editPatient(Model model, Long id,
                              @RequestParam(name="page", defaultValue = "0") int page,
                              @RequestParam(name="keyword",defaultValue = "") String keyword){
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editPatients";
    }




}
