package com.foft.microservicefiche.proxies;

import com.foft.microservicefiche.bean.ProgrammeBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name ="MicroserviceProgramme", url = "http://localhost:9005")
public interface MicroserviceProgrammeProxy {

    @PostMapping("/programme")
    ResponseEntity<ProgrammeBean> createProgramme(@RequestBody ProgrammeBean programme);
    @PostMapping("/programmec/{id}")
    ProgrammeBean updateProgramme(@PathVariable("id") int id, @RequestBody ProgrammeBean programme);
    @DeleteMapping("/deleteprogramme/{id}")
    void deleteProgramme(@PathVariable int id);
    @GetMapping("/programme/{idjour}")
    List<ProgrammeBean> programmes (@PathVariable("idjour") Integer idjour);
    @GetMapping("/programme/{id}")
    Optional<ProgrammeBean> getProgrammes(@PathVariable int id);

}
