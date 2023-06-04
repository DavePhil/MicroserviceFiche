package com.foft.microservicefiche.proxies;

import com.foft.microservicefiche.bean.ProgrammeBean;
import com.foft.microservicefiche.bean.UniteEnseignementBean;
import com.foft.microservicefiche.bean.EnseignantBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name ="MicroserviceProgramme")
@RibbonClient(name = "MicroserviceProgramme", configuration = {})
public interface MicroserviceProgrammeProxy {

    @PostMapping("/MicroProg/programme")
    ResponseEntity<ProgrammeBean> createProgramme(@RequestBody ProgrammeBean programme);
    @PostMapping("/MicroProg/programmec/{id}")
    ProgrammeBean updateProgramme(@PathVariable("id") int id, @RequestBody ProgrammeBean programme);
    @DeleteMapping("/MicroProg/deleteprogramme/{id}")
    void deleteProgramme(@PathVariable int id);
    @GetMapping("/MicroProg/programmes/{idjour}")
    List<ProgrammeBean> programmes (@PathVariable("idjour") Integer idjour);
    @GetMapping("/MicroProg/programme/{id}")
    Optional<ProgrammeBean> getProgrammes(@PathVariable int id);

    @GetMapping("/MicroProg/Enseignant/{id}")
    EnseignantBean getEnseignant(@PathVariable("id") final Integer id );

    @GetMapping("/MicroProg/Enseignant")
    public Iterable<EnseignantBean> getEnseignants();

    @GetMapping("/MicroProg/ue/{id}")
    UniteEnseignementBean uniteEnseignement (@PathVariable("id") Integer id);

    @GetMapping("/MicroProg/ues")
    Iterable<UniteEnseignementBean> uniteEnseignements();
    

}
