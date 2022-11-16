package study.restful.user;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UserDaoService {

    private static final List<Users> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new Users(1, "John", LocalDateTime.now(), "pass1", "701010-1111111"));
        users.add(new Users(2, "Alice", LocalDateTime.now(), "pass2", "801010-1111111"));
        users.add(new Users(3, "Elena", LocalDateTime.now(), "pass2", "901010-1111111"));
    }

    public List<Users> findAll() {
        return users;
    }

    public Users save(Users user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }

        users.add(user);

        return user;
    }

    public Users findOne(int id) {
        Optional<Users> findUser = users.stream()
                .filter(user -> user.getId() == id)
                .findAny();

        return findUser.orElse(null);
    }

    public Users deleteById(int id) {

        Iterator<Users> userIterator = users.iterator();

        while (userIterator.hasNext()) {
            Users user = userIterator.next();

            if(user.getId() == id){
                userIterator.remove();
                return user;
            }
        }

        return null;
    }


}
