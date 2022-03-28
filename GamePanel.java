import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel{

    public GamePanel(){
        this.setPreferredSize(new Dimension(860,560));
        this.setVisible(true);
        this.setFocusable(true);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawLines(g);
        Piece(g);
        if(Variables.Bcheck || Variables.Wcheck){
            if(Variables.holdingPiece instanceof King){
                legalMoves(g);
            }
            drawCheckMoves(g);
        }
        else{
            legalMoves(g);
        }
        drawCheck(g);
        drawPieces(g);
    }

    public void drawLines(Graphics g){
        boolean b = true;
        for(int i = 0;i < 8; i ++){
            for(int j = 0; j < 8 ; j ++){
                if(b){
                    g.setColor(new Color(255,204,153));
                }
                else{
                    g.setColor(new Color(255,153,51));
                }
                g.fillRect(i*70, j*70, 70, 70);
                b = !b;
            }
            b = !b;
        }
    }

    public void drawPieces(Graphics g){
        for(Piece i : Variables.pieces){
            g.drawImage(i.image, i.x*70, i.y*70, this);
        }
    }

    public void legalMoves(Graphics g){
        g.setColor(new Color(255,102,102));
        if(Variables.holdingPiece != null){
            for(int[] i : Variables.holdingPiece.getMoves()){
                g.fillOval(i[0]*70 + 25, i[1]*70 + 25, 20, 20);
            }
            if(Variables.holdingPiece instanceof King){
                King king = (King) Variables.holdingPiece;
                if(king.canShortCastle()){
                    if(king.isWhite){
                        g.fillOval(6*70 + 25, 7*70 + 25, 20, 20);
                    }
                    else{
                        g.fillOval(6*70 + 25, 25, 20, 20);
                    }
                }
                else if(king.canLongCastle()){
                    if(king.isWhite){
                        g.fillOval(2*70 + 25, 7*70 + 25, 20, 20);
                    }
                    else{
                        g.fillOval(2*70 + 25, + 25, 20, 20);
                    }
                }
            }
        }
    }

    public void drawCheck(Graphics g){
        g.setColor(Color.RED);
        if(Variables.Wcheck){
            g.fillRect(Variables.bKing.x*70, Variables.bKing.y*70, 70, 70);
        }
        else if(Variables.Bcheck){
            g.fillRect(Variables.wKing.x*70, Variables.wKing.y*70, 70, 70);
        }
    }

    public void drawCheckMoves(Graphics g){
        g.setColor(new Color(255,102,102));
        if(Variables.holdingPiece != null){
            for(int[] i : Variables.holdingPiece.getCheckMoves()){
                g.fillOval(i[0]*70 + 25, i[1]*70 + 25, 20, 20);
            }
        }
    }

    public void Piece(Graphics g){
        g.setColor(new Color(255,102,102));
        if(Variables.drawedPiece != null){
            g.fillRect(Variables.drawedPiece.x*70, Variables.drawedPiece.y*70, 70 , 70);
        }
    }
}
