import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ArbolBinario {
    private static class Nodo {
        String palabra;
        int ocurrencias;
        Nodo izq;
        Nodo der;

        Nodo(String palabra) {
            this.palabra = palabra;
            this.ocurrencias = 1;
        }
    }

    private static class Dibujo {
        final List<String> lineas;
        final int ancho;
        final int centro;

        Dibujo(List<String> lineas, int ancho, int centro) {
            this.lineas = lineas;
            this.ancho = ancho;
            this.centro = centro;
        }
    }

    private final Collator comparador = Collator.getInstance(Locale.forLanguageTag("es"));
    private Nodo raiz;

    // Las palabras repetidas incrementan ocurrencias sin crear nodos adicionales.
    public void insertar(String palabra) {
        String normalizada = palabra.toLowerCase(Locale.ROOT);
        Nodo nuevo = new Nodo(normalizada);
        if (raiz == null) {
            raiz = nuevo;
            return;
        }

        Nodo actual = raiz;
        Nodo anterior = null;
        while (actual != null) {
            anterior = actual;
            int resultado = comparador.compare(normalizada, actual.palabra);
            if (resultado == 0) {
                actual.ocurrencias++;
                return;
            } else if (resultado < 0) {
                actual = actual.izq;
            } else {
                actual = actual.der;
            }
        }

        if (comparador.compare(normalizada, anterior.palabra) < 0) {
            anterior.izq = nuevo;
        } else {
            anterior.der = nuevo;
        }
    }

    public void imprimirArbol() {
        if (raiz == null) {
            System.out.println("(árbol vacío)");
            return;
        }

        Dibujo dibujo = dibujar(raiz);
        for (String linea : dibujo.lineas) {
            System.out.println(linea.stripTrailing());
        }
    }

    private Dibujo dibujar(Nodo nodo) {
        String etiqueta = nodo.palabra;
        if (nodo.ocurrencias > 1) {
            etiqueta += " (" + nodo.ocurrencias + ")";
        }

        Dibujo izquierda = nodo.izq == null ? null : dibujar(nodo.izq);
        Dibujo derecha = nodo.der == null ? null : dibujar(nodo.der);

        if (izquierda == null && derecha == null) {
            List<String> lineas = new ArrayList<>();
            lineas.add(etiqueta);
            return new Dibujo(lineas, etiqueta.length(), etiqueta.length() / 2);
        }

        int espacio = 3;
        int desplazamientoIzquierda = 0;
        int desplazamientoDerecha = izquierda == null ? 0 : izquierda.ancho + espacio;
        int centroIzquierda = izquierda == null ? -1
                : desplazamientoIzquierda + izquierda.centro;
        int centroDerecha = derecha == null ? -1
                : desplazamientoDerecha + derecha.centro;

        int centro;
        if (izquierda != null && derecha != null) {
            centro = (centroIzquierda + centroDerecha) / 2;
        } else if (izquierda != null) {
            centro = centroIzquierda + espacio;
        } else {
            centro = centroDerecha - espacio;
        }

        int inicioEtiqueta = centro - etiqueta.length() / 2;
        int ajuste = Math.max(0, -inicioEtiqueta);
        desplazamientoIzquierda += ajuste;
        desplazamientoDerecha += ajuste;
        centro += ajuste;
        inicioEtiqueta += ajuste;

        int ancho = Math.max(inicioEtiqueta + etiqueta.length(),
                Math.max(izquierda == null ? 0 : desplazamientoIzquierda + izquierda.ancho,
                        derecha == null ? 0 : desplazamientoDerecha + derecha.ancho));

        List<String> lineas = new ArrayList<>();
        char[] lineaRaiz = crearLinea(ancho);
        copiar(lineaRaiz, etiqueta, inicioEtiqueta);
        lineas.add(new String(lineaRaiz));

        char[] lineaRamas = crearLinea(ancho);
        if (izquierda != null) {
            lineaRamas[(centro + centroIzquierda + ajuste) / 2] = '/';
        }
        if (derecha != null) {
            lineaRamas[(centro + centroDerecha + ajuste) / 2] = '\\';
        }
        lineas.add(new String(lineaRamas).stripTrailing());

        int altoHijos = Math.max(izquierda == null ? 0 : izquierda.lineas.size(),
                derecha == null ? 0 : derecha.lineas.size());
        for (int fila = 0; fila < altoHijos; fila++) {
            char[] lineaHijos = crearLinea(ancho);
            if (izquierda != null && fila < izquierda.lineas.size()) {
                copiar(lineaHijos, izquierda.lineas.get(fila), desplazamientoIzquierda);
            }
            if (derecha != null && fila < derecha.lineas.size()) {
                copiar(lineaHijos, derecha.lineas.get(fila), desplazamientoDerecha);
            }
            lineas.add(new String(lineaHijos).stripTrailing());
        }

        return new Dibujo(lineas, ancho, centro);
    }

    private char[] crearLinea(int ancho) {
        char[] linea = new char[ancho];
        Arrays.fill(linea, ' ');
        return linea;
    }

    private void copiar(char[] destino, String texto, int inicio) {
        texto.getChars(0, texto.length(), destino, inicio);
    }

    public void inOrden() {
        inOrdenRec(raiz);
        System.out.println();
    }

    private void inOrdenRec(Nodo reco) {
        if (reco != null) {
            inOrdenRec(reco.izq);
            mostrarPalabra(reco);
            inOrdenRec(reco.der);
        }
    }

    public void preOrden() {
        preOrdenRec(raiz);
        System.out.println();
    }

    private void preOrdenRec(Nodo reco) {
        if (reco != null) {
            mostrarPalabra(reco);
            preOrdenRec(reco.izq);
            preOrdenRec(reco.der);
        }
    }

    public void postOrden() {
        postOrdenRec(raiz);
        System.out.println();
    }

    private void postOrdenRec(Nodo reco) {
        if (reco != null) {
            postOrdenRec(reco.izq);
            postOrdenRec(reco.der);
            mostrarPalabra(reco);
        }
    }

    public int contarNodos() {
        return contarNodosRec(raiz);
    }

    private int contarNodosRec(Nodo reco) {
        if (reco == null) {
            return 0;
        }
        return 1 + contarNodosRec(reco.izq) + contarNodosRec(reco.der);
    }

    public int contarNodosInternos() {
        return contarNodosInternosRec(raiz);
    }

    private int contarNodosInternosRec(Nodo reco) {
        if (reco == null || (reco.izq == null && reco.der == null)) {
            return 0;
        }
        return 1 + contarNodosInternosRec(reco.izq) + contarNodosInternosRec(reco.der);
    }

    public String palabraMaxima() {
        if (raiz == null) {
            return null;
        }

        Nodo actual = raiz;
        while (actual.der != null) {
            actual = actual.der;
        }
        return actual.palabra;
    }

    public void mostrarHojas() {
        mostrarHojasRec(raiz);
        System.out.println();
    }

    private void mostrarHojasRec(Nodo reco) {
        if (reco != null) {
            if (reco.izq == null && reco.der == null) {
                mostrarPalabra(reco);
            }
            mostrarHojasRec(reco.izq);
            mostrarHojasRec(reco.der);
        }
    }

    public void mostrarNodosInternos() {
        mostrarNodosInternosRec(raiz);
        System.out.println();
    }

    private void mostrarNodosInternosRec(Nodo reco) {
        if (reco != null) {
            if (reco.izq != null || reco.der != null) {
                mostrarPalabra(reco);
            }
            mostrarNodosInternosRec(reco.izq);
            mostrarNodosInternosRec(reco.der);
        }
    }

    private void mostrarPalabra(Nodo nodo) {
        System.out.print(nodo.palabra);
        if (nodo.ocurrencias > 1) {
            System.out.print(" (" + nodo.ocurrencias + " veces)");
        }
        System.out.print(" ");
    }
}