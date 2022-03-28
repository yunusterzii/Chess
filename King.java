import java.util.ArrayList;
import java.awt.Image;

public class King extends Piece{
    public ArrayList<int[]> nearSquares = new ArrayList<int[]>();

    public King(int x, int y, Image image, boolean isWhite){
        super(x, y, image, isWhite);
    }

    public void move(){
        int[][] squares = {{0,-1},{0,1},{1,0},{-1,0},{1,-1},{1,1},{-1,1},{-1,-1}};

        for(int[] i : squares){
            int new_x = x + i[0];
            int new_y = y + i[1];
            int m[] = {new_x,new_y};

            if(new_x > 7 || new_x < 0 || new_y > 7 || new_y < 0){
                continue;
            }

            Piece a = null;
            for(Piece p : Variables.pieces){
                if(p.x == m[0] && p.y == m[1]){
                    a = p;
                }
            }
            if(a != null){
                if(a.isWhite == isWhite){
                    protectedPieces.add(a);
                    continue;
                }
                else{
                    if(a instanceof King){
                        continue;
                    }
                    moves.add(m);
                    nearSquares.add(m);
                    continue;
                }
            }
            nearSquares.add(m);
            moves.add(m);
        }
    }

    public void cantMove(){
        ArrayList<int[]> enemyMoves = null;
        if(isWhite){
            enemyMoves = Variables.blackMoves;
        }
        else{
            enemyMoves = Variables.whiteMoves;
        }

        for(Piece p : Variables.pieces){
            if(p.isWhite != isWhite && p instanceof Pawn){
                Pawn pawn = (Pawn) p;
                for(int[] i : pawn.checkSquares()){
                    enemyMoves.add(i);
                }
            }
        }

        if(this.isWhite){
            Variables.bKing.move();
            for(int[] i : Variables.bKing.getNearSquares()){
                enemyMoves.add(i);
            }
        }
        else{
            Variables.wKing.move();
            for(int[] i : Variables.wKing.getNearSquares()){
                enemyMoves.add(i);
            } 
        }

        ArrayList<Piece> protPieces = new ArrayList<Piece>();
        for(Piece p : Variables.pieces){
            p.move();
            if(p.isWhite != isWhite){
                for(Piece piece : p.getProtectedPieces()){
                    protPieces.add(piece);
                }
            }
        }
        for(Piece p : protPieces){
            int[] m = {p.x, p.y};
            enemyMoves.add(m);
        }

        for(int[] i : enemyMoves){
            int[] removable = null;
            for(int[] j : moves){
                if(i[0] == j[0] && i[1] == j[1]){
                    removable = j;
                }
            }
            moves.remove(removable);
        }
    }

    @Override
    public ArrayList<int[]> getMoves(){
        moves.clear();
        move();
        cantMove();
        pinnedMoves();
        return moves;
    }

    public ArrayList<int[]> getNearSquares(){
        nearSquares.clear();
        move();
        return nearSquares;
    }

    @Override
    public ArrayList<Piece> getProtectedPieces(){
        protectedPieces.clear();
        move();
        return protectedPieces;
    }

    public boolean canShortCastle(){
        Rook rook = null;
        if(isWhite){
            if(Variables.Bcheck){
                return false;
            }
        }
        else{
            if(Variables.Wcheck){
                return false;
            }
        }
        if(isWhite){
            rook = Variables.wrRook;
        }
        else{
            rook = Variables.brRook;
        }

        if(counter != 0 || rook.counter != 0){
            return false;
        }

        if(isWhite){
            for(Piece p : Variables.pieces){
                if((p.x == 5 && p.y == 7) || (p.x == 6 && p.y == 7)){
                    return false;
                }
            }
        }
        else{
            for(Piece p : Variables.pieces){
                if((p.x == 5 && p.y == 0) || (p.x == 6 && p.y == 0)){
                    return false;
                }
            }
        }

        if(isWhite){
            for(int[] i : Variables.blackMoves){
                if((i[0] == 5 && i[1] == 7) || (i[0] == 6 && i[1] == 7)){
                    return false;
                }
            }
        }
        else{
            for(int[] i : Variables.whiteMoves){
                if((i[0] == 5 && i[1] == 0) || (i[0] == 6 && i[1] == 0)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canLongCastle(){
        Rook rook = null;
        if(isWhite){
            if(Variables.Bcheck){
                return false;
            }
        }
        else{
            if(Variables.Wcheck){
                return false;
            }
        }
        if(isWhite){
            rook = Variables.wlRook;
        }
        else{
            rook = Variables.blRook;
        }

        if(counter != 0 || rook.counter != 0){
            return false;
        }

        if(isWhite){
            for(Piece p : Variables.pieces){
                if((p.x == 1 && p.y == 7) || (p.x == 2 && p.y == 7) || (p.x == 3 && p.y == 7)){
                    return false;
                }
            }
        }
        else{
            for(Piece p : Variables.pieces){
                if((p.x == 1 && p.y == 0) || (p.x == 2 && p.y == 0) || (p.x == 3 && p.y == 0)){
                    return false;
                }
            }
        }

        if(isWhite){
            for(int[] i : Variables.blackMoves){
                if((i[0] == 1 && i[1] == 7) || (i[0] == 2 && i[1] == 7) || (i[0] == 3 && i[1] == 7)){
                    return false;
                }
            }
        }
        else{
            for(int[] i : Variables.whiteMoves){
                if((i[0] == 1 && i[1] == 0) || (i[0] == 2 && i[1] == 0) || (i[0] == 3 && i[1] == 0)){
                    return false;
                }
            }
        }
        return true;
    }

    public void shortCastle(){
        if(isWhite){
            Variables.wKing.x = 6;
            Variables.wKing.y = 7;
            Variables.wrRook.x = 5;
            Variables.wrRook.y = 7;
        }
        else{
            Variables.bKing.x = 6;
            Variables.bKing.y = 0;
            Variables.brRook.x = 5;
            Variables.brRook.y = 0;
        }
    }

    public void longCastle(){
        if(isWhite){
            Variables.wKing.x = 2;
            Variables.wKing.y = 7;
            Variables.wlRook.x = 3;
            Variables.wlRook.y = 7;
        }
        else{
            Variables.bKing.x = 2;
            Variables.bKing.y = 0;
            Variables.blRook.x = 3;
            Variables.blRook.y = 0;
        }
    }
}
