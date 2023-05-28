package kpo.kdz2.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import kpo.kdz2.model.UserDTO;
import kpo.kdz2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private final UserService userService;

    public UserResource(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createUser(@RequestBody @Valid final UserDTO userDTO) {
        if (userService.usernameExists(userDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "user name exists");
        }
        if (userService.emailExists(userDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "email exists");
        }
        if (userService.emailCorrect(userDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorrect email");
        }
        final Integer createdId = userService.create(userDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final UserDTO userDTO) {
        if (userService.usernameExists(userDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "user name exists");
        }
        if (userService.emailExists(userDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "email exists");
        }
        if (userService.emailCorrect(userDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorrect email");
        }
        userService.update(id, userDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") final Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
