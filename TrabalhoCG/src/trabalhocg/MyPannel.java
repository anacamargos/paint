/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhocg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author anacamargos
 */
public class MyPannel extends JPanel {
    
    //private BufferedImage buffer = new BufferedImage(821, 546, BufferedImage.TYPE_INT_RGB);
    private BufferedImage buffer = new BufferedImage(2000, 2000, BufferedImage.TYPE_INT_RGB);
    
    public MyPannel(){
        limpa();
        repaint();
    }
    
    @Override
    public void paint (Graphics graphic) {
        Graphics2D graphics = (Graphics2D) graphic;

        // desenha a matriz na tela
        graphics.drawImage(buffer, null, 0, 0);
        
    }
    
    public void limpa(){
        for( int y = 0; y < buffer.getHeight(); y++){
            for( int x = 0; x < buffer.getWidth(); x++){
                buffer.setRGB(x, y, Color.WHITE.getRGB());
            }
        }
    }
    
    public void atualizar(){
        //System.out.println("teste");
        repaint();
    }
    
    public BufferedImage getBuffer(){
        return this.buffer;
    }
    
}
