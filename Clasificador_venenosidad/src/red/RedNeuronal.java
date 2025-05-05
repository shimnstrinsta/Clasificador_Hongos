
package red;
import java.util.LinkedList;

public class RedNeuronal {
    
    private LinkedList<Capa> capas = new LinkedList<>();
    
    
    public RedNeuronal(LinkedList<LinkedList<LinkedList<Double>>> pesosPorCapa){
        // La cantidad de capas y neuronas se determina por las dimensiones de pesosPorCapa                
        
        int cantidadCapas = pesosPorCapa.size();
                
        
        for(int i = 0; i < cantidadCapas;i++){
            capas.add(new Capa(pesosPorCapa.get(i)));
        }
        
    }
    
    
    
    public boolean predecirVenenosidad(LinkedList<Double> entradas){
        boolean venenoso = true;        
                        
        for(Capa capa : capas){            
            entradas = capa.sinapsis(entradas); // Resuelve capa por capa y toma las salidas para la siguiente
        }
        
        double resultado = entradas.get(0); // Toma el resultado de la neurona de la ultima capa
        
        if (resultado < 0.5){
            venenoso = false;
        }
        
        return venenoso;
    }
    
    
    
}
