/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class Juego extends JFrame {

    private JTextField[][] Tablero = new JTextField[9][9];

    public Juego() {
        setTitle("S");
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // PANEL TITULO
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

        // PANEL SUDOKU (TABLERO)
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

        // PANEL BOTONES (OPCIONAL)
        JPanel panelBotones = new JPanel();
        JButton btnResolver = new JButton("Resolver");
        panelBotones.add(btnResolver);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private Border bordeCelda(int fila, int col) {
        int top = 1, left = 1, bottom = 1, right = 1;

        if (fila % 3 == 0) top = 3;
        if (col % 3 == 0) left = 3;
        if (fila == 8) bottom = 3;
        if (col == 8) right = 3;

        return BorderFactory.createMatteBorder(top, left, bottom, right, new Color(0, 70, 140));
    }
}
