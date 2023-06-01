package com.foft.microservicefiche.proxies;

import com.foft.microservicefiche.bean.SeanceBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="MicroserviceSeance")
@RibbonClient(name = "MicroserviceSeance")
public interface MicroserviceSeanceProxy {

    @GetMapping("/MicroSeance/seance")
    Iterable<SeanceBean> getSeances();
    @GetMapping("/MicroSeance/seance/{id}")
    SeanceBean getSeance(@PathVariable("id") final Integer id );

}
