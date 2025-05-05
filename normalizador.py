
def normalizar(valor, limites):
    min = limites[0]
    max = limites[1]

    valor_normalizado = round((int(valor) - min) / (max - min),2)

    return valor_normalizado

archivo = open("./Datos/Categorizado.csv")

datos = []
normalizado = []
min_max = [ (1,6), 
            (1,4),
            (1,10),
            (1,2), 
            (1,9),
            (1,4), 
            (1,3), 
            (1,2),
            (1,12),
            (1,2), 
            (1,7),
            (1,4),
            (1,4),
            (1,9),
            (1,9),
            (1,2),
            (1,4),
            (1,3),
            (1,8),
            (1,9),
            (1,6),
            (1,7)]

for linea in archivo:
    valores = linea.split(",")
    valores[len(valores)-1] = valores[len(valores)-1].replace("\n","")    
    datos.append(valores)

archivo.close()

for registro in datos:
    for dato in registro:
        print(dato,end=" ")
    print()

for registro in datos:
    valores_normalizados = []
    for i in range(len(registro)):
        valores_normalizados.append(normalizar(registro[i],min_max[i]))
    normalizado.append(valores_normalizados)

print("# -------------------------------------- #")

archivo = open("./Datos/Final.csv","w")

for registro in normalizado:
    for dato in registro:
        archivo.write(str(dato) + ",")
        print(dato,end=" ")
    archivo.write("\n")
    print()

archivo.close()