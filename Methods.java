import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Methods {
    
    public static void createPieces(){
        ArrayList<Piece> mainPieces = new ArrayList<>();
        Variables.wlRook = new Rook(0, 7, Variables.imgs[4], true);
        Variables.wrRook = new Rook(7, 7, Variables.imgs[4], true);
        Variables.blRook = new Rook(0, 0, Variables.imgs[10], false);
        Variables.brRook = new Rook(7, 0, Variables.imgs[10], false);
        Variables.wKing = new King(4,7, Variables.imgs[0], true);
        Variables.bKing = new King(4,0, Variables.imgs[6], false);

        mainPieces.clear();
        mainPieces.add(new Bishop(2,0,Variables.imgs[8],false));
        mainPieces.add(new Bishop(5,0,Variables.imgs[8],false));
        mainPieces.add(Variables.blRook);
        mainPieces.add(Variables.brRook);
        mainPieces.add(new Knight(1,0,Variables.imgs[9],false));
        mainPieces.add(new Knight(6,0,Variables.imgs[9],false));
        mainPieces.add(new Queen(3,0,Variables.imgs[7],false));
        mainPieces.add(Variables.bKing);

        mainPieces.add(new Bishop(2,7,Variables.imgs[2],true));
        mainPieces.add(new Bishop(5,7,Variables.imgs[2],true));
        mainPieces.add(Variables.wlRook);
        mainPieces.add(Variables.wrRook);
        mainPieces.add(new Knight(1,7,Variables.imgs[3],true));
        mainPieces.add(new Knight(6,7,Variables.imgs[3],true));
        mainPieces.add(new Queen(3,7,Variables.imgs[1],true));
        mainPieces.add(Variables.wKing);

        int xx = 0;
        int yy = 1;
        for(int i = 0;i<8;i++){
            mainPieces.add(new Pawn(xx,yy,Variables.imgs[11],false));
            xx++;
        }

        xx = 0;
        yy = 6;
        for(int i = 0;i<8;i++){
            mainPieces.add(new Pawn(xx,yy,Variables.imgs[5],true));
            xx++;
        }

        Variables.holdingPiece = null;
        Variables.pieces.clear();
        Variables.pieces.addAll(mainPieces);

        for(Piece p : Variables.pieces){
            if(p.isWhite){
                Variables.WhitePieces.add(p);
            }
            else{
                Variables.BlackPieces.add(p);
            }
        }
    }

    public static void CreateImages(Image[] imgs){
        BufferedImage all = null;
        try {
            all = ImageIO.read(new File("\\chess.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int ind = 0;
        for(int m = 0;m<400;m+=200){
            for(int l = 0;l<1200;l+=200){
                imgs[ind] = all.getSubimage(l, m, 200, 200)
                        .getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                ind++;
            }
        }
    }

    
    public static ArrayList<int[]> getBlackMoves(){
        ArrayList<int[]> arr = new ArrayList<int[]>();
        for(Piece p : Variables.BlackPieces){
            arr.addAll(p.getMoves());
        }
        return arr;
    }

    public static ArrayList<int[]> getWhiteMoves(){
        ArrayList<int[]> arr = new ArrayList<int[]>();
        for(Piece p : Variables.WhitePieces){
            arr.addAll(p.getMoves());
        }
        return arr;
    }

    public static void run(){
        CreateImages(Variables.imgs);
        createPieces();
        new GameBoard();
    }
}
