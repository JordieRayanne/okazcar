package com.okazcar.okazcar.services;

import com.okazcar.okazcar.models.Contact;
import com.okazcar.okazcar.models.HistoriqueMessage;
import com.okazcar.okazcar.models.Users;
import com.okazcar.okazcar.models.Utilisateur;
import com.okazcar.okazcar.models.mongodb.UserMongoDb;
import com.okazcar.okazcar.repositories.HistoriqueMessageRepository;
import com.okazcar.okazcar.repositories.mongodb.UserMongoDbRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HistoriqueMessageService {

    final HistoriqueMessageRepository historiqueMessageRepository;
    final UtilisateurService utilisateurService;
    final UserMongoDbRepository userMongoDbRepository;

    @Autowired
    public HistoriqueMessageService(HistoriqueMessageRepository historiqueMessageRepository, UtilisateurService utilisateurService, UserMongoDbRepository userMongoDbRepository) {
        this.historiqueMessageRepository = historiqueMessageRepository;
        this.utilisateurService = utilisateurService;
        this.userMongoDbRepository = userMongoDbRepository;
    }

    // Par défaut utilisateur1 est celui qui a envoyé le 1er message
    public HistoriqueMessage insert(String utilisateur1, String utilisateur2) {
        HistoriqueMessage historiqueMessage = new HistoriqueMessage(utilisateur1, utilisateur2);
        return historiqueMessageRepository.save(historiqueMessage);
    }

    public Contact getContact(HttpServletRequest request) {
        Utilisateur utilisateur = utilisateurService.extractUtilisateurFromHttpServletRequest(request);
        utilisateur.setPassword("Hidden");
        utilisateur.setRoles(null);
        Users users = new Users(utilisateur, userMongoDbRepository.findUserMongoDbByUserId(utilisateur.getUtilisateurId()));
        List<HistoriqueMessage> historiqueMessages = historiqueMessageRepository.findAllContacts(utilisateur.getUtilisateurId());
        List<String> userIdList = extractUtilisateurId(historiqueMessages, utilisateur);
        List<UserMongoDb> userMongoDbs = userMongoDbRepository.findUserMongoDbsByUserIdIn(userIdList);
        List<Users> usersList = extractUserWithImage(historiqueMessages, utilisateur, userMongoDbs);
        return new Contact(users, usersList);
    }

    private List<String> extractUtilisateurId(List<HistoriqueMessage> historiqueMessages, Utilisateur moi) {
        List<String> userIdList = new ArrayList<>();
        for (HistoriqueMessage historiqueMessage: historiqueMessages) {
            if (moi.getUtilisateurId().equals(historiqueMessage.getUtilisateurIdHistoriqueMessage1()))
                userIdList.add(historiqueMessage.getUtilisateurIdHistoriqueMessage2());
            else
                userIdList.add(historiqueMessage.getUtilisateurIdHistoriqueMessage1());
        }
        return userIdList;
    }

    private List<Users> extractUserWithImage(List<HistoriqueMessage> historiqueMessages, Utilisateur moi, List<UserMongoDb> userMongoDbs ) {
        List<Users> usersList = new ArrayList<>();
        Optional<UserMongoDb> userMongoDbOptional;
        for (HistoriqueMessage historiqueMessage: historiqueMessages) {
            if (moi.getUtilisateurId().equals(historiqueMessage.getUtilisateurIdHistoriqueMessage1())) {
                historiqueMessage.getUtilisateur2().setPassword("Hidden");
                historiqueMessage.getUtilisateur2().setRoles(null);
                userMongoDbOptional = userMongoDbs.stream().filter(fm -> fm.getUserId().equals(historiqueMessage.getUtilisateurIdHistoriqueMessage2())).findFirst();
                userMongoDbOptional.ifPresent(userMongoDb -> usersList.add(new Users(historiqueMessage.getUtilisateur2(), userMongoDb)));
            } else {
                historiqueMessage.getUtilisateur1().setPassword("Hidden");
                historiqueMessage.getUtilisateur1().setRoles(null);
                userMongoDbOptional = userMongoDbs.stream().filter(fm -> fm.getUserId().equals(historiqueMessage.getUtilisateurIdHistoriqueMessage1())).findFirst();
                userMongoDbOptional.ifPresent(userMongoDb -> usersList.add(new Users(historiqueMessage.getUtilisateur1(), userMongoDb)));
            }
        }
        return usersList;
    }
}
