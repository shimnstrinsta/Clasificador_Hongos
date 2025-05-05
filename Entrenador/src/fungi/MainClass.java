package fungi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import network.NeuralNetwork;
import util.utils;

public class MainClass {

    public static void main(String[] args) throws Exception {

        LinkedList<double[]> datos_entreamiento = leerSetDeDatos("src/data/Entrenamiento.csv"); // 5679 registros 
        LinkedList<double[]> datos_test = leerSetDeDatos("src/data/Prueba.csv"); // 2445 registros

        int cantidadEntradas = 22;
        int cantidadSalidas = 1;

        LinkedList<double[]> entradas_entrenamiento = separarEntradas(datos_entreamiento, cantidadEntradas);
        LinkedList<double[]> salidas_entrenamiento = separarSalidas(datos_entreamiento, cantidadEntradas);
        LinkedList<double[]> entradas_test = separarEntradas(datos_test, cantidadEntradas);
        LinkedList<double[]> salidas_test = separarSalidas(datos_test, cantidadEntradas);

        
        NeuralNetwork net = new NeuralNetwork(0.001);
        net.newInputLayer(2, cantidadEntradas);
        net.newHiddenLayer(4);
        net.newHiddenLayer(cantidadSalidas);
        
        System.out.println("# ---------------- #\nEntrenamiento: ");
        System.out.println("Entradas: " + entradas_entrenamiento.size() + " registros " + entradas_entrenamiento.get(0).length + " atributos");
        System.out.println("Salidas: " + salidas_entrenamiento.size() + " registros " + salidas_entrenamiento.get(0).length + " atributos");

//        for (double[] registro: entradas_entrenamiento){
//            for (int i = 0; i < registro.length; i++){
//                System.out.print(String.valueOf(registro[i])+" ");
//            }
//            System.out.println();
//        } 
//        
//        for (double[] registro: salidas_entrenamiento){
//            for (int i = 0; i < registro.length; i++){
//                System.out.print(String.valueOf(registro[i])+" ");
//            }
//            System.out.println();
//        } 
        
        
        
        System.out.println("\n# ---------------- #\nTesteo: ");
        System.out.println("Entradas: " + entradas_test.size() + " registros " + entradas_test.get(0).length + " atributos");
        System.out.println("Salidas: " + salidas_test.size() + " registros " + salidas_test.get(0).length + " atributos" );
        
        // 70% datos de entrenamiento, 30% datos de prueba
        
        boolean verbose = true;
        
        System.out.println("Sin entrenar: ");
        net.guardarRegistroPesos(false,"Pesos_iniciales");
        utils.binaryMetrics(net, entradas_test, salidas_test, 0.5,true);   
        
        net.train(entradas_entrenamiento, salidas_entrenamiento, 1000, "FUNGI");
        net.guardarRegistroPesos(false,"Pesos_finales");
        
        System.out.println("Entrenada: ");
        utils.binaryMetrics(net, entradas_test, salidas_test, 0.5,verbose); 
        
//        net.logForwardFeed(entradas_entrenamiento.get(7));
//        net.logForwardFeed(entradas_entrenamiento.get(1002));
//        net.logForwardFeed(entradas_entrenamiento.get(2000));
    }

    private static LinkedList<double[]> leerSetDeDatos(String archivo) throws Exception {

        LinkedList<double[]> datos = new LinkedList<>();

        BufferedReader in = new BufferedReader(new FileReader(archivo));
        String linea = in.readLine();

        while (linea != null) {
            String[] registro = linea.split(",");
            double[] valores = new double[registro.length];

            for (int i = 0; i < valores.length; i++) {
                valores[i] = Double.parseDouble(registro[i]);
            }

            datos.add(valores);
            linea = in.readLine();
        }

        // Guarda los datos ya codificados y normalizados en una matriz dinamica de doubles
        return datos;
    }

    private static LinkedList<double[]> separarEntradas(LinkedList<double[]> datos, int cantidadEntradas) {
        // Devuelve las entradas de un set de datos (entrenamiento o test)

        LinkedList<double[]> entradas = new LinkedList<>();

        for (double[] registro : datos) {
            double[] entradasRegistro = new double[cantidadEntradas];
            System.arraycopy(registro, 0, entradasRegistro, 0, cantidadEntradas); // copia desde el índice 0 hasta cantidadEntradas
            entradas.add(entradasRegistro);
        }

        return entradas;

    }

    private static LinkedList<double[]> separarSalidas(LinkedList<double[]> datos, int cantidadEntradas) {
        // Devuelve las salidas de un set de datos (entrenamiento o test)

        LinkedList<double[]> salidas = new LinkedList<>();

        for (double[] registro : datos) {
            int cantidadSalidas = registro.length - cantidadEntradas;
            double[] salidasRegistro = new double[cantidadSalidas];
            System.arraycopy(registro, cantidadEntradas, salidasRegistro, 0, cantidadSalidas); // copiamos desde cantidadEntradas al final
            salidas.add(salidasRegistro);
        }

        return salidas;
    }

}
