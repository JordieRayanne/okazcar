package com.okazcar.okazcar.providers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.okazcar.okazcar.models.UserAuthentication;
import com.okazcar.okazcar.token.FirebaseAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


public class FirebaseIdTokenProvider implements AuthenticationProvider {
    public static final Logger logger = LoggerFactory.getLogger(FirebaseIdTokenProvider.class);
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FirebaseAuthenticationToken token = (FirebaseAuthenticationToken) authentication;
        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token.getTokenId(), true);
            String uid = firebaseToken.getUid();
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
            return new UserAuthentication(userRecord);
        } catch (FirebaseAuthException e) {
            if(e.getErrorCode().toString().equals("id-token-revoked")) {
                throw new SecurityException("User token has been revoked, please sign in again !");
            } else {
                throw new SecurityException("Authentication failed");
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(FirebaseAuthenticationToken.class);
    }
}
