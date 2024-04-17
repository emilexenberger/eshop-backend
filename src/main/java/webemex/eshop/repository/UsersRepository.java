package webemex.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webemex.eshop.model.OurUsers;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<OurUsers, Integer> {
    Optional<OurUsers> findByUsername(String username);
}