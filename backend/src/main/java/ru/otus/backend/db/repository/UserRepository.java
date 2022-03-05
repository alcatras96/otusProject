package ru.otus.backend.db.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.backend.db.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = """
            select u.id       as user_id,
                   u.login    as user_login,
                   u.email    as user_email,
                   u.password as user_password,
                   r.id       as role_id,
                   r.name     as role_name
            from users u
                     inner join roles r
                                on u.role_id = r.id
            where u.login = :login
                                                    """,
            resultSetExtractorRef = "userByLoginExtractor")
    Optional<User> findByLogin(String login);
}
