/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame {

    public int nivelseleccionado = 1;

    public Menu() {
        setTitle("CLOUD SUDOKU");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(170, 210, 255));
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        JLabel titulo = new JLabel("CLOUD SUDOKU", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        titulo.setForeground(new Color(0, 70, 140));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Elige tu nivel de dificultad:", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.BOLD, 20));
        subtitulo.setForeground(new Color(0, 70, 140));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelTitulo.add(titulo);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 15)));
        panelTitulo.add(subtitulo);

        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(170, 210, 255));
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100)); 

        JButton btnFacil = new JButton("Facil :c");
        JButton btnMedio = new JButton("Medio :)");
        JButton btnDificil = new JButton("Dificil >:D");
        JButton btnSalir = new JButton("-- SALIR --");

        JButton[] botones = {btnFacil, btnMedio, btnDificil, btnSalir};
        for (JButton btn : botones) {
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.setForeground(new Color(0, 70, 140));
            btn.setBackground(new Color(220, 240, 255));
            btn.setFocusPainted(false);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            panelBotones.add(btn);
            panelBotones.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        add(panelBotones, BorderLayout.CENTER);

        btnFacil.addActionListener(e -> iniciarJuego(1));
        btnMedio.addActionListener(e -> iniciarJuego(2));
        btnDificil.addActionListener(e -> iniciarJuego(3));
        btnSalir.addActionListener(e -> {
            System.out.println("Saliendo del juego...");
            System.exit(0);
        });
    }

    private void iniciarJuego(int nivel){
        nivelseleccionado = nivel;
        System.out.println("Nivel seleccionado: " + nivel);
        dispose();
        Juego jugar = new Juego(nivel); 
        jugar.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Menu menu = new Menu();
            menu.setVisible(true);
        });
    }
}
