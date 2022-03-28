import java.awt.Image;
import java.util.ArrayList;

public class Piece{
    public int x;
    public int y;
    public Image image;
    public boolean isWhite;
    public ArrayList<int[]> moves = new ArrayList<int[]>();
    public ArrayList<int[]> CheckMoves = new ArrayList<int[]>();
    public ArrayList<int[]> newMoves = new ArrayList<int[]>();
    public boolean isChecking = false;
    public int counter = 0;
    public ArrayList<ArrayList<int[]>> vectors = new ArrayList<>();
    public ArrayList<Piece> protectedPieces = new ArrayList<Piece>();

    public Piece(int x, int y, Image image, boolean isWhite){
        this.x = x;
        this.y = y;
        this.image = image;
        this.isWhite = isWhite;
    }

    public ArrayList<int[]> getMoves(){
        return null;
    }

    public ArrayList<int[]> getCheckMoves(){
        return CheckMoves;
    }

    public ArrayList<Piece> getProtectedPieces(){
        return null;
    }

    public void move(){
    }

    public boolean CheckPlay(){
        boolean canMove = false;
        if(!(this instanceof King)){
            for(int[] i : this.getMoves()){
                if(GameBoard.checkingVector != null){
                    for(int[] j : GameBoard.checkingVector){
                        if(i[0] == j[0] && i[1] == j[1]){
                            canMove = true;
                            newMoves.add(j);
                        }
                    }
                }
            }
        }
        boolean canAttack = false;
        for(int[] i : this.getMoves()){
            if(i[0] == GameBoard.checkingPiece.x && i[1] == GameBoard.checkingPiece.y){
                canAttack = true;
                int[] kk = {GameBoard.checkingPiece.x, GameBoard.checkingPiece.y};
                newMoves.add(kk);
            }
        }
        return (canMove || canAttack);
    }

    public void pinnedMoves(){
        if(isWhite){
            ArrayList<Piece> blackPieces = new ArrayList<>();
            ArrayList<int[]> cancelMoves = new ArrayList<>();
            for(Piece i : Variables.pieces){
                if(!i.isWhite){
                    blackPieces.add(i);
                }
            }

            for(int[] i : this.moves){
                int oldX = this.x;
                int oldY = this.y;
                boolean cancel = false;
                this.x = i[0];
                this.y = i[1];

                for(Piece piece : blackPieces){
                    piece.move();
                    if(piece.isChecking){
                        cancel = true;
                        if(piece.x == this.x && piece.y == this.y){
                            cancel = false;
                        }
                    }
                }
                if(cancel){
                    cancelMoves.add(i);
                }

                this.x = oldX;
                this.y = oldY;
            }
            for(int[] i : cancelMoves){
                moves.remove(i);
            }

        }
        else{
            ArrayList<Piece> whitePieces = new ArrayList<>();
            ArrayList<int[]> cancelMoves = new ArrayList<>();
            for(Piece i : Variables.pieces){
                if(i.isWhite){
                    whitePieces.add(i);
                }
            }

            for(int[] i : this.moves){
                int oldX = this.x;
                int oldY = this.y;
                boolean cancel = false;
                this.x = i[0];
                this.y = i[1];

                for(Piece piece : whitePieces){
                    piece.move();
                    if(piece.isChecking){
                        cancel = true;
                        if(piece.x == this.x && piece.y == this.y){
                            cancel = false;
                        }
                    }
                }
                if(cancel){
                    cancelMoves.add(i);
                }

                this.x = oldX;
                this.y = oldY;
            }
            for(int[] i : cancelMoves){
                moves.remove(i);
            }
        }
    }
}
