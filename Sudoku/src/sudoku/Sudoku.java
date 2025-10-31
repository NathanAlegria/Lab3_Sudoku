/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sudoku;

/**
 *
 * @author Nathan
 */
public class Sudoku extends Logica {

    // constructor
    public Sudoku(int dimension) {
        super(dimension);
        }
    
    //Métodos que se heredaron
    @Override
    public boolean MovimientoValido() {
        int n = Tablero.length;
        int subindice = (int) Math.sqrt(n);
        if (subindice * subindice != n)
            return false;
        
        for (int i = 0; i < n; i++) {
            if (!filasSinDuplicas(i))
                return false;
            }
        
        for (int j = 0; j < n; j++) {
            if (!columnasSinDuplica(j));
                return false;
        }
        
        for (int fila = 0; fila < n; fila += subindice) {
            for (int caja = 0; caja < n; caja += subindice) {
                if (!cajaSinDuplica(fila, caja, subindice))
                    return false;
            }
        }
        
        return true;
    }
    
    @Override
    public boolean Resolver() {
        if (!MovimientoValido())
            return false;
        
        int[] pos = buscarVacio();
        if (pos == null)
            return true;
        
        int i = pos[0], j = pos[1];
        int n = Tablero.length;
        
        for (int valor = 1; valor <= n; valor++) {
            if (esSeguro(i, j, valor)) {
                Tablero[i][j] = valor;
                
                if (Resolver())
                    return true; 
                
                Tablero[i][j] = 0;
            }
        }
        
        return false;
    }
    
    private int[] buscarVacio() {
        for (int i = 0; i < Tablero.length; i++) {
            for (int j = 0; j < Tablero.length; j++) {
                if (Tablero[i][j] == 0)
                    return new int[]{i, j};
            } 
        }
        return null;
    }
    
    private boolean esSeguro(int i, int j, int valor) {
        return valorNoEnFila(i valor) && 
                valorNoEnColumna(j, valor) && 
                valorNoEnCaja(i, j, valor);
    }
    
    private boolean filasSinDuplica(int fila) {
        int n = Tablero.length;
        boolean[] visto = new boolean[n + 1];
        for (int j = 0; j < n; j++) {
            int v = Tablero[fila][j];
            if (v == 0)
                continue;
            if (v < 1 || v > n || visto[v])
                return false;
            visto[v] = true;
        }
        return true;
    }
    
    private boolean columnaSinDuplica(int columna) {
        int n = Tablero.length;
        boolean[] visto = new boolean[n + 1];
        for (int i = 0; i < n; i++) {
            int v = Tablero[i][columna];
            if (v == 0)
                continue;
            if ( v < 1 || v > n || visto[v])
                return false;
            visto[v] = true;
        }
        
        return true;
    }
    
    private boolean cajaSinDuplica(int filaInicial, int colInicial, int subindice) {
        int n = Tablero.length;
        boolean[] visto = new boolean[n + 1];
        for (int i = 0; i < subindice; i++) {
            for (int j = 0; j < subindice; j++) {
                int v = Tablero[filaInicial + i][colInicial + j];
                if ( v == 0)
                    continue;
                if (v < 1 || v > n || visto[v])
                    return false;
                visto[v] = true;
            }
        }
        
        return true;
    }
    
    private boolean valorNoEnColumna(int columna, int valor) {
        for(int i = 0; i < Tablero.length; i++) {
            if (Tablero[i])
        }
    }
    
}
