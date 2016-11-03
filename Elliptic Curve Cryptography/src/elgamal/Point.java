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
public class Point {
    private BigInteger x;
    private BigInteger y;
    
    public Point() {
        this.x = new BigInteger("0");
        this.y = new BigInteger("0");
    }
    
    public Point(BigInteger x, BigInteger y){
        this.x = x;
        this.y = y;
    }
    
    public BigInteger gradien1(Point point, BigInteger p){
        BigInteger gradienY = y.subtract(point.y).mod(p);
        BigInteger gradienX = x.subtract(point.x).modInverse(p);
        return gradienY.multiply(gradienX).mod(p); 
    }
    
    public BigInteger gradien2(BigInteger p, BigInteger a){
        BigInteger gradienY = x.pow(2).multiply(new BigInteger("3")).add(a);
        BigInteger gradienX = y.multiply(new BigInteger("2")).modInverse(p);
        return gradienY.multiply(gradienX).mod(p); 
    }
    
    public Point plus(Point point, BigInteger p, BigInteger a){
        Point r = new Point();
        
        if(point.x == x && point.y == y) {
            BigInteger m = gradien2(p, a);
            r.x = m.pow(2).subtract(x.multiply(new BigInteger("2"))).mod(p);
            r.y = m.multiply(x.subtract(r.x)).subtract(y).mod(p);
        }       
        else {
            BigInteger m = gradien1(point, p);
            r.x = m.pow(2).subtract(x).subtract(point.x).mod(p);
            r.y = m.multiply(x.subtract(r.x)).subtract(y).mod(p);        
        }
        return r;
    }
    
    public Point minus(Point point, BigInteger p, BigInteger a){
        Point r = new Point();
        point.y = point.y.negate().mod(p);
        r = plus(point,p,a);
        return r;
    }
    
    public Point multiplyTwo(BigInteger p, BigInteger a){
        return plus(this, p, a);
    }
    
    public Point multiply(BigInteger k, BigInteger p, BigInteger a){
        Point r = this;
        if(k.equals(new BigInteger("1"))){
            // do nothing
        } else if(k.equals(new BigInteger("2"))){
            r = r.multiplyTwo(p,a);
        } else {
            if(k.mod(new BigInteger("2")).equals(new BigInteger("0"))) {
                r = r.multiply(k.divide(new BigInteger("2")), p, a);
                r = r.multiplyTwo(p,a);
            } else {
                r = r.plus(r.multiply(k.subtract(new BigInteger("1")), p, a), p, a);
            }

        }
        return r;
    }
    
    public BigInteger getX(){
        return x;
    }
    
    public BigInteger getY(){
        return y;
    }
}
