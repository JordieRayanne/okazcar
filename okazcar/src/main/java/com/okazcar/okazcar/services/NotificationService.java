package com.okazcar.okazcar.services;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.okazcar.okazcar.exception.ForgetException;
import com.okazcar.okazcar.models.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NotificationService {

    final FirebaseMessaging firebaseMessaging;

    @Autowired
    public NotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public void sendNotification(Utilisateur utilisateurSender, Utilisateur utilisateurReceiver, com.okazcar.okazcar.models.mongodb.Message messageToSend) throws FirebaseMessagingException, ForgetException {
        Notification notification;
        if (messageToSend.getType().equals("FILE")) {
            notification = Notification
                    .builder()
                    .setTitle("Okazcar")
                    .setBody(utilisateurSender.getUsername() + " vous a envoyé un fichier")
                    .build();
        } else {
            notification = Notification
                    .builder()
                    .setTitle("Okazcar")
                    .setBody(utilisateurSender.getUsername() + " vous a envoyé un message:\""+ messageToSend.getContent()+"\"")
                    .build();
        }
        if (utilisateurReceiver.getFcmToken() == null)
            throw new ForgetException("L'utilisateur : " + utilisateurReceiver.getUsername() + " ne peut pas recevoir de notification pour l'instant", new Throwable("FCM token null"));
        Message message = Message
                .builder()
                .setToken(utilisateurReceiver.getFcmToken())
                .setNotification(notification)
                .build();
        firebaseMessaging.send(message);
    }
}
