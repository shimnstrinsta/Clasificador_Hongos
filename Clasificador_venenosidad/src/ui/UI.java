package ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import logic.Soporte;

public class UI {

    private final JComboBox[] valores_opciones = new JComboBox[22];
    private final Soporte soporte = new Soporte();

    public UI() {
        JFrame ventana = new JFrame("Clasificador de venenosidad");
        ventana.setSize(900, 1000);

        JPanel contenedorPrincipal = new JPanel(new CardLayout());

        contenedorPrincipal.add(crearPanelFormulario(contenedorPrincipal), "pagina_formulario");

        ventana.add(contenedorPrincipal);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        ventana.setVisible(true);
    }

    private JLabel crearTitulo(String texto, JPanel contenedor) {
        Font fuente = new Font("Arial", Font.BOLD, 24);
        JLabel titulo = new JLabel(texto);
        titulo.setFont(fuente);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        contenedor.add(Box.createVerticalStrut(20));
        contenedor.add(titulo);
        contenedor.add(Box.createVerticalStrut(20));
        return titulo;
    }

    private JLabel crearTextoNormal(String contenido, JPanel contenedor) {
        Font fuente = new Font("Arial", Font.BOLD, 14);
        JLabel texto = new JLabel(contenido);

        texto.setFont(fuente);

        contenedor.add(Box.createVerticalStrut(5));
        contenedor.add(texto);
        return texto;
    }

    private JButton crearBoton(String texto, JPanel contenedor) {
        JButton btn = new JButton(texto);
        contenedor.add(Box.createVerticalStrut(15));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(btn);
        btn.setBackground(Color.WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);

        return btn;
    }

    private JButton crearBotonFeedBack(String texto, JPanel contenedor) {

        ImageIcon original = new ImageIcon("img/" + texto + ".png");
        Image imagenEscalada = original.getImage().getScaledInstance(100, 75, Image.SCALE_SMOOTH);
        ImageIcon escalada = new ImageIcon(imagenEscalada);

        JButton btn = new JButton(escalada);

        contenedor.add(btn);

        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    private JLabel crearImagen(String texto, JPanel contenedor) {
        ImageIcon original = new ImageIcon("img/" + texto + ".png");
        Image imagenEscalada = original.getImage().getScaledInstance(100, 75, Image.SCALE_SMOOTH);
        ImageIcon escalada = new ImageIcon(imagenEscalada);

        JLabel lbl = new JLabel(escalada);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(20, 0, 20, 0));

        contenedor.add(lbl);
        return lbl;
    }

