package net.inventorymanagement.usermanagementwebservice.service;

import lombok.extern.slf4j.Slf4j;
import net.inventorymanagement.usermanagementwebservice.model.Configuration;
import net.inventorymanagement.usermanagementwebservice.model.User;
import net.inventorymanagement.usermanagementwebservice.repository.ConfigurationRepository;
import net.inventorymanagement.usermanagementwebservice.repository.UserManagementRepository;
import net.inventorymanagement.usermanagementwebservice.utils.FileReader;
import net.inventorymanagement.usermanagementwebservice.utils.Security;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * User management rest service, responsible for all the program logic.
 */

@Service
@Slf4j
public class UserManagementService {

    @Autowired
    private UserManagementRepository userManagementRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    private final Security security = new Security();

    // GET or create one user, if it doesn't exist already
    // user data is created from login-name and might need additional manipulation
    public User getOneData(String username, boolean rememberMe) {
        username = prepareUsername(username);
        String firstName = getFirstName(username);
        String lastName = getLastName(username);
        String mailAddress = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@kultur-burgenland.at";
        Integer userIndex = getUserIndex(firstName, lastName);
        User user;
        if (userIndex != -1) {
            user = userManagementRepository.findByUserId(userIndex);
            user.setLastLogin(LocalDateTime.now());
        } else {
            System.out.println("Created new user " + firstName + " " + lastName + ".");
            user = new User(firstName, lastName, mailAddress, -1, false,
                    false, false, LocalDateTime.now(), true);
        }
        user.setToken(null);
        if (rememberMe) {
            // save user to ensure that a userId is set
            user = userManagementRepository.save(user);
            Date rememberMeExpirationDate = new Date();
            rememberMeExpirationDate.setTime(rememberMeExpirationDate.getTime() + getConfiguration().getRememberMeCookieDaysUntilExpiration() * 24 * 60 * 60 * 1000);
            Security.TokenEntity tokenEntity = new Security.TokenEntity(user.getId(), rememberMeExpirationDate);
            if (StringUtils.isEmpty(user.getTokenSalt())) {
                user.setTokenSalt(UUID.randomUUID().toString());
            }
            String encryptedToken = security.encrypt(tokenEntity, user.getTokenSalt());
            if (encryptedToken != null) {
                user.setToken(encryptedToken);
            } else {
                log.error("Unable to encrypt Security.TokenEntity {} for User {} {}.", tokenEntity, user.getFirstName(), user.getLastName());
            }
        }
        return userManagementRepository.save(user);
    }

    // checks if user already exists inside database
    private Integer getUserIndex(String firstName, String lastName) {
        List<User> userList = userManagementRepository.findAll();
        for (int i = 0; i < userList.size(); i++) {
            for (User entry : userList) {
                if (Objects.equals(entry.getFirstName(), firstName) && Objects.equals(entry.getLastName(), lastName)) {
                    return entry.getId();
                }
            }
        }
        return -1;
    }

    // GET team
    // gets group id from user, checks if he/she is a teamleader and returns all members of his team if true
    public List<User> getTeamData(Integer id) {
        User teamLeader = userManagementRepository.findByUserId(id);
        List<User> team = new ArrayList<>();
        if (teamLeader.isTeamLeader()) {
            team = userManagementRepository.findByGroupId(teamLeader.getGroupId());
            team.sort(Comparator.naturalOrder());
        }
        return team;
    }

    // GET all
    // checks if user is super admin and returns all users if true
    public List<User> getAllData(Integer id) {
        User admin = userManagementRepository.findByUserId(id);
        if (admin.isAdmin() || admin.isSuperAdmin()) {
            List<User> allUsers = userManagementRepository.findAll();
            allUsers.sort(Comparator.naturalOrder());
            return allUsers;
        } else {
            return null;
        }
    }

    // additional manipulation of username for special usernames
    private String prepareUsername(String username) {
        username = username.replaceAll("\"", "").replaceAll(" ", "_");
        List<String> userExceptionsList = new ArrayList<>();
        FileReader fileReader = FileReader.getInstance();
        fileReader.loadFile(userExceptionsList);
        if (!userExceptionsList.isEmpty()) {
            for (String entry : userExceptionsList) {
                String[] userExceptionsEntry = entry.split(";");
                if (userExceptionsEntry[0].equalsIgnoreCase(username)) {
                    username = userExceptionsEntry[1];
                }
            }
        }
        username = replaceSpecialCharacters(username);
        return username;
    }

    // remove some special characters from the string
    private String replaceSpecialCharacters(String name) {
        name = name.toLowerCase()
                .replaceAll("ä", "ae").replaceAll("ö", "oe").replaceAll("ü", "ue")
                .replaceAll("á", "a").replaceAll("à", "a").replaceAll("â", "a")
                .replaceAll("é", "e").replaceAll("è", "e").replaceAll("ê", "e")
                .replaceAll("í", "i").replaceAll("ì", "i").replaceAll("î", "i")
                .replaceAll("ó", "o").replaceAll("ò", "o").replaceAll("ô", "o")
                .replaceAll("ú", "u").replaceAll("ù", "u").replaceAll("û", "u")
                .replaceAll("ß", "ss");
        return name;
    }

    // gets first part of the full name and formats it correctly
    private String getFirstName(String username) {
        String[] splitString = username.split("_");
        if (splitString[0].contains("-")) {
            String[] splitStringTwo = splitString[0].split("-");
            return splitStringTwo[0].substring(0, 1).toUpperCase() + splitStringTwo[0].substring(1).toLowerCase() + "-" +
                    splitStringTwo[1].substring(0, 1).toUpperCase() + splitStringTwo[1].substring(1).toLowerCase();
        } else {
            return splitString[0].substring(0, 1).toUpperCase() + splitString[0].substring(1).toLowerCase();
        }
    }

    // gets last part of the full name and formats it correctly
    private String getLastName(String username) {
        String[] splitString = username.split("_");
        if (splitString[splitString.length - 1].contains("-")) {
            String[] splitStringTwo = splitString[splitString.length - 1].split("-");
            return splitStringTwo[0].substring(0, 1).toUpperCase() + splitStringTwo[0].substring(1).toLowerCase() + "-" +
                    splitStringTwo[1].substring(0, 1).toUpperCase() + splitStringTwo[1].substring(1).toLowerCase();
        } else {
            return splitString[splitString.length - 1].substring(0, 1).toUpperCase() + splitString[splitString.length - 1].substring(1).toLowerCase();
        }
    }

    public User getUserByTokenIfNotExpired(String token) {
        User user = userManagementRepository.findByToken(token);
        if (user != null) {
            String dbToken = user.getToken();
            String dbTokenSalt = user.getTokenSalt();
            Security.TokenEntity tokenEntity = security.decrypt(dbToken, dbTokenSalt);
            if (tokenEntity != null && tokenEntity.getUserId().equals(user.getId()) && tokenEntity.getExpirationDate().after(new Date())) {
                return user;
            }
        }
        return null;
    }

    // ####################### Configuration #######################
    public Configuration getConfiguration() {
        Configuration activeConfigurationInDb = configurationRepository.findActiveConfiguration();
        if (activeConfigurationInDb == null) {
            // create default configuration
            return new Configuration(1, 1);
        }
        return activeConfigurationInDb;
    }

}