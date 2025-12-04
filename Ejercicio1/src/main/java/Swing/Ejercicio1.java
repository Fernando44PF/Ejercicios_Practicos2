/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Swing;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Simulador DOM - Generador de Páginas Web con Árbol
 * 
 * Esta aplicación implementa una simulación de creación de página web
 * a través de un árbol, similar al DOM (Document Object Model).
 * Permite visualizar la estructura jerárquica y el HTML generado
 * en tiempo real con operaciones de agregar, eliminar y editar nodos.
 * 
 * @author Fernando Miguel Olvera Juárez
 * @version 1.0
 */


public class Ejercicio1 extends JFrame {
    // Componentes principales
    private JTree domTree;
    private DefaultTreeModel treeModel;
    private JTextArea htmlView;
    private JTextField tagField, contentField;
    private JButton addButton;
    
    // Nodo raíz del árbol DOM
    private DefaultMutableTreeNode rootNode;
    
    public Ejercicio1() {
        super("Simulación de DOM - Creación de Página Web");
        initComponents();
        setupLayout();
        setupListeners();
        setupWindow();
    }
    
    private void initComponents() {
        // Crear el nodo raíz del árbol (html)
        rootNode = new DefaultMutableTreeNode("html");
        treeModel = new DefaultTreeModel(rootNode);
        domTree = new JTree(treeModel);
        
        // Personalizar el árbol
        domTree.setCellRenderer(new DomTreeRenderer());
        
        // Configurar el área de vista HTML
        htmlView = new JTextArea();
        htmlView.setEditable(false);
        htmlView.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        // Campos de entrada
        tagField = new JTextField(10);
        contentField = new JTextField(15);
        
        // Botón de agregar
        addButton = new JButton("Agregar Nodo");
        
        // Crear la estructura básica del ejemplo
        createBasicDOM();
    }
    
    private void createBasicDOM() {
        // Limpiar cualquier nodo existente
        rootNode.removeAllChildren();
        
        // Crear nodos del ejemplo
        DefaultMutableTreeNode pNode = new DefaultMutableTreeNode("p: Bienvenido");
        DefaultMutableTreeNode h1Node = new DefaultMutableTreeNode("h1: Bienvenidos");
        DefaultMutableTreeNode footerNode = new DefaultMutableTreeNode("footer: Copyright");
        
        // Agregar nodos al árbol
        rootNode.add(pNode);
        rootNode.add(h1Node);
        rootNode.add(footerNode);
        
        // Actualizar el modelo
        treeModel.reload();
        
        // Actualizar vista HTML
        updateHTMLView();
        
        // Expandir todos los nodos
        expandAllNodes();
    }
    
    private void setupLayout() {
        // Panel principal con BorderLayout
        setLayout(new BorderLayout(5, 5));
        
        // Panel superior para controles
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        controlPanel.add(new JLabel("Etiqueta:"));
        controlPanel.add(tagField);
        controlPanel.add(new JLabel("Texto:"));
        controlPanel.add(contentField);
        controlPanel.add(addButton);
        
        // Panel izquierdo: Árbol DOM
        JPanel treePanel = new JPanel(new BorderLayout());
        treePanel.setBorder(BorderFactory.createTitledBorder("Árbol DOM"));
        JScrollPane treeScroll = new JScrollPane(domTree);
        treePanel.add(treeScroll, BorderLayout.CENTER);
        
        // Panel derecho: Vista HTML
        JPanel htmlPanel = new JPanel(new BorderLayout());
        htmlPanel.setBorder(BorderFactory.createTitledBorder("Vista HTML"));
        JScrollPane htmlScroll = new JScrollPane(htmlView);
        htmlPanel.add(htmlScroll, BorderLayout.CENTER);
        
        // Dividir la ventana
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, htmlPanel);
        splitPane.setDividerLocation(300);
        
        // Agregar todos los componentes al frame principal
        add(controlPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }
    
