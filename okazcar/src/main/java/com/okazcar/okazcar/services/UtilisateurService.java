package com.okazcar.okazcar.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.okazcar.okazcar.details.UtilisateurDetails;
import com.okazcar.okazcar.exception.ForgetException;
import com.okazcar.okazcar.models.Users;
import com.okazcar.okazcar.models.dto.UserLoginDto;
import com.okazcar.okazcar.models.mongodb.UserMongoDb;
import com.okazcar.okazcar.repositories.UtilisateurRepository;
import com.okazcar.okazcar.repositories.mongodb.UserMongoDbRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.okazcar.okazcar.models.*;
import com.okazcar.okazcar.models.dto.*;

import static com.okazcar.okazcar.handlers.ResponseHandler.mapRolesToAuthorities;


@Service
public class UtilisateurService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(UtilisateurService.class);

    @Value("${key-hash}")
    private String KEY;

    @Value("${minutes-expired}")
    private int MINUTES_EXPIRATION;

    private final UtilisateurRepository utilisateurRepository;
    private final UserMongoDbRepository userMongoDbRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository, UserMongoDbRepository userMongoDbRepository) {
        this.userMongoDbRepository = userMongoDbRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public String delete(String uid) throws FirebaseAuthException {
        userMongoDbRepository.deleteById(uid);
        utilisateurRepository.deleteById(uid);
        FirebaseAuth.getInstance().deleteUser(uid);
        return uid;
    }

    public Users update(UserInsertDto userInsertDto) throws ForgetException, FirebaseAuthException, IOException {
        if (userInsertDto.getImage() == null && userInsertDto.getImageFile() == null && userInsertDto.getUserId() == null) {
            return updateUserWithoutImage(userInsertDto);
        }
        return updateUserWithImage(userInsertDto);
    }

    private Users updateUserWithImage(UserInsertDto userInsertDto) throws ForgetException, FirebaseAuthException, IOException {
        Utilisateur user = updatetUser(userInsertDto);
        userInsertDto.setImage(user.getImageUrl());
        UserMongoDb userMongoDb = new UserMongoDb(userInsertDto, user.getUtilisateurId());
        userMongoDbRepository.save(userMongoDb);
        return new Users(user, userMongoDb);
    }

    private Users updateUserWithoutImage(UserInsertDto userInsertDto) throws ForgetException, FirebaseAuthException {
        return new Users(updatetUser(userInsertDto), null);
    }

    private Utilisateur updatetUser(UserInsertDto userInsertDto) throws ForgetException, FirebaseAuthException {
        Utilisateur utilisateur;
        userInsertDto.setPassword(bCryptPasswordEncoder.encode(userInsertDto.getPassword()));
        UserRecord.UpdateRequest request = updateRequest(userInsertDto);
        utilisateur = new Utilisateur(userInsertDto);
        utilisateur.setUtilisateurId(userInsertDto.getUserId());
        FirebaseAuth.getInstance().updateUser(request);
        return utilisateurRepository.save(utilisateur);
    }

    public Users logIn(UserLoginDto userDto) throws ForgetException {
        Utilisateur utilisateur;
        utilisateur = utilisateurRepository.findUtilisateurByEmail(userDto.getEmail());
        if (utilisateur == null)
            throw new ForgetException("Error! Email or password are incorrect", new Throwable("Authentication failed"));
        if (!isPasswordValid(userDto, utilisateur))
            throw new ForgetException("Error! Email or password are incorrect", new Throwable("Authentication failed"));
        if (userDto.getFcmToken() != null) {
            utilisateur.setFcmToken(userDto.getFcmToken());
            utilisateur = utilisateurRepository.save(utilisateur);
        }
        return new Users(utilisateur, userMongoDbRepository.findUserMongoDbByUserId(utilisateur.getUtilisateurId()));
    }
    private boolean isPasswordValid(UserLoginDto userLoginDto, Utilisateur utilisateur) {
        return bCryptPasswordEncoder.matches(userLoginDto.getPassword(), utilisateur.getPassword());
    }


    public Users insert(UserInsertDto userDto) throws FirebaseAuthException, IOException, ForgetException {
        if (userDto.getImage() == null && userDto.getImageFile() == null && userDto.getUserId() == null) {
            return insertUserWithoutImage(userDto);
        }
        return insertUserWithImage(userDto);
    }

    public Utilisateur extractUtilisateurFromHttpServletRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.replace("Bearer ", "");
        String email = extractUsername(token);
        return utilisateurRepository.findUtilisateurByEmail(email);
    }

    private Utilisateur insertUser(UserInsertDto userDto) throws FirebaseAuthException, ForgetException {
        UserRecord userRecord;
        Utilisateur utilisateur;
        if (userDto.getPlatform().equals("Email/Phone")) {
            userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            UserRecord.CreateRequest request = createRequest(userDto);
            utilisateur = new Utilisateur(userDto);
            userRecord = FirebaseAuth.getInstance().createUser(request);
        } else {
            userRecord = FirebaseAuth.getInstance().getUser(userDto.getUserId());
            utilisateur = new Utilisateur(userRecord, userDto);
        }
        utilisateur.setUtilisateurId(userRecord.getUid());
        try {
            return utilisateurRepository.save(utilisateur);
        } catch (Exception e) {
            FirebaseAuth.getInstance().deleteUser(userRecord.getUid());
            throw e;
        }
    }

    private UserRecord.CreateRequest createRequest(UserInsertDto userInsertDto) {
        return new UserRecord.CreateRequest()
                .setEmail(userInsertDto.getEmail())
                .setEmailVerified(false)
                .setDisplayName(userInsertDto.getUsername())
                .setPassword(userInsertDto.getPassword())
                .setDisabled(false)
                .setPhoneNumber(userInsertDto.getPhoneNumber());
    }

    private UserRecord.UpdateRequest updateRequest(UserInsertDto userInsertDto) {
        return new UserRecord.UpdateRequest(userInsertDto.getUserId())
                .setEmail(userInsertDto.getEmail())
                .setEmailVerified(false)
                .setDisplayName(userInsertDto.getUsername())
                .setPassword(userInsertDto.getPassword())
                .setDisabled(false)
                .setPhoneNumber(userInsertDto.getPhoneNumber());
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
    // Generate token
    public String generateToken(String email)  {
        return createToken(loadUserByUsername(email));
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(KEY.getBytes()).parseClaimsJws(token).getBody();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String createToken(UserDetails userDetails) {
        String role = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet())
                .iterator()
                .next();
        return Jwts
                .builder()
                .claim("role", role)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(MINUTES_EXPIRATION)))
                .signWith(SignatureAlgorithm.HS256, KEY.getBytes())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur user = utilisateurRepository.findUtilisateurByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email : " + email + " not found");
        }
        return new UtilisateurDetails(user, mapRolesToAuthorities(user.getRoles()));
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final UserDetails userDetails) {
        Claims claims = extractAllClaims(token);

        final Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get("role").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

}
