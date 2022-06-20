package io.leaderli.demo;

import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;


/**
 * @author leaderli
 * @since 2022/6/10
 */
public class SM2UtilTest {

    @Test
    public void test() throws Exception {
        SM2Util sm2Util = new SM2Util();
        KeyPair keyPair = sm2Util.generateSm2KeyPair();
        BCECPublicKey publicKey = (BCECPublicKey) keyPair.getPublic();
        BCECPrivateKey privateKey = (BCECPrivateKey) keyPair.getPrivate();
        String pubKey = new String(Hex.encode(publicKey.getQ().getEncoded(true)));
        String prvKey = privateKey.getD().toString(16);
        System.out.println(pubKey);
        System.out.println(prvKey);

        System.out.println(sm2Util.encode("hello", pubKey));
        System.out.println(new String(sm2Util.decoder(sm2Util.encode("hello", pubKey), prvKey)));


    }

    @Test
    public void test2() throws Exception{
        SM2Util sm2Util = new SM2Util();
        KeyPair keyPair = sm2Util.generateSm2KeyPair();
        BCECPublicKey publicKey = (BCECPublicKey) keyPair.getPublic();
        BCECPrivateKey privateKey = (BCECPrivateKey) keyPair.getPrivate();
        String pubKey = new String(Hex.encode(publicKey.getQ().getEncoded(true)));
        String prvKey = privateKey.getD().toString(16);
        System.out.println(publicKey.getQ());
        System.out.println(pubKey);
        System.out.println(prvKey);

        String input = sm2Util.encode("hello", pubKey);
        System.out.println(input);
//        System.out.println(new String(sm2Util.decoder(sm2Util.encode("hello", pubKey), prvKey)));
        SM2Util sm2Util2 = new SM2Util();

//        String prikey = "c3036b054ebb2701a27fd5fde12099d80b47a824b33bdbacbb56653b84efbd9f";
//        String input = "BAQ8FCp4iAfeH0Rj2itgePICaU5kS2biZO4U/WcovtgkjTd3hoRDOOgVyAqpru2kixmme5na6zQ+L/rCbsqZ2OPSShacXcUpxWBoRN2Wt+sMMcyou/G2b2qlsW8tPUPShYAkRXHR";
        System.out.println(new String(sm2Util2.decoder(input, prvKey)));

    }


}

