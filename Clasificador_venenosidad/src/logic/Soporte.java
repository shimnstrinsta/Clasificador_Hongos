
package logic;

import java.util.LinkedList;
import java.util.Hashtable;

import red.RedNeuronal;

public class Soporte {
    
    private final Dato dato = new Dato();
    private RedNeuronal red;
    
    public boolean procesarEntradas(String[] entradas){
        
        LinkedList<Double> entradasProcesadas = normalizarEntradas(categorizarEntradas(entradas));
                                       
        red = new RedNeuronal(dato.leerPesos());
        boolean resultado = red.predecirVenenosidad(entradasProcesadas);
        
        return resultado;
    } 
    
    private int[] categorizarEntradas(String[] entradas){
        int[] categorizadas = new int[entradas.length];
        
        LinkedList<Hashtable<String,Integer>> codigos = dato.leerCodigos();

        for(int i = 0; i < categorizadas.length; i++){
            categorizadas[i] = codigos.get(i).get(entradas[i]);            
        }
        
        return categorizadas;
    }
    
    private LinkedList<Double> normalizarEntradas(int[] categorizadas){
        LinkedList<Double> normalizadas = new LinkedList<>();
        int[][] min_max = { 
            {1,6},
            {1,4},
            {1,10},
            {1,2},
            {1,9},
            {1,4}, 
            {1,3},
            {1,2},
            {1,12},
            {1,2}, 
            {1,7},
            {1,4},
            {1,4},
            {1,9},
            {1,9},
            {1,2},
            {1,4},
            {1,3},
            {1,8},
            {1,9},
            {1,6},
            {1,7}
        };

            
        for(int i = 0; i < categorizadas.length;i++){                        
            int min = min_max[i][0], max = min_max[i][1];                   
            double norm = (categorizadas[i] - min) / (double) (max - min) ;                        
            normalizadas.add(norm);
        }    
            
        
        
        return normalizadas;
        
    }
    
    public boolean validarDatos(String[] datos){                
        boolean resultado = true;
        int i = 0;
        
        while(resultado && i < datos.length){
            if(datos[i].isEmpty()){
                resultado = false;
            }
            i++;
        }
        
        return resultado;
    }                    
    
    public void evaluarResultado(String[] entradas,boolean resultado,boolean feedback){
        dato.guardarResultado(entradas,resultado,feedback);
    }
}
