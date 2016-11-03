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
public class Main {
    public static void main(String[] args){
        
        IOFile file = new IOFile("data/plaintext.txt");
        
        System.out.println("Plaintext");
        System.out.println(new BigInteger(file.readFile().getBytes()));
        
        EllipticCurve curve = new EllipticCurve(new BigInteger("2"), new BigInteger("1"), new BigInteger("32416190071"));
        BigInteger privateKey = new BigInteger("13456789");
        BigInteger randomKey = new BigInteger("99");
        Point basis = new Point(new BigInteger("1"), new BigInteger("0"));
        ECCElGamal encryption = new ECCElGamal(file.readFile(), "", privateKey, randomKey, basis, curve);
        encryption.encrypt();
    }
}
