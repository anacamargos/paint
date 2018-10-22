/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhocg;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author anacamargos
 */
public class Circunferencia {
    
    int x1, y1;
    int x2, y2;
    int raio;
    
    public Circunferencia () {
        
    }
    public Circunferencia (int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    public int getX1 () {
        return this.x1;
    } 
    public int getX2 () {
        return this.x2;
    }
    public int getY1 () {
        return this.y1;
    }
    public int getY2 () {
        return this.y2;
    }
    
    public void distanciaEuclidiana( int x1, int y1, int x2, int y2 ){
        int deltaXquadrado = (int)Math.pow( (x2-x1) , 2 );
        int deltaYquadrado = (int)Math.pow( (y2-y1) , 2 );
        
        //return (int)( Math.sqrt( deltaXquadrado + deltaYquadrado) );
        this.raio = (int)( Math.sqrt( deltaXquadrado + deltaYquadrado) );
    }
    
    public void bresenham(int x1, int y1, int x2, int y2, BufferedImage buffer){
        distanciaEuclidiana(x1, y1, x2, y2 );
        
        int x = 0, y = raio, p = 3 - 2 * raio;

        plotaSimetricos(x, y, x1, y1, Color.BLACK.getRGB(), buffer);
        while (x < y) { // 2 octante
            if (p < 0) {
                p += 4 * x + 6;
            } else {
                p += 4 * (x - y) + 10;
                y--;
            }
            x++;

            plotaSimetricos(x, y, x1, y1, Color.BLACK.getRGB(), buffer);
        }
    }

    public void plotaSimetricos(int x, int y, int xCentro, int yCentro, int cor, BufferedImage buffer) {
        
        buffer.setRGB(xCentro + x, yCentro + y, cor);
        buffer.setRGB(xCentro + x, yCentro - y, cor);
        buffer.setRGB(xCentro - x, yCentro + y, cor);
        buffer.setRGB(xCentro - x, yCentro - y, cor);

        buffer.setRGB(xCentro + y, yCentro + x, cor);
        buffer.setRGB(xCentro + y, yCentro - x, cor);
        buffer.setRGB(xCentro - y, yCentro + x, cor);
        buffer.setRGB(xCentro - y, yCentro - x, cor);
    }
}
