package org.stelios.courses;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

public class ApplicationTest {

    @Test
    void testApplication() {
        try (MockedStatic<SpringApplication> utilities = mockStatic(SpringApplication.class)) {
            utilities.when((MockedStatic.Verification) SpringApplication.run(Application.class, new String[]{"Hello", "World"})).thenReturn(null);
            Application.main(new String[]{});
            assertThat(SpringApplication.run(Application.class, "Hello", "World")).isEqualTo(null);
        }
    }
}
