import java.util.ArrayList;
import java.awt.Image;

public class Pawn extends Piece{
    public Piece EnPassantPiece = null;
    public Pawn(int x, int y, Image image, boolean isWhite){
        super(x, y, image, isWhite);
    }
    
    public void move(){
        int[][] squares;
        if(counter == 0){
            if(isWhite){
                int[][] m = {{0,-1},{0,-2}};
                squares = m;
            }
            else{
                int[][] m = {{0,1},{0,2}};
                squares = m;
            }
        }
        else{
            if(isWhite){
                int[][] m = {{0,-1}};
                squares = m;
            }
            else{
                int[][] m = {{0,1}};
                squares = m;
            }
        }
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
                break;
            }
            moves.add(m);
        }
    }

    public boolean canEnpassant(){
        if(this.isWhite){
            if(this.counter == 1 && this.y == 4){
                return true;
            }
        }
        else{
            if(this.counter == 1 && this.y == 3){
                return true;
            }
        }
        return false;
    }

    public int[] EnPassant(){
        for(Piece p : Variables.pieces){
            if((p.x == this.x - 1 && p.y == this.y) || (p.x == this.x + 1 && p.y == this.y)){
                if(p instanceof Pawn){
                    Pawn pawn = (Pawn) p;
                    if(pawn.canEnpassant()){
                        if(p.isWhite != this.isWhite){
                            if(this.isWhite){
                                int[] m = {p.x, p.y - 1};
                                moves.add(m);
                                EnPassantPiece = p;
                                return m;
                            }
                            else{
                                int[] m = {p.x, p.y + 1};
                                moves.add(m);
                                EnPassantPiece = p;
                                return m;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public void attack(){
        if(isWhite){
            int[][] m = {{x+1,y-1},{x-1,y-1}};
            boolean anyKing = false;
            for(int[] i : m){
                if(i[0] > 7 || i[0] < 0 || i[1] > 7 || i[1] < 0){
                    continue;
                }
                for(Piece p : Variables.pieces){
                    if(p.x == i[0] && p.y == i[1]){
                        if(p.isWhite == false){
                            if(p instanceof King){
                                isChecking = true;
                                anyKing = true;
                                continue;
                            }
                            else{
                                moves.add(i);
                            }
                        }
                        else{
                            protectedPieces.add(p);
                        }
                    }
                }
                if(!anyKing){
                    isChecking = false;
                }
            }
        }
        else if(!isWhite){
            int[][] m = {{x+1,y+1},{x-1,y+1}};
            boolean anyKing = false;
            for(int[] i : m){
                if(i[0] > 7 || i[0] < 0 || i[1] > 7 || i[1] < 0){
                    continue;
                }
                for(Piece p : Variables.pieces){
                    if(p.x == i[0] && p.y == i[1]){
                        if(p.isWhite == true){
                            if(p instanceof King){
                                isChecking = true;
                                anyKing = true;
                            }
                            else{
                                moves.add(i);
                            }
                        }
                        else{
                            protectedPieces.add(p);
                        }
                    }
                }
                if(!anyKing){
                    isChecking = false;
                }
            }
        }
    }

    public ArrayList<int[]> checkSquares(){
        ArrayList<int[]> a = new ArrayList<>();
        if(isWhite){
            int[] first = {x+1,y-1};
            int[] second = {x-1,y-1};
            a.add(first);
            a.add(second);
        }
        else{
            int[] first = {x+1,y+1};
            int[] second = {x-1,y+1};
            a.add(first);
            a.add(second);
        }
        return a;
    }

    public void promate(){
        if(isWhite && y == 0){
            GameBoard.wQueen.setVisible(true);
            GameBoard.wRook.setVisible(true);
            GameBoard.wKnight.setVisible(true);
            GameBoard.wBishop.setVisible(true);
            Variables.pieces.remove(this);
        }
        else if(!isWhite && y == 7){
            GameBoard.bQueen.setVisible(true);
            GameBoard.bRook.setVisible(true);
            GameBoard.bKnight.setVisible(true);
            GameBoard.bBishop.setVisible(true);
            Variables.pieces.remove(this);
        }
    }

    @Override
    public ArrayList<int[]> getMoves(){
        moves.clear();
        move();
        attack();
        pinnedMoves();
        EnPassant();
        return moves;
    }

    @Override
    public ArrayList<Piece> getProtectedPieces(){
        protectedPieces.clear();
        attack();
        return protectedPieces;
    }
}
