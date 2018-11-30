package ru.jug.nsk.spring.boot.test.client;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Condition;
import org.assertj.core.api.HamcrestCondition;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import ru.jug.nsk.spring.boot.test.client.dto.ClientDto;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static ru.jug.nsk.spring.boot.test.client.TestUtils.LIST_DTO_TYPE;

@RunWith(Parameterized.class)
@Slf4j
public class ClientSearchTests extends AbstractParameterizedApplicationTest {

    public interface TestArguments extends Consumer<ClientDto> {

        long getExpectedCount();

        String getQuery();
    }

    @Parameterized.Parameter
    public TestArguments args;

    @Parameterized.Parameters
    public static Iterable<TestArguments> init() {
        return ImmutableList.of(
                args("login=in111", 1, ClientDto::getLogin, containsString("in111")),
                args("login=login1", 3, ClientDto::getLogin, containsString("login1")),
                args("login=oGi", 4, ClientDto::getLogin, containsString("ogi")),

                args("email=host111.xz", 1, ClientDto::getEmail, containsString("host111.xz")),
                args("email=Host1", 3, ClientDto::getEmail, containsString("host1")),
                args("email=maIl@", 4, ClientDto::getEmail, containsString("mail@")),

                args("familyName=lient11", 0, dto -> dto.getFamilyName().toLowerCase(), startsWith("lient11")),
                args("familyName=client3", 1, dto -> dto.getFamilyName().toLowerCase(), equalTo("client3")),
                args("familyName=client1", 3, dto -> dto.getFamilyName().toLowerCase(), startsWith("client1")),
                args("familyName=client", 4, dto -> dto.getFamilyName().toLowerCase(), startsWith("client")),

                args("firstName=Search", 0, dto -> dto.getFirstName().toLowerCase(), startsWith("search")),
                args("firstName=forsearch3", 1, dto -> dto.getFirstName().toLowerCase(), equalTo("forsearch3")),
                args("firstName=forsearch1", 3, dto -> dto.getFirstName().toLowerCase(), startsWith("forsearch1")),
                args("firstName=forsearch", 4, dto -> dto.getFirstName().toLowerCase(), startsWith("forsearch")),

                args("deleted=false", 2, ClientDto::isDeleted, equalTo(Boolean.FALSE)),
                args("deleted=true", 2, ClientDto::isDeleted, equalTo(Boolean.TRUE)),

                ComplexTestArguments.builder().expectedCount(1).collection(ImmutableList.of(
                        field("login=in1", ClientDto::getLogin, containsString("in1")),
                        field("deleted=true", ClientDto::isDeleted, equalTo(Boolean.TRUE))
                )).build()
        );
    }

    @Test
    public void search() {
        List<ClientDto> actual = perform(null, HttpMethod.GET,
                "clients/search?" + args.getQuery(), LIST_DTO_TYPE, HttpStatus.OK);

        actual.forEach(args); // Проверяем, что запись удовлетворяет критерию поиска

        // Проверяем, что записи, маркированные "forSearch" нашлись в нужном количестве
        assertThat(actual.stream()
                .filter(c -> c.getFirstName().startsWith("forSearch"))
                .count())
                .isEqualTo(args.getExpectedCount());
    }

    private static <T> TestArguments args(String query, long count, Function<ClientDto, T> getter, Matcher<T> matcher) {
        return FieldTestArguments.<T>builder()
                .query(query)
                .expectedCount(count)
                .getter(getter)
                .condition(new HamcrestCondition<>(matcher)).build();
    }

    private static <T> TestArguments field(String query, Function<ClientDto, T> getter, Matcher<T> matcher) {
        return args(query, 0, getter, matcher);
    }

    @Value
    @Builder
    private static class FieldTestArguments<T> implements TestArguments {

        private final long expectedCount;
        private final Function<ClientDto, T> getter;
        private final Condition<T> condition;

        private final String query;

        @Override
        public void accept(ClientDto clientDto) {
            assertThat(getter.apply(clientDto)).is(condition);
        }
    }

    @Value
    @Builder
    private static class ComplexTestArguments implements TestArguments {

        private final Collection<TestArguments> collection;
        private final long expectedCount;

        @Override
        public String getQuery() {
            return collection.stream()
                    .map(TestArguments::getQuery)
                    .collect(Collectors.joining("&"));
        }

        @Override
        public void accept(ClientDto clientDto) {
            collection.forEach(args -> args.accept(clientDto));
        }
    }
}