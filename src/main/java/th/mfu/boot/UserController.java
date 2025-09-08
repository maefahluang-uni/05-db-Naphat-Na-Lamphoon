package th.mfu.boot;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users") // กำหนด base path ของทุก endpoint
public class UserController {

    @Autowired
    private UserRepository repo;

    // 1️⃣ Register a new user
    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (repo.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }
        repo.save(user);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }

    // 2️⃣ List all users
    @GetMapping
    public ResponseEntity<List<User>> list() {
        List<User> users = repo.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // 3️⃣ Get user by username
    @GetMapping("/by-username/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = repo.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // 4️⃣ Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        repo.deleteById(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
