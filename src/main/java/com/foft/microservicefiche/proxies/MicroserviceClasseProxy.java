package com.foft.microservicefiche.proxies;

import com.foft.microservicefiche.bean.ClasseBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@FeignClient(name ="MicroserviceClasse")
@RibbonClient(name ="MicroserviceClasse")
public interface MicroserviceClasseProxy {
    @GetMapping("/MicroClasse/classes")
    Iterable<ClasseBean> getClasses ();
    @GetMapping("/MicroClasse/classe/{id}")
    Optional<ClasseBean> getclasse (@PathVariable("id") final Integer id);
}
