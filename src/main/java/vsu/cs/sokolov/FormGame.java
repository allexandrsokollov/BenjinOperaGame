package vsu.cs.sokolov;

import util.JTableUtils;
import vsu.cs.sokolov.entities.Field;
import vsu.cs.sokolov.entities.Game;
import vsu.cs.sokolov.entities.Point;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormGame extends JFrame{
    private static final int DEFAULT_CELL_SIZE = 50;

    private Game game;

    private JPanel panelMain;
    private JPanel Options;
    private JButton buttonStartNewGame;
    private JLabel labelScore;
    private JTable tableGame;

    Point pointDrag;
    Point pointDrop;


    public FormGame() {
        this.setTitle("Beijing Opera");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();

        startNewGame();

        tableGame.setRowHeight(DEFAULT_CELL_SIZE);
        JTableUtils.initJTableForArray(tableGame, DEFAULT_CELL_SIZE,
                false, false, false, false);
        tableGame.setIntercellSpacing(new Dimension(1, 1));
        tableGame.setEnabled(false);
        JTableUtils.resizeJTable(tableGame, Field.getDefaultFieldSize(), Field.getDefaultFieldSize(),
                DEFAULT_CELL_SIZE, DEFAULT_CELL_SIZE);

        tableGame.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            final class DrawComponent extends Component {
                private int row = 0, column = 0;

                @Override
                public void paint(Graphics gr) {
                    Graphics2D g2d = (Graphics2D) gr;
                    paintCell(row, column, g2d);
                }
            }

            final DrawComponent comp = new DrawComponent();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                comp.row = row;
                comp.column = column;
                return comp;
            }
        });
        buttonStartNewGame.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
//        tableGame.addMouseListener(new MouseAdapter() {
//            /**
//             * {@inheritDoc}
//             *
//             * @param e
//             * @since 1.6
//             */
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                int rowDrag = tableGame.rowAtPoint(e.getPoint());
//                int columnDrag = tableGame.columnAtPoint(e.getPoint());
//                Point dragPoint = game.getField().getPointOn(rowDrag, columnDrag);
//
//                int rowDrop = tableGame.rowAtPoint(e);
//                int columnDrop = tableGame.columnAtPoint(e.getPoint());
//                Point dropPoint = game.getField().getPointOn(rowDrop, columnDrop);
//
//
//                super.mouseDragged(e);
//            }
//        });

        tableGame.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {

                int rowDrag = tableGame.rowAtPoint(e.getPoint());
                int columnDrag = tableGame.columnAtPoint(e.getPoint());
                pointDrag = game.getField().getPointOn(rowDrag, columnDrag);

                super.mouseClicked(e);
            }
        });

        tableGame.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                if (pointDrag != null ) {
                    if (pointDrag.isPointBeside(pointDrop)) {

                        int rowDrop = tableGame.rowAtPoint(e.getPoint());
                        int columnDrop = tableGame.columnAtPoint(e.getPoint());
                        pointDrop = game.getField().getPointOn(rowDrop, columnDrop);


                        super.mouseReleased(e);
                    }
                }
            }
        });
    }

    private void paintCell(int row, int column, Graphics2D graphics2D) {
        switch (game.getField().getPointOn(column, row).getColor()) {
            case RED -> {
                graphics2D.setColor(Color.RED);
                graphics2D.fillRect(0,0, DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5);
                graphics2D.drawRoundRect(0,0,
                        DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5, 5 , 5);
            }

            case BLUE -> {
                graphics2D.setColor(Color.BLUE);
                graphics2D.fillRect(0,0, DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5);
                graphics2D.drawRoundRect(0,0,
                        DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5, 5 , 5);
            }

            case ORANGE -> {
                graphics2D.setColor(Color.ORANGE);
                graphics2D.fillRect(0,0, DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5);
                graphics2D.drawRoundRect(0,0,
                        DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5, 5 , 5);
            }
            case BLACK -> {
                graphics2D.setColor(Color.BLACK);
                graphics2D.fillRect(0,0, DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5);
                graphics2D.drawRoundRect(0,0,
                        DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5, 5 , 5);
            }
            case PURPLE -> {
                graphics2D.setColor(Color.MAGENTA);
                graphics2D.fillRect(0,0, DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5);
                graphics2D.drawRoundRect(0,0,
                        DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5, 5 , 5);
            }
            case YELLOW -> {
                graphics2D.setColor(Color.YELLOW);
                graphics2D.fillRect(0,0, DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5);
                graphics2D.drawRoundRect(0,0,
                        DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5, 5 , 5);
            }


        }

        updateField();
    }

    private void updateField() {tableGame.repaint();}

    private void startNewGame() {
        game = new Game();

        boolean stillNotReady = true;

        while (stillNotReady) {
            if (!game.replacePoints()) {
                stillNotReady = false;
            }
        }
        tableGame.repaint();
    }
}
