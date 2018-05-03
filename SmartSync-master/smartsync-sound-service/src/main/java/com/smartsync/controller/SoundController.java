package com.smartsync.controller;

import com.smartsync.dto.SoundFileDTO;
import com.smartsync.error.*;
import com.smartsync.model.SoundFile;
import com.smartsync.service.SoundService;
import com.smartsync.validator.SoundValidator;
import com.smartsync.validator.ValidationError;
import com.smartsync.validator.ValidationErrorBuilder;
import communication.UserServiceCommunication;
import model.UserPOJO;
import org.apache.coyote.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.Socket;
import java.net.InetAddress;
import java.io.PrintWriter;

import java.util.List;

import static com.smartsync.util.HttpUtil.executeGetRequest;



/**
 *
 * The sound controller
 */
@RestController
public class SoundController {

    private final String RASPBERRY_PI_URL = "169.254.130.182";
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private SoundService soundService;


    @RequestMapping(method = RequestMethod.GET, value = "/sound", produces = "application/json")
    public ResponseEntity getAllSoundFiles() {
        List<SoundFile> soundFileList = this.soundService.getAllSoundFile();

        return ResponseEntity.ok(soundFileList);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/", produces = "application/json")
    public ResponseEntity addSoundFile(@RequestBody SoundFileDTO soundFileDTO, Errors errors) {

        SoundValidator validator = new SoundValidator();
        validator.validate(soundFileDTO, errors);

        if(errors.hasErrors()) {
            String message = "Could not add new sound file " + soundFileDTO;
            String url = "/sound/";

            ValidationError validationError = ValidationErrorBuilder.fromBindErrors(errors);

            logger.error(message + "\n" + "Errors: " + validationError);
            throw new IllegalRequestFormatException(message, url, validationError);
        }

        // make sure the use exists
        UserServiceCommunication userServiceCommunication = new UserServiceCommunication();
        UserPOJO user = userServiceCommunication.getUser(soundFileDTO.getUserId());
        if(user == null) {
            String message = "Could not find user with id " + soundFileDTO.getUserId();
            String url = "sound/";

            logger.error(message);
            throw new UserNotFoundException(message, url);
        }


        SoundFile soundFile = this.soundService.addSoundFile(soundFileDTO);
        logger.info("Successfully added new sound file " + soundFile);
        return ResponseEntity.ok(soundFile);
    }

    @RequestMapping(method = RequestMethod.OPTIONS, value = "/play/{id}", produces = "application/json")
    public ResponseEntity playSoundFileOptions(@PathVariable("id") Long id) {
        System.out.println("OPTIONS");
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/play/{id}", produces = "application/json")
    public ResponseEntity playSoundFilePost(@PathVariable("id") Long id) {
        SoundFile soundFile = this.soundService.getSoundfileById(id);

        if(soundFile == null) {
            String message = "Could not find sound file with id " + id;
            String url = "/" + id;

            logger.error(message);
            throw new SoundFileNotFoundException(message, url);
        }

        // send to Pi
        try {
        Socket socket = new Socket(InetAddress.getByName(RASPBERRY_PI_URL), 5000);
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        pw.print(soundFile.getFilepath());
        pw.close();
        socket.close();
         }
        catch (Exception e) {}
        //executeGetRequest(RASPBERRY_PI_URL + "play" + soundFile.getFilepath());

        return ResponseEntity.ok(soundFile);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/play/{id}", produces = "application/json")
    public ResponseEntity playSoundFileGet(@PathVariable("id") Long id) {
        SoundFile soundFile = this.soundService.getSoundfileById(id);

        if(soundFile == null) {
            String message = "Could not find sound file with id " + id;
            String url = "/" + id;

            logger.error(message);
            throw new SoundFileNotFoundException(message, url);
        }

        // send to Pi
        try {
            Socket socket = new Socket(InetAddress.getByName(RASPBERRY_PI_URL), 5000);
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.print(soundFile.getFilepath());
            pw.close();
            socket.close();
        }
        catch (Exception e) {}
        //executeGetRequest(RASPBERRY_PI_URL + "play" + soundFile.getFilepath());

        return ResponseEntity.ok(soundFile);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/sound", produces = "application/json")
    public ResponseEntity getSoundFileById(@PathVariable("id") Long id) {
        SoundFile soundFile = this.soundService.getSoundfileById(id);

        if(soundFile == null) {
            String message = "Could not find sound file with id " + id;
            String url = "/" + id;

            logger.error(message);
            throw new SoundFileNotFoundException(message, url);
        }

        return ResponseEntity.ok(soundFile);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}", produces = "application/json")
    public ResponseEntity deleteSoundFileById(@PathVariable("id") Long id) {
        SoundFile soundFile = this.soundService.deleteSoundFileById(id);

        if(soundFile == null) {
            String message = "Could not delete sound file with id " + id + " because it could not be found";
            String url = "/" + id;

            logger.error(message);
            throw new SoundFileNotFoundException(message, url);
        }

        return ResponseEntity.ok(soundFile);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/sound", produces = "application/json")
    public ResponseEntity getSoundFilesForUser(@PathVariable("userId") Long userId) {

        UserServiceCommunication userServiceCommunication = new UserServiceCommunication();
        UserPOJO user = userServiceCommunication.getUser(userId);

        if(user == null) {
            String message = "Could not find user with id " + userId;
            String url = "sound/";

            logger.error(message);
            throw new UserNotFoundException(message, url);
        }


        List<SoundFile> soundFileList = this.soundService.getSoundFilesForUser(userId);

        logger.info("Successfully got sound files for user: " + soundFileList);
        return ResponseEntity.ok(soundFileList);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "users/{userId}", produces = "application/json")
    public ResponseEntity deleteAllWeatherForUser(@PathVariable("userId") Long userId) {

        UserServiceCommunication userServiceCommunication = new UserServiceCommunication();
        UserPOJO user = userServiceCommunication.getUser(userId);

        if(user == null) {
            String message = "Could not find user with id " + userId;
            String url = "sound/";

            logger.error(message);
            throw new UserNotFoundException(message, url);
        }

        List<SoundFile> soundFileList = this.soundService.deleteSoundFilesForUser(userId);

        logger.info("Successfully deleted sound files for user: " + soundFileList);
        return ResponseEntity.ok(soundFileList);
    }

    @ExceptionHandler(value = SoundFileNotFoundException.class)
    public ResponseEntity handleSoundFileNotFoundException(SoundFileNotFoundException e) {
        ErrorInfo error = new ErrorInfo("Sound File Not Found", e.getMessage(), e.getUrl());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles the user not found exception
     * @param e the user not found exception
     * @return the response entity with the error
     */
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(UserNotFoundException e) {
        ErrorInfo error = new ErrorInfo("User Not Found", e.getMessage(), e.getUrl());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles when there is an illegal request format exception. This includes missing parameters, improper input,
     * and other bad requests.
     * @param e the illegal request format exception
     * @return the response entity with the error
     */
    @ExceptionHandler(value = IllegalRequestFormatException.class)
    public ResponseEntity handleIllegalRequestFormatException(IllegalRequestFormatException e) {
        IllegalRequestFormatErrorInfo error = new IllegalRequestFormatErrorInfo("Illegal Request Format",
                e.getMessage(), e.getUrl(), e.getErrors());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
