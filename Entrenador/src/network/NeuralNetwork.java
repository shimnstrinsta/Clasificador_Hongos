package network;

import java.util.ArrayList;
import java.util.LinkedList;
import org.plot.Plot;
import util.utils;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Red neuronal implementada mediante neuronas con funcion de activacion
 * sigmoidea. Las neuronas se inicializan con pesos y bias aleatorios entre 0 y
 * 1.
 *
 * @author vichoko
 *
 */
public class NeuralNetwork {

    ArrayList<NeuralLayer> layers = new ArrayList<>();
    boolean isComplete = false;
    double learningRate = 0.1;

    /**
     * CONSTRUCCION
     */
    public NeuralNetwork() {
        // Red vacia, learning rate	fijo
    }

    /**
     * Inicia red vacia con tasa de aprendizaje explicita.
     *
     * @param learningRate Tasa de aprendizaje de las neuronas
     */
    public NeuralNetwork(double learningRate) {
        // Red vacia
        this.learningRate = learningRate;
    }

    /**
     * Crea capa de entrada
     *
     * @param inputSize Cantidad de entradas
     * @param numberOfNeurons Cantidad de neuronas
     * @throws Exception En caso de agregar mas de una capa de entrada
     */
    public void newInputLayer(int numberOfNeurons, int inputSize) throws Exception {
        if (layers.size() > 0) {
            throw new Exception("Tried adding more than one input layer");
        }
        layers.add(new NeuralLayer(numberOfNeurons, inputSize));
    }

    /**
     * Crea capa escondida o de salida (si se llama closeNetork despues)
     *
     * @param numberOfNeurons Numero de neuronas que tendra la capa
     * @throws Exception
     */
    public void newHiddenLayer(int numberOfNeurons) throws Exception {
        if (layers.size() == 0) {
            throw new Exception("Tried adding hidden layer without input layer");
        } else if (isComplete) {
            throw new Exception("Tried adding hidden layer after network is closed (i.e. output layer added)");
        }
        NeuralLayer previousLayer = layers.get(layers.size() - 1);
        layers.add(new NeuralLayer(numberOfNeurons, previousLayer.getOutputSize()));
    }

    /**
     * Transforma ultima capa oculta/entrada en capa de salida. Cierra la red
     * neuronal a modificaciones topologicas.
     *
     * @throws Exception en caso de cerrar dos veces o si se llama antes de
     * crear capa de entrada.
     */
    public void closeNetwork() throws Exception {
        /**
         * Transforma la ultima capa en Output Layer y cierra red a
         * modificaicones
         */
        if (layers.size() == 0) {
            throw new Exception("Tried adding output layer without input layer");
        } else if (isComplete) {
            throw new Exception("Tried adding more than one output layer, network already closed");
        }
        isComplete = true;
    }

    /*
     * METODOS PUBLICOS
     */
 /*
     * Entrena la red neuronal con el conjunto de entrenamiento entregado en
     * input y expectedOutput. La cantidad de elementos de input y
     * expectedOutput deben coincidir.
     *
     * @param entradas_entrenamiento Entradas a la red neuronal, debe coincidir su cantidad con
     * numero de entradas de la capa de entrada.
     * @param salidas_entrenamiento Salidas esperadas de la red neuronal. Su cantidad
     * debe coincidir con la cantidad de neuronas de la capa de salida.
     * @param nEpochs Cantidad de veces que se entrenara con el data set
     * entregado.
     * @param errorPlotName if not null, error is plotted in file with this
     * name.
     * @throws Exception En caso de detectar inconsistencias entre input y
     * expectedOutput.
     */
    public void train(LinkedList<double[]> entradas_entrenamiento, LinkedList<double[]> salidas_entrenamiento, int nEpochs, String errorPlotName) throws Exception {
        // recibe dataset de entrenamiento; varios input con sus respectivos output
        guardarRegistroPesos(false, "Pesos_iniciales.txt");
        double[] errors = new double[nEpochs];

        // Siguientes variables son para no saturar de impresiones en consola.

        for (int epochIndex = 0; epochIndex < nEpochs; epochIndex++) {
            double sumError = 0;
            
            for (int dataIndex = 0; dataIndex < entradas_entrenamiento.size(); dataIndex++) {
                // entrenar sobre cada par de vectores input/output.
                double[] realOutput = this.forwardFeed(entradas_entrenamiento.get(dataIndex));

                for (int outputIndex = 0; outputIndex < realOutput.length; outputIndex++) {
                    double y = salidas_entrenamiento.get(dataIndex)[outputIndex];
                    double yHat = realOutput[outputIndex];

                    yHat = Math.max(1e-10, Math.min(1 - 1e-10, yHat));
                    sumError += -(y * Math.log(yHat) + (1 - y) * Math.log(1 - yHat));
                }

                this.backPropagation(salidas_entrenamiento.get(dataIndex));
                this.updateWeights(entradas_entrenamiento.get(dataIndex));

                if (epochIndex % 10 == 0) {
                    guardarRegistroPesos(false, "Pesos_Finales.txt");
                }

            }

            errors[epochIndex] = sumError;
            System.out.println("Epoch: " + epochIndex + ", error: " + sumError);
        }

        // do plot to outfile
        if (errorPlotName != null) {
            double[] x = new double[errors.length];
            for (int i = 0; i < x.length; i++) {
                x[i] = i + 1;
            }
            Plot plot = Plot.plot(Plot.plotOpts().title("Error vs Epochs")).xAxis("Epochs", null).yAxis("Error", null).series(null, Plot.data().xy(x, errors), null);
            plot.save(errorPlotName, "png");
        }
    }

