package study.restful.user;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDaoService {

    private static final List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "John", LocalDateTime.now()));
        users.add(new User(2, "Alice", LocalDateTime.now()));
        users.add(new User(3, "Elena", LocalDateTime.now()));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }

        users.add(user);

        return user;
    }

    public User findOne(int id) {
        Optional<User> findUser = users.stream()
                .filter(user -> user.getId() == id)
                .findAny();

        return findUser.orElse(null);
    }

    public User deleteById(int id) {

        Iterator<User> userIterator = users.iterator();

        while (userIterator.hasNext()) {
            User user = userIterator.next();

            if(user.getId() == id){
                userIterator.remove();
                return user;
            }
        }

        return null;
    }


}
