import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameBoard implements ActionListener{
    public JFrame frame;
    public JPanel panel;
    public JButton Startbutton, Restartbutton;
    public static JButton wQueen, wRook, wKnight, wBishop, bQueen, bRook, bKnight, bBishop;
    public static JLabel time1, time2, WhiteWins, BlackWins;
    public int[] promatingPawn = new int[2];
    public static ArrayList<int[]> checkingVector;
    public static Piece checkingPiece;
    public static Piece checkingPiece2;
    public static boolean doubleCheck = false;
    public static boolean checkMate = false;
    public Timer ActualTimer;
    public boolean start = true;
    public static int mouseX, mouseY;

    public GameBoard() {
        panel = new GamePanel();

        frame = new JFrame();

        time1 = new JLabel("5.00",SwingConstants.CENTER);
        time1.setBackground(Color.WHITE);
        time1.setOpaque(true);
        time1.setVisible(true);
        time1.setBounds(610, 150, 200, 50);

        time2 = new JLabel("5.00",SwingConstants.CENTER);
        time2.setBackground(Color.WHITE);
        time2.setOpaque(true);
        time2.setVisible(true);
        time2.setBounds(610, 360, 200, 50);

        BlackWins = new JLabel("BLACK WINS",SwingConstants.CENTER);
        BlackWins.setFont(new Font("Serif", Font.PLAIN, 35));
        BlackWins.setBackground(Color.WHITE);
        BlackWins.setOpaque(true);
        BlackWins.setVisible(false);
        BlackWins.setBounds(140, 210, 280, 140);

        WhiteWins = new JLabel("WHITE WINS",SwingConstants.CENTER);
        WhiteWins.setFont(new Font("Serif", Font.PLAIN, 35));
        WhiteWins.setBackground(Color.WHITE);
        WhiteWins.setOpaque(true);
        WhiteWins.setVisible(false);
        WhiteWins.setBounds(140, 210, 280, 140);

        /*
        textfield1 = new JTextField();
        textfield1.setVisible(true);
        textfield1.setBackground(Color.WHITE);
        textfield1.setOpaque(true);
        textfield1.setBounds(610, 80, 200, 50);
        */

        Startbutton = new JButton();
        Startbutton.setVisible(true);
        Startbutton.setBackground(Color.WHITE);
        Startbutton.setOpaque(true);
        Startbutton.setBounds(610, 220, 200, 50);
        Startbutton.setText("START");
        Startbutton.addActionListener(this);

        Restartbutton = new JButton();
        Restartbutton.setVisible(true);
        Restartbutton.setBackground(Color.WHITE);
        Restartbutton.setOpaque(true);
        Restartbutton.setBounds(610, 290, 200, 50);
        Restartbutton.setText("RESTART");
        Restartbutton.addActionListener(this);

        ImageIcon img1 = new ImageIcon(Variables.imgs[1]);
        wQueen = new JButton(img1);
        wQueen.setVisible(false);
        wQueen.setBackground(Color.WHITE);
        wQueen.setOpaque(true);
        wQueen.setBounds(235, 100, 90, 90);
        wQueen.addActionListener(this);

        ImageIcon img2 = new ImageIcon(Variables.imgs[4]);
        wRook = new JButton(img2);
        wRook.setVisible(false);
        wRook.setBackground(Color.WHITE);
        wRook.setOpaque(true);
        wRook.setBounds(235, 190, 90, 90);
        wRook.addActionListener(this);

        ImageIcon img3 = new ImageIcon(Variables.imgs[3]);
        wKnight= new JButton(img3);
        wKnight.setVisible(false);
        wKnight.setBackground(Color.WHITE);
        wKnight.setOpaque(true);
        wKnight.setBounds(235, 280, 90, 90);
        wKnight.addActionListener(this);

        ImageIcon img4 = new ImageIcon(Variables.imgs[2]);
        wBishop = new JButton(img4);
        wBishop.setVisible(false);
        wBishop.setBackground(Color.WHITE);
        wBishop.setOpaque(true);
        wBishop.setBounds(235, 370, 90, 90);
        wBishop.addActionListener(this);

        ImageIcon img5 = new ImageIcon(Variables.imgs[7]);
        bQueen = new JButton(img5);
        bQueen.setVisible(false);
        bQueen.setBackground(Color.WHITE);
        bQueen.setOpaque(true);
        bQueen.setBounds(235, 100, 90, 90);
        bQueen.addActionListener(this);

        ImageIcon img6 = new ImageIcon(Variables.imgs[10]);
        bRook = new JButton(img6);
        bRook.setVisible(false);
        bRook.setBackground(Color.WHITE);
        bRook.setOpaque(true);
        bRook.setBounds(235, 190, 90, 90);
        bRook.addActionListener(this);

        ImageIcon img7 = new ImageIcon(Variables.imgs[9]);
        bKnight = new JButton(img7);
        bKnight.setVisible(false);
        bKnight.setBackground(Color.WHITE);
        bKnight.setOpaque(true);
        bKnight.setBounds(235, 280, 90, 90);
        bKnight.addActionListener(this);

        ImageIcon img8 = new ImageIcon(Variables.imgs[8]);
        bBishop = new JButton(img8);
        bBishop.setVisible(false);
        bBishop.setBackground(Color.WHITE);
        bBishop.setOpaque(true);
        bBishop.setBounds(235, 370, 90, 90);
        bBishop.addActionListener(this);

        frame.add(Startbutton);
        frame.add(Restartbutton);
        frame.add(BlackWins);
        frame.add(WhiteWins);
        //frame.add(textfield1);
        frame.add(wQueen);
        frame.add(wRook);
        frame.add(wKnight);
        frame.add(wBishop);
        frame.add(bQueen);
        frame.add(bRook);
        frame.add(bKnight);
        frame.add(bBishop);
        frame.add(time1);
        frame.add(time2);
        frame.add(panel);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(new Dimension(860,560));
        frame.setLocationRelativeTo(null);

        frame.addMouseListener(new MyMouse());
    }

    int minutes1 = 5;
    int seconds1 = 0;

    int minutes2 = 5;
    int seconds2 = 0;

    public void stopTimer(Timer timer){
        timer.cancel();
    }

    public Timer startTimer(boolean white){
        Timer timer1 = new Timer();
        TimerTask task1 = new TimerTask(){
            public void run(){
                if(minutes1 == 0 && seconds1 == 0){
                    stopTimer(ActualTimer);
                    Variables.checkmateBlack = true;
                    WhiteWins.setVisible(true);
                }
                if(seconds1 < 10){
                    time1.setText(minutes1 + ":0" + seconds1);
                }
                else{
                    time1.setText(minutes1 + ":" + seconds1);
                }
                if(seconds1 == 0){
                    minutes1--;
                    seconds1 = 60;
                }
                seconds1--;
            }
        };

        Timer timer2 = new Timer();
        TimerTask task2 = new TimerTask(){
            public void run(){
                if(minutes2 == 0 && seconds2 == 0){
                    stopTimer(ActualTimer);
                    Variables.checkmateWhite = true;
                    BlackWins.setVisible(true);
                }
                if(seconds2 < 10){
                    time2.setText(minutes2 + ":0" + seconds2);
                }
                else{
                    time2.setText(minutes2 + ":" + seconds2);
                }
                if(seconds2 == 0){
                    minutes2--;
                    seconds2 = 60;
                }
                seconds2--;
            }
        };
        if(!white){
            timer1.scheduleAtFixedRate(task1,0,1000);
            return timer1;
        }
        else{
            timer2.scheduleAtFixedRate(task2,0,1000);
            return timer2;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Startbutton){
            if(start){
                ActualTimer = startTimer(true);
                start = false;
            }
        }
        else if(e.getSource() == Restartbutton){
            Methods.createPieces();
            Variables.Wcheck = false;
            Variables.Bcheck = false;
            Variables.whiteTurn = true;
            stopTimer(ActualTimer);
            minutes1 = 5;
            minutes2 = 5;
            seconds1 = 0;
            seconds2 = 0;
            start = true;
            WhiteWins.setVisible(false);
            BlackWins.setVisible(false);
            Variables.checkmateBlack = false;
            Variables.checkmateWhite = false;
            time1.setText("5.00");
            time2.setText("5.00");
            frame.repaint();
        }
        else if(e.getSource() == wQueen){
            Queen queen = new Queen(promatingPawn[0], promatingPawn[1], Variables.imgs[1], true);
            Variables.pieces.add(queen);
            queen.move();

            promate(true);
        }
        else if(e.getSource() == bQueen){
            Queen queen = new Queen(promatingPawn[0], promatingPawn[1], Variables.imgs[7], false);
            Variables.pieces.add(queen);
            queen.move();

            promate(false);
        }
        else if(e.getSource() == wRook){
            Rook rook = new Rook(promatingPawn[0], promatingPawn[1], Variables.imgs[4], true);
            Variables.pieces.add(rook);
            rook.move();

            promate(true);
        }
        else if(e.getSource() == bRook){
            Rook rook = new Rook(promatingPawn[0], promatingPawn[1], Variables.imgs[10], false);
            Variables.pieces.add(rook);
            rook.move();
            
            promate(false);
        }
        else if(e.getSource() == wKnight){
            Knight knight = new Knight(promatingPawn[0], promatingPawn[1], Variables.imgs[3], true);
            Variables.pieces.add(knight);
            knight.move();

            promate(true);
        }
        else if(e.getSource() == bKnight){
            Knight knight = new Knight(promatingPawn[0], promatingPawn[1], Variables.imgs[9], false);
            Variables.pieces.add(knight);
            knight.move();

            promate(false);
        }
        else if(e.getSource() == wBishop){
            Bishop bishop = new Bishop(promatingPawn[0], promatingPawn[1], Variables.imgs[2], true);
            Variables.pieces.add(bishop);
            bishop.move();

            promate(true);
        }
        else if(e.getSource() == bBishop){
            Bishop bishop = new Bishop(promatingPawn[0], promatingPawn[1], Variables.imgs[8], false);
            Variables.pieces.add(bishop);
            bishop.move();

            promate(false);
        }
    }

    public void promate(boolean color){
        if(color){
            wQueen.setVisible(false);
            wRook.setVisible(false);
            wKnight.setVisible(false);
            wBishop.setVisible(false);
        }
        else{
            bQueen.setVisible(false);
            bRook.setVisible(false);
            bKnight.setVisible(false);
            bBishop.setVisible(false);
        }
        
        frame.repaint();
    }

    public static void refreshColoredMoves(){
        Variables.whiteMoves.clear();
        Variables.blackMoves.clear();
        for(Piece p : Variables.pieces){
            if(p.isWhite){
                for(int[] i : p.getMoves()){
                    Variables.whiteMoves.add(i);
                }
            }
            else{
                for(int[] i : p.getMoves()){
                    Variables.blackMoves.add(i);
                }
            }
        }
    }

    class MyMouse implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            for(Piece p : Variables.pieces){
                if(p.x == e.getX()/70 && p.y == e.getY()/70){
                    if(Variables.whiteTurn && p.isWhite){
                        Variables.drawedPiece = p;
                    }
                    else if(!(Variables.whiteTurn) && !(p.isWhite)){
                        Variables.drawedPiece = p;
                    }
                }
            }
            if(start){
                Variables.drawedPiece = null;
            }
            for(Piece p : Variables.pieces){
                if(p.x == e.getX()/70 && p.y == e.getY()/70){
                    if(Variables.whiteTurn && p.isWhite){
                        if(Variables.Bcheck){

                            boolean canMove = false;
                            ArrayList<int[]> newMoves = new ArrayList<int[]>();
                            if(!(p instanceof King)){
                                for(int[] i : p.getMoves()){
                                    for(int[] j : checkingVector){
                                        if(i[0] == j[0] && i[1] == j[1]){
                                            canMove = true;
                                            newMoves.add(j);
                                        }
                                    }
                                }
                            }
                            boolean canAttack = false;
                            for(int[] i : p.getMoves()){
                                if(i[0] == checkingPiece.x && i[1] == checkingPiece.y){
                                    canAttack = true;
                                    int[] kk = {checkingPiece.x, checkingPiece.y};
                                    newMoves.add(kk);
                                }
                            }
                            if(canMove || canAttack){
                                p.CheckMoves.clear();
                                p.CheckMoves = newMoves;
                                Variables.holdingPiece = p;
                            }
                            else if(p instanceof King){
                                Variables.holdingPiece = p;
                            }
                            else{
                                Variables.holdingPiece = null;
                            }
                        }
                        else{
                            Variables.holdingPiece = p;
                        }

                    }
                    else if(!Variables.whiteTurn && !p.isWhite){
                        if(Variables.Wcheck){

                            boolean canMove = false;
                            ArrayList<int[]> newMoves = new ArrayList<int[]>();
                            if(!(p instanceof King)){
                                for(int[] i : p.getMoves()){
                                    for(int[] j : checkingVector){
                                        if(i[0] == j[0] && i[1] == j[1]){
                                            canMove = true;
                                            newMoves.add(j);
                                        }
                                    }
                                }
                            }
                            boolean canAttack = false;
                            for(int[] i : p.getMoves()){
                                if(i[0] == checkingPiece.x && i[1] == checkingPiece.y){
                                    canAttack = true;
                                    int[] kk = {checkingPiece.x, checkingPiece.y};
                                    newMoves.add(kk);
                                }
                            }
                            if(canMove || canAttack){
                                p.CheckMoves.clear();
                                p.CheckMoves = newMoves;
                                Variables.holdingPiece = p;
                            }
                            else if(p instanceof King){
                                Variables.holdingPiece = p;
                            }
                            else{
                                Variables.holdingPiece = null;
                            }
                        }
                        else{
                           Variables.holdingPiece = p; 
                        }
                    }
                }
            }
            if(doubleCheck){
                if(!(Variables.holdingPiece instanceof King)){
                    Variables.holdingPiece = null;
                }
            }
            if(start){
                Variables.holdingPiece = null;
            }
            if(Variables.checkmateBlack || Variables.checkmateWhite){
                Variables.holdingPiece = null;
            }
            frame.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Piece piece = null;
            if(Variables.holdingPiece != null){
                piece = Variables.holdingPiece;
                int[] m = {e.getX()/70, e.getY()/70};

                //Castle
                if(piece instanceof King){
                    King king = (King) piece;
                    if((m[0] == 6 && m[1] == 7) || (m[0] == 6 && m[1] == 0)){
                        if(king.canShortCastle()){
                            king.shortCastle();

                            //Timer
                            if(king.isWhite){
                                stopTimer(ActualTimer);
                                ActualTimer = startTimer(false);
                            }
                            else{
                                stopTimer(ActualTimer);
                                ActualTimer = startTimer(true);
                            }

                            king.counter += 1;
                            Variables.whiteTurn = !Variables.whiteTurn;
                            Variables.holdingPiece = null;
                            
                        }
                    }
                    else if((m[0] == 2 && m[1] == 7) || (m[0] == 2 && m[1] == 0)){
                        if(king.canLongCastle()){
                            king.longCastle();

                            king.counter += 1;
                            Variables.whiteTurn = !Variables.whiteTurn;
                            Variables.holdingPiece = null;
                        }
                    }
                }
                ArrayList<int[]> moves = new ArrayList<int[]>();
                if(Variables.Wcheck || Variables.Bcheck){
                    if(piece instanceof King){
                        moves = piece.getMoves();
                    }
                    else{
                        moves = piece.getCheckMoves();
                    }
                }
                else{
                    moves = piece.getMoves();
                }

                int[] enPassantSquare = null;
                if(piece instanceof Pawn){
                    Pawn pawn = (Pawn) piece;
                    int[] mm = pawn.EnPassant();
                    enPassantSquare = mm;
                }

                for(int[] i : moves){
                    if(i[0] == m[0] && i[1] == m[1]){
                        piece.x = m[0];
                        piece.y = m[1];

                        refreshColoredMoves();

                        //Pawn getting promate
                        if(piece instanceof Pawn){
                            Pawn pawn = (Pawn)piece;
                            pawn.promate();
                            int[] xy = {pawn.x, pawn.y};
                            promatingPawn = xy;
                        }

                        //EnPassant
                        if(piece instanceof Pawn){
                            Pawn pawn = (Pawn) piece;
                            if(enPassantSquare != null){
                                if(piece.x == enPassantSquare[0] && piece.y == enPassantSquare[1]){
                                    Variables.pieces.remove(pawn.EnPassantPiece);
                                }
                            }
                        }

                        //Removing attacked piece
                        Piece removePiece = null;
                        for(Piece p : Variables.pieces){
                            if(piece.isWhite != p.isWhite){
                                if(p.x == piece.x && p.y == piece.y){
                                    removePiece = p;
                                }
                            }
                        }
                        if(removePiece != null){
                            Variables.pieces.remove(removePiece);
                        }

                        //Check control
                        piece.getMoves();
                        int x = 0;
                        for(Piece p : Variables.pieces){
                            if(p.isChecking){
                                if(x == 0){
                                    checkingPiece = p;
                                    doubleCheck = false;
                                    x += 1;
                                }
                                else{
                                    checkingPiece2 = p;
                                    doubleCheck = true;
                                }
                                if(checkingPiece.isWhite){
                                    Variables.Wcheck = true;
                                }
                                else{
                                    Variables.Bcheck = true;
                                }

                                for(ArrayList<int[]> vector : checkingPiece.vectors){
                                    for(int[] k : vector){
                                        if(k[0] == 99 && k[1] == 99){
                                            checkingVector = vector;
                                            break;
                                        }
                                    }
                                }
                                if(checkingPiece.isWhite){
                                    boolean isCheckMate = true;
                                    for(Piece pi : Variables.pieces){
                                        if(!pi.isWhite){
                                            if(pi.CheckPlay()){
                                                isCheckMate = false;
                                            }
                                        }
                                    }

                                    Variables.bKing.move();
                                    if(isCheckMate && Variables.bKing.getMoves().size() == 0){
                                        Variables.checkmateWhite = true;
                                        WhiteWins.setVisible(true);
                                    }
                                }
                                else{
                                    boolean isCheckMate = true;
                                    for(Piece pi : Variables.pieces){
                                        if(pi.isWhite){
                                            if(pi.CheckPlay()){
                                                isCheckMate = false;
                                            }
                                        }
                                    }

                                    Variables.wKing.move();
                                    if(isCheckMate && Variables.wKing.getMoves().size() == 0){
                                        Variables.checkmateBlack = true;
                                        BlackWins.setVisible(true);
                                    }
                                }
                            }
                        }

                        //Timer
                        if(piece.isWhite){
                            stopTimer(ActualTimer);
                            ActualTimer = startTimer(false);
                        }
                        else{
                            stopTimer(ActualTimer);
                            ActualTimer = startTimer(true);
                        }
                        if(Variables.checkmateBlack || Variables.checkmateWhite){
                            stopTimer(ActualTimer);
                        }

                        if(piece.isWhite){
                            Variables.Bcheck = false;
                        }
                        else{
                            Variables.Wcheck = false;
                        }
                        
                        piece.counter += 1;
                        Variables.whiteTurn = !Variables.whiteTurn;
                        Variables.holdingPiece = null;
                        Variables.drawedPiece = null;
                        
                        break;
                    }  
                }
            }
            frame.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }
    }
}