    private void setupListeners() {
        // Listener para agregar nodo
        addButton.addActionListener(e -> addNode());
        
        // Listener para eliminar nodo con Delete
        domTree.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    removeNode();
                }
            }
        });
        
        // Listener para actualizar vista HTML cuando cambia el árbol
        domTree.addTreeSelectionListener(e -> updateHTMLView());
    }
    
    private void setupWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    private void addNode() {
        String tag = tagField.getText().trim();
        String content = contentField.getText().trim();
        
        if (tag.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La etiqueta no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear el nuevo nodo
        String displayText = content.isEmpty() ? tag : tag + ": " + content;
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(displayText);
        
        // Obtener el nodo seleccionado, si no hay selección, agregar a la raíz
        DefaultMutableTreeNode selectedNode = getSelectedNode();
        if (selectedNode == null) {
            selectedNode = rootNode;
        }
        
        // Agregar al árbol
        selectedNode.add(newNode);
        treeModel.reload();
        
        // Expandir el nodo padre
        domTree.expandPath(new TreePath(selectedNode.getPath()));
        
        // Limpiar campos
        tagField.setText("");
        contentField.setText("");
        
        // Actualizar vista HTML
        updateHTMLView();
    }
    
    private void removeNode() {
        DefaultMutableTreeNode selectedNode = getSelectedNode();
        if (selectedNode == null || selectedNode == rootNode) {
            JOptionPane.showMessageDialog(this, "Selecciona un nodo válido para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Eliminar el nodo seleccionado?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();
            if (parent != null) {
                treeModel.removeNodeFromParent(selectedNode);
                updateHTMLView();
            }
        }
    }
    
    private void updateHTMLView() {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html>\n");
        
        // Generar HTML recursivamente
        generateHTML(rootNode, htmlBuilder, 1);
        
        htmlBuilder.append("</html>");
        
        // Mostrar en el área de texto
        htmlView.setText(htmlBuilder.toString());
        htmlView.setCaretPosition(0);
    }
    
    private void generateHTML(DefaultMutableTreeNode node, StringBuilder builder, int depth) {
        // Solo procesar nodos que no sean la raíz
        if (node != rootNode) {
            // Obtener información del nodo
            String nodeText = node.getUserObject().toString();
            String[] parts = nodeText.split(":", 2);
            String tag = parts[0].trim();
            String content = parts.length > 1 ? parts[1].trim() : "";
            
            // Indentación
            String indent = "  ".repeat(depth);
            
            // Construir etiqueta
            builder.append(indent).append("<").append(tag).append(">");
            
            // Agregar contenido si existe
            if (!content.isEmpty()) {
                builder.append(content);
            }
            
            // Cerrar etiqueta
            builder.append("</").append(tag).append(">\n");
        }
        
        // Procesar hijos
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
            generateHTML(child, builder, depth);
        }
    }
    
    private void expandAllNodes() {
        for (int i = 0; i < domTree.getRowCount(); i++) {
            domTree.expandRow(i);
        }
    }
    
    private DefaultMutableTreeNode getSelectedNode() {
        TreePath selectedPath = domTree.getSelectionPath();
        if (selectedPath != null) {
            return (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
        }
        return null;
    }
    
    // Clase interna para personalizar la renderización del árbol
    private class DomTreeRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            String nodeText = node.getUserObject().toString();
            
            // Personalizar iconos según el tipo de nodo
            if (node == rootNode) {
                setIcon(UIManager.getIcon("FileView.computerIcon"));
            } else if (nodeText.startsWith("html")) {
                setIcon(UIManager.getIcon("FileView.directoryIcon"));
            } else if (nodeText.startsWith("head") || nodeText.startsWith("body") || nodeText.startsWith("div")) {
                setIcon(UIManager.getIcon("FileView.hardDriveIcon"));
            } else if (nodeText.startsWith("h")) {
                setIcon(UIManager.getIcon("FileView.fileIcon"));
                setForeground(Color.BLUE);
            } else if (nodeText.startsWith("p")) {
                setIcon(UIManager.getIcon("FileView.fileIcon"));
                setForeground(Color.GREEN.darker());
            } else if (nodeText.startsWith("footer")) {
                setIcon(UIManager.getIcon("FileView.fileIcon"));
                setForeground(Color.RED.darker());
            } else {
                setIcon(UIManager.getIcon("Tree.leafIcon"));
            }
            
            return this;
        }
    }
    
    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Establecer look and feel del sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            Ejercicio1 simulator = new Ejercicio1();
            simulator.setVisible(true);
        });
    }
}