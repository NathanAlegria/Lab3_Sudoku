/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

/**
 *
 * @author Nathan
 */
public abstract class Logica {
    protected int [][] Tablero;
    public Logica(int dimension){
        Tablero=new int[dimension][dimension];
    }
    
    public void setTablero(int [][] Tableroinicial){
        this.Tablero=Tableroinicial;
    }
    
    public int [][] getTablero(){
        return Tablero;
    }
    
    public abstract boolean MovimientoValido();
    public abstract boolean Resolver();
    
    
}
