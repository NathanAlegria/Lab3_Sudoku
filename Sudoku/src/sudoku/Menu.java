/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Nathan
 */
public class Menu extends JFrame {

    public int nivelseleccionado = 1;

    public Menu() {
        setTitle("Menú Sudoku");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel titulo = new JLabel("Selecciona la dificultad", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo);

        JButton btnFacil = new JButton("Fácil");
        JButton btnMedio = new JButton("Medio");
        JButton btnDificil = new JButton("Difícil");

        add(btnFacil);
        add(btnMedio);
        add(btnDificil);

        btnFacil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego(1);
            }
        });
        
        btnMedio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego(2);
            }
        });
        
        btnDificil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego(3);
            }
        });
    }
    
    private void iniciarJuego(int nivel){
        nivelseleccionado=nivel;
        dispose();
        Juego jugar=new Juego();
        jugar.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Menu menu = new Menu();
                menu.setVisible(true);
            }
        });
    }
}
