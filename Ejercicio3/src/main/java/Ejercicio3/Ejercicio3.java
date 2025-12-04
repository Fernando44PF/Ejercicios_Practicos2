/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Ejercicio3;

/**
 * Aplicación de Gestión de Conjuntos Java Swing
 * 
 * Esta aplicación permite gestionar conjuntos de datos del entorno
 * y realizar operaciones de conjuntos sobre ellos.
 * 
 * Fernando Miguel Olvera Juárez
 * @version 1.0
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Ejercicio3 extends JFrame {
    
    // Conjuntos de datos
    private Set<String> frutas = new HashSet<>();
    private Set<String> verduras = new HashSet<>();
    private Set<String> animales = new HashSet<>();
    private Set<String> paises = new HashSet<>();
    private Set<String> colores = new HashSet<>();
    
    // Componentes
    private JTextArea resultadoArea;
    private JComboBox<String> comboBox;
    private JButton unionBtn, interBtn, difBtn, difSimBtn, subBtn, cardBtn;
    private JButton agregarBtn, eliminarBtn;
    private JTextField textoField;
    
    public Ejercicio3() {
        super("Gestión de Conjuntos - Java Swing");
        
        // Inicializar datos
        cargarDatos();
        
        // Configurar interfaz
        setLayout(new BorderLayout());
        
        // Panel superior
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Conjunto:"));
        
        comboBox = new JComboBox<>(new String[]{"Frutas", "Verduras", "Animales", "Países", "Colores"});
        topPanel.add(comboBox);
        
        // Panel de operaciones
        JPanel opPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        opPanel.setBorder(BorderFactory.createTitledBorder("Operaciones"));
        
        unionBtn = new JButton("Unión");
        interBtn = new JButton("Intersección");
        difBtn = new JButton("Diferencia");
        difSimBtn = new JButton("Dif. Simétrica");
        subBtn = new JButton("Subconjunto");
        cardBtn = new JButton("Cardinalidad");
        
        opPanel.add(unionBtn);
        opPanel.add(interBtn);
        opPanel.add(difBtn);
        opPanel.add(difSimBtn);
        opPanel.add(subBtn);
        opPanel.add(cardBtn);
        
        // Panel de gestión
        JPanel gestionPanel = new JPanel();
        gestionPanel.setBorder(BorderFactory.createTitledBorder("Gestión"));
        
        textoField = new JTextField(15);
        agregarBtn = new JButton("Agregar");
        eliminarBtn = new JButton("Eliminar");
        
        gestionPanel.add(new JLabel("Elemento:"));
        gestionPanel.add(textoField);
        gestionPanel.add(agregarBtn);
        gestionPanel.add(eliminarBtn);
        
        // Área de resultados
        resultadoArea = new JTextArea(15, 40);
        resultadoArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(resultadoArea);
        
        // Organizar paneles
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(opPanel, BorderLayout.CENTER);
        leftPanel.add(gestionPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(scroll, BorderLayout.CENTER);
        
        // Configurar eventos
        configurarEventos();
        
        // Mostrar información inicial
        mostrarInfo();
        
        // Configurar ventana
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void cargarDatos() {
        // 5 conjuntos con datos del entorno
        frutas.add("Manzana");
        frutas.add("Banana");
        frutas.add("Naranja");
        frutas.add("Uva");
        frutas.add("Mango");
        
        verduras.add("Zanahoria");
        verduras.add("Tomate");
        verduras.add("Lechuga");
        verduras.add("Cebolla");
        verduras.add("Papa");
        
        animales.add("Perro");
        animales.add("Gato");
        animales.add("Pájaro");
        animales.add("Peces");
        animales.add("Conejo");
        
        paises.add("México");
        paises.add("España");
        paises.add("Argentina");
        paises.add("Colombia");
        paises.add("Chile");
        
        colores.add("Rojo");
        colores.add("Azul");
        colores.add("Verde");
        colores.add("Amarillo");
        colores.add("Negro");
    }
    
    private void configurarEventos() {
        // Operación 1: Unión
        unionBtn.addActionListener(e -> {
            Set<String> A = getConjuntoSeleccionado();
            Set<String> B = getOtroConjunto();
            Set<String> union = new HashSet<>(A);
            union.addAll(B);
            resultadoArea.setText("UNIÓN (A ∪ B):\n" + union);
        });
        
        // Operación 2: Intersección
        interBtn.addActionListener(e -> {
            Set<String> A = getConjuntoSeleccionado();
            Set<String> B = getOtroConjunto();
            Set<String> inter = new HashSet<>(A);
            inter.retainAll(B);
            resultadoArea.setText("INTERSECCIÓN (A ∩ B):\n" + inter);
        });
        
        // Operación 3: Diferencia
        difBtn.addActionListener(e -> {
            Set<String> A = getConjuntoSeleccionado();
            Set<String> B = getOtroConjunto();
            Set<String> dif = new HashSet<>(A);
            dif.removeAll(B);
            resultadoArea.setText("DIFERENCIA (A - B):\n" + dif);
        });
        
        // Operación 4: Diferencia Simétrica
        difSimBtn.addActionListener(e -> {
            Set<String> A = getConjuntoSeleccionado();
            Set<String> B = getOtroConjunto();
            Set<String> difSim = new HashSet<>(A);
            Set<String> temp = new HashSet<>(B);
            difSim.removeAll(B);
            temp.removeAll(A);
            difSim.addAll(temp);
            resultadoArea.setText("DIFERENCIA SIMÉTRICA (A Δ B):\n" + difSim);
        });
        
        // Operación 5: Subconjunto
        subBtn.addActionListener(e -> {
            Set<String> A = getConjuntoSeleccionado();
            Set<String> B = getOtroConjunto();
            boolean esSub = B.containsAll(A);
            resultadoArea.setText("¿A es subconjunto de B?\n" + esSub);
        });
        
        // Operación 6: Cardinalidad
        cardBtn.addActionListener(e -> {
            Set<String> conjunto = getConjuntoSeleccionado();
            resultadoArea.setText("CARDINALIDAD |A|:\n" + conjunto.size() + " elementos");
        });
        
        // Agregar elemento
        agregarBtn.addActionListener(e -> {
            String elemento = textoField.getText().trim();
            if (!elemento.isEmpty()) {
                getConjuntoActual().add(elemento);
                textoField.setText("");
                mostrarInfo();
            }
        });
        
        // Eliminar elemento
        eliminarBtn.addActionListener(e -> {
            String elemento = textoField.getText().trim();
            if (!elemento.isEmpty()) {
                getConjuntoActual().remove(elemento);
                textoField.setText("");
                mostrarInfo();
            }
        });
    }
    
    private Set<String> getConjuntoSeleccionado() {
        String seleccion = (String) comboBox.getSelectedItem();
        switch(seleccion) {
            case "Frutas": return new HashSet<>(frutas);
            case "Verduras": return new HashSet<>(verduras);
            case "Animales": return new HashSet<>(animales);
            case "Países": return new HashSet<>(paises);
            case "Colores": return new HashSet<>(colores);
            default: return new HashSet<>();
        }
    }
    
    private Set<String> getConjuntoActual() {
        String seleccion = (String) comboBox.getSelectedItem();
        switch(seleccion) {
            case "Frutas": return frutas;
            case "Verduras": return verduras;
            case "Animales": return animales;
            case "Países": return paises;
            case "Colores": return colores;
            default: return frutas;
        }
    }
    
    private Set<String> getOtroConjunto() {
        // Siempre usa "Frutas" como segundo conjunto para las operaciones
        return new HashSet<>(frutas);
    }
    
    private void mostrarInfo() {
        resultadoArea.setText("=== CONJUNTOS DISPONIBLES ===\n\n");
        resultadoArea.append("1. Frutas: " + frutas + "\n\n");
        resultadoArea.append("2. Verduras: " + verduras + "\n\n");
        resultadoArea.append("3. Animales: " + animales + "\n\n");
        resultadoArea.append("4. Países: " + paises + "\n\n");
        resultadoArea.append("5. Colores: " + colores + "\n\n");
        resultadoArea.append("Operaciones disponibles:\n");
        resultadoArea.append("- Unión, Intersección, Diferencia\n");
        resultadoArea.append("- Diferencia Simétrica, Subconjunto, Cardinalidad");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Ejercicio3 app = new Ejercicio3();
            app.setVisible(true);
        });
    }
}
