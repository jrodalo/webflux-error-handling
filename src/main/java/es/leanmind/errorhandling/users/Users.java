package es.leanmind.errorhandling.users;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class Users {
    private final DatabaseClient databaseClient;

    public Users(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public Mono<User> findById(String userId) {
        return databaseClient
            .sql("SELECT id, name FROM users WHERE id = :userId")
            .bind("userId", userId)
            .map(this::toUser)
            .one();
    }

    private User toUser(Row row, RowMetadata rowMetadata) {
        return new User(
            row.get("id", String.class),
            row.get("name", String.class)
        );
    }
}
