/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elgamal;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zulvafachrina
 */
public class Main {
    private static BigInteger privateKey1, privateKey2, randomKey;
    private static Point publicKey1, publicKey2, basis;
    private static long startTime, endTime;
    public static void main(String[] args) throws IOException{
        EllipticCurve curve = new EllipticCurve(new BigInteger("2"), new BigInteger("1"), new BigInteger("32416190071"));
        buildKey("23456789", "0", curve);
        
        File output;
        Scanner scanner = new Scanner(System.in);
        displayMenu();
        String choice = scanner.nextLine();
        while (!choice.equals("4")) {
            switch (choice) {
                case "1":
                    System.out.println("\n********** Encryption **********\n");
                    System.out.print("The file you want to encrypt: ");
                    String infile = scanner.nextLine(); 
                    System.out.println();
                    IOFile file = new IOFile(infile);
                    List<String> blocks = new ArrayList<String>(file.getDataSegment());
                    System.out.println("------- Plaintext -------");
                    System.out.println(file.getData()); 
                    ECCElGamal encryption = new ECCElGamal();
                    String result = new String();
                    startTime = System.currentTimeMillis();
                    for (int i=0; i<blocks.size(); i++) {
                        encryption = new ECCElGamal(blocks.get(i), "", privateKey1, randomKey, basis, curve, publicKey1);  
                        encryption.encrypt();
                        result += encryption.getCiphertext();
                    }
                    endTime = System.currentTimeMillis();
                    System.out.println("\n------- CipherText -------");
                    System.out.println(result);
                    System.out.println("\nRunning time: " + (endTime-startTime) + " milisecond");
                    System.out.print("\nSave the result to : ");
                    String outfile = scanner.nextLine(); 
                    file = new IOFile(outfile);
                    System.out.println("Saving the result...");
                    output = new File(outfile);
                    double kilobytes = ( output.length() / 1024);
                    System.out.println("Done saving. File size: "+ kilobytes + " kilobytes");
                    file.writeFile(result);
                    break;
                case "2":
                    System.out.println("\n********** Decryption **********\n");
                    System.out.print("The file you want to decrypt: ");
                    infile = scanner.nextLine(); 
                    System.out.println();
                    file = new IOFile(infile);
                    ECCElGamal decryption = new ECCElGamal("", file.readFile(), privateKey2, randomKey, basis, curve, publicKey2);
                    startTime = System.currentTimeMillis();
                    decryption.decrypt();
                    endTime = System.currentTimeMillis();
                    System.out.println("\n------- Plaintext -------");
                    System.out.println(decryption.bigIntToString());
                    System.out.println("\nRunning time: " + (endTime-startTime) + " milisecond");
                    System.out.print("\nSave the result to : ");
                    file.writeFile(decryption.bigIntToString());
                    outfile = scanner.nextLine(); 
                    file = new IOFile(outfile);
                    System.out.println("Saving the result...");
                    output = new File(outfile);
                    double kb = (output.length() / 1024);
                    System.out.println("Done saving. File size: "+ kb + " kilobytes");
                    break;
                case "3":
                    System.out.print("Enter private key: ");
                    String privkey = scanner.nextLine();
                    System.out.print("Enter base value: ");
                    String base = scanner.nextLine(); 
                    System.out.println();
                    System.out.println("Saving public key and private key");
                    buildKey(privkey, base, curve);
                default:
                    System.out.println("Your input is not recognizable");
                    break;
                    
            }
            System.out.println();
            displayMenu();
            choice = scanner.nextLine(); 
            System.out.println();
        }
    }
    
    public static void displayMenu() {
        System.out.println("CHOOSE COMMAND");
        System.out.println("1. Encrypt a Plaintext");
        System.out.println("2. Decrypt a Ciphertext");
        System.out.println("3. Build Key");
        System.out.println("4. Exit");
        System.out.print("your input: ");
    }
    
    public static void buildKey(String privKey2, String base, EllipticCurve curve) {
        try {
            privateKey1 = new BigInteger("13456789");
            privateKey2 = new BigInteger(privKey2);
            randomKey = new BigInteger("99");
            basis = new Point(new BigInteger(base), curve.calculateY(new BigInteger("0")));
            publicKey1 = basis.multiply(privateKey2, new BigInteger("32416190071"), new BigInteger("2"));
            publicKey2 = basis.multiply(privateKey1, new BigInteger("32416190071"), new BigInteger("2"));
            
            IOFile privKey = new IOFile("key.pri");
            IOFile pubKey = new IOFile("key.pub");
            privKey.writeFile(privateKey1.toString());
            pubKey.writeFile(publicKey1.toString() + "\n" + publicKey1.toString());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
