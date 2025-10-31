/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Juego extends JFrame {

    private JTextField[][] Tablero = new JTextField[9][9];
    private boolean[][] fijo = new boolean[9][9];
    private Sudoku logicaSudoku;
    private int nivel;

    public Juego(int nivel) {
        this.nivel = nivel;
        logicaSudoku = new Sudoku(9);

        setTitle("CLOUD SUDOKU");
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(170, 210, 255));
        panelTitulo.setPreferredSize(new Dimension(600, 80));
        panelTitulo.setLayout(new BorderLayout());
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JLabel titulo = new JLabel("CLOUD SUDOKU :D", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(0, 70, 140));
        panelTitulo.add(titulo, BorderLayout.CENTER);
        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelSudoku = new JPanel();
        panelSudoku.setLayout(new GridLayout(9, 9));
        panelSudoku.setBackground(new Color(170, 210, 255));

        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                JTextField campo = new JTextField();
                campo.setHorizontalAlignment(JTextField.CENTER);
                campo.setFont(new Font("Arial", Font.BOLD, 20));
                campo.setBackground(new Color(220, 240, 255));
                campo.setBorder(bordeCelda(fila, col));
                Tablero[fila][col] = campo;
                panelSudoku.add(campo);

                int finalFila = fila;
                int finalCol = col;

                campo.addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusLost(java.awt.event.FocusEvent e) {
                        if (fijo[finalFila][finalCol]) return;

                        String txt = campo.getText();
                        if (!txt.isEmpty()) {
                            try {
                                int valor = Integer.parseInt(txt);
                                if (valor < 1 || valor > 9) throw new NumberFormatException();

                                if (!logicaSudoku.esMovimientoValidoEn(finalFila, finalCol, valor)) {
                                    campo.setText("");
                                    JOptionPane.showMessageDialog(null,
                                            "Número inválido en la posición (" + (finalFila + 1) + "," + (finalCol + 1) + ")",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    logicaSudoku.Tablero[finalFila][finalCol] = valor;
                                    fijo[finalFila][finalCol] = true;
                                    campo.setEditable(false);
                                }

                            } catch (NumberFormatException ex) {
                                campo.setText("");
                                JOptionPane.showMessageDialog(null,
                                        "Debes ingresar un número del 1 al 9",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            logicaSudoku.Tablero[finalFila][finalCol] = 0;
                        }
                    }
                });
            }
        }
        add(panelSudoku, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(170, 210, 255));

        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 18));
        btnSalir.setForeground(new Color(0, 70, 140));
        btnSalir.setBackground(new Color(220, 240, 255));
        btnSalir.setFocusPainted(false);
        panelBotones.add(btnSalir);

        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setFont(new Font("Arial", Font.BOLD, 18));
        btnReiniciar.setForeground(new Color(0, 70, 140));
        btnReiniciar.setBackground(new Color(220, 240, 255));
        btnReiniciar.setFocusPainted(false);
        panelBotones.add(btnReiniciar);

        JButton btnResolver = new JButton("Resolver");
        btnResolver.setFont(new Font("Arial", Font.BOLD, 18));
        btnResolver.setForeground(new Color(0, 70, 140));
        btnResolver.setBackground(new Color(220, 240, 255));
        btnResolver.setFocusPainted(false);
        panelBotones.add(btnResolver);

        JButton btnValidar = new JButton("Validar");
        btnValidar.setFont(new Font("Arial", Font.BOLD, 18));
        btnValidar.setForeground(new Color(0, 70, 140));
        btnValidar.setBackground(new Color(220, 240, 255));
        btnValidar.setFocusPainted(false);
        panelBotones.add(btnValidar);

        add(panelBotones, BorderLayout.SOUTH);

        btnSalir.addActionListener(e -> {
            this.dispose();
            new Menu().setVisible(true);
        });

        btnReiniciar.addActionListener(e -> {
            generarSudokuPorNivel();
        });

        btnResolver.addActionListener(e -> {
            if (logicaSudoku.Resolver()) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        Tablero[i][j].setText(String.valueOf(logicaSudoku.Tablero[i][j]));
                        Tablero[i][j].setEditable(false);
                    }
                }
                JOptionPane.showMessageDialog(this, "FELICIDADES SUDOKU RESUELTO!!!", "Listo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se puede resolver", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnValidar.addActionListener(e -> {
            boolean completo = true;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String txt = Tablero[i][j].getText().trim();
                    if (txt.equals("")) {
                        completo = false;
                        break;
                    }
                    int valor = Integer.parseInt(txt);
                    if (!logicaSudoku.esMovimientoValidoEn(i, j, valor)) {
                        completo = false;
                        break;
                    }
                }
                if (!completo) break;
            }
            if (completo) {
                JOptionPane.showMessageDialog(this, "¡Sudoku correcto! Felicidades", "Listo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Faltan números o hay errores en el tablero", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        generarSudokuPorNivel();
    }

    private Border bordeCelda(int fila, int col) {
        int top = 1, left = 1, bottom = 1, right = 1;
        if (fila % 3 == 0) top = 3;
        if (col % 3 == 0) left = 3;
        if (fila == 8) bottom = 3;
        if (col == 8) right = 3;
        return BorderFactory.createMatteBorder(top, left, bottom, right, new Color(0, 70, 140));
    }

    private void generarSudokuPorNivel() {
        logicaSudoku.generarCompleto();

        int visibles;
        switch (nivel) {
            case 1: 
                visibles = 40; 
                break;
            case 2: 
                visibles = 30; 
                break;
            case 3: 
                visibles = 22; 
                break;
            default: 
                visibles = 40; 
                break;
        }

        ArrayList<int[]> todasCeldas = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                todasCeldas.add(new int[]{i, j});

        Collections.shuffle(todasCeldas);

        for (int k = 0; k < 81 - visibles; k++) {
            int[] celda = todasCeldas.get(k);
            logicaSudoku.Tablero[celda[0]][celda[1]] = 0;
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (logicaSudoku.Tablero[i][j] != 0) {
                    Tablero[i][j].setText(String.valueOf(logicaSudoku.Tablero[i][j]));
                    Tablero[i][j].setEditable(false);
                    fijo[i][j] = true;
                } else {
                    Tablero[i][j].setText("");
                    Tablero[i][j].setEditable(true);
                    fijo[i][j] = false;
                }
            }
        }
    }
}
