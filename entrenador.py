import tensorflow as tf #import tensorflow
from tensorflow import keras 
import pandas as pd #import pandas
import numpy as np #import numpy

def guardarModelo(modelo,precision):
    archivo = open("Precisiones.txt","a")

    archivo.write("Capa 1: "+ str(modelo[0]) +" Capa 2:"+ str(modelo[1]) +" Precision: "+ str(precision) + "\n")

    archivo.close()


def separarDatos(url): # Aca separa el set de datos para entrenamiento y test 
    datos = pd.read_csv(url,names=["Forma sombrero", "Superficie sombrero", "Color sombrero", "Abolladura", "Olor", "Accesorio branquia", "Espaciamiento branquia", "Tamaño branquia", "Color branquia", "Forma tallo", "Raiz tallo", "Superficie del tallo por encima del anillo", "Superficie del tallo por debajo del anillo", "Color del tallo por encima del anillo","Color del tallo por debajo del anillo", "Tipo velo", "Color velo", "Número anillos", "Tipo anillo", "Color espora", "Poblacion", "Habitat", "Resultado"]) #Reemplazar por todos los indicadores de tu set de datos
    entradas = (datos.drop(columns=["Resultado"])) # Remplazar la lista por las salidas
    salidas = (datos[["Resultado"]]) # Remplazar la lista por las salidas   

    print("Largo entradas: ",entradas) 
    print("Largo salidas: ",salidas)

    entradas = np.array(entradas)
    salidas = np.array(salidas)

    return entradas,salidas


entradas_entr, salidas_entr = separarDatos("./Datos/Entrenamiento.csv") # 5679 registros
entradas_test, salidas_test = separarDatos("./Datos/Prueba.csv") # 2445 registros

initializer = tf.keras.initializers.RandomUniform(minval = 0,maxval=1) #Aca inizializa los valores de los pesos desde -1 hasta 1

modelo = [None]*2
preAlta = float()

try:
    for i in range(1,20): #Prueba de 1 a 20 neuronas en la capa inicial
        for j in range(3,20): #Prueba de 1 a 20 neuronas en la capa del medio
            modelo[0] = i
            modelo[1] = j

            model = tf.keras.Sequential([
                tf.keras.layers.Dense(i, activation="sigmoid", input_shape=(22,)), # Input_shape es la cantida de entradas (tenes que reemplazarlo)
                tf.keras.layers.Dense(j, activation="sigmoid"), # Prueba con 3 capas 
                tf.keras.layers.Dense(1, activation="sigmoid") # 10 es la cantidad de salidas, en tu caso seria 1 (tenes que reemplazarlo)
            ])  

            model.compile(optimizer='adam',
                          loss= "binary_crossentropy",
                          metrics=['accuracy']) 
            
            model.fit(entradas_entr, salidas_entr, epochs=200,verbose=0)    # Aca prueba el modelo y registra la precision
            evaluacion = model.evaluate(entradas_test,salidas_test, verbose=0)
            
            print(f"Modelo: {modelo[0]} {modelo[1]}\n")

            if(evaluacion[1] > preAlta):
                print(f"\n\nLa precisión más alta registrada es de : {(preAlta)} Cantidad neuronas 1: {modelo[0]} Cantidad neuronas 2:{modelo[1]}")
                preAlta = evaluacion[1]
                guardarModelo(modelo,preAlta)
                                    
except Exception as e:
    print("Ocurrio una excepcion: ", e)
    guardarModelo(modelo,preAlta)


