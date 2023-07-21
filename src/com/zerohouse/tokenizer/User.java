package com.zerohouse.tokenizer;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;

public class User implements Tokenize {

    String asdf = "dsfsdf";

    @Override
    public JWTCreator.Builder build(JWTCreator.Builder builder) {
        builder.withClaim("asdf", asdf);
        return builder;
    }

    @Override
    public void fill(DecodedJWT jwt) {
        this.asdf = jwt.getClaim("asdf").asString();
    }
}
