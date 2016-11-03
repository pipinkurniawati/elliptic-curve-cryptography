/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elgamal;

import java.math.BigInteger;

/**
 *
 * @author zulvafachrina
 */
public class ECCElGamal {
    String plaintext;
    String ciphertext;
    BigInteger privateKey;
    BigInteger publicKey;
    EllipticCurve curve;
    Point basis;
    
    public ECCElGamal(){
        plaintext = new String();
        ciphertext = new String();
        privateKey = new BigInteger("0");
        publicKey = new BigInteger("0");
        basis = new Point();
        curve = new EllipticCurve();
    }
    
    public ECCElGamal(String plain, String cipher, BigInteger a, BigInteger k, Point b, EllipticCurve c) {
        plaintext = plain;
        ciphertext = cipher;
        privateKey = a;
        publicKey = k;
        basis = b;
        curve = c;
    }
    
    public Point makePoint(){
        BigInteger x = new BigInteger(plaintext.getBytes());
        BigInteger y = curve.calculateY(x);
        return new Point(x,y);
    }
    
    public void encrypt() {
        Point init = makePoint();
        Point cipherX = basis.multiply(publicKey, curve.getMod(), curve.getCurve()[2]);
        Point publicPoint = basis.multiply(privateKey, curve.getMod(), curve.getCurve()[2]);
        Point cipherY = init.plus(publicPoint.multiply(publicKey, curve.getMod(), curve.getCurve()[2]), curve.getMod(), curve.getCurve()[2]);
        
        System.out.println("------- Enkripsi -------");
        System.out.println("CipherText");
        System.out.println("Point 1 ");
        System.out.println("x: " + cipherX.getX() + " y: " + cipherX.getY());
        System.out.println("Point 2 ");
        System.out.println("x: " + cipherY.getX() + " y: " + cipherY.getY());
    }
    
    public void decrypt(){
        
    }
    
    public String getPlaintext(){
        return plaintext;
    }
    
    public String getCiphertext(){
        return ciphertext;
    }
    
}
