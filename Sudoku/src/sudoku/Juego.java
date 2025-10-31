/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Juego extends JFrame {

    private JTextField[][] Tablero = new JTextField[9][9];
    private Sudoku logicaSudoku;
    private int nivel;

    public Juego(int nivel) {
        this.nivel = nivel;
        logicaSudoku = new Sudoku(9); // tablero 9x9

        setTitle("Sudoku");
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // -------------------- PANEL TITULO --------------------
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(170, 210, 255));
        panelTitulo.setPreferredSize(new Dimension(600, 80));
        panelTitulo.setLayout(new BorderLayout());
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JLabel titulo = new JLabel("SUDOKU", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(0, 70, 140));
        panelTitulo.add(titulo, BorderLayout.CENTER);
        add(panelTitulo, BorderLayout.NORTH);

        // -------------------- PANEL SUDOKU --------------------
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
                String txt = campo.getText();
                if (!txt.isEmpty()) {
                    try {
                        int valor = Integer.parseInt(txt);
                        logicaSudoku.Tablero[finalFila][finalCol] = valor;

                        if (!logicaSudoku.MovimientoValido()) {
                            campo.setText("");
                            logicaSudoku.Tablero[finalFila][finalCol] = 0;
                            JOptionPane.showMessageDialog(null, "Número inválido en la posición (" + (finalFila+1) + "," + (finalCol+1) + ")", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        campo.setText("");
                        logicaSudoku.Tablero[finalFila][finalCol] = 0;
                        JOptionPane.showMessageDialog(null, "Debes ingresar un número del 1 al 9", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
            logicaSudoku.Tablero[finalFila][finalCol] = 0;
                }
                }
                });
            }
        }

        add(panelSudoku, BorderLayout.CENTER);

        // -------------------- PANEL BOTONES --------------------
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(170, 210, 255));

        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 18));
        btnSalir.setForeground(new Color(0, 70, 140)); 
        btnSalir.setBackground(new Color(220, 240, 255));
        btnSalir.setFocusPainted(false);
        panelBotones.add(btnSalir);

        JButton btnResolver = new JButton("Resolver");
        btnResolver.setFont(new Font("Arial", Font.BOLD, 18));
        btnResolver.setForeground(new Color(0, 70, 140));
        btnResolver.setBackground(new Color(220, 240, 255));
        btnResolver.setFocusPainted(false);
        panelBotones.add(btnResolver);

        add(panelBotones, BorderLayout.SOUTH);

        // -------------------- ACCIONES BOTONES --------------------
        btnSalir.addActionListener(e -> {
            this.dispose();
            new Menu().setVisible(true);
        });

        btnResolver.addActionListener(e -> {
            // Pasamos los valores de la GUI a la lógica
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String txt = Tablero[i][j].getText();
                    logicaSudoku.Tablero[i][j] = txt.isEmpty() ? 0 : Integer.parseInt(txt);
                }
            }

            // Intentamos resolver
            if (logicaSudoku.Resolver()) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        Tablero[i][j].setText(String.valueOf(logicaSudoku.Tablero[i][j]));
                        Tablero[i][j].setEditable(false);
                    }
                }
                JOptionPane.showMessageDialog(this, "Sudoku resuelto", "Listo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se puede resolver", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // -------------------- GENERAR NUMEROS ALEATORIOS --------------------
        int cantidad = 0;
        switch (nivel) {
            case 1: cantidad = 40; break; // Fácil
            case 2: cantidad = 30; break; // Medio
            case 3: cantidad = 22; break; // Difícil
        }
        generarNumerosAleatorios(cantidad);
    }

    // -------------------- BORDE DE CADA CELDA --------------------
    private Border bordeCelda(int fila, int col) {
        int top = 1, left = 1, bottom = 1, right = 1;

        if (fila % 3 == 0) top = 3;
        if (col % 3 == 0) left = 3;
        if (fila == 8) bottom = 3;
        if (col == 8) right = 3;

        return BorderFactory.createMatteBorder(top, left, bottom, right, new Color(0, 70, 140));
    }

    // -------------------- GENERAR NUMEROS ALEATORIOS --------------------
    private void generarNumerosAleatorios(int cantidad) {
        Random rand = new Random();
        int colocados = 0;

        while (colocados < cantidad) {
            int fila = rand.nextInt(9);
            int col = rand.nextInt(9);

            if (Tablero[fila][col].getText().isEmpty()) {
                ArrayList<Integer> numerosPosibles = new ArrayList<>();
                for (int n = 1; n <= 9; n++) numerosPosibles.add(n);

                int startRow = (fila / 3) * 3;
                int startCol = (col / 3) * 3;
                for (int i = startRow; i < startRow + 3; i++) {
                    for (int j = startCol; j < startCol + 3; j++) {
                        String val = Tablero[i][j].getText();
                        if (!val.isEmpty()) numerosPosibles.remove(Integer.valueOf(Integer.parseInt(val)));
                    }
                }

                if (!numerosPosibles.isEmpty()) {
                    int elegido = numerosPosibles.get(rand.nextInt(numerosPosibles.size()));
                    Tablero[fila][col].setText(String.valueOf(elegido));
                    Tablero[fila][col].setEditable(false);
                    logicaSudoku.Tablero[fila][col] = elegido; // actualizar lógica
                    colocados++;
                }
            }
        }
    }
}
