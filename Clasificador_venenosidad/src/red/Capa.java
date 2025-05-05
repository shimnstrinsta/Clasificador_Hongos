package red;

import java.util.LinkedList;

public class Capa {

    private final LinkedList<Neurona> neuronas = new LinkedList<>();
    private LinkedList<Double> salidasAnteriores = new LinkedList<>();

    Capa(LinkedList<LinkedList<Double>> pesosPorNeurona) {
        int cantidadNeuronas = pesosPorNeurona.size();
        
        for (int i = 0; i < cantidadNeuronas; i++) {
            neuronas.add(new Neurona(pesosPorNeurona.get(i)));
        }
    }

    public LinkedList<Double> sinapsis(LinkedList<Double> entradas) {

        this.salidasAnteriores = new LinkedList<>();

        for (Neurona neurona : neuronas) {
            salidasAnteriores.add(neurona.sinapsis(entradas));
        }
        return salidasAnteriores;
    }

}
