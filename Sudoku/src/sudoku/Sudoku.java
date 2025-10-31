/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sudoku;

import java.util.ArrayList;
import java.util.Collections;

public class Sudoku extends Logica {

    public Sudoku(int dimension) {
        super(dimension);
    }

    public void generarCompleto() {
        Tablero = new int[9][9];
        generarRecursivo(0,0);
    }

    private boolean generarRecursivo(int fila, int col) {
        if (fila == 9) return true;
        int siguienteFila = (col == 8) ? fila + 1 : fila;
        int siguienteCol = (col == 8) ? 0 : col + 1;

        ArrayList<Integer> numeros = new ArrayList<>();
        for (int i = 1; i <= 9; i++) numeros.add(i);
        Collections.shuffle(numeros);

        for (int n : numeros) {
            if (esSeguro(fila, col, n)) {
                Tablero[fila][col] = n;
                if (generarRecursivo(siguienteFila, siguienteCol)) return true;
                Tablero[fila][col] = 0;
            }
        }
        return false;
    }

    @Override
    public boolean MovimientoValido() {
        int n = Tablero.length;
        int sub = (int) Math.sqrt(n);
        for (int i = 0; i < n; i++) {
            if (!filasSinDuplicados(i)) return false;
            if (!columnasSinDuplicados(i)) return false;
        }
        for (int i = 0; i < n; i += sub) {
            for (int j = 0; j < n; j += sub) {
                if (!cajaSinDuplicados(i, j, sub)) return false;
            }
        }
        return true;
    }

    @Override
    public boolean Resolver() {
        int[] pos = buscarVacio();
        if (pos == null) return true;
        int i = pos[0], j = pos[1];
        for (int valor = 1; valor <= 9; valor++) {
            if (esSeguro(i, j, valor)) {
                Tablero[i][j] = valor;
                if (Resolver()) return true;
                Tablero[i][j] = 0;
            }
        }
        return false;
    }

    private int[] buscarVacio() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (Tablero[i][j] == 0) return new int[]{i,j};
        return null;
    }

    private boolean esSeguro(int fila, int col, int valor) {
        return valorNoEnFila(fila, col, valor) && valorNoEnColumna(fila, col, valor) && valorNoEnCaja(fila, col, valor);
    }

    private boolean valorNoEnFila(int fila, int col, int valor) {
        for (int j = 0; j < 9; j++) {
            if (j == col) continue;            // <-- evita compararse consigo misma
            if (Tablero[fila][j] == valor) return false;
        }
        return true;
    }

    private boolean valorNoEnColumna(int col, int fila, int valor) {
         for (int i = 0; i < 9; i++) {
            if (i == fila) continue;           // <-- evita compararse consigo misma
            if (Tablero[i][col] == valor) return false;
        }
        return true;
    }

    private boolean valorNoEnCaja(int fila, int col, int valor) {
        int sub = 3;
        int startFila = (fila / sub) * sub;
        int startCol  = (col  / sub) * sub;
        for (int i = 0; i < sub; i++) {
            for (int j = 0; j < sub; j++) {
                int r = startFila + i, c = startCol + j;
                if (r == fila && c == col) continue; // <-- evita compararse consigo misma
                if (Tablero[r][c] == valor) return false;
            }
        }
        return true;
    }

    private boolean filasSinDuplicados(int fila) {
        boolean[] vistos = new boolean[10];
        for (int j = 0; j < 9; j++) {
            int v = Tablero[fila][j];
            if (v == 0) continue;
            if (vistos[v]) return false;
            vistos[v] = true;
        }
        return true;
    }

    private boolean columnasSinDuplicados(int col) {
        boolean[] vistos = new boolean[10];
        for (int i = 0; i < 9; i++) {
            int v = Tablero[i][col];
            if (v == 0) continue;
            if (vistos[v]) return false;
            vistos[v] = true;
        }
        return true;
    }

    private boolean cajaSinDuplicados(int filaInicio, int colInicio, int sub) {
        boolean[] vistos = new boolean[10];
        for (int i = 0; i < sub; i++)
            for (int j = 0; j < sub; j++) {
                int v = Tablero[filaInicio + i][colInicio + j];
                if (v == 0) continue;
                if (vistos[v]) return false;
                vistos[v] = true;
            }
        return true;
    }

    public boolean esMovimientoValidoEn(int fila, int col, int valor) {
        return esSeguro(fila, col, valor);
    }
}
