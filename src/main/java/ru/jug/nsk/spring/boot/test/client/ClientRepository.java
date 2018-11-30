package ru.jug.nsk.spring.boot.test.client;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;
import ru.jug.nsk.spring.boot.test.client.entity.Client;
import ru.jug.nsk.spring.boot.test.client.entity.QClient;

import java.util.UUID;

@SuppressWarnings("InterfaceNeverImplemented")
@Repository
public interface ClientRepository extends
        JpaRepository<Client, UUID>,
        QueryDslPredicateExecutor<Client>,
        QuerydslBinderCustomizer<QClient>
{
    @Override
    default void customize(QuerydslBindings bindings, QClient qClient) {
        bindings.bind(qClient.login).first(StringExpression::containsIgnoreCase);
        bindings.bind(qClient.email).first(StringExpression::containsIgnoreCase);
        bindings.bind(qClient.familyName).first(StringExpression::startsWithIgnoreCase);
        bindings.bind(qClient.firstName).first(StringExpression::startsWithIgnoreCase);
        bindings.bind(qClient.deleted).first(BooleanExpression::eq);
        bindings.excluding(qClient.passwordHash, qClient.uuid);
    }
}
