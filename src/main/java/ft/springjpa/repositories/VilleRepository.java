package ft.springjpa.repositories;

import ft.springjpa.entities.Ville;
import org.springframework.data.repository.CrudRepository;

public interface VilleRepository extends CrudRepository<Ville, Integer> {
}
