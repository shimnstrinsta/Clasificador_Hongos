package logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Hashtable;
import java.io.IOException;

public class Dato {

    public LinkedList<Hashtable<String, Integer>> leerCodigos() {
        LinkedList<Hashtable<String, Integer>> codigos = new LinkedList<>();

        try {

            BufferedReader in = new BufferedReader(new FileReader("data/Codigos.txt"));
            String linea = in.readLine();

            while (linea != null) {
                String[] registro = linea.split(",");
                Hashtable<String, Integer> codigo = new Hashtable<>();

                for (int i = 0; i < registro.length; i++) {
                    String llave = registro[i].split(":")[0];
                    int valor = Integer.parseInt(registro[i].split(":")[1]);

                    codigo.put(llave, valor);
                }

                codigos.add(codigo);

                linea = in.readLine();
            }

        } catch (IOException e) {System.out.println(e);}

        //System.out.println("Codigos: "+codigos);
        
        return codigos;
    }
    
    public LinkedList<LinkedList<LinkedList<Double>>> leerPesos(){
        LinkedList<LinkedList<LinkedList<Double>>> pesosPorCapa = new LinkedList<>();
        
        try {

            BufferedReader in = new BufferedReader(new FileReader("data/Pesos.txt"));
            String linea = in.readLine();

            while (linea != null) {
                String[] neuronasString = linea.split("N");
                                                                
                LinkedList<LinkedList<Double>> neuronas = new LinkedList<>();
                
                for(String neurona : neuronasString){
                    
                    LinkedList<Double> pesos = new LinkedList<>();
                    
                    for(String pesosString : neurona.split(",")){                        
                        pesos.add(Double.valueOf(pesosString));
                    }
                        
                    neuronas.add(pesos);
                }

                pesosPorCapa.add(neuronas);
                linea = in.readLine();
            }

        } catch (IOException e) {System.out.println(e);}
        
        return pesosPorCapa;        
    }

    public void guardarResultado(String[] entradas,boolean salida,boolean feedback){
        try {

            BufferedWriter out = new BufferedWriter(new FileWriter("data/Feedback.csv",true));
            
            for(String entrada:entradas){
                out.write(entrada+",");
            }
            
            if(salida){
                out.write("Venenoso,");
            }
            else{
                out.write("No venenoso,");
            }
            
            if(feedback){
                out.write("Correcto\n");
            }
            else{
                out.write("Incorrecto\n");
            }

            out.close();
        } catch (IOException e) {System.out.println(e);}
    }
}
