package network;

import java.util.Random;

public class NeuralLayer {

    Neuron[] neurons;
    double[] pastOutputs;

    /*
     * CONSTRUCTORES
     */
    public NeuralLayer(int n, double[] weights, double bias) {
        neurons = new Neuron[n];// explicit declaration
        for (int i = 0; i < n; i++) {
            neurons[i] = new Neuron(bias, weights);
        }
    }

    /*
     * Constructor de capa que inicializa los pesos y bias aleatoriamente entre
     * 0 y 1.
     *
     * @param neuronQuantity Cantidad de neuronas de la capa
     * @param inputSize Cantidad de input que recibira cada neurona i.e. la capa
     * en su totalidad.
     */
    NeuralLayer(int neuronQuantity, int inputSize) {
        neurons = new Neuron[neuronQuantity];
        // todas las neuronas de una layer tienen la misma cantidad de pesos
        // bias recomendado entre 0 y 1. Pesos recomendados entre 0 y 1.
        double limit = Math.sqrt(6.0 / (inputSize + 1));
        
        for (int i = 0; i < neuronQuantity; i++) {
        Random r = new Random();
            
            double[] weights = new double[inputSize];
            
            for (int j = 0; j < inputSize; j++) {
                
                
                double peso = r.nextDouble() * 2 * limit - limit;  // [-limit, limit]

                weights[j] = peso;// (r.nextDouble());
            }
   
            neurons[i] = new Neuron(r.nextDouble() * 2 * limit - limit, weights);
        }
    }

    /**
     * Obtiene salida predecida por la capa en la "sinapsis" previa.
     *
     * @throws Exception Si no hubo sinapsis previa.
     */
    double[] getPastOutputs() throws Exception {
        if (pastOutputs == null) {
            throw new Exception("Layer have not been feed before calling getPastOutputs.");
        }
        return pastOutputs;
    }

    /**
     * METODOS
     */
    /**
     * Obtiene tamano de la salida.
     *
     * @return
     */
    int getOutputSize() {
        return neurons.length;
    }

    /**
     * Pasa una serie de input por la capa y obtiene su salida.
     *
     * @param inputs
     * @return
     * @throws Exception
     */
    double[] synapsis(double[] inputs) throws Exception {
        // must have same size as weights

        this.pastOutputs = new double[neurons.length];
        for (int i = 0; i < pastOutputs.length; i++) {
            pastOutputs[i] = neurons[i].synapsis(inputs);
        }
        return pastOutputs;

    }

}
