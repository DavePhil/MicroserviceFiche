package com.foft.microservicefiche.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;


@Component  // On utilise Component pour renseigner à spring d'utilser cette classe comme composant et afficher les erreurs correspondantes. Il aurait été possible de créer une classe annoter de @Configuration et de d'y instancier une classe qui fera de même
public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defautErrorDecoder = new Default(); /* on récupère une instance d'error Decoder pour afficher l'erreur feign par defaut
    si l'on ne reconnait pas l'erreur correspondante */
    @Override
    public Exception decode(String s, Response response) {  // On implémente la méthode decode qui nous permet de récupérer le code d'erreur envoyé, afin de lancer des exceptions en fonction de celui-ci.
        if (response.status() == 400 || response.status() == 404){
            return new BadRequest(
                    "Requête incorrecte"
            );
        }
        return defautErrorDecoder.decode(s,response);
    }
}
