import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws Exception {
        RSAKey rsaKey = new RSAKeyGenerator(2048)
                .keyUse(KeyUse.SIGNATURE)
                .keyID(UUID.randomUUID().toString())
                .generate();

        System.out.println(rsaKey.toPublicJWK());

        JWKSet jwkSet = new JWKSet(rsaKey);

        // JWKS exposing the public key
        System.out.println(jwkSet.toPublicJWKSet().toJSONObject());
    }
}