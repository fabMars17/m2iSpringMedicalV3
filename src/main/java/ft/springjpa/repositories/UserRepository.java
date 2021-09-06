package ft.springjpa.repositories;

import ft.springjpa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

//JpaRepository
public interface UserRepository extends CrudRepository<User, Integer> {
    //public User findByUserName(String username);
    public User findByEmail(String username);
}
