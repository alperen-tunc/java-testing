package gmbh.conteco.seminarverwaltung.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SeminarTest {
    @Test
    // given_when_then
    void shouldSetAndGetTitleCorrectly(){
        Seminar seminar = new Seminar();
        seminar.setTitel("JUnit Einführung");
        assertThat(seminar.getTitel()).isEqualTo("JUnit Einführung");
    }
}