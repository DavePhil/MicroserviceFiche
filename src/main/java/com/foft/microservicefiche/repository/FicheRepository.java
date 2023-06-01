package com.foft.microservicefiche.repository;

import com.foft.microservicefiche.model.Fiche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FicheRepository extends JpaRepository<Fiche, Integer> {

    Fiche findFicheById(int id);

    @Modifying
    @Transactional
    @Query("update Fiche fiche set fiche.state = ?1 where fiche.id =?2 ")
    int valideOrReject (int state, int id);

    @Modifying
    @Transactional
    @Query("update Fiche fiche set fiche.motif = ?1  where  fiche.id = ?2 ")
    int ajouteMotif(String motif, int id);

    @Modifying
    @Transactional
    @Query("update Fiche fiche set fiche.signatureEnseignant = ?1 where fiche.id = ?2 ")
    int ajouteSignature(MultipartFile signature, int id);

    Optional<Fiche> findFicheByIdAndState(int id, int state);

    void deleteByIdAndState(int id, int state);

    @Query("select fiche from Fiche fiche where fiche.idEnseignant=:enseignant_id")
    List<Fiche> findByEnseignant(@Param("enseignant_id") Integer enseignant_id);

    @Query("select fiche from Fiche fiche  where fiche.idEnseignant=:enseignant_id and fiche.state=:state")
    List<Fiche> findByEnseignantAndState(@Param("enseignant_id") Integer enseignant_id, @Param("state") Integer state);

    @Query("select fiche from Fiche fiche  where fiche.idClasse=:idClasse and fiche.state=:state")
    List<Fiche> findByDelegueAndState (@Param("idClasse")Integer idClasse, @Param("state") Integer state);

    @Query("select fiche from Fiche fiche  where fiche.idClasse=:idClasse")
    List<Fiche> findByDelegue(@Param("idClasse") Integer idClasse);

    List<Fiche> findByState(@Param("state") Integer state);
    @Query("select count(fiche) FROM Fiche fiche  where fiche.idEnseignant=:enseignant_id and fiche.idSeance=:seance")
    int countFicheBySignatureEnseignant(@Param("enseignant_id") Integer enseignant_id, @Param("seance") Integer seance);
}
