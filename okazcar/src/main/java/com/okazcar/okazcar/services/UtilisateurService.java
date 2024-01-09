package com.okazcar.okazcar.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.okazcar.okazcar.exception.ForgetException;
import com.okazcar.okazcar.models.Users;
import com.okazcar.okazcar.models.dto.UserLoginDto;
import com.okazcar.okazcar.models.mongodb.UserMongoDb;
import com.okazcar.okazcar.repositories.UtilisateurRepository;
import com.okazcar.okazcar.repositories.mongodb.UserMongoDbRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import com.okazcar.okazcar.models.*;
import com.okazcar.okazcar.models.dto.*;


@Service
public class UtilisateurService {
    Logger logger = LoggerFactory.getLogger(UtilisateurService.class);

    private final UtilisateurRepository utilisateurRepository;
    private final UserMongoDbRepository userMongoDbRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UtilisateurService(PasswordEncoder passwordEncoder, UtilisateurRepository utilisateurRepository, UserMongoDbRepository userMongoDbRepository) {
        this.userMongoDbRepository = userMongoDbRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users logIn(UserLoginDto userDto) throws FirebaseAuthException, ForgetException {
        Utilisateur utilisateur;
        if (userDto.getUserId() == null) {
            utilisateur = utilisateurRepository.findUtilisateurByEmail(userDto.getEmail());
            if (utilisateur == null)
                throw new ForgetException("Error! Email or password are incorrect", new Throwable("Authentication failed"));
            if (!isPasswordValid(userDto, utilisateur))
                throw new ForgetException("Error! Email or password are incorrect", new Throwable("Authentication failed"));
            return new Users(utilisateur, userMongoDbRepository.findUserMongoDbByUserId(utilisateur.getUtilisateurId()));
        }
        utilisateur = utilisateurRepository.findUtilisateurByUtilisateurId(userDto.getUserId());
        return new Users(utilisateur, userMongoDbRepository.findUserMongoDbByUserId(utilisateur.getUtilisateurId()));
    }
    private boolean isPasswordValid(UserLoginDto userLoginDto, Utilisateur utilisateur) {
        return passwordEncoder.matches(userLoginDto.getPassword(), utilisateur.getPassword());
    }


    public Users insert(UserInsertDto userDto) throws FirebaseAuthException, IOException, ForgetException {
        if (userDto.getImage() == null && userDto.getImageFile() == null && userDto.getUserId() == null) {
            return insertUserWithoutImage(userDto);
        }
        return insertUserWithImage(userDto);
    }

    private Utilisateur insertUser(UserInsertDto userDto) throws FirebaseAuthException, ForgetException {
        UserRecord userRecord;
        Utilisateur utilisateur;
        if (userDto.getPlatform().equals("Email/Phone")) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(userDto.getEmail())
                    .setEmailVerified(false)
                    .setDisplayName(userDto.getUsername())
                    .setPassword(userDto.getPassword())
                    .setDisabled(false)
                    .setPhoneNumber(userDto.getPhoneNumber());
            utilisateur = new Utilisateur(userDto);
            userRecord = FirebaseAuth.getInstance().createUser(request);
        } else {
            userRecord = FirebaseAuth.getInstance().getUser(userDto.getUserId());
            utilisateur = new Utilisateur(userRecord, userDto);
        }
        utilisateur.setUtilisateurId(userRecord.getUid());
        return utilisateurRepository.save(utilisateur);
    }

    private Users insertUserWithoutImage(UserInsertDto userDto) throws FirebaseAuthException, ForgetException {
        return new Users(insertUser(userDto), null);
    }

    private Users insertUserWithImage(UserInsertDto userDto) throws FirebaseAuthException, IOException, ForgetException {
        Utilisateur user = insertUser(userDto);
        userDto.setImage(user.getImageUrl());
        UserMongoDb userMongoDb = new UserMongoDb(userDto, user.getUtilisateurId());
        userMongoDbRepository.save(userMongoDb);
        return new Users(user, userMongoDb);
    }

    public String generateToken(String uid) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().createCustomToken(uid);
    }

    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }
}
