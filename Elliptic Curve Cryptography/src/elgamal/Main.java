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
public class Main {
    public static void main(String[] args) throws IOException{
        
        IOFile file = new IOFile("data/plaintext.txt");
        List<String> blocks = new ArrayList<String>(file.getDataSegment());
        
        System.out.println("Plaintext");
        System.out.println(file.getData());        
        
        EllipticCurve curve = new EllipticCurve(new BigInteger("2"), new BigInteger("1"), new BigInteger("32416190071"));
        BigInteger privateKey1 = new BigInteger("13456789");
        BigInteger privateKey2 = new BigInteger("23456789");
        BigInteger randomKey = new BigInteger("99");
        Point basis = new Point(new BigInteger("0"), curve.calculateY(new BigInteger("0"))); 
        
        Point publicKey1 = basis.multiply(privateKey2, new BigInteger("32416190071"), new BigInteger("2"));
        Point publicKey2 = basis.multiply(privateKey1, new BigInteger("32416190071"), new BigInteger("2"));
        
        ECCElGamal encryption = new ECCElGamal();
        String result = new String();
        for (int i=0; i<blocks.size(); i++) {
            encryption = new ECCElGamal(blocks.get(i), "", privateKey1, randomKey, basis, curve, publicKey1);  
            encryption.encrypt();
            result += encryption.getCiphertext();
        }
        file = new IOFile("result.txt");
        file.writeFile(result);
        
        System.out.println("\n------- Enkripsi -------\n");
        System.out.println("CipherText");
        System.out.println(result);
        
        System.out.println("\n------- Dekripsi -------\n");
        file = new IOFile("result.txt");
        ECCElGamal decryption = new ECCElGamal("", file.readFile(), privateKey2, randomKey, basis, curve, publicKey2);
        decryption.decrypt();
        System.out.println(decryption.bigIntToString());
    }
}
