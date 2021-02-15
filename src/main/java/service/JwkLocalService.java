package service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.util.*;

public class JwkLocalService {

    private Long keyPairExpiration = 7200l /*2 hours*/ + 3000l /*50 minutes*/;

    private RSAKey kp;

    private String payload = "Hello Cryptography";

    public RSAKey getKeyPair() {
        return kp;
    }

    public RSAKey generateNewPair() throws Exception{
        return new RSAKeyGenerator(2048)
                .keyUse(KeyUse.SIGNATURE)
                .keyID(UUID.randomUUID().toString())
                .generate();

    }

    public void getSignedKey() throws Exception {
        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(kp.getKeyID()).build(),
                new JWTClaimsSet.Builder()
                        .subject("alice")
                        .issueTime(new Date())
                        .issuer("https://proishan11.github.io")
                        .build()
        );

        signedJWT.sign(new RSASSASigner(kp));

        JWEObject jweObject = new JWEObject(
                new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
                .contentType("JWT")
                .build(),
                new Payload(signedJWT)
        );

//        jweObject.encrypt(new RSAEncrypter(recipientPublicKey));
    }

}
