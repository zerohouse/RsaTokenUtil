package com.zerohouse.tokenizer;

import org.junit.Test;


public class RsaTokenUtilTest {

    @Test
    public void test() {
        RsaTokenUtil util = new RsaTokenUtil(
                "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJqmvwnJK88csg5wu1P36fp1gLOr27YI\n" + "lHz8AlKWvBu4UsT/PRv0TA05qoFlRLaq0DxLx6dvKB6k2ldDqmHIB2sCAwEAAQ==",
                "MIIBOwIBAAJBAJqmvwnJK88csg5wu1P36fp1gLOr27YIlHz8AlKWvBu4UsT/PRv0\n" + "TA05qoFlRLaq0DxLx6dvKB6k2ldDqmHIB2sCAwEAAQJAaUQw6vdM45gzttKTIA+y\n" + "6vGKXkIPtnNWp1BeQFsrxW12/5kvS5xp/OawEVTCfl8hfMPQVG/OzhflEpCH/5m0\n" + "GQIhAPioIAVJmI9ixgYDcUSqEWeSC5fJSpGeN2aaVWUrVy8fAiEAnzft2jw9cExm\n" + "s6u/1gAo6Dt8P4tpixluHemOu+9p+jUCIQCmCwbO4RVJ8mhPja7ubi6PHbw2EImF\n" + "8cQG9No/Y5ClewIgMxEhPMntesNGfYIsuVq7xCCwLUFtzKuPYhCzMxwLBbECIQDC\n" + "rcHtkFNo0svXWSAEx5oowo76ucs86W6PU8IjnBK6og==",
                "musaday", 1
        );

        User user = new User();
        String token = util.getToken(user);

        user.fill(util.fromToken(token));

        System.out.println(user.asdf);
    }
}

