package controller;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.JwkLocalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JWKSetController {

    private static final String JWK_SET_URI = "/.well-known/jwks.json";

    private JwkLocalService jwkLocalService;

    public JWKSetController(JwkLocalService jwkLocalService) {
        this.jwkLocalService = jwkLocalService;
    }

    @GetMapping(JWK_SET_URI)
    public ResponseEntity<Map<String, ?>> getKeys() {

        JWKSet jwks = new JWKSet(jwkLocalService.getKeyPair());
        Map<String, Object> response = new HashMap<>();
        response.put("keys", jwks.toPublicJWKSet().toJSONObject());

        return ResponseEntity.ok(response);
    }
}
