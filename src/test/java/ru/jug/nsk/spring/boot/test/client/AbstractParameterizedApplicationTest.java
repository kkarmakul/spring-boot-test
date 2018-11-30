package ru.jug.nsk.spring.boot.test.client;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

@RunWith(Parameterized.class)
public abstract class AbstractParameterizedApplicationTest extends BaseApplicationTest {
    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule(); // Requires this base class to be public
    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();
}
