package com.foft.microservicefiche.controller;

import com.foft.microservicefiche.bean.ProgrammeBean;
import com.foft.microservicefiche.model.Fiche;
import com.foft.microservicefiche.proxies.MicroserviceProgrammeProxy;
import com.foft.microservicefiche.service.FicheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("MicroFiche")
public class FicheController {

    @Autowired
    private FicheService ficheService;

    @Autowired
    private MicroserviceProgrammeProxy microserviceProgrammeProxy;
    @GetMapping("/Fiche/{id}")
    public Fiche getFiche(@PathVariable("id") final Integer id ){
        Optional<Fiche> fiche = ficheService.getFiche(id);
        if(fiche.isPresent()) {
            return fiche.get();
        } else {
            return null;
        }
    }

    @GetMapping("/fiche-count/{enseignant_id}")
    public int countFiche(@PathVariable("enseignant_id")Integer enseignant_id){
        return ficheService.countFiche(enseignant_id);
    }

    @GetMapping("/Fiche/{id}/{state}")
    public Fiche getByIdAndState(@PathVariable int id, @PathVariable int state){
        Optional<Fiche> fiche = ficheService.getFicheByIdAndState(id,state);
        if(fiche.isPresent()) {
            return fiche.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Fiche")
    public Iterable<Fiche> getFiches() {
        return ficheService.getFiches();
    }

    @DeleteMapping("/Fiche/{id}")
    public void deleteFiche(@PathVariable("id") final Integer id) {
        ficheService.deleteFiche(id);
    }

    @DeleteMapping("/Fiche/{id}/{state}")
    public void deleteIfReject(@PathVariable int id, @PathVariable int state){
        if(state==2) ficheService.deleteFicheByIdAndState(id, state);
        else return;
    }



    @PutMapping("/valideOrReject/{state}/{id}")
    public int acceptFiche(@PathVariable int state, @PathVariable Integer id) {
        return ficheService.valideOrRejectFiche(state, id);
    }

    @PutMapping("/updateFicheMotif/{id}")
    public int updateFicheMotif(@RequestParam String motif, @PathVariable("id") Integer id ){
        Optional<Fiche> f = ficheService.getFiche(id);
        if(f.isPresent()){
            if (f.get().getState()==0){
                f.get().setState(2);
                ficheService.saveFiche(f.get());
                return ficheService.ajouteMotif(motif, id);
            }
        }
        return 0;
    }




    @PutMapping("/addSignatureEnseignant/{id}")
    public int addSignatureEnseignant (@RequestParam("signature") MultipartFile signature,
                                       @PathVariable("id")Integer id) throws IOException {
        Optional<Fiche> f = ficheService.getFiche(id);
        final String folder = new ClassPathResource("static/SignatureE/").getFile().getAbsolutePath();
        final String route = ServletUriComponentsBuilder.fromCurrentContextPath().path("/SignatureE/").path(signature.getOriginalFilename()).toUriString();
        byte [] bytes = signature.getBytes();
        Path path = Paths.get(folder + File.separator + signature.getOriginalFilename());

        if(f.isPresent()){
            if(f.get().getState() == 0) {
                System.out.println(route);
                Files.write(path,bytes);
                f.get().setState(1);
                f.get().setSignatureEnseignant("/SignatureE/"+signature.getOriginalFilename());
                ficheService.saveFiche(f.get());
                return 1;
            }
        }
        return 0;
    }



    @GetMapping("/fichebyenseignant/{enseignant_id}")
    public Iterable<Fiche> findByIdEnseignant(@PathVariable("enseignant_id") Integer enseignant_id){
        return ficheService.findByIdEnseignant(enseignant_id);
    }

    @GetMapping("/fichebyenseignantandstate/{enseignant_id}/{state}")
    public Iterable<Fiche> findByIdEnseignantAndState(@PathVariable("enseignant_id") Integer enseignant_id, @PathVariable("state")int state){
        return ficheService.findByEnseignantAndState(enseignant_id, state);
    }

    @GetMapping("/fichesdelegue/{delegue_id}")
    public Iterable<Fiche> findByIdDelegue(@PathVariable("delegue_id") Integer delegue_id){
        return ficheService.findByDelegue(delegue_id);
    }

    @GetMapping("/fichedelegueandstate/{classe_id}/{state}")
    public Iterable<Fiche> findByIdDelegueAndState(@PathVariable("classe_id") Integer classe_id, @PathVariable("state") Integer state){
        return ficheService.findByDelegueAndState(classe_id,state);
    }

    @GetMapping("/lol/{id}")
    public List<ProgrammeBean> all(@PathVariable("id") int id){
        return microserviceProgrammeProxy.programmes(id);
    }

}
