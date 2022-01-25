package vsu.cs.sokolov;

import util.JTableUtils;
import util.SwingUtils;
import vsu.cs.sokolov.entities.Field;
import vsu.cs.sokolov.entities.Game;
import vsu.cs.sokolov.entities.Point;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;

public class FormGame extends JFrame{
    private static final int DEFAULT_CELL_SIZE = 50;

    private Game game;

    private JPanel panelMain;
    private JPanel Options;
    private JButton buttonStartNewGame;
    private JLabel labelScore;
    private JTable tableGame;
    private JSpinner spinner1;
    private JLabel labelTimeLeft;

    private Point pointDrag;
    private Point pointDrop;

    private boolean wasItClicked = false;

    private int time = 60;
    private final Timer timer = new Timer(1000, e -> {
        time--;
        this.labelTimeLeft.setText(String.valueOf(time));

        if (time <= 0) {
            SwingUtils.showInfoMessageBox("You have lose");
            stopTimer();
            game = null;
            tableGame.repaint();
        }
    });

    public FormGame() {
        this.setTitle("Beijing Opera");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();

        spinner1.setValue(1);

        Options.setVisible(true);

        labelTimeLeft.setText(String.valueOf(time));

        tableGame.setRowHeight(DEFAULT_CELL_SIZE);
        JTableUtils.initJTableForArray(tableGame, DEFAULT_CELL_SIZE,
                false, false, false, false);
        tableGame.setIntercellSpacing(new Dimension(1, 1));
        tableGame.setEnabled(false);
        JTableUtils.resizeJTable(tableGame, Field.getDefaultFieldSize(), Field.getDefaultFieldSize(),
                DEFAULT_CELL_SIZE, DEFAULT_CELL_SIZE);

        if (game != null) {
            labelScore.setText(String.valueOf(game.getScore()));
        }

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

        tableGame.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mousePressed(MouseEvent e) {

                game.setGameIsGoing(true);

                if (pointDrag != null) {
                    wasItClicked = true;
                } else {
                    int rowDrag = tableGame.rowAtPoint(e.getPoint());
                    int columnDrag = tableGame.columnAtPoint(e.getPoint());
                    pointDrag = game.getField().getPointOn(rowDrag, columnDrag);

                    System.out.println("drag " + pointDrag + "  row " + rowDrag + "  colmn " + columnDrag);

                    super.mouseClicked(e);
                }
            }
        });

        tableGame.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if (pointDrag != null && wasItClicked) {

                    int rowDrop = tableGame.rowAtPoint(e.getPoint());
                    int columnDrop = tableGame.columnAtPoint(e.getPoint());
                    pointDrop = game.getField().getPointOn(rowDrop, columnDrop);

                    System.out.println("drop " + pointDrop + "  row " + rowDrop + "  colmn " + columnDrop);
                    updateField();
                    if (pointDrag.isPointBeside(pointDrop)) {

                        game.switchPoints(pointDrag, pointDrop);

                        wasItClicked = false;
                        pointDrag = null;
                        pointDrop = null;
                        updateField();
                        labelScore.setText(String.valueOf(game.getScore()));

                        if (game.getAmountOfNewPoints() <= 0) {
                            SwingUtils.showInfoMessageBox("You win");
                            game = null;
                            tableGame.repaint();
                        }
                        super.mouseReleased(e);
                    } else {
                        pointDrop = null;
                    }
                }
            }
        });

        spinner1.addComponentListener(new ComponentAdapter() {
            /**
             * Invoked when the component's size changes.
             *
             */
            @Override
            public void componentResized(ComponentEvent e) {
                int value = (int) spinner1.getValue();

                if (value < 1) {
                   spinner1.setValue(1);
                }

                game.setLevel(value);
                super.componentResized(e);
            }
        });
    }

    private void paintCell(int row, int column, Graphics2D graphics2D) {
        if (game != null) {
            switch (game.getField().getPointOn(row, column).getColor()) {
                case RED -> graphics2D.setColor(Color.RED);

                case BLUE -> graphics2D.setColor(Color.BLUE);

                case ORANGE -> graphics2D.setColor(Color.ORANGE);

                case BLACK -> graphics2D.setColor(Color.BLACK);

                case DELETED -> graphics2D.setColor(Color.DARK_GRAY);

                case PURPLE -> graphics2D.setColor(Color.MAGENTA);

                case YELLOW -> graphics2D.setColor(Color.YELLOW);
            }
        } else {
            graphics2D.setColor(Color.WHITE);
        }

        graphics2D.fillRect(3, 3, DEFAULT_CELL_SIZE - 6, DEFAULT_CELL_SIZE - 6);

        if (pointDrag != null && pointDrag.getRowIndex() == row && pointDrag.getColumnIndex() == column) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.fillOval(19, 19, 10, 10);
        }

        updateField();
    }

    private void updateField() {tableGame.repaint();}

    private void startNewGame() {
        labelTimeLeft.setText("60");
        time = 60000;
        timer.start();
        game = new Game((Integer) spinner1.getValue());
        labelScore.setText(String.valueOf(game.getScore()));
        pointDrop = null;
        pointDrag = null;

        int amountOfReplacedPoints = 100;

        while (amountOfReplacedPoints != 0) {
            amountOfReplacedPoints = game.replacePoints();
        }
        tableGame.repaint();
    }

    private void stopTimer() {
        timer.stop();
    }
}
