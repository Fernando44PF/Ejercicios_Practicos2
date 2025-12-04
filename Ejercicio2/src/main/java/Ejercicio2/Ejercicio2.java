/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Ejercicio2;

/**
 * Visualizador de Árbol Binario de Búsqueda (ABB)
 * 
 * Aplicación dinámica para la comprensión de conceptos de árboles,
 * árboles binarios y árboles binarios de búsqueda. Implementa
 * operaciones de agregar nodo, eliminar nodo, buscar nodo y
 * recorridos InOrden, PreOrden y PostOrden con pruebas
 * predefinidas para validación.
 * 
 * @author Fernando Miguel Olvera Juárez
 * @version 1.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

public class Ejercicio2 extends JFrame {
    // Clase Nodo para el árbol
    class Nodo { 
        int valor;
        Nodo izquierdo, derecho;
        
        Nodo(int valor) {
            this.valor = valor;
            izquierdo = derecho = null;
        }
    }
    
    // Componentes de la interfaz
    private Nodo raiz;
    private ArbolPanel panelArbol;
    private JTextField valorField;
    private JButton insertarBtn, eliminarBtn, buscarBtn, limpiarBtn;
    private JTextArea resultadoArea;
    private JCheckBox inordenCheck, preordenCheck, postordenCheck;
    private JScrollPane scrollArbol;
    
    // Colores para resaltar nodos
    private Color colorNormal = Color.WHITE;
    private Color colorResaltado = Color.RED;
    private Nodo nodoResaltado = null;
    
    public Ejercicio2() {
        super("Visualizador de Árbol Binario de Búsqueda (ABB)");
        raiz = null;
        initComponents();
        setupLayout();
        setupListeners();
        setupWindow();
    }
    
    private void initComponents() {
        // Panel para dibujar el árbol
        panelArbol = new ArbolPanel();
        panelArbol.setBackground(Color.WHITE);
        scrollArbol = new JScrollPane(panelArbol);
        scrollArbol.setPreferredSize(new Dimension(600, 400));
        
        // Campo de entrada para valores
        valorField = new JTextField(10);
        valorField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Botones de operaciones
        insertarBtn = new JButton("+ Insertar");
        eliminarBtn = new JButton("- Eliminar");
        buscarBtn = new JButton("Q Buscar");
        limpiarBtn = new JButton("□ Limpiar Árbol");
        
        // Aplicar colores a los botones
        insertarBtn.setBackground(new Color(76, 175, 80));
        insertarBtn.setForeground(Color.WHITE);
        eliminarBtn.setBackground(new Color(244, 67, 54));
        eliminarBtn.setForeground(Color.WHITE);
        buscarBtn.setBackground(new Color(33, 150, 243));
        buscarBtn.setForeground(Color.WHITE);
        limpiarBtn.setBackground(new Color(255, 193, 7));
        
        // Área de texto para resultados
        resultadoArea = new JTextArea(10, 30);
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultadoArea.setBackground(new Color(240, 240, 240));
        resultadoArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Checkboxes para recorridos
        inordenCheck = new JCheckBox("Recorrido Inorden");
        preordenCheck = new JCheckBox("Recorrido Preorden");
        postordenCheck = new JCheckBox("Recorrido Postorden");
        
        // Configurar fuente
        Font labelFont = new Font("Arial", Font.BOLD, 12);
        inordenCheck.setFont(labelFont);
        preordenCheck.setFont(labelFont);
        postordenCheck.setFont(labelFont);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior con controles
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // Panel de valor y operaciones
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Operaciones del Árbol"));
        
        JPanel valorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        valorPanel.add(new JLabel("Valor:"));
        valorPanel.add(valorField);
        controlPanel.add(valorPanel);
        
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        botonesPanel.add(insertarBtn);
        botonesPanel.add(eliminarBtn);
        botonesPanel.add(buscarBtn);
        botonesPanel.add(limpiarBtn);
        controlPanel.add(botonesPanel);
        
        topPanel.add(controlPanel, BorderLayout.NORTH);
        
        // Panel de recorridos
        JPanel recorridosPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        recorridosPanel.setBorder(BorderFactory.createTitledBorder("Recorridos"));
        recorridosPanel.add(inordenCheck);
        recorridosPanel.add(preordenCheck);
        recorridosPanel.add(postordenCheck);
        
        topPanel.add(recorridosPanel, BorderLayout.CENTER);
        
        // Panel de botones para ejecutar recorridos
        JButton ejecutarRecorridosBtn = new JButton("Ejecutar Recorridos Seleccionados");
        ejecutarRecorridosBtn.setBackground(new Color(156, 39, 176));
        ejecutarRecorridosBtn.setForeground(Color.WHITE);
        ejecutarRecorridosBtn.addActionListener(e -> ejecutarRecorridos());
        
        JPanel recorridosBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        recorridosBtnPanel.add(ejecutarRecorridosBtn);
        topPanel.add(recorridosBtnPanel, BorderLayout.SOUTH);
        
        // Panel principal dividido
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Panel izquierdo para el árbol
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(scrollArbol, BorderLayout.CENTER);
        
        // Panel derecho para resultados
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Resultados y Pruebas"));
        
        // Área de resultados con scroll
        JScrollPane scrollResultados = new JScrollPane(resultadoArea);
        rightPanel.add(scrollResultados, BorderLayout.CENTER);
        
        // Panel para botones de pruebas predefinidas
        JPanel pruebasPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        pruebasPanel.setBorder(BorderFactory.createTitledBorder("Pruebas Predefinidas"));
        
        // Botones para pruebas de inserción
        JButton p1_1Btn = new JButton("P1.1: Insercion Balanceada");
        JButton p1_2Btn = new JButton("P1.2: Solo Derecha");
        JButton p1_3Btn = new JButton("P1.3: Solo Izquierda");
        JButton p1_4Btn = new JButton("P1.4: Duplicados");
        
        // Botones para pruebas de recorrido
        JButton p2_1Btn = new JButton("P2.1: Inorden");
        JButton p2_2Btn = new JButton("P2.2: Preorden");
        JButton p2_3Btn = new JButton("P2.3: Postorden");
        JButton p3_1Btn = new JButton("P3: Busquedas");
        
        // Añadir action listeners
        p1_1Btn.addActionListener(e -> pruebaP1_1());
        p1_2Btn.addActionListener(e -> pruebaP1_2());
        p1_3Btn.addActionListener(e -> pruebaP1_3());
        p1_4Btn.addActionListener(e -> pruebaP1_4());
        p2_1Btn.addActionListener(e -> pruebaP2_1());
        p2_2Btn.addActionListener(e -> pruebaP2_2());
        p2_3Btn.addActionListener(e -> pruebaP2_3());
        p3_1Btn.addActionListener(e -> pruebaP3());
        
        pruebasPanel.add(p1_1Btn);
        pruebasPanel.add(p1_2Btn);
        pruebasPanel.add(p1_3Btn);
        pruebasPanel.add(p1_4Btn);
        pruebasPanel.add(p2_1Btn);
        pruebasPanel.add(p2_2Btn);
        pruebasPanel.add(p2_3Btn);
        pruebasPanel.add(p3_1Btn);
        
        rightPanel.add(pruebasPanel, BorderLayout.SOUTH);
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(500);
        
        // Añadir todo al frame
        add(topPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        
        // Panel de información
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(new Color(225, 245, 254));
        infoPanel.add(new JLabel("Instrucciones: Ingresa un valor y selecciona una operación. Usa las pruebas predefinidas para verificar el funcionamiento."));
        add(infoPanel, BorderLayout.SOUTH);
    }
    
    private void setupListeners() {
        // Insertar
        insertarBtn.addActionListener(e -> {
            try {
                int valor = Integer.parseInt(valorField.getText());
                insertar(valor);
                valorField.setText("");
                limpiarResaltado();
                repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Eliminar
        eliminarBtn.addActionListener(e -> {
            try {
                int valor = Integer.parseInt(valorField.getText());
                eliminar(valor);
                valorField.setText("");
                limpiarResaltado();
                repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Buscar
        buscarBtn.addActionListener(e -> {
            try {
                int valor = Integer.parseInt(valorField.getText());
                buscar(valor);
                valorField.setText("");
                repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Limpiar
        limpiarBtn.addActionListener(e -> {
            raiz = null;
            limpiarResaltado();
            resultadoArea.setText("");
            repaint();
        });
        
        // Enter en el campo de texto
        valorField.addActionListener(e -> insertarBtn.doClick());
    }
    
    private void setupWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));
    }
    
    // Métodos del árbol
    private void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
        resultadoArea.append("Insertado: " + valor + "\n");
    }
    
    private Nodo insertarRecursivo(Nodo nodo, int valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }
        
        if (valor < nodo.valor) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.derecho = insertarRecursivo(nodo.derecho, valor);
        } else {
            // Valor duplicado (dependiendo de la implementación)
            resultadoArea.append("Valor duplicado: " + valor + " (se inserta a la derecha)\n");
            nodo.derecho = insertarRecursivo(nodo.derecho, valor);
        }
        
        return nodo;
    }
    
    private void eliminar(int valor) {
        if (buscarNodo(raiz, valor) != null) {
            raiz = eliminarRecursivo(raiz, valor);
            resultadoArea.append("Eliminado: " + valor + "\n");
        } else {
            resultadoArea.append("No encontrado para eliminar: " + valor + "\n");
        }
    }
    
    private Nodo eliminarRecursivo(Nodo nodo, int valor) {
        if (nodo == null) return null;
        
        if (valor < nodo.valor) {
            nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.derecho = eliminarRecursivo(nodo.derecho, valor);
        } else {
            // Caso 1: Nodo hoja o con un solo hijo
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            } else if (nodo.derecho == null) {
                return nodo.izquierdo;
            }
            
            // Caso 2: Nodo con dos hijos
            nodo.valor = encontrarMinimo(nodo.derecho).valor;
            nodo.derecho = eliminarRecursivo(nodo.derecho, nodo.valor);
        }
        
        return nodo;
    }
    
    private Nodo encontrarMinimo(Nodo nodo) {
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return nodo;
    }
    
    private void buscar(int valor) {
        Nodo encontrado = buscarNodo(raiz, valor);
        if (encontrado != null) {
            nodoResaltado = encontrado;
            resultadoArea.append("Encontrado: " + valor + " (resaltado en rojo)\n");
        } else {
            resultadoArea.append("No encontrado: " + valor + "\n");
        }
    }
    
    private Nodo buscarNodo(Nodo nodo, int valor) {
        if (nodo == null || nodo.valor == valor) {
            return nodo;
        }
        
        if (valor < nodo.valor) {
            return buscarNodo(nodo.izquierdo, valor);
        } else {
            return buscarNodo(nodo.derecho, valor);
        }
    }
    
    private void limpiarResaltado() {
        nodoResaltado = null;
    }
    
    // Métodos de recorrido
    private String recorridoInorden() {
        StringBuilder sb = new StringBuilder();
        inordenRecursivo(raiz, sb);
        return sb.toString().trim();
    }
    
    private void inordenRecursivo(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            inordenRecursivo(nodo.izquierdo, sb);
            sb.append(nodo.valor).append(" ");
            inordenRecursivo(nodo.derecho, sb);
        }
    }
    
    private String recorridoPreorden() {
        StringBuilder sb = new StringBuilder();
        preordenRecursivo(raiz, sb);
        return sb.toString().trim();
    }
    
    private void preordenRecursivo(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            sb.append(nodo.valor).append(" ");
            preordenRecursivo(nodo.izquierdo, sb);
            preordenRecursivo(nodo.derecho, sb);
        }
    }
    
    private String recorridoPostorden() {
        StringBuilder sb = new StringBuilder();
        postordenRecursivo(raiz, sb);
        return sb.toString().trim();
    }
    
    private void postordenRecursivo(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            postordenRecursivo(nodo.izquierdo, sb);
            postordenRecursivo(nodo.derecho, sb);
            sb.append(nodo.valor).append(" ");
        }
    }
    
    private void ejecutarRecorridos() {
        resultadoArea.append("\n=== RECORRIDOS ===\n");
        
        if (inordenCheck.isSelected()) {
            resultadoArea.append("Inorden: " + recorridoInorden() + "\n");
        }
        
        if (preordenCheck.isSelected()) {
            resultadoArea.append("Preorden: " + recorridoPreorden() + "\n");
        }
        
        if (postordenCheck.isSelected()) {
            resultadoArea.append("Postorden: " + recorridoPostorden() + "\n");
        }
        
        if (!inordenCheck.isSelected() && !preordenCheck.isSelected() && !postordenCheck.isSelected()) {
            resultadoArea.append("Selecciona al menos un tipo de recorrido\n");
        }
    }
    
    // Métodos de pruebas predefinidas
    private void pruebaP1_1() {
        limpiarArbol();
        int[] valores = {50, 30, 70, 20, 40, 60, 80};
        resultadoArea.setText("=== PRUEBA P1.1: INSERCIÓN BALANCEADA ===\n");
        resultadoArea.append("Insertando: 50, 30, 70, 20, 40, 60, 80\n");
        resultadoArea.append("Resultado esperado: Árbol completo y balanceado\n\n");
        
        for (int valor : valores) {
            insertar(valor);
        }
        repaint();
    }
    
    private void pruebaP1_2() {
        limpiarArbol();
        int[] valores = {10, 20, 30, 40, 50, 60, 70};
        resultadoArea.setText("=== PRUEBA P1.2: SOLO DERECHA ===\n");
        resultadoArea.append("Insertando: 10, 20, 30, 40, 50, 60, 70\n");
        resultadoArea.append("Resultado esperado: Árbol degenerado (solo a la derecha)\n\n");
        
        for (int valor : valores) {
            insertar(valor);
        }
        repaint();
    }
    
    private void pruebaP1_3() {
        limpiarArbol();
        int[] valores = {70, 60, 50, 40, 30, 20, 10};
        resultadoArea.setText("=== PRUEBA P1.3: SOLO IZQUIERDA ===\n");
        resultadoArea.append("Insertando: 70, 60, 50, 40, 30, 20, 10\n");
        resultadoArea.append("Resultado esperado: Árbol degenerado (solo a la izquierda)\n\n");
        
        for (int valor : valores) {
            insertar(valor);
        }
        repaint();
    }
    
    private void pruebaP1_4() {
        limpiarArbol();
        resultadoArea.setText("=== PRUEBA P1.4: VALORES DUPLICADOS ===\n");
        resultadoArea.append("Insertando: 50, 50\n");
        resultadoArea.append("Resultado esperado: El segundo 50 es insertado a la derecha\n\n");
        
        insertar(50);
        insertar(50);
        repaint();
    }
    
    private void pruebaP2_1() {
        resultadoArea.setText("=== PRUEBA P2.1: RECORRIDO INORDEN ===\n");
        resultadoArea.append("Secuencia: 50, 30, 70, 20, 40, 60, 80\n");
        resultadoArea.append("Resultado esperado: 20 30 40 50 60 70 80\n");
        resultadoArea.append("Resultado obtenido: " + recorridoInorden() + "\n");
        
        // Verificar
        String obtenido = recorridoInorden();
        if (obtenido.equals("20 30 40 50 60 70 80")) {
            resultadoArea.append("✓ PRUEBA EXITOSA\n");
        } else {
            resultadoArea.append("✗ PRUEBA FALLIDA\n");
        }
    }
    
    private void pruebaP2_2() {
        resultadoArea.setText("=== PRUEBA P2.2: RECORRIDO PREORDEN ===\n");
        resultadoArea.append("Secuencia: 50, 30, 70, 20, 40, 60, 80\n");
        resultadoArea.append("Resultado esperado: 50 30 20 40 70 60 80\n");
        resultadoArea.append("Resultado obtenido: " + recorridoPreorden() + "\n");
        
        // Verificar
        String obtenido = recorridoPreorden();
        if (obtenido.equals("50 30 20 40 70 60 80")) {
            resultadoArea.append("✓ PRUEBA EXITOSA\n");
        } else {
            resultadoArea.append("✗ PRUEBA FALLIDA\n");
        }
    }
    
    private void pruebaP2_3() {
        resultadoArea.setText("=== PRUEBA P2.3: RECORRIDO POSTORDEN ===\n");
        resultadoArea.append("Secuencia: 50, 30, 70, 20, 40, 60, 80\n");
        resultadoArea.append("Resultado esperado: 20 40 30 60 80 70 50\n");
        resultadoArea.append("Resultado obtenido: " + recorridoPostorden() + "\n");
        
        // Verificar
        String obtenido = recorridoPostorden();
        if (obtenido.equals("20 40 30 60 80 70 50")) {
            resultadoArea.append("✓ PRUEBA EXITOSA\n");
        } else {
            resultadoArea.append("✗ PRUEBA FALLIDA\n");
        }
    }
    
    private void pruebaP3() {
        resultadoArea.setText("=== PRUEBA P3: BÚSQUEDAS ===\n");
        resultadoArea.append("Árbol: 50, 30, 70, 20, 40, 60, 80\n\n");
        
        // P3.1: Buscar la raíz (50)
        resultadoArea.append("P3.1 - Buscar 50 (raíz): ");
        buscar(50);
        repaint();
        
        // P3.2: Buscar hoja (80)
        resultadoArea.append("P3.2 - Buscar 80 (hoja derecha): ");
        buscar(80);
        repaint();
        
        // P3.3: Buscar nodo interno (30)
        resultadoArea.append("P3.3 - Buscar 30 (nodo interno): ");
        buscar(30);
        repaint();
        
        // P3.4: Buscar valor inexistente (99)
        resultadoArea.append("P3.4 - Buscar 99 (inexistente): ");
        buscar(99);
        repaint();
        
        // Limpiar resaltado después de mostrar
        Timer timer = new Timer(2000, e -> {//marca error aqui
            limpiarResaltado();
            repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void limpiarArbol() {
        raiz = null;
        limpiarResaltado();
        repaint();
    }
    
    // Clase para dibujar el árbol
    class ArbolPanel extends JPanel {
        private final int RADIO = 25;
        private final int ESPACIO_VERTICAL = 70;
        private final int ESPACIO_HORIZONTAL = 50;
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (raiz != null) {
                int x = getWidth() / 2;
                int y = 50;
                dibujarArbol(g2d, raiz, x, y, getWidth() / 4);
            } else {
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                g2d.setColor(Color.GRAY);
                g2d.drawString("Árbol vacío", getWidth() / 2 - 50, getHeight() / 2);
            }
        }
        
        private void dibujarArbol(Graphics2D g, Nodo nodo, int x, int y, int espacio) {
            if (nodo == null) return;
            
            // Dibujar conexiones con hijos
            if (nodo.izquierdo != null) {
                g.setColor(Color.BLACK);
                g.drawLine(x, y, x - espacio, y + ESPACIO_VERTICAL);
                dibujarArbol(g, nodo.izquierdo, x - espacio, y + ESPACIO_VERTICAL, espacio / 2);
            }
            
            if (nodo.derecho != null) {
                g.setColor(Color.BLACK);
                g.drawLine(x, y, x + espacio, y + ESPACIO_VERTICAL);
                dibujarArbol(g, nodo.derecho, x + espacio, y + ESPACIO_VERTICAL, espacio / 2);
            }
            
            // Dibujar el nodo
            if (nodo == nodoResaltado) {
                g.setColor(colorResaltado);
            } else {
                g.setColor(colorNormal);
            }
            g.fillOval(x - RADIO, y - RADIO, RADIO * 2, RADIO * 2);
            
            // Borde del nodo
            g.setColor(Color.BLACK);
            g.drawOval(x - RADIO, y - RADIO, RADIO * 2, RADIO * 2);
            
            // Texto del valor
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            String texto = String.valueOf(nodo.valor);
            FontMetrics fm = g.getFontMetrics();
            int textoWidth = fm.stringWidth(texto);
            int textoHeight = fm.getHeight();
            g.drawString(texto, x - textoWidth / 2, y + textoHeight / 4);
        }
        
        @Override
        public Dimension getPreferredSize() {
            int altura = calcularAltura(raiz) * ESPACIO_VERTICAL + 100;
            return new Dimension(800, altura);
        }
        
        private int calcularAltura(Nodo nodo) {
            if (nodo == null) return 0;
            return 1 + Math.max(calcularAltura(nodo.izquierdo), calcularAltura(nodo.derecho));
        }
    }
    
    // Método principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            Ejercicio2 app = new Ejercicio2();
            app.setVisible(true);
        });
    }
}
