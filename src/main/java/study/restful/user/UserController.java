package study.restful.user;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDaoService service;

    @GetMapping("/users")
    public List<Users> retrieveAllUsers() {
        return service.findAll();
    }

    // GET /users/1 or /users/10 -> String -> int 로 컨버팅
    @GetMapping("/users/{id}")
    public EntityModel<Users> retrieveUser(@PathVariable int id) {
        Users user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // HATEOAS
        EntityModel<Users> model = EntityModel.of(user);

        //링크 생성
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        //링크 추가
        model.add(linkTo.withRel("all-users"));

        return model;
    }

    @PostMapping("/users")
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users user) {
        Users savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        Users user = service.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }
}
