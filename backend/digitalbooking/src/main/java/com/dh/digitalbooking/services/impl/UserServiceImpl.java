package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.data.UserDetailsData;
import com.dh.digitalbooking.entities.ClientEntity;
import com.dh.digitalbooking.entities.ConfirmationToken;
import com.dh.digitalbooking.entities.UserEntity;
import com.dh.digitalbooking.exceptions.UserNotFoundException;
import com.dh.digitalbooking.repository.UserRepository;
import com.dh.digitalbooking.services.IUserService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService<UserEntity>, UserDetailsService {

    private final UserRepository userRepository;
    private final ConfirmationTokenServiceImpl confirmationTokenService;
    private final EmailServiceImpl emailService;
    private final PasswordEncoder encoder;
    private final Logger log = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ConfirmationTokenServiceImpl confirmationTokenService,
                           EmailServiceImpl emailService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
        this.encoder = encoder;
    }


    @Override
    public String save(UserEntity user) {

        String token = registerUser(user);


        //String link = "http://localhost:8080/api/v1/user/confirm?token=" + token;
        String link = "http://18.207.203.35:8080/api/v1/user/confirm?token=" + token;
        emailService.send(
                user.getEmail(),
                buildEmail(user.getFirstName(), link));

        log.info("Saving user.");

        return token;
    }

    @Override
    public UserEntity update(UserEntity user) throws UserNotFoundException {
        user.setPassword(encoder.encode(user.getPassword()));
        UserEntity userFound = findByEmail(user.getEmail());
        userFound.setFirstName(user.getFirstName());
        userFound.setLastName(user.getLastName());
        userFound.setPassword(user.getPassword());
        log.info("Updating user with id = "+user.getId()+".");
        return userRepository.save(userFound);
    }

    @Override
    public void deleteById(Integer id) throws UserNotFoundException {
        verifyIfExists(id);
        log.info("Deleting user with id = "+id+".");
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity findById(Integer id) throws UserNotFoundException {
        UserEntity user = verifyIfExists(id);
        log.info("Finding user with id = "+id+".");
        return user;
    }

    @Override
    public UserEntity findByEmail(String email) throws UserNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        log.info("Finding user with email = "+email+".");
        return user;
    }

    @Override
    public ClientEntity findClientById(Integer id) throws UserNotFoundException {
        ClientEntity client = userRepository.findClientById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        log.info("Finding client with id = "+id+".");
        return client;
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        log.info("Listing all users.");
        return userRepository.findAll(pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(username);
        if(user.isEmpty()){
            log.error("User not found ["+username+"].");
            throw new UsernameNotFoundException("User ["+username+"] not found.");
        } else {
            log.info("Loading user ["+username+"].");
            return new UserDetailsData(user);
        }
    }


    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found."));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        confirmationTokenService.setConfirmedAt(token);

        userRepository.enableUser(confirmationToken.getUser().getEmail());

        return "Confirmed!";
    }

    private String registerUser(UserEntity user) {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if(userExists){
            throw new IllegalStateException("Email already taken.");
        }
        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

        return token;
    }

    private UserEntity verifyIfExists(Integer id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirme seu email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Olá " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Obrigado por se registrar. Clique no link abaixo para ativar sua conta: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Ative agora</a> </p></blockquote>\n O link expira em 15 minutos. <p>Até logo!</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
