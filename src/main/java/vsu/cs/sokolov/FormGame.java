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

    private int time = 10;
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

        tableGame.setRowHeight(DEFAULT_CELL_SIZE);
        JTableUtils.initJTableForArray(tableGame, DEFAULT_CELL_SIZE,
                false, false, false, false);
        tableGame.setIntercellSpacing(new Dimension(1, 1));
        tableGame.setEnabled(false);
        JTableUtils.resizeJTable(tableGame, Field.getDefaultFieldSize(), Field.getDefaultFieldSize(),
                DEFAULT_CELL_SIZE, DEFAULT_CELL_SIZE);

        if (game != null) {
            labelScore.setText(String.valueOf(game.getAmountOfNewPoints()));
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

                    if (pointDrag.isPointBeside(pointDrop)) {

                        game.tryToSwitchPoints(pointDrag, pointDrop);

                        wasItClicked = false;
                        pointDrag = null;
                        pointDrop = null;
                        updateField();
                        labelScore.setText(String.valueOf(game.getAmountOfNewPoints()));

                        if (game.getAmountOfNewPoints() <= 0) {
                            SwingUtils.showInfoMessageBox("You win");
                        }
                        super.mouseReleased(e);
                    }
                }
            }
        });

        spinner1.addComponentListener(new ComponentAdapter() {
            /**
             * Invoked when the component's size changes.
             *
             * @param e
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
            switch (game.getField().getPointOn(column, row).getColor()) {
                case RED -> {
                    graphics2D.setColor(Color.RED);
                    graphics2D.fillRect(0, 0, DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5);
                    graphics2D.drawRoundRect(0, 0,
                            DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5, 5, 5);
                }

                case BLUE -> {
                    graphics2D.setColor(Color.BLUE);
                    graphics2D.fillRect(0, 0, DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5);
                    graphics2D.drawRoundRect(0, 0,
                            DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5, 5, 5);
                }

                case ORANGE -> {
                    graphics2D.setColor(Color.ORANGE);
                    graphics2D.fillRect(0, 0, DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5);
                    graphics2D.drawRoundRect(0, 0,
                            DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5, 5, 5);
                }
                case BLACK -> {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.fillRect(0, 0, DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5);
                    graphics2D.drawRoundRect(0, 0,
                            DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5, 5, 5);
                }
                case PURPLE -> {
                    graphics2D.setColor(Color.MAGENTA);
                    graphics2D.fillRect(0, 0, DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5);
                    graphics2D.drawRoundRect(0, 0,
                            DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5, 5, 5);
                }
                case YELLOW -> {
                    graphics2D.setColor(Color.YELLOW);
                    graphics2D.fillRect(0, 0, DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5);
                    graphics2D.drawRoundRect(0, 0,
                            DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5, 5, 5);
                }


            }
        } else {
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(0, 0, DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5);
            graphics2D.drawRoundRect(0, 0,
                    DEFAULT_CELL_SIZE - 5, DEFAULT_CELL_SIZE - 5, 5, 5);
        }

        updateField();
    }

    private void updateField() {tableGame.repaint();}

    private void startNewGame() {
        timer.start();
        game = new Game((Integer) spinner1.getValue());
        labelScore.setText(String.valueOf(game.getAmountOfNewPoints()));

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
