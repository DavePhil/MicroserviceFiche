package com.foft.microservicefiche.bean;

import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;

@Data
public class EnseignantBean {

    private Integer id ;
    private String nom;
    private String email;
    private String password;
    private String photo;
}
