package com.foft.microservicefiche.service;

import com.foft.microservicefiche.bean.EnseignantBean;
import com.foft.microservicefiche.bean.ProgrammeBean;
import com.foft.microservicefiche.bean.SeanceBean;
import com.foft.microservicefiche.model.Fiche;
import com.foft.microservicefiche.proxies.MicroserviceProgrammeProxy;
import com.foft.microservicefiche.proxies.MicroserviceSeanceProxy;
import com.foft.microservicefiche.repository.FicheRepository;
import com.foft.microservicefiche.bean.UniteEnseignementBean;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class FicheService {

    @Autowired
    FicheRepository ficheRepository;

    @Autowired
    MicroserviceProgrammeProxy programmeProxy;
    @Autowired
    MicroserviceSeanceProxy seanceProxy;


    public Optional<Fiche> getFiche(Integer id){
        return ficheRepository.findById(id);
    }

    public void createFiche (Integer idProgramme){
        Fiche fiche = new Fiche();
        fiche.setIdProgramme(idProgramme);
        //On récupère le programme correspondant
        Optional<ProgrammeBean> programmeBean = programmeProxy.getProgrammes(idProgramme);
        ProgrammeBean programme = programmeBean.get();
        // On récupère l'unité d'enseignant correspondant
        UniteEnseignementBean uniteEnseignement = programmeProxy.uniteEnseignement(programme.getIdUe());
        //On récupère l'enseignant
        EnseignantBean enseignant = programmeProxy.getEnseignant(programme.getIdEnseignant());
        //On récupère la seance
        SeanceBean seance = seanceProxy.getSeance(programme.getSeance().getId());

        fiche.setTitre(programme.getSeance().getNom() + " de " + uniteEnseignement.getCode());
        fiche.setDate(new Date());
        fiche.setIdClasse(programme.getIdClasse());
        fiche.setIdSeance(seance.getId());
        ficheRepository.save(fiche);
    }

    public Iterable<Fiche> getFiches(){
        return ficheRepository.findAll();
    }

    public void deleteFiche (Integer id){
        ficheRepository.deleteById(id);
    }

    public void deleteFicheByIdAndState(Integer id, Integer state){ficheRepository.deleteByIdAndState(id,state);}

    public Fiche saveFiche (Fiche fiche){
        Fiche saved = ficheRepository.save(fiche);
        return saved;
    }
    public Optional<Fiche> getFicheByIdAndState(int id, int state){
        return ficheRepository.findFicheByIdAndState(id,state);
    }

    public int valideOrRejectFiche(int state, Integer id){
        return ficheRepository.valideOrReject(state, id);
    }

    public int ajouteMotif(String motif, int id){
        return ficheRepository.ajouteMotif(motif,id);
    }

    public int ajouteSignature(MultipartFile signature, int id){
        return ficheRepository.ajouteSignature(signature,id);
    }

    public List<Fiche> findByIdEnseignant(Integer id_enseignant){
        return ficheRepository.findByEnseignant(id_enseignant);
    }

    public List<Fiche> findByEnseignantAndState(Integer id_enseignant, Integer state){
        return ficheRepository.findByEnseignantAndState(id_enseignant, state);
    }

    public List<Fiche> findByDelegueAndState(Integer classe_id, Integer state){
        return ficheRepository.findByDelegueAndState(classe_id, state);
    }

    public List<Fiche> findByDelegue (Integer id_delegue){
        return ficheRepository.findByDelegue(id_delegue);
    }

    public  List<Fiche> findByState (Integer state) {return ficheRepository.findByState(state);}

    public  int countFiche(Integer enseignant_id){

        int nombreFicheCM = ficheRepository.countFicheBySignatureEnseignant(enseignant_id,1);
        int nombreFicheTD = ficheRepository.countFicheBySignatureEnseignant(enseignant_id,2);
        return (nombreFicheTD*2) + (nombreFicheCM*3);
    }

    public int idOfJour (String jour){
        //  System.out.println(id);
        return switch (jour) {
            case "lundi" -> 1;
            case "mardi" -> 2;
            case "mercredi" -> 3;
            case "jeudi" -> 4;
            case "vendredi" -> 5;
            case "samedi" -> 6;
            case "dimanche" -> 7;
            default -> 0;
        };
    }

    Date aujourdhui = new Date();
    private static final SimpleDateFormat formater = new SimpleDateFormat("EEEE");
    private static final SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm");
    String jour = formater.format(aujourdhui);

        @Scheduled(cron = "0 59 23 * * ?") // Exécute tous les jours à 23 h 59 min
    public void planifierRattrapage() {
        List<Fiche> fiches = findByState(0);
        for(Fiche fiche : fiches) fiche.setState(3);
    }
    @Scheduled(cron = "0 59 23 * * L") // Exécute le dernier jour de la semaine à 23 h 59 min
    public void planifierEchec() {
        List<Fiche> fiches = findByState(0);
        for(Fiche fiche : fiches) fiche.setState(3);
    }

    @Scheduled(fixedRate = 29000) // Exécute toutes les 30 secondes
    public void genererFiche() throws ParseException {

        List<ProgrammeBean> programmes = programmeProxy.programmes(idOfJour(jour));
        for (ProgrammeBean programme : programmes){
            String heureDebut = heureFormat.format(heureFormat.parse(programme.getHeureDeDebut().toString()));
            if(heureDebut.equals(heureFormat.format(new Date()))){
                createFiche(programme.getId());
            }
        }

    }

}
