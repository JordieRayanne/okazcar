package com.okazcar.okazcar.token;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {
    private final String tokenId;
    public FirebaseAuthenticationToken(String tokenId) {
        super(null);
        this.tokenId = tokenId;
    }
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