    private JScrollPane crearPanelFormulario(JPanel contenedorPrincipal) {
        JPanel formulario = new JPanel();
        formulario.setBackground(Color.cyan);
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));

        String[] texto_entradas = {"Forma del sombrero",
            "Tapa del sombrero",
            "Color del sombrero",
            "Abolladura",
            "Olor",
            "Accesorio de la branquia",
            "Espaciamiento branquial",
            "Tamanio de branquias",
            "Color de branquia",
            "Forma del tallo",
            "Raiz del tallo",
            "Superficie del tallo por encima del anillo",
            "Superficie del tallo por debajo del anillo",
            "Color del tallo por encima del anillo",
            "Color del tallo por debajo del anillo",
            "Tipo del velo",
            "Color del velo",
            "Numero de anillos",
            "Tipo de anillos",
            "Color de la espora",
            "Poblacion",
            "Habitat"};

        String[][] valores_entradas = {
            {"", "Campana", "Conica", "Convexa", "Plana", "Nudosa", "Hundida"},
            {"", "Fibrosa", "Ranuras", "Escamosa", "Lisa"},
            {"", "Marron", "Beige", "Canela", "Gris", "Verde", "Rosa", "Morado", "Rojo", "Blanco", "Amarillo"},
            {"", "Si", "No"},
            {"", "Almendra", "Anis", "Creosota", "Pescado", "Fetido", "Mohoso", "Nada", "Acre", "Picoso"},
            {"", "Unida", "Descendente", "Suelta", "Con muescas"},
            {"", "Cerca", "Junto", "Distante"},
            {"", "Anchas", "Estrechas"},
            {"", "Negro", "Marron", "Beige", "Chocolate", "Gris", "Verde", "Naranja", "Rosa", "Morado", "Rojo", "Blanco", "Amarillo", "Sin color"},
            {"", "Agrandado", "Afinado"},
            {"", "Bulboso", "Trebol", "Copa", "Igual", "Rizomorfos", "Enraizado"},
            {"", "Fibroso", "Escamoso", "Sedoso", "Liso"},
            {"", "Fibroso", "Escamoso", "Sedoso", "Liso"},
            {"", "Marron", "Beige", "Canela", "Gris", "Naranja", "Rosa", "Rojo", "Blanco", "Amarillo"},
            {"", "Marron", "Beige", "Canela", "Gris", "Naranja", "Rosa", "Rojo", "Blanco", "Amarillo"},
            {"", "Parcial", "Universal"},
            {"", "Marron", "Naranja", "Blanco", "Amarrillo"},
            {"", "Ninguno", "Uno", "Dos"},
            {"", "Telarania", "Evanescente", "Ensanchado", "Largo", "Ninguno", "Colgante", "Revestimiento", "Zona"},
            {"", "Negro", "Marron", "Beige", "Chocolate", "Verde", "Naranja", "Morado", "Blanco", "Amarillo"},
            {"", "Abundante", "Agrupada", "Numerosa", "Dispersa", "Varias", "Solitaria"},
            {"", "Pastos", "Hojas", "Prados", "Caminos", "Urbano", "Residuos", "Bosques"}};

        crearTitulo("¡Bienvenido!", formulario);
        JLabel info = crearTextoNormal("Ingrese los siguientes datos del hongo", formulario);
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.setBorder(new EmptyBorder(20, 0, 20, 0));

        for (int i = 0; i < texto_entradas.length; i++) {
            JPanel celda = new JPanel();
            celda.setLayout(new BorderLayout());
            celda.setBackground(Color.cyan);
            JLabel info_entrada = crearTextoNormal(texto_entradas[i], celda);
            JComboBox opciones = new JComboBox(valores_entradas[i]);

            valores_opciones[i] = opciones;
            opciones.setBackground(Color.WHITE);

            opciones.setCursor(new Cursor(Cursor.HAND_CURSOR));
            opciones.setPreferredSize(new Dimension(200, 30));
            info_entrada.setBorder(new EmptyBorder(0, 40, 0, 0));
            celda.setBorder(new EmptyBorder(2, 0, 2, 40));

            celda.add(info_entrada, BorderLayout.WEST);
            celda.add(opciones, BorderLayout.EAST);
            formulario.add(celda);

        }

        JButton btn_predecir = crearBoton("Predecir", formulario);
        btn_predecir.addActionListener((ActionEvent e) -> {

            String[] respuestas = new String[texto_entradas.length];

            for (int i = 0; i < valores_opciones.length; i++) {
                respuestas[i] = String.valueOf(valores_opciones[i].getSelectedItem());
            }

            if (soporte.validarDatos(respuestas)) {
                contenedorPrincipal.add(crearPanelResultado(contenedorPrincipal), "pagina_resultado");
                CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
                cl.show(contenedorPrincipal, "pagina_resultado");
                for (JComboBox valores_opcione : valores_opciones) {
                    valores_opcione.setSelectedIndex(0); // Vaciar opciones
                }
            } else {
                JOptionPane.showMessageDialog(null, "Complete todos los datos antes de continuar", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        JScrollPane scrollPane = new JScrollPane(formulario);   // Volverlo un panel con scroll

        return scrollPane;
    }

    private JPanel crearPanelResultado(JPanel contenedorPrincipal) {
        JPanel panelResultado = new JPanel();
        panelResultado.setBackground(Color.cyan);
        panelResultado.setBorder(new EmptyBorder(20, 0, 20, 0));
        panelResultado.setLayout(new BoxLayout(panelResultado, BoxLayout.Y_AXIS));

        crearTitulo("Su hongo es ...", panelResultado);

        String[] respuestas = new String[valores_opciones.length];

        for (int i = 0; i < valores_opciones.length; i++) {
            respuestas[i] = String.valueOf(valores_opciones[i].getSelectedItem());
        }

        boolean resultado = soporte.procesarEntradas(respuestas);

        String imagen_resultado = "venenoso", texto = "VENENOSO", extra = "Ni se te ocurra a comerlo";

        if (resultado) {
            texto = "NO VENENOSO";
            extra = "(Por las dudas no lo comas)";
            imagen_resultado = "no_venenoso";
        }

        crearTitulo(texto, panelResultado);
        crearImagen(imagen_resultado, panelResultado);
        JLabel info_extra = crearTextoNormal(extra, panelResultado);
        info_extra.setAlignmentX(Component.CENTER_ALIGNMENT);
        crearTitulo("¿Te sirvió el resultado?",panelResultado);
        
        JPanel botones = new JPanel();
        botones.setLayout(new BoxLayout(botones, BoxLayout.X_AXIS));
        botones.setBackground(Color.CYAN);
        JButton btn_aprobar = crearBotonFeedBack("aprobado", botones);
        JButton btn_desaprobar = crearBotonFeedBack("desaprobado", botones);
        panelResultado.add(botones);

        JButton btn_volver = crearBoton("Predecir otro hongo", panelResultado);

        btn_aprobar.addActionListener((ActionEvent e) -> {
            soporte.evaluarResultado(respuestas, resultado, true);

            btn_aprobar.setDisabledIcon(btn_aprobar.getIcon()); // Reutiliza el ícono actual como ícono deshabilitado
            btn_aprobar.setEnabled(false);
            btn_desaprobar.setEnabled(false);
            btn_volver.setEnabled(true);

        });

        btn_desaprobar.addActionListener((ActionEvent e) -> {
            soporte.evaluarResultado(respuestas, resultado, false);            

            btn_desaprobar.setDisabledIcon(btn_desaprobar.getIcon()); // Reutiliza el ícono actual como ícono deshabilitado

            btn_desaprobar.setEnabled(false);
            btn_aprobar.setEnabled(false);
            btn_volver.setEnabled(true);
        });

        btn_volver.setEnabled(false);
        btn_volver.addActionListener((ActionEvent e) -> {
            CardLayout cl = (CardLayout) contenedorPrincipal.getLayout();
            cl.show(contenedorPrincipal, "pagina_formulario");
        });

        return panelResultado;
    }

}
