package com.smsmode.uaa.service.impl;

import com.smsmode.uaa.dao.service.TokenDaoService;
import com.smsmode.uaa.dao.service.UserDaoService;
import com.smsmode.uaa.dao.specification.TokenSpecification;
import com.smsmode.uaa.dao.specification.UserSpecification;
import com.smsmode.uaa.enumeration.TokenTypeEnum;
import com.smsmode.uaa.exception.AuthenticationUnauthorizedException;
import com.smsmode.uaa.exception.AuthorizationForbiddenException;
import com.smsmode.uaa.exception.ResourceNotFoundException;
import com.smsmode.uaa.exception.enumeration.AuthenticationUnauthorizedExceptionTitleEnum;
import com.smsmode.uaa.exception.enumeration.AuthorizationForbiddenExceptionTitleEnum;
import com.smsmode.uaa.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.uaa.model.TokenModel;
import com.smsmode.uaa.model.UserModel;
import com.smsmode.uaa.resource.auth.*;
import com.smsmode.uaa.service.AuthenticationService;
import com.smsmode.uaa.service.JwtTokenProviderService;
import com.smsmode.uaa.service.MailingService;
import com.smsmode.uaa.service.TokenService;
import com.smsmode.uaa.util.SecurityUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * Implementation of the AuthenticationService providing methods for user authentication and
 * password reset functionality. This class interacts with various services such as
 * AuthenticationManager, UserDaoService, JwtTokenProviderService, PasswordEncoder, and
 * MailingServiceImpl to accomplish its tasks.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 * <p>Created 09 Oct 2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDaoService credentialDaoService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProviderService jwtTokenProviderService;
    private final TokenService tokenService;
    private final MailingService mailingService;
    private final TokenDaoService tokenDaoService;
    private final PasswordEncoder passwordEncoder;

/*    @Value("${uaa.security.login-attempt-max}")
    private int badLoginAttemptMax;*/

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<TokenGetResource> authenticate(
            UserCredentialsPostResource userCredentialsPostResource) {
        Optional<UserModel> optionalLogin = Optional.empty();
        try {
            optionalLogin =
                    Optional.of(
                            credentialDaoService.findOneBy(
                                    UserSpecification.withEmail(userCredentialsPostResource.getUsername())));
            log.info("Found user with email: {}", userCredentialsPostResource.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userCredentialsPostResource.getUsername(),
                            userCredentialsPostResource.getPassword()));
        } catch (BadCredentialsException | ResourceNotFoundException exception) {
            log.warn(
                    "Bad credentials or users not found with username: {}",
                    userCredentialsPostResource.getUsername());
/*            if (exception instanceof BadCredentialsException && optionalLogin.isPresent()) {
                log.debug("Bad credentials will increment bad login attempt ...");
                optionalLogin.get().setBadLoginAttempt(optionalLogin.get().getBadLoginAttempt() + 1);
                if (optionalLogin.get().getBadLoginAttempt() >= badLoginAttemptMax) {
                    log.debug("Bad login attempts exceeded, will set the state of the account to Blocked");
                    optionalLogin.get().setState(CredentialStateEnum.BLOCKED);
                }
                log.debug("Saving the credential to database ...");
                credentialDaoService.save(optionalLogin.get());
            }*/
            throw new AuthenticationUnauthorizedException(
                    AuthenticationUnauthorizedExceptionTitleEnum.NOT_AUTHORIZED,
                    "Invalid credentials. Please try again.");
        }
        UserModel login = optionalLogin.get();
/*        if (login.getBadLoginAttempt() > 0) {
            // since the credentials are correct we will set the badLoginAttempts to 0
            login.setBadLoginAttempt(0);
            credentialDaoService.save(login);
        }*/
        final String jwtToken = jwtTokenProviderService.generateToken(login);
        TokenGetResource tokenGetResource = new TokenGetResource();
        tokenGetResource.setAccessToken(jwtToken);
        tokenGetResource.setExpiresIn(
                jwtTokenProviderService.getExpirationDateFromToken(jwtToken).toInstant().getEpochSecond());

        // Necessary for recent devices activity
