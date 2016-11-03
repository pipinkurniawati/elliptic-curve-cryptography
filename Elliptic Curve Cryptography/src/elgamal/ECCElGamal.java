/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elgamal;

import java.io.IOException;
import java.math.BigInteger;

/**
 *
 * @author zulvafachrina
 */
public class ECCElGamal {
    String plaintext;
    String ciphertext;
    BigInteger privateKey;
    BigInteger randomKey;
    Point publicKey;
    EllipticCurve curve;
    Point basis;
    
    public ECCElGamal(){
        plaintext = new String();
        ciphertext = new String();
        privateKey = new BigInteger("0");
        randomKey = new BigInteger("0");
        basis = new Point();
        curve = new EllipticCurve();
        publicKey = new Point();
    }
    
    public ECCElGamal(String plain, String cipher, BigInteger a, BigInteger k, Point b, EllipticCurve c, Point p) {
        plaintext = plain;
        ciphertext = cipher;
        privateKey = a;
        randomKey = k;
        basis = b;
        curve = c;
        publicKey = p;
    }
    
    public Point makePoint(){
        BigInteger x = new BigInteger(plaintext.getBytes());
        BigInteger y = curve.calculateY(x);
        return new Point(x,y);
    }
    
    public void encrypt() throws IOException {
        Point init = makePoint();
        Point cipherX = basis.multiply(randomKey, curve.getMod(), curve.getCurve()[1]);
        Point cipherY = init.plus(publicKey.multiply(randomKey, curve.getMod(), curve.getCurve()[1]), curve.getMod(), curve.getCurve()[1]);
        
        ciphertext = cipherX.getX().toString() + "\n" +  cipherX.getY().toString() + "\n" + cipherY.getX().toString() + "\n" + cipherY.getY().toString();

    }
    
    public void decrypt(){
        String[] ciphers = ciphertext.split("\n", 4);

        Point cipherX = new Point(new BigInteger(ciphers[0]), new BigInteger(ciphers[1]));
        Point cipherY = new Point(new BigInteger(ciphers[2]), new BigInteger(ciphers[3]));
        
        Point plainPoint = cipherY.minus(cipherX.multiply(privateKey, curve.getMod(), curve.getCurve()[1]), curve.getMod(), curve.getCurve()[1]);
        System.out.println(plainPoint.getX().toString());
        
    }
    
    public String getPlaintext(){
        return plaintext;
    }
    
    public String getCiphertext(){
        return ciphertext;
    }
    
}
