
package red;

import java.util.LinkedList;

public class Neurona {
    
    private double umbral;
    private LinkedList<Double> pesos = new LinkedList<>();
    
    public Neurona(LinkedList<Double> pesosParam){
        umbral = pesosParam.get(0);
                
        for (int i = 1; i< pesosParam.size(); i++){
            pesos.add(pesosParam.get(i));
        }
    }
    
    public double sinapsis(LinkedList<Double> entradas){
        double resultado = 0;
        
        for(int i = 0; i < entradas.size(); i++){
            resultado += entradas.get(i) * pesos.get(i);
        }
        
        resultado += umbral;
        
        return sigmoide(resultado);
    }
    
    private double sigmoide(double x){
        double resultado = 1.0 / (1 + Math.exp(-x));
                
        return resultado;        
    }
    
}
