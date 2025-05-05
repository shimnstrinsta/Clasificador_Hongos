

codigos = [
    {"bell":4,"conical":6,"convex":2,"flat":1,"knobbed":5,"sunked":3},
    {"fibrous":3,	"grooves":2	,"scaly":4,	"smooth":1},
    {"brown":1	,"buff":	2,"cinnamon":5	,"gray":	3,"green":10,	"pink":7,	"purple"	:9,"red":8,	"white":	6,"yellow":4},
    {"bruises": 1,	"no": 2},
    {"almond": 1,	"anise": 2,	"creosote": 9,	"fishy": 6,	"foul": 8,	"musty": 5,	"none": 3,	"pungent": 7,	"spicy": 4},
    {"attached": 3,	"descending": 4,	"free": 1,	"notched": 2},
    {"close": 2,	"crowded": 1,	"distant": 3},
    {"broad": 1,	"narrow": 2},
    {"black": 1,	"brown": 2,	"buff": 5,	"chocolate": 3,	"gray": 6,	"green": 12,	"orange":9 ,	"pink": 4,	"purple":10 ,	"red": 11,	"white": 7,	"yellow": 8, "missing": 0, "0": 0},
    {"enlarging":1 ,	"tapering": 2},
    {"bulbous": 3,	"club": 4,	"cup":5 ,	"equal":2 ,	"rhizomorphs": 7,	"rooted": 6,	"0": 1},
    {"fibrous": 3,	"scaly": 4,	"silky": 2,	"smooth": 1},
    {"fibrous": 3,	"scaly": 4,	"silky": 2,	"smooth": 1},
    {"brown": 1,	"buff": 2,	"cinnamon": 3,	"gray": 6,	"orange": 9,	"pink": 5,	"red": 8,	"white": 4,	"yellow": 7},
    {"brown": 1,	"buff": 2,	"cinnamon": 3,	"gray": 6,	"orange": 9,	"pink": 5,	"red": 8,	"white": 4,	"yellow": 7},
    {"partial": 1,	"universal": 2},
    {"brown": 2,	"orange":4 ,	"white": 1,	"yellow":3 },
    {"none":1 ,	"one": 2,	"two": 3},
    {"cobwebby": 4,	"evanescent":2 ,	"flaring": 5,	"large": 8,	"none": 1,	"pendant": 6,	"sheathing": 7,	"zone": 3},
    {"black": 9,	"brown":2 ,	"buff": 3,	"chocolate":4 ,	"green": 7,	"orange": 8,	"purple": 6,	"white": 1,	"yellow": 5},
    {"abundant": 6,	"clustered": 5,	"numerous": 4,	"scattered": 2,	"several":3 ,	"solitary": 1},
    {"grasses": 2,	"leaves":4 ,	"meadows": 3,	"paths": 5,	"urban": 6,	"waste": 7,	"woods":1 },
    {"Venenoso":2,"Comestible":1},
]

crudo = []

archivo = open("Datos/Crudo.csv","r")
for linea in archivo:
    valores = linea.split(",")
    valores[len(valores)-1] = valores[len(valores)-1].replace("\n","")    
    crudo.append(valores)
archivo.close()

categorizado = []

for registro in crudo:
    nuevo_registro = []
    for i in range (len(registro)):        
        nuevo_registro.append(codigos[i].get(registro[i]))
    categorizado.append(nuevo_registro)


archivo = open("Datos/Categorizado.csv","w")

for registro in categorizado:
    for dato in registro:
        archivo.write(str(dato) + ",")
    archivo.write("\n")

archivo.close()