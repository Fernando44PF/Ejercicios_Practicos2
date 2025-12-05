/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Ejercicio3;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * EJERCICIO 3: Aplicación de Gestión de Conjuntos Java Swing
 * 
 * Implementa una aplicación donde utilices conjunto de Java, voltea a tu alrededor para que
 * puedas implementar:
 * • Utilizar Swing Java
 * • Manejar al menos cinco datos de información
 * • Emplear al menos seis operaciones que manejen los conjuntos
 * • Documenta cada parte de tu código.
 * 
 * @author Fernando Miguel Olvera Juárez
 * @version 1.0
 */
public class Ejercicio3 extends JFrame {
    
    // =========================================================================
    // DECLARACIÓN DE CONJUNTOS - 5 DATOS DE INFORMACIÓN DEL ENTORNO
    // =========================================================================
    
    /** Conjunto 1: Frutas comunes en la alimentación diaria */
    private Set<String> frutas = new HashSet<>();
    
    /** Conjunto 2: Verduras básicas para una dieta saludable */
    private Set<String> verduras = new HashSet<>();
    
    /** Conjunto 3: Animales domésticos y de granja comunes */
    private Set<String> animales = new HashSet<>();
    
    /** Conjunto 4: Países de habla hispana importantes */
    private Set<String> paises = new HashSet<>();
    
    /** Conjunto 5: Colores primarios y secundarios básicos */
    private Set<String> colores = new HashSet<>();
    
    // =========================================================================
    // COMPONENTES DE LA INTERFAZ SWING
    // =========================================================================
    
    /** Área de texto para mostrar resultados de operaciones */
    private JTextArea resultadoArea;
    
    /** ComboBox para seleccionar el conjunto activo */
    private JComboBox<String> comboBox;
    
    /** Botones para las 6 operaciones de conjuntos */
    private JButton unionBtn, interBtn, difBtn, difSimBtn, subBtn, cardBtn;
    
    /** Botones para gestión de elementos en conjuntos */
    private JButton agregarBtn, eliminarBtn;
    
    /** Campo de texto para entrada de nuevos elementos */
    private JTextField textoField;
    
    // =========================================================================
    // CONSTRUCTOR PRINCIPAL
    // =========================================================================
    
