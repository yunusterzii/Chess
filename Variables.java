import java.util.ArrayList;
import java.awt.Image;

public class Variables {
    public static Image[] imgs = new Image[12];
    public static boolean Wcheck = false;
    public static boolean Bcheck = false;
    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> BlackPieces = new ArrayList<>();
    public static ArrayList<Piece> WhitePieces = new ArrayList<>();
    public static ArrayList<int[]> checkingVector = new ArrayList<int[]>();
    public static ArrayList<int[]> whiteMoves = new ArrayList<int[]>();
    public static ArrayList<int[]> blackMoves = new ArrayList<int[]>();
    public static Piece holdingPiece = null;
    public static Piece drawedPiece = null;
    public static boolean whiteTurn = true;
    public static Piece promatePiece = null;
    public static boolean checkmateWhite = false;
    public static boolean checkmateBlack = false;
    public static Rook wrRook, wlRook, brRook, blRook;
    public static King wKing, bKing;
    public static Image wQ, bQ;
}
