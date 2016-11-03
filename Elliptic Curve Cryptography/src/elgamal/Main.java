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
public class Main {
    public static void main(String[] args) throws IOException{
        
        IOFile file = new IOFile("data/plaintext.txt");
        
        System.out.println("Plaintext");
        System.out.println(new BigInteger(file.readFile().getBytes()));
        
        
        EllipticCurve curve = new EllipticCurve(new BigInteger("2"), new BigInteger("1"), new BigInteger("32416190071"));
        BigInteger privateKey1 = new BigInteger("13456789");
        BigInteger privateKey2 = new BigInteger("23456789");
        BigInteger randomKey = new BigInteger("99");
        Point basis = new Point(new BigInteger("0"), curve.calculateY(new BigInteger("0"))); 
        
        Point publicKey1 = basis.multiply(privateKey2, new BigInteger("32416190071"), new BigInteger("2"));
        Point publicKey2 = basis.multiply(privateKey1, new BigInteger("32416190071"), new BigInteger("2"));
        
        ECCElGamal encryption = new ECCElGamal(file.readFile(), "", privateKey1, randomKey, basis, curve, publicKey1);
        
        encryption.encrypt();
        file = new IOFile("result.txt");
        file.writeFile(encryption.getCiphertext());
        
        System.out.println("\n------- Enkripsi -------\n");
        System.out.println("CipherText");
        System.out.println(encryption.getCiphertext());
        
        System.out.println("\n------- Dekripsi -------\n");
        file = new IOFile("result.txt");
        ECCElGamal decryption = new ECCElGamal("", file.readFile(), privateKey2, randomKey, basis, curve, publicKey2);
        decryption.decrypt();
        
        
        
    }
}
