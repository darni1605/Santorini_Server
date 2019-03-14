package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.controller.HttpException;
import ch.uzh.ifi.seal.soprafs19.controller.UserException;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserService {

    //create logger and user repository //

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;


    @Autowired
    // create public user repository, method to get all users & to create a user //
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {

        // check, if username has already been registered //
        if(userRepository.findByUsername(newUser.getUsername()) != null){
            throw new HttpException("Username already registered!");
        }

       // assign random token and status //
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.OFFLINE);

       // add birthday according to users input, save the user & return him //
        newUser.setBirthday(newUser.getBirthday());
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }
    // check for specific user //
    public User checkUser(User newUser) {

        // assign username to variable: if username exists & password matches
        // user has been identified, his status becomes Online & he gets a token assigned
        User nameUser = userRepository.findByUsername(newUser.getUsername());
        if(nameUser != null && nameUser.getPassword().equals(newUser.getPassword())) {
            User isUser = userRepository.findByUsername(newUser.getUsername());
            isUser.setStatus(UserStatus.ONLINE);
            isUser.setToken(UUID.randomUUID().toString());
            userRepository.save(isUser);
            return isUser;
        }
        // throw exception if user was not found //
        throw new UserException("Name: "+newUser.getPassword()+" Username: "+newUser.getUsername());
    }

    // user logs out & status becomes Offline //
    public User logoutUser(User newUser) {
        User isUser = userRepository.findByToken(newUser.getToken());
        isUser.setStatus(UserStatus.OFFLINE);
        userRepository.save(isUser);
        return isUser;
    }

    // check for user by id, if not found throw exception //
    public User getUser(long id) {
        User isUser = userRepository.findById(id);
        if(isUser !=null) {
            return isUser;
        }
        else {
            throw new UserException("");
        }
    }

    // edit account information //
    public User editUser(User newUser) {

        // find user by token //
        User isUser = userRepository.findByToken(newUser.getToken());
       // if username been updated, change to new one, else throw exception //
        if(userRepository.findByUsername(newUser.getUsername()) != null && userRepository.findByUsername(newUser.getUsername())!=isUser) {
            throw new HttpException("Username already exists!.");
        }
        else {
            // set birthday & checked username and save new username //
            isUser.setBirthday(newUser.getBirthday());
            isUser.setUsername(newUser.getUsername());
            userRepository.save(isUser);
            return newUser;
        }
    }

}

