package com.zerohouse.tokenizer;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;

public interface Tokenize {
    JWTCreator.Builder build(JWTCreator.Builder builder);

    void fill(DecodedJWT jwt);
}
