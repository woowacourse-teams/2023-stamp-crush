package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.common.DataCleaner;
import com.stampcrush.backend.common.DataClearExtension;
import com.stampcrush.backend.common.KorNamingConverter;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@KorNamingConverter
@ExtendWith(DataClearExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DataCleaner cleaner;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        cleaner.clear();
    }
}
