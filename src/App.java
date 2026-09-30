import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ArbolBinario arbol = new ArbolBinario();
        Console consola = System.console();
        Scanner scanner = consola == null
            ? new Scanner(System.in, StandardCharsets.UTF_8)
            : new Scanner(System.in, consola.charset());

        System.out.print("Ingresa una oración: ");
        String oracion = scanner.nextLine();

        String[] palabras = oracion.split("[^\\p{L}\\p{M}]+");
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                arbol.insertar(palabra);
            }
        }

        System.out.println("\n--- Árbol binario ---");
        arbol.imprimirArbol();

        System.out.println("\n--- Recorridos del Árbol ---");
        System.out.print("InOrden: ");
        arbol.inOrden();

        System.out.print("PreOrden: ");
        arbol.preOrden();

        System.out.print("PostOrden: ");
        arbol.postOrden();

        System.out.println("\n--- Análisis ---");
        System.out.println("Total de nodos (palabras distintas): " + arbol.contarNodos());
        System.out.println("Nodos internos: " + arbol.contarNodosInternos());

        String maxima = arbol.palabraMaxima();
        System.out.println("Palabra con máximo valor alfabético: "
            + (maxima == null ? "(ninguna)" : maxima));

        System.out.print("Hojas: ");
        arbol.mostrarHojas();
        System.out.print("Nodos internos: ");
        arbol.mostrarNodosInternos();

        System.out.println("\nNota: las palabras repetidas no crean nodos; se muestra su frecuencia.");
        scanner.close();
    }
}