    /**
     * Obtener prediccion de la red neuronal, dado una entrada particular.
     *
     * @param input Entrada que se desea hacer una prediccion.
     * @return Salida predecida por la red neuronal. Valores entre 0 y 1. Su
     * dimension coincide con la capa de salida.
     * @throws Exception
     */
    public double[] predict(double[] input) throws Exception {
        return forwardFeed(input);
    }

    /**
     * Obtener prediccion de la red neuronal, obteniendo valores 0 o 1. Al pasar
     * la prediccion real por umbral explicitado.
     *
     * @param input Entrada que se desea hacer una prediccion.
     * @param threshold Umbral desde el cual se considerara la clase como 1. De
     * lo contrario 0.
     * @return Salida predecida, obteniendo valores 0 o 1.
     * @throws Exception
     */
    public int[] binaryPredict(double[] input, double threshold) throws Exception {
        double[] outputs = forwardFeed(input);
        int[] res = new int[outputs.length];

        for (int i = 0; i < outputs.length; i++) {
            //System.out.println("Salida original: " + outputs[i]);
            res[i] = outputs[i] > threshold ? 1 : 0;
        }
        return res;
    }

    /**
     * METODOS (PRIVADOS) DE APRENDIZAJE MEDIANTE BACK PROPAGATION
     */
    double[] forwardFeed(double[] food) throws Exception {
        //System.out.println("\nEntradas a predecir (" + food.length + "):");
//        for (double entrada : food) {
//            System.out.print(entrada + " ");
//        }
        
        //System.out.println("LARGOOOOO DE LAYERS: "+layers.size());


        for (NeuralLayer layer : layers) {
            food = layer.synapsis(food);
        }
        return food;
    }

    void backPropagation(double[] expectedOutput) {
//        for (int layerIndex = layers.size() - 1; layerIndex > 0; layerIndex--) {
//            // backward iteration
//            NeuralLayer layer = layers.get(layerIndex);
//            if (layerIndex == layers.size() - 1) {
//                // Caso capa de salida
//                int neuronIndex = 0;
//                for (Neuron neuron : layer.neurons) {
//                    neuron.delta = (expectedOutput[neuronIndex++] - neuron.lastOutput); //* utils.transferDerivative(neuron.lastOutput);
//                }
//            } else {
//                // caso capa escondida o de entrada
//                for (int neuronIndex = 0; neuronIndex < layer.neurons.length; neuronIndex++) {
//                    Neuron neuron = layer.neurons[neuronIndex];
//                    double error = 0;
//                    for (Neuron neighborNeuron : layers.get(layerIndex + 1).neurons) {
//                        error += (neighborNeuron.weights[neuronIndex] * neighborNeuron.delta);
//                    }
//                    neuron.delta = error * utils.transferDerivative(neuron.lastOutput);
//
//                }
//
//            }
//
//        }
        for (int layerIndex = layers.size() - 1; layerIndex > 0; layerIndex--) {
            NeuralLayer currentLayer = layers.get(layerIndex);

            if (layerIndex == layers.size() - 1) {
                // Capa de salida
                for (int i = 0; i < currentLayer.neurons.length; i++) {
                    Neuron neuron = currentLayer.neurons[i];
                    double output = neuron.lastOutput;
                    double target = expectedOutput[i];

                    // Si estás usando sigmoid + binary crossentropy, podés usar solo (target - output)
                    neuron.delta = (target - output) * utils.transferDerivative(output);
                }
            } else {
                // Capas ocultas
                NeuralLayer nextLayer = layers.get(layerIndex + 1);

                for (int i = 0; i < currentLayer.neurons.length; i++) {
                    Neuron neuron = currentLayer.neurons[i];
                    double sum = 0.0;

                    for (Neuron nextNeuron : nextLayer.neurons) {
                        sum += nextNeuron.weights[i] * nextNeuron.delta;
                    }

                    // Calcular delta con la derivada de la activación
                    neuron.delta = sum * utils.transferDerivative(neuron.lastOutput);
                }
            }
        }
    }

