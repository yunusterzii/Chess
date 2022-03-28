import java.util.ArrayList;
import java.awt.Image;

public class Queen extends Piece{
    public Queen(int x, int y, Image image, boolean isWhite){
        super(x, y, image, isWhite);
    }

    @Override
    public void move(){
        int[][] squares = {{0,-1},{0,1},{1,0},{-1,0},{1,-1},{1,1},{-1,1},{-1,-1}};
        boolean anyKing = false;

        for(int[] i : squares){
            ArrayList<int[]> vector = new ArrayList<>();
            int new_x = x;
            int new_y = y;
            while(true){
                new_x += i[0];
                new_y += i[1];
                int m[] = {new_x,new_y};
                if(new_x > 7 || new_x < 0 || new_y > 7 || new_y < 0 ){
                    break;
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
                        break;
                    }
                    else{
                        if(a instanceof King){
                            isChecking = true;
                            anyKing = true;
                            int[] cm = {99,99};
                            vector.add(cm);
                            continue;
                        }
                        moves.add(m);
                        vector.add(m);
                        break;
                    }
                }
                if(!anyKing){
                    isChecking = false;
                }
                moves.add(m);
                vector.add(m);
            }
            vectors.add(vector);
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
