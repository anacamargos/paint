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
public class Reta {
    
    int x1, y1, x2, y2;
    double u1, u2;
    
    public Reta() {
        
    }
    public Reta (int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public int getX1() {
        return this.x1;
    }
    public int getX2() {
        return this.x2;
    }
    public int getY1() {
        return this.y1;
    }
    public int getY2() {
        return this.y2;
    }
    
    public void setX1 ( int x1 ) {
        this.x1 = x1;
    }
    public void setX2 ( int x2 ) {
        this.x2 = x2;
    }
    public void setY1 ( int y1 ) {
        this.y1 = y1;
    }
    public void setY2 ( int y2 ) {
        this.y2 = y2;
    }

    public void dda(int x1, int y1, int x2, int y2, BufferedImage buffer, int cor) {
            System.out.println("Entrei no dda");
            int deltaX = x2 - x1;
            int deltaY = y2 - y1;

            double x = (double) x1, y = (double) y1;

            // colore o primeiro pixel da reta
            buffer.setRGB((int) Math.round(x), (int) Math.round(y), cor);


            int passos = Math.max(Math.abs(deltaX), Math.abs(deltaY));
            double xIncr = ((double) deltaX) / ((double) passos);
            double yIncr = ((double) deltaY) / ((double) passos);

            for (int k = 1; k <= passos; k++) {
                x += xIncr;
                y += yIncr;

                buffer.setRGB((int) Math.round(x), (int) Math.round(y), cor);
            }
   
    }
    
    public void bresenham (int x1, int y1, int x2, int y2, BufferedImage buffer) {
            System.out.println("Entrei no bresenham");
            int deltaX = x2 - x1;
            int deltaY = y2 - y1;
            int x = x1;
            int y = y1;

            int xIncr, yIncr, p, c1, c2;

            //colore(x, y, cor);
            buffer.setRGB(x, y, Color.BLACK.getRGB());

            if (deltaX < 0) {
                deltaX = -deltaX;
                xIncr = -1;
            } else {
                xIncr = 1;
            }

            if (deltaY < 0) {
                deltaY = -deltaY;
                yIncr = -1;
            } else {
                yIncr = 1;
            }

            if (deltaX > deltaY) { // 1 caso
                p = 2 * deltaY - deltaX;
                c1 = 2 * deltaY;
                c2 = 2 * (deltaY - deltaX);

                for (int k = 1; k <= deltaX; k++) {
                    x += xIncr;
                    if (p < 0) {
                        p += c1;
                    } else {
                        p += c2;
                        y += yIncr;
                    }

                    //colore(x, y, cor);
                    buffer.setRGB(x, y, Color.BLACK.getRGB());
                }// fim for
            }// fim if
            else { // 2 caso
                p = 2 * deltaX - deltaY;
                c1 = 2 * deltaX;
                c2 = 2 * (deltaX - deltaY);

                for (int k = 1; k <= deltaY; k++) {
                    y += yIncr;
                    if (p < 0) {
                        p += c1;
                    } else {
                        p += c2;
                        x += xIncr;
                    }
                    //colore(x, y, cor);
                    buffer.setRGB(x, y, Color.BLACK.getRGB());
                }// fim for
            }// fim else

    }
    
    public int obtemCodigo( int xDado, int yDado, int xMin, int yMin, int xMax, int yMax ) {
        int codigo = 0;

        // fazer para xInicio e xFim separado
        
        if (xDado < xMin) {
            codigo += 1;
        }
        if ( xDado > xMax) {
            codigo += 2;
        }

        if ( yDado < yMin) {
            codigo += 4;
        }
        if ( yDado > yMax) {
            codigo += 8;
        }

        return codigo;
    }
    
    public int bit(int codigo, int pos) {

        int bit = codigo << (31 - pos);
        bit = bit >>> 31;
        return bit;
    }
    
    public void cohenSutherland(int x1Dado, int y1Dado, int x2Dado, int y2Dado, int xMin, int yMin, int xMax, int yMax, BufferedImage buffer) {
        boolean aceito = false;
        boolean feito = false;


        int cFora;

        double xInt = 0,
               yInt = 0; 



        while (!feito) {
            int c1 = obtemCodigo(x1Dado, y1Dado, xMin, yMin, xMax, yMax);
            int c2 = obtemCodigo( x2Dado, y2Dado, xMin, yMin, xMax, yMax);

            if (c1 == 0 && c2 == 0) {
                aceito = true;
                feito = true;
            } else if ((c1 & c2) != 0) { // totalmente fora da janela

                // marca a figura para nao ser desenhada
                feito = true;
            } else { // calcula

                // se c1 != 0 então cFora = c1
                // senao cFora = c2
                cFora = (c1 != 0) ? c1 : c2;

                if (bit(cFora, 0) == 1) {// esq
                    xInt = xMin;
                    yInt = y1Dado + (y2Dado - y1Dado) * (xMin - x1Dado) / (x2Dado - x1Dado);

                } else if (bit(cFora, 1) == 1) {// dir
                    xInt = xMax;
                    yInt = y1Dado + (y2Dado - y1Dado) * (xMax - x1Dado) / (x2Dado - x1Dado);

                } else if (bit(cFora, 2) == 1) {// inferior
                    yInt = yMin;
                    xInt = x1Dado + (x2Dado - x1Dado) * (yMin - y1Dado) / (y2Dado - y1Dado);

                } else if (bit(cFora, 3) == 1) { // superior
                    yInt = yMax;
                    xInt = x1Dado + (x2Dado - x1Dado) * (yMax - y1Dado) / (y2Dado - y1Dado);

                }

                if (cFora == c1) {
                    x1Dado = (int)(Math.round(xInt));
                    y1Dado = (int)(Math.round(yInt));
                    
                } else {
                    x2Dado = (int)(Math.round(xInt));
                    y2Dado = (int)(Math.round(yInt));
                    
                }

            }
        }// fim while
        
        if(aceito) {
            x1 = x1Dado;
            y1 = y1Dado;
            x2 = x2Dado;
            y2= y2Dado;
            this.dda(x1Dado, y1Dado, x2Dado, y2Dado, buffer, Color.BLACK.getRGB());
        }
    }
    
    
    public boolean clipset(double p, double q) {
        double r = q / p;

        // Implementacao da professora
        if (p < 0) {
            if (r > 1) {
                return false;
            } else if (r > u1) {
                u1 = r;
            }
        } else if (p > 0) {
            if (r < 0) {
                return false;
            } else if (r < u2) {
                u2 = r;
            }
        } else if (q < 0) {
            return false;
        }
        return true;
    }
    
    public void liang(double x1Dado, double y1Dado, double x2Dado, double y2Dado, int xMin,int yMin, int xMax, int yMax, BufferedImage buffer) {
      

        double dx = x2Dado - x1Dado,
               dy = y2Dado - y1Dado;
        
        this.u1 = 0.0;
        this.u2 = 1.0;


        if (clipset(-dx, x1Dado - xMin)) {
            
            if (clipset(dx, xMax - x1Dado)) {
                
                if (clipset(-dy, y1Dado - yMin)) {
                    
                    if (clipset(dy, yMax - y1Dado)) {
                        

                        if (u2 < 1.0) {
                            x2Dado = x1Dado + dx * u2;
                            y2Dado = y1Dado + dy * u2;
                        }
                        if (u1 > 0.0) {
                            x1Dado = x1Dado + dx * u1;
                            y1Dado = y1Dado + dy * u1;
                        }

                   
                        this.dda((int)(Math.round(x1Dado)), (int)(Math.round(y1Dado)), (int)(Math.round(x2Dado)), (int)(Math.round(y2Dado)), buffer, Color.BLACK.getRGB());

                        x1 = (int)(Math.round(x1Dado));
                        y1 = (int)(Math.round(y1Dado));
                        x2 = (int)(Math.round(x2Dado));
                        y2= (int)(Math.round(y2Dado));
                    }
                }
            }
        }// fim todos if's

    }// fim metodo liang
    
    public static void boundaryFill4 (int x, int y, int corBorda, int corNova, BufferedImage buffer) {
        
        int corAtual = buffer.getRGB(x, y);
        if (corAtual != corBorda && corAtual != corNova) {
            buffer.setRGB(x, y, corNova);
            
            boundaryFill4(x + 1, y, corBorda, corNova, buffer);
            boundaryFill4(x - 1, y, corBorda, corNova, buffer);
            boundaryFill4(x, y + 1, corBorda, corNova, buffer);
            boundaryFill4(x, y - 1, corBorda, corNova, buffer);
        }
        
    }
    
    public static void floodFill4 (int x, int y, int corAntiga, int corNova, BufferedImage buffer) {
        
        int corAtual = buffer.getRGB(x,y);
        
        if (corAtual == corAntiga) {
            buffer.setRGB(x, y, corNova);
            
            floodFill4 (x + 1, y, corAntiga, corNova, buffer);
            floodFill4 (x - 1, y, corAntiga, corNova, buffer);
            floodFill4 (x, y + 1, corAntiga, corNova, buffer);
            floodFill4 (x, y - 1, corAntiga, corNova, buffer);
        }
        
    }
    
}
