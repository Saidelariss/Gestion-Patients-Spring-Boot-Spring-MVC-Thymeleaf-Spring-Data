package ma.ehtp.patient_mvc;

import ma.ehtp.patient_mvc.entities.Patient;
import ma.ehtp.patient_mvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(new Patient(null,"said",new Date(),false,102));
            patientRepository.save(new Patient(null,"mohammed",new Date(),true,802));
            patientRepository.save(new Patient(null,"imad",new Date(),true,158));
            patientRepository.save(new Patient(null,"ayoub",new Date(),false,137));

            patientRepository.findAll().forEach(p->{
                System.out.println(p.getNom());
            });
        };
    }

}
