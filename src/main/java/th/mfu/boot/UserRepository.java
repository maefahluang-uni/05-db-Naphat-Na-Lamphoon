package th.mfu.boot;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

public interface UserRepository extends CrudRepository<User, Long> {
    
    @NonNull
    List<User> findAll();

    User findByUsername(String username);

}
