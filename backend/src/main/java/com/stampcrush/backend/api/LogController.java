package com.stampcrush.backend.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogController {

    @GetMapping("test")
    public ResponseEntity<Void> logTest() {
        log.debug("debug log");
        log.info("info log");
        log.warn("warn log");
        log.error("error log");

        return ResponseEntity.ok().build();
    }
}
