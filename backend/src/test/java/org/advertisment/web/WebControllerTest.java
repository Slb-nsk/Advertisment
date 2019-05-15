package org.advertisment.web;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

public class WebControllerTest {

    @BeforeAll
    static void initAll() {
    }

    @Test
    void suchBannerDoesNotExistTest() {
        WebController wc = mock(WebController.class);
    }

}
