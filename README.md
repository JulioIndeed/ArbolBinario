# Árbol binario de búsqueda de palabras

El programa lee una oración y almacena sus palabras en un árbol binario de búsqueda. Separa las palabras por cualquier carácter que no sea una letra o una marca diacrítica, y normaliza mayúsculas a minúsculas. El orden se compara con el alfabeto español; los acentos se conservan.

## Decisión sobre duplicados

Una palabra repetida no crea otro nodo: incrementa la frecuencia almacenada en el nodo existente. Por eso, el total de nodos representa palabras distintas. Los recorridos y listados muestran la frecuencia cuando es mayor que uno.

Un nodo interno es cualquier nodo que tiene al menos un hijo. Las hojas son los nodos que no tienen hijos. La palabra máxima se obtiene siguiendo los hijos derechos, que contienen los valores alfabéticamente mayores.

## Apoyo de IA y verificación

Una instrucción útil para solicitar una primera propuesta fue:

> Adapta el árbol binario de búsqueda en Java para almacenar palabras de una oración. Define cómo tratar palabras repetidas, conserva letras acentuadas, muestra inorden, preorden y postorden, y calcula nodos totales, internos, hojas y la palabra máxima en español. Explica las decisiones para que puedan verificarse con una prueba.

La propuesta se revisó contra los requisitos antes de integrarla. En particular, se verificó que el inorden sea alfabético, que el contador represente nodos distintos y que los duplicados aparezcan como frecuencias. Una primera prueba enviada mediante una tubería de PowerShell fragmentó palabras acentuadas por la codificación del flujo; la lectura interactiva se corrigió usando el charset declarado por la consola, con UTF-8 como alternativa para entrada redirigida.

### Registro de interacción con IA

| # | Pregunta realizada a la IA | Respuesta obtenida | ¿Se utilizó? | Modificaciones realizadas | Justificación |
|---|---|---|---|---|---|
| 1 | ¿Qué información debe guardar cada nodo del árbol de palabras? | La palabra, sus hijos izquierdo y derecho, y una frecuencia para duplicados. | Sí | Se sustituyó el dato entero por `palabra` y se agregó `ocurrencias`. | Permite guardar texto y conservar las repeticiones sin crear nodos duplicados. |
| 2 | ¿Cómo insertar y comparar palabras en español? | Normalizar mayúsculas y comparar alfabéticamente; ir a la izquierda si es menor y a la derecha si es mayor. | Sí | Se usa `Locale.ROOT` para normalizar mayúsculas/minúsculas y `Collator` español para comparar; si coincide, aumenta la frecuencia. | Mantiene un orden consistente y evita que diferencias de mayúsculas creen nodos distintos. |
| 3 | ¿Cómo contar nodos totales e internos? | Contar recursivamente cada nodo; considerar interno al que tiene al menos un hijo. | Sí | Se añadieron conteos recursivos; el total corresponde a palabras distintas. | Separa la cantidad de nodos de las frecuencias y define con claridad qué es un nodo interno. |
| 4 | ¿Qué caso permite revisar duplicados, acentos y recorridos? | Probar `Sol luna árbol Árbol niño nino sol` y comprobar recorridos, conteos y máximo. | Sí, ejecutado | Se ejecutó el programa interactivamente y se comparó la salida con los valores esperados. | La prueba verifica duplicados por mayúsculas, acentos, orden alfabético y estadísticas. |
| 5 | ¿Cómo representar el árbol en la consola con ramas visibles? | Dibujar los niveles recursivamente y alinear los hijos con conectores `/` y `\\`. | Sí | Se agregó `imprimirArbol()` y se probó con un árbol de varias ramas. | Hace visible la estructura del árbol, no sólo el orden lineal de sus recorridos. |

## Prueba manual

Ejecuta `App` e ingresa:

```text
Sol luna árbol Árbol niño nino sol
```

Resultados que permiten revisar la implementación:

- Inorden: `árbol (2 veces) luna nino niño sol (2 veces)`
- Preorden: `sol (2 veces) luna árbol (2 veces) niño nino`
- Postorden: `árbol (2 veces) nino niño luna sol (2 veces)`
- Nodos distintos: `5`
- Nodos internos: `3`
- Máximo alfabético: `sol`
- Hojas: `árbol (2 veces)`, `nino`
- Nodos internos: `sol (2 veces)`, `luna`, `niño`


# arbol-binario
 ¿Qué problema resuelve este código?

Este codigo ordena las palabras insertadas y hace que la busqueda de palabras sea mas eficiente.

¿Entendemos cómo funciona?

Sí, entendemos que el código usa un árbol binario de búsqueda  para organizar cada palabra de la oración en nodos, facilitando su posterior análisis y búsqueda rápida. 

¿Qué entradas necesita?

Necesita que se escriba una oración.

¿Qué resultado produce?

Un arbol binario con las palabras de la oración que se insertó.

¿Encontramos algún error?

El código no organizaba correctamnete las palabras en el árbol en orden alfabético, además no imprimía correctamente el diagrama del código.

¿Qué modificación realizamos?

Reesrtructuramos la parte del código que organizaba el árbol en orden alfabético y buscamos otra manera en la que podíamos imprimir el diagrama en la consola.

¿Por qué realizamos esa modificación?

Para que el árbol organice las palabras de manera correcta y el diagrama del árbol se imprimiera correctamente y se viera mejor estéticamente, a pesar de que no se realizó de manera gráfica. 



REFLEXIONES:

ROCIO CERON #00571949

La IA me ayudó a hacer la estructura del árbol binario y los recorridos. Tuve que revisar y corregir algunas partes porque al principio el árbol no se mostraba como yo quería. Durante el proceso aprendí mejor cómo se acomodan los nodos y cómo funcionan los recorridos de preorden, inorden y postorden. También aprendí que es importante revisar el código que genera la IA y no solo copiarlo.

Diego Alberto Cervantes Funes #00594828

Use la IA mas que todo para verificar que todo estuviera correctamente. Tambien, la use para editar como se miraba el arbol graficamente en el sistema, ya que se miraba de una forma que no nos gustaba. Es importante no solo guiarse de la capacidad de la IA sino tambien de nuestros conocimientos y de lo que sabemos programar. Hay que usarla como un ayudante no como la persona que hace todo.

Edwin Ulises Pedrero Chávez #00476339 

La IA me sirvió bastante para armar la parte de insertar las palabras en el árbol (decidir si iban a la izquierda o a la derecha) y para entender cómo se hacen los recorridos inorden, preorden y postorden, que al principio se me hacían un poco confusos. Lo que sí tuve que revisar con cuidado fue el tema de las palabras repetidas y las mayúsculas/signos de puntuación, porque la primera versión que me dio no los tomaba en cuenta bien, y me di cuenta al probar el programa con oraciones reales. Al final aprendí que la IA ayuda mucho a avanzar más rápido, pero igual tienes que entender bien lo que está pasando para poder detectar cuando algo no funciona como debería y corregirlo tú mismo.

Julio Olguín #00579148

Utilicé la IA domo una herramienta para modificar un código base de un árbol binario y poder realizar métodos más complejos como permitir al usuario insertar lo que debe ir dentro del árbol, así como el orden que tiene que impirmir la información, la palabra más repetida, entre otros. Me ayudó a facilitar el proceso de realizar el código, y sirvió como un apoyo para entender y mantener la lógica del código base para aplicarla en este proyecto.


