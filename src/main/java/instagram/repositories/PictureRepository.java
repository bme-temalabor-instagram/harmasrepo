package instagram.repositories;

import instagram.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, String>
{
    List<Picture> findByNameContains(String name);

}
