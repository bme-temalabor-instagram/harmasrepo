package instagram.repositories;

import instagram.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kalma on 16/11/29.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>
{


}