/*        UserContextHolder.getContext().setUserId(login.getId());
        UserContextHolder.getContext()
                .setEmail(login.getEmail());*/
        return ResponseEntity.ok(tokenGetResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Void> forgotPassword(
            UserForgotPasswordPostResource userForgotPasswordPostResource) {
        try {
            UserModel user =
                    credentialDaoService.findOneBy(
                            UserSpecification.withEmail(userForgotPasswordPostResource.getEmail()));
            log.debug(
                    "Found user {} {} with corresponding email: {}",
                    user.getId(),
                    user.getFullName(),
                    userForgotPasswordPostResource.getEmail());
            log.debug("Will generate a reset key and send it by email ...");
            TokenModel passwordResetToken = tokenService.generatePasswordToken(user);
            mailingService.sendResetPasswordEmail(user, passwordResetToken);
        } catch (Exception ignored) {
            log.debug(
                    "No user found with email: {}. No response will be returned to the user",
                    userForgotPasswordPostResource.getEmail());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Void> resetPassword(
            UserResetPasswordPostResource userResetPasswordPostResource) {
        TokenModel tokenModel;
        UserModel loginModel;
        try {
            log.debug("retrieve user based on password reset key");
            String encryptedToken = userResetPasswordPostResource.getResetPasswordKey();
            tokenModel =
                    tokenDaoService.findOneBy(
                            TokenSpecification.withEncryptedToken(encryptedToken)
                                    .and(TokenSpecification.withType(TokenTypeEnum.PASSWORD_RESET)));
            loginModel = tokenModel.getLogin();
            log.debug(
                    "Found user {} {} with corresponding reset key",
                    loginModel.getId(),
                    loginModel.getFullName());
        } catch (Exception e) {
            throw new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.RESET_KEY_NOT_FOUND,
                    "The provided password reset key is invalid or was not found.");
        }
        // check resetKey expiration date
        if (!LocalDateTime.now().isBefore(tokenModel.getExpirationDate())) {
            log.debug(
                    "Reset password key has expired. Expiration date was for: {}. Will generate a new one and send it by email",
                    tokenModel.getExpirationDate());
            tokenDaoService.delete(tokenModel);
            tokenModel = tokenService.generatePasswordToken(loginModel);

            // send email with new key
            mailingService.sendResetPasswordEmail(loginModel, tokenModel);
            throw new AuthorizationForbiddenException(
                    AuthorizationForbiddenExceptionTitleEnum.RESET_KEY_EXPIRED,
                    "The password reset key has expired. A new reset password link will be sent to your registered email address.");
        }
        tokenDaoService.delete(tokenModel);
        loginModel.setPassword(
                passwordEncoder.encode(userResetPasswordPostResource.getPassword()));
        loginModel = credentialDaoService.save(loginModel);
        mailingService.sendResetPasswordConfirmationEmail(loginModel);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Void> validateAccount(
            UserValidateAccountPostResource userValidateAccountPostResource) {

        UserModel loginModel;
        TokenModel tokenModel;
        try {
            log.debug("retrieve user based on account validation reset key");
            String encryptedToken = userValidateAccountPostResource.getActivationKey();
            tokenModel =
                    tokenDaoService.findOneBy(
                            TokenSpecification.withEncryptedToken(encryptedToken)
                                    .and(TokenSpecification.withType(TokenTypeEnum.ACCOUNT_VALIDATION)));
            loginModel = tokenModel.getLogin();
            log.debug(
                    "Found user {} {} with corresponding account validation key",
                    loginModel.getId(),
                    loginModel.getFullName());
        } catch (Exception e) {
            throw new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.RESET_KEY_NOT_FOUND,
                    "The provided account validation key is invalid or was not found.");
        }
        // check resetKey expiration date
        if (!LocalDateTime.now().isBefore(tokenModel.getExpirationDate())) {
            log.debug(
                    "Account validation key has expired. Expiration date was for: {}. Will generate a new one and send it by email",
                    tokenModel.getExpirationDate());
            tokenDaoService.delete(tokenModel);
            tokenModel = tokenService.generateAccountValidationToken(loginModel);
            mailingService.sendActivationAccountEmail(loginModel, tokenModel);
            throw new AuthorizationForbiddenException(
                    AuthorizationForbiddenExceptionTitleEnum.RESET_KEY_EXPIRED,
                    "The account validation key has expired. A new account validation link will be sent to your registered email address.");
        }
        tokenDaoService.delete(tokenModel);
        loginModel.setActivated(true);
        loginModel.setPassword(
                passwordEncoder.encode(userValidateAccountPostResource.getPassword()));
        loginModel = credentialDaoService.save(loginModel);
        mailingService.sendActivationAccountConfirmationEmail(loginModel);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Void> validateToken(
            TokenValidationPostResource tokenValidationPostResource) {
        if (!jwtTokenProviderService.validateToken(tokenValidationPostResource.getToken())) {
            throw new AuthorizationForbiddenException(
                    AuthorizationForbiddenExceptionTitleEnum.TOKEN_NOT_VALID, "Token used is not valid");
        }
        // necessary for recent devices history
        Claims claims =
                jwtTokenProviderService.getClaimsFromToken(tokenValidationPostResource.getToken());
/*        UserContextHolder.getContext().setUserId(claims.get(IDENTIFIER_KEY).toString());
        UserContextHolder.getContext().setEmail(claims.get(USERNAME_KEY).toString());*/
        return ResponseEntity.noContent().build();
    }

    /*
     {@inheritDoc}
    */
    @Override
    public ResponseEntity<TokenGetResource> refreshToken(
            RefreshTokenPostResource refreshTokenPostResource) {
        TokenValidationPostResource tokenValidationPostResource = new TokenValidationPostResource();
        tokenValidationPostResource.setToken(refreshTokenPostResource.getRefreshToken());
        this.validateToken(tokenValidationPostResource);
        String userId =
                jwtTokenProviderService.getClaimFromTokenEvenIfExpired(
                        tokenValidationPostResource.getToken(), SecurityUtil.IDENTIFIER_KEY);

        UserModel user = credentialDaoService.findOneBy(UserSpecification.withUuid(userId));
        if (!user.isEnabled()) {
            throw new AuthorizationForbiddenException(
                    AuthorizationForbiddenExceptionTitleEnum.USER_DISABLED,
                    "Your account is disabled. Please contact support.");
        }
/*        if (user.getState().equals(CredentialStateEnum.BLOCKED)) {
            throw new TooManyRequestException(
                    TooManyRequestExceptionTitleEnum.MAX_LOGIN_ATTEMPT,
                    "Too many failed login attempts. Please wait before trying again.");
        }*/
        if (!user.isActivated()) {
            throw new AuthorizationForbiddenException(
                    AuthorizationForbiddenExceptionTitleEnum.USER_NOT_ACTIVATED,
                    "Your account is not yet validated. Please check your email for validation instructions.");
        }

        final String jwtToken = jwtTokenProviderService.generateToken(user);
        // necessary for recent devices history
/*        Claims claims = jwtTokenProviderService.getClaimsFromToken(jwtToken);
        UserContextHolder.getContext().setUserId(claims.get(IDENTIFIER_KEY).toString());
        UserContextHolder.getContext().setEmail(claims.get(USERNAME_KEY).toString());*/
        TokenGetResource tokenGetResource = new TokenGetResource();
        tokenGetResource.setAccessToken(jwtToken);
        tokenGetResource.setExpiresIn(
                jwtTokenProviderService.getExpirationDateFromToken(jwtToken).toInstant().getEpochSecond());
        return ResponseEntity.ok(tokenGetResource);
    }

}