    /**
     * Constructor que inicializa la aplicación de gestión de conjuntos.
     * Configura la interfaz gráfica, carga los datos y establece los eventos.
     */
    public Ejercicio3() {
        super("Gestión de Conjuntos - Java Swing");
        
        // Inicializar los 5 conjuntos con datos del entorno
        cargarDatos();
        
        // Configurar la interfaz gráfica Swing
        configurarInterfaz();
        
        // Establecer los manejadores de eventos
        configurarEventos();
        
        // Mostrar información inicial
        mostrarInformacionInicial();
        
        // Configurar propiedades de la ventana
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // =========================================================================
    // MÉTODO 1: CARGA DE DATOS - 5 CONJUNTOS DE INFORMACIÓN
    // =========================================================================
    
    /**
     * Inicializa los 5 conjuntos con datos del entorno real.
     * Cada conjunto contiene 5 elementos representativos.
     */
    private void cargarDatos() {
        // Conjunto 1: Frutas (alimentos comunes)
        frutas.add("Manzana");
        frutas.add("Banana");
        frutas.add("Naranja");
        frutas.add("Uva");
        frutas.add("Mango");
        
        // Conjunto 2: Verduras (dieta saludable)
        verduras.add("Zanahoria");
        verduras.add("Tomate");
        verduras.add("Lechuga");
        verduras.add("Cebolla");
        verduras.add("Papa");
        
        // Conjunto 3: Animales (domésticos y de granja)
        animales.add("Perro");
        animales.add("Gato");
        animales.add("Pájaro");
        animales.add("Peces");
        animales.add("Conejo");
        
        // Conjunto 4: Países (hispanohablantes)
        paises.add("México");
        paises.add("España");
        paises.add("Argentina");
        paises.add("Colombia");
        paises.add("Chile");
        
        // Conjunto 5: Colores (básicos)
        colores.add("Rojo");
        colores.add("Azul");
        colores.add("Verde");
        colores.add("Amarillo");
        colores.add("Negro");
    }
    
    // =========================================================================
    // MÉTODO 2: CONFIGURACIÓN DE INTERFAZ SWING
    // =========================================================================
    
    /**
     * Configura todos los componentes de la interfaz gráfica Swing.
     * Organiza los paneles, botones y áreas de texto.
     */
    private void configurarInterfaz() {
        // Establecer layout principal
        setLayout(new BorderLayout());
        
        // ================= PANEL SUPERIOR =================
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Conjunto seleccionado:"));
        
        // ComboBox para seleccionar entre los 5 conjuntos
        comboBox = new JComboBox<>(new String[]{"Frutas", "Verduras", "Animales", "Países", "Colores"});
        topPanel.add(comboBox);
        
        // ================= PANEL DE OPERACIONES =================
        JPanel opPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        opPanel.setBorder(BorderFactory.createTitledBorder("6 Operaciones de Conjuntos"));
        
        // Crear los 6 botones de operaciones
        unionBtn = crearBoton("Unión (A ∪ B)", Color.GREEN);
        interBtn = crearBoton("Intersección (A ∩ B)", Color.BLUE);
        difBtn = crearBoton("Diferencia (A - B)", Color.ORANGE);
        difSimBtn = crearBoton("Dif. Simétrica (A Δ B)", Color.MAGENTA);
        subBtn = crearBoton("Subconjunto (A ⊆ B)", Color.CYAN);
        cardBtn = crearBoton("Cardinalidad (|A|)", Color.GRAY);
        
        // Agregar botones al panel
        opPanel.add(unionBtn);
        opPanel.add(interBtn);
        opPanel.add(difBtn);
        opPanel.add(difSimBtn);
        opPanel.add(subBtn);
        opPanel.add(cardBtn);
        
        // ================= PANEL DE GESTIÓN =================
        JPanel gestionPanel = new JPanel();
        gestionPanel.setBorder(BorderFactory.createTitledBorder("Gestión de Elementos"));
        
        // Componentes para agregar/eliminar elementos
        textoField = new JTextField(15);
        agregarBtn = crearBoton("Agregar", new Color(76, 175, 80));
        eliminarBtn = crearBoton("Eliminar", new Color(244, 67, 54));
        
        gestionPanel.add(new JLabel("Elemento:"));
        gestionPanel.add(textoField);
        gestionPanel.add(agregarBtn);
        gestionPanel.add(eliminarBtn);
        
        // ================= ÁREA DE RESULTADOS =================
        resultadoArea = new JTextArea(15, 40);
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(resultadoArea);
        
        // ================= ORGANIZACIÓN FINAL =================
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(opPanel, BorderLayout.CENTER);
        leftPanel.add(gestionPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(scroll, BorderLayout.CENTER);
    }
    
    /**
     * Método auxiliar para crear botones con estilo uniforme.
     * @param texto Texto que aparece en el botón
     * @param color Color de fondo del botón
     * @return JButton configurado
     */
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 11));
        return boton;
    }
    
    // =========================================================================
    // MÉTODO 3: CONFIGURACIÓN DE EVENTOS - 6 OPERACIONES DE CONJUNTOS
    // =========================================================================
    
    /**
     * Configura los manejadores de eventos para los 6 botones de operaciones
     * y los botones de gestión.
     */
    private void configurarEventos() {
        // ========== OPERACIÓN 1: UNIÓN ==========
        /**
         * Operación de Unión: A ∪ B = {x | x ∈ A ∨ x ∈ B}
         * Combina todos los elementos de dos conjuntos sin duplicados.
         */
        unionBtn.addActionListener(e -> {
            Set<String> conjuntoA = obtenerConjuntoSeleccionado();
            Set<String> conjuntoB = obtenerConjuntoComparacion();
            Set<String> resultado = new TreeSet<>(conjuntoA);
            resultado.addAll(conjuntoB); // Operación addAll() para unión
            mostrarResultado("UNIÓN (A ∪ B)", resultado);
        });
        
        // ========== OPERACIÓN 2: INTERSECCIÓN ==========
        /**
         * Operación de Intersección: A ∩ B = {x | x ∈ A ∧ x ∈ B}
         * Encuentra elementos comunes entre dos conjuntos.
         */
        interBtn.addActionListener(e -> {
            Set<String> conjuntoA = obtenerConjuntoSeleccionado();
            Set<String> conjuntoB = obtenerConjuntoComparacion();
            Set<String> resultado = new TreeSet<>(conjuntoA);
            resultado.retainAll(conjuntoB); // Operación retainAll() para intersección
            mostrarResultado("INTERSECCIÓN (A ∩ B)", resultado);
        });
        
        // ========== OPERACIÓN 3: DIFERENCIA ==========
        /**
         * Operación de Diferencia: A - B = {x | x ∈ A ∧ x ∉ B}
         * Elementos que están en A pero no en B.
         */
        difBtn.addActionListener(e -> {
            Set<String> conjuntoA = obtenerConjuntoSeleccionado();
            Set<String> conjuntoB = obtenerConjuntoComparacion();
            Set<String> resultado = new TreeSet<>(conjuntoA);
            resultado.removeAll(conjuntoB); // Operación removeAll() para diferencia
            mostrarResultado("DIFERENCIA (A - B)", resultado);
        });
        
        // ========== OPERACIÓN 4: DIFERENCIA SIMÉTRICA ==========
        /**
         * Operación de Diferencia Simétrica: A Δ B = (A - B) ∪ (B - A)
         * Elementos que están en A o en B, pero no en ambos.
         */
        difSimBtn.addActionListener(e -> {
            Set<String> conjuntoA = obtenerConjuntoSeleccionado();
            Set<String> conjuntoB = obtenerConjuntoComparacion();
            Set<String> resultado = new TreeSet<>(conjuntoA);
            Set<String> temp = new TreeSet<>(conjuntoB);
            resultado.removeAll(conjuntoB); // A - B
            temp.removeAll(conjuntoA);      // B - A
            resultado.addAll(temp);         // (A - B) ∪ (B - A)
            mostrarResultado("DIFERENCIA SIMÉTRICA (A Δ B)", resultado);
        });
        
        // ========== OPERACIÓN 5: SUBCONJUNTO ==========
        /**
         * Verificación de Subconjunto: A ⊆ B
         * Retorna true si todos los elementos de A están en B.
         */
        subBtn.addActionListener(e -> {
            Set<String> conjuntoA = obtenerConjuntoSeleccionado();
            Set<String> conjuntoB = obtenerConjuntoComparacion();
            boolean esSubconjunto = conjuntoB.containsAll(conjuntoA); // containsAll() para subconjunto
            resultadoArea.setText("¿A es subconjunto de B?\n" + 
                                  "A = " + conjuntoA + "\n" +
                                  "B = " + conjuntoB + "\n" +
                                  "Resultado: " + esSubconjunto);
        });
        
        // ========== OPERACIÓN 6: CARDINALIDAD ==========
        /**
         * Operación de Cardinalidad: |A|
         * Retorna el número de elementos en el conjunto.
         */
        cardBtn.addActionListener(e -> {
            Set<String> conjunto = obtenerConjuntoSeleccionado();
            int cardinalidad = conjunto.size(); // size() para cardinalidad
            resultadoArea.setText("CARDINALIDAD |A|\n" +
                                 "Conjunto: " + conjunto + "\n" +
                                 "Número de elementos: " + cardinalidad);
        });
        
        // ========== GESTIÓN: AGREGAR ELEMENTO ==========
        /**
         * Agrega un nuevo elemento al conjunto seleccionado.
         * Usa el método add() de HashSet.
         */
        agregarBtn.addActionListener(e -> {
            String elemento = textoField.getText().trim();
            if (!elemento.isEmpty()) {
                obtenerConjuntoActual().add(elemento); // add() para agregar
                textoField.setText("");
                mostrarInformacionInicial();
            }
        });
        
        // ========== GESTIÓN: ELIMINAR ELEMENTO ==========
        /**
         * Elimina un elemento del conjunto seleccionado.
         * Usa el método remove() de HashSet.
         */
        eliminarBtn.addActionListener(e -> {
            String elemento = textoField.getText().trim();
            if (!elemento.isEmpty()) {
                obtenerConjuntoActual().remove(elemento); // remove() para eliminar
                textoField.setText("");
                mostrarInformacionInicial();
            }
        });
        
        // Evento para Enter en campo de texto
        textoField.addActionListener(e -> agregarBtn.doClick());
    }
    
    // =========================================================================
    // MÉTODOS AUXILIARES PARA MANEJO DE CONJUNTOS
    // =========================================================================
    
    /**
     * Obtiene una copia del conjunto seleccionado en el ComboBox.
     * @return Copia del conjunto seleccionado
     */
    private Set<String> obtenerConjuntoSeleccionado() {
        String seleccion = (String) comboBox.getSelectedItem();
        // Retorna nueva instancia para no modificar el original
        return new HashSet<>(obtenerConjuntoPorNombre(seleccion));
    }
    
    /**
     * Obtiene la referencia al conjunto actual para modificaciones.
     * @return Referencia al conjunto original
     */
    private Set<String> obtenerConjuntoActual() {
        String seleccion = (String) comboBox.getSelectedItem();
        return obtenerConjuntoPorNombre(seleccion);
    }
    
    /**
     * Obtiene el conjunto de comparación (siempre usa Frutas).
     * @return Conjunto para operaciones binarias
     */
    private Set<String> obtenerConjuntoComparacion() {
        return new HashSet<>(frutas);
    }
    
    /**
     * Método helper para obtener conjunto por nombre.
     * @param nombre Nombre del conjunto
     * @return Referencia al conjunto
     */
    private Set<String> obtenerConjuntoPorNombre(String nombre) {
        switch(nombre) {
            case "Frutas": return frutas;
            case "Verduras": return verduras;
            case "Animales": return animales;
            case "Países": return paises;
            case "Colores": return colores;
            default: return frutas;
        }
    }
    
    // =========================================================================
    // MÉTODOS DE VISUALIZACIÓN
    // =========================================================================
    
    /**
     * Muestra información inicial sobre los 5 conjuntos disponibles.
     */
    private void mostrarInformacionInicial() {
        resultadoArea.setText("=== SISTEMA DE GESTIÓN DE CONJUNTOS ===\n\n");
        resultadoArea.append("CONJUNTOS DISPONIBLES (5 datos del entorno):\n\n");
        resultadoArea.append("1. FRUTAS: " + frutas + "\n");
        resultadoArea.append("   Elementos: " + frutas.size() + "\n\n");
        resultadoArea.append("2. VERDURAS: " + verduras + "\n");
        resultadoArea.append("   Elementos: " + verduras.size() + "\n\n");
        resultadoArea.append("3. ANIMALES: " + animales + "\n");
        resultadoArea.append("   Elementos: " + animales.size() + "\n\n");
        resultadoArea.append("4. PAÍSES: " + paises + "\n");
        resultadoArea.append("   Elementos: " + paises.size() + "\n\n");
        resultadoArea.append("5. COLORES: " + colores + "\n");
        resultadoArea.append("   Elementos: " + colores.size() + "\n\n");
        resultadoArea.append("OPERACIONES DISPONIBLES (6 operaciones):\n");
        resultadoArea.append("• Unión (A ∪ B)\n");
        resultadoArea.append("• Intersección (A ∩ B)\n");
        resultadoArea.append("• Diferencia (A - B)\n");
        resultadoArea.append("• Diferencia Simétrica (A Δ B)\n");
        resultadoArea.append("• Subconjunto (A ⊆ B)\n");
        resultadoArea.append("• Cardinalidad (|A|)\n");
    }
    
    /**
     * Muestra el resultado de una operación en el área de texto.
     * @param operacion Nombre de la operación realizada
     * @param resultado Conjunto resultante
     */
    private void mostrarResultado(String operacion, Set<String> resultado) {
        resultadoArea.setText("=== RESULTADO DE OPERACIÓN ===\n\n");
        resultadoArea.append("Operación: " + operacion + "\n\n");
        resultadoArea.append("Conjunto A: " + obtenerConjuntoSeleccionado() + "\n");
        resultadoArea.append("Conjunto B: " + obtenerConjuntoComparacion() + "\n\n");
        resultadoArea.append("Resultado: " + resultado + "\n\n");
        resultadoArea.append("Cardinalidad del resultado: " + resultado.size() + " elementos");
    }
    
    // =========================================================================
    // MÉTODO PRINCIPAL DE EJECUCIÓN
    // =========================================================================
    
    /**
     * Método principal que inicia la aplicación.
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            Ejercicio3 app = new Ejercicio3();
            app.setVisible(true);
        });
    }
}
