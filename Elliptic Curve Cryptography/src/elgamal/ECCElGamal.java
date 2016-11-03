/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elgamal;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zulvafachrina
 */
public class ECCElGamal {
    private String plaintext;
    private String ciphertext;
    private BigInteger privateKey;
    private BigInteger randomKey;
    private Point publicKey;
    private EllipticCurve curve;
    private Point basis;
    private List<BigInteger> decryptionPoints;
    
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
        
        ciphertext = cipherX.getX().toString(16).toUpperCase() + " " +  cipherX.getY().toString(16).toUpperCase() + " " + cipherY.getX().toString(16).toUpperCase() + " " + cipherY.getY().toString(16).toUpperCase() +" ";
    }
    
    public void decrypt(){
        String[] ciphers = ciphertext.split(" ");
        decryptionPoints = new ArrayList<BigInteger>();
        for (int i=0; i<ciphers.length; i+=4) {
            Point cipherX = new Point(new BigInteger(ciphers[i],16), new BigInteger(ciphers[i+1],16));
            Point cipherY = new Point(new BigInteger(ciphers[i+2],16), new BigInteger(ciphers[i+3],16));
            Point plainPoint = cipherY.minus(cipherX.multiply(privateKey, curve.getMod(), curve.getCurve()[1]), curve.getMod(), curve.getCurve()[1]);
            decryptionPoints.add(plainPoint.getX());
        }      
    }
    
    public String bigIntToString() {
        StringBuilder stringbuff = new StringBuilder();
        for (int i=0; i<decryptionPoints.size(); i++) {
            stringbuff.append(new String (decryptionPoints.get(i).toByteArray()));
        }
        return stringbuff.toString();
    }
    
    public String getPlaintext(){
        return plaintext;
    }
    
    public String getCiphertext(){
        return ciphertext;
    }
    
}