    void updateWeights(double[] input) throws Exception {
//        for (int layerIndex = 1; layerIndex < this.layers.size(); layerIndex++) {
//            NeuralLayer layer = layers.get(layerIndex);
//            if (layerIndex > 0) {
//                // Si no es input layer, los input vienen de layers previas
//                input = layers.get(layerIndex - 1).getPastOutputs();
//            }
//            for (Neuron neuron : layer.neurons) {
//                // se actualiza su peso                                
//                for (int inputIndex = 0; inputIndex < input.length; inputIndex++) {
//                    neuron.weights[inputIndex] += this.learningRate * neuron.delta * input[inputIndex];
//                }
//                neuron.bias += this.learningRate * neuron.delta;
//            }
//        }
        // Recorremos desde la capa 1 (la capa 0 es la de entrada, sin pesos)
        for (int layerIndex = 0; layerIndex < layers.size(); layerIndex++) {
            NeuralLayer layer = layers.get(layerIndex);
            double[] inputs;

            if (layerIndex == 0) {
                // Si estamos en la primera capa oculta, usamos las entradas originales
                inputs = input;
            } else {
                // Si estamos en cualquier otra capa, usamos las salidas de la capa anterior
                inputs = layers.get(layerIndex - 1).getPastOutputs();
            }

            for (Neuron neuron : layer.neurons) {
                for (int i = 0; i < neuron.weights.length; i++) {
                    // Regla de actualización estándar:
                    // w_i = w_i + learningRate * delta * entrada_i
                    neuron.weights[i] += learningRate * neuron.delta * inputs[i];
                }

                // También se actualiza el sesgo (bias), si lo estás usando como un peso adicional
                neuron.bias += learningRate * neuron.delta;
            }
        }

    }

    public void guardarRegistroPesos(boolean reemplazar, String archivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/data/" + archivo + ".txt", reemplazar))) {

            for (int layerIndex = 0; layerIndex < this.layers.size(); layerIndex++) {
                NeuralLayer layer = layers.get(layerIndex);
                writer.write("\nCapa " + layerIndex + "\n");
                int i_neuron = 1;
                for (Neuron neuron : layer.neurons) {
                    writer.write("N " + i_neuron + " : B " + neuron.bias + " ");
                    for (double peso : neuron.getWeights()) {
                        writer.write(String.valueOf(peso) + " ");
                    }
                    writer.newLine();
                    i_neuron++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void logForwardFeed(double[] input) throws Exception {
        double[] layerInput = input;

        System.out.println("\nIniciando forwardFeed con entrada:");
        printArray(layerInput);

        for (int i = 0; i < layers.size(); i++) {
            NeuralLayer layer = layers.get(i);
            System.out.println("\nCapa " + i + ":");

            double[] netInputs = new double[layer.neurons.length];
            double[] activaciones = new double[layer.neurons.length];

            for (int j = 0; j < layer.neurons.length; j++) {
                Neuron neuron = layer.neurons[j];

                // Calcular net input manualmente
                double suma = 0;
                for (int k = 0; k < neuron.weights.length; k++) {
                    suma += layerInput[k] * neuron.weights[k];
                }
                suma += neuron.bias;
                

                netInputs[j] = suma;
                activaciones[j] = utils.sigmoid(suma);
            }

            System.out.print("   Suma neta:     ");
            printArray(netInputs);

            System.out.print("   Activación:    ");
            printArray(activaciones);

            layerInput = layer.synapsis(layerInput); // avanza al siguiente layer
        }

        System.out.println("\n✅ Resultado final de la red:");
        printArray(layerInput);
    }

    private void printArray(double[] arr) {
        for (double v : arr) {
            System.out.printf("%.5f ", v);
        }
        System.out.println();
    }

}
