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
public class EllipticCurve {
    BigInteger[] curve;
    BigInteger modulus;
    
    public EllipticCurve(){
        curve = new BigInteger[4];
        modulus = new BigInteger("0");
    }
    
    public EllipticCurve(BigInteger a, BigInteger b, BigInteger p) {
        curve = new BigInteger[4];
        curve[0] = b;
        curve[1] = a;
        curve[2] = new BigInteger("0");
        curve[3] = new BigInteger("1");
        modulus = p;
    }
    
    public BigInteger calculateY(BigInteger x){
        BigInteger result = new BigInteger("0");
        for(int i=0; i<curve.length; i++){
            result.add(curve[i].multiply(x.pow(i)));
        }
        return result.mod(modulus);
    }
    
    public BigInteger getMod(){
        return modulus;
    }
    
    public BigInteger[] getCurve(){
        return curve;
    }
    
}
