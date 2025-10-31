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
    private int nivel;

    public Juego(int nivel) {
        this.nivel = nivel;
        setTitle("Sudoku");
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

        // -------------------- PANEL SUDOKU (TABLERO) --------------------
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
            if (validarSudoku()) {
                JOptionPane.showMessageDialog(this, "¡Bien hecho!", "Correcto", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Hay errores en el Sudoku", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // -------------------- GENERAR NUMEROS ALEATORIOS --------------------
        if (nivel == 1) {
            generarNumerosAleatorios(80);
        }
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

    // -------------------- VALIDAR SUDOKU --------------------
    private boolean validarSudoku() {
        for (int i = 0; i < 9; i++) {
            boolean[] fila = new boolean[9];
            boolean[] col = new boolean[9];
            for (int j = 0; j < 9; j++) {
                String textoFila = Tablero[i][j].getText();
                if (!textoFila.matches("[1-9]")) return false;
                int numFila = Integer.parseInt(textoFila);
                if (fila[numFila - 1]) return false;
                fila[numFila - 1] = true;

                String textoCol = Tablero[j][i].getText();
                int numCol = Integer.parseInt(textoCol);
                if (col[numCol - 1]) return false;
                col[numCol - 1] = true;
            }
        }

        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                boolean[] bloque = new boolean[9];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        String texto = Tablero[row + i][col + j].getText();
                        int num = Integer.parseInt(texto);
                        if (bloque[num - 1]) return false;
                        bloque[num - 1] = true;
                    }
                }
            }
        }

        return true;
    }

    // -------------------- GENERAR NUMEROS ALEATORIOS --------------------
    private void generarNumerosAleatorios(int cantidad) {
        Random rand = new Random();
        int colocados = 0;

        while (colocados < cantidad) {
            int fila = rand.nextInt(9);
            int col = rand.nextInt(9);

            if (Tablero[fila][col].getText().isEmpty()) {
                ArrayList<Integer> numerosPosibles = new ArrayList<Integer>();
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
                    colocados++;
                }
            }
        }
    }
}
