import java.util.ArrayList;
import java.awt.Image;

public class Knight extends Piece{
    public Knight(int x, int y, Image image, boolean isWhite){
        super(x, y, image, isWhite);
    }

    public void move(){
        int[][] squares = {{1,2},{-1,2},{2,1},{-2,1},{2,-1},{-2,-1},{1,-2},{-1,-2}};
        boolean anyKing = false;

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
                        isChecking = true;
                        anyKing = true;
                        continue;
                    }
                    moves.add(m);
                    continue;
                }
            }
            if(!anyKing){
                isChecking = false;
            }
            moves.add(m);
        }
    }

    @Override
    public ArrayList<int[]> getMoves(){
        moves.clear();
        move();
        pinnedMoves();
        return moves;
    }

    @Override
    public ArrayList<Piece> getProtectedPieces(){
        protectedPieces.clear();
        move();
        return protectedPieces;
    }
}
