package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @GetMapping(value = "/v1/test")
    public ResponseEntity<String> publicTest() throws JsonProcessingException {
        return ResponseEntity.ok().body("TEST OK");
    }
}
