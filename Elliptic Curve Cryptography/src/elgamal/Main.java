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
        Point P = new Point(new BigInteger("2"), new BigInteger("4"));
        Point Q = new Point(new BigInteger("5"), new BigInteger("9"));
        Point R = P.plus(Q, new BigInteger("11"), new BigInteger("1"));
        System.out.println("Hasil Tambah -- x: " + R.getX() + " y:" + R.getY());
        
        R =P.plus(P, new BigInteger("11"), new BigInteger("1"));
        R = R.plus(P,  new BigInteger("11"), new BigInteger("1"));
        System.out.println("Hasil Tambah -- 4: " + R.getX() + " y:" + R.getY());
        
        R = P.multiply(new BigInteger("12"),new BigInteger("11"), new BigInteger("1"));
        System.out.println("Hasil Kali -- x: " + R.getX() + " y:" + R.getY());
    }
}
