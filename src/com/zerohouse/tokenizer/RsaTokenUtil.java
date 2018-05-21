package com.zerohouse.tokenizer;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.time.DateUtils;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RsaTokenUtil {

    String issuer;
    KeyFactory keyFactory;
    Algorithm algorithm;
    Integer expireDays;
    JWTVerifier verifier;

    public RsaTokenUtil(String pubKey, String privateKey, String issuer, Integer expireDays) {
        this.issuer = issuer;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            algorithm = Algorithm.RSA256(getPublicKey(pubKey), getPrivateKey(privateKey));
            this.expireDays = expireDays;
            verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            e.printStackTrace();
        }
    }

    public String getToken(Tokenize tokenize) {
        JWTCreator.Builder builder = tokenize.setProperties(JWT.create()
                .withExpiresAt(DateUtils.addDays(new Date(), expireDays))
                .withIssuer(issuer));
        return builder.sign(algorithm);
    }

    public DecodedJWT fromToken(String token) {
        return verifier.verify(token);
    }

    private RSAPublicKey getPublicKey(String keyString) throws InvalidKeySpecException {
        byte[] publicBytes = Base64.decodeBase64(keyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    private RSAPrivateKey getPrivateKey(String keyString) throws IOException, InvalidKeySpecException {
        byte[] privateBytes = Base64.decodeBase64(keyString);
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(new ASN1Integer(0));
        ASN1EncodableVector v2 = new ASN1EncodableVector();
        v2.add(new ASN1ObjectIdentifier(PKCSObjectIdentifiers.rsaEncryption.getId()));
        v2.add(DERNull.INSTANCE);
        v.add(new DERSequence(v2));
        v.add(new DEROctetString(privateBytes));
        ASN1Sequence seq = new DERSequence(v);
        byte[] privKey = seq.getEncoded("DER");
        PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privKey);
        return (RSAPrivateKey) keyFactory.generatePrivate(privSpec);
    }

}
