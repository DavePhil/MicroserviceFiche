package com.foft.microservicefiche.repository;

import com.foft.microservicefiche.model.Fiche;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FicheRepository extends JpaRepository<Fiche, Integer> {
}
