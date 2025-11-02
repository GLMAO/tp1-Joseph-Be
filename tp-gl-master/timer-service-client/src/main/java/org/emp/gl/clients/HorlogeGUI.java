package org.emp.gl.clients;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;


public class HorlogeGUI extends JFrame implements TimerChangeListener {
    
    private TimerService timerService;
    

    private JLabel labelHeures;
    private JLabel labelMinutes;
    private JLabel labelSecondes;
    private JLabel labelDate;
    private JLabel labelJour;
    

    private JLabel labelChrono;
    private int chronoSecondes = 0;
    private boolean chronoActif = false;
    private JButton btnStartStop;
    private JButton btnReset;
    
    public HorlogeGUI(TimerService timerService) {
        this.timerService = timerService;
        
        setTitle("Horloge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
        timerService.addTimeChangeListener(this);
        updateDisplay();
    }
    
    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(20, 25, 35));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Heure
        JPanel horlogePanel = createHorlogePanel();
        
        // chrono
        JPanel chronoPanel = createChronoPanel();
        
        // Ajouter les sections
        mainPanel.add(horlogePanel, BorderLayout.CENTER);
        mainPanel.add(chronoPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    

    private JPanel createHorlogePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBackground(new Color(20, 25, 35));
        
        // Jour
        labelJour = new JLabel("", SwingConstants.CENTER);
        labelJour.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelJour.setForeground(new Color(100, 180, 255));
        
        // Heure
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        timePanel.setBackground(new Color(30, 35, 50));
        timePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 70, 90), 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        // Labels pour heures, minutes, secondes
        labelHeures = createTimeLabel();
        labelMinutes = createTimeLabel();
        labelSecondes = createTimeLabel();
        
        JLabel sep1 = createSeparatorLabel();
        JLabel sep2 = createSeparatorLabel();
        
        timePanel.add(labelHeures);
        timePanel.add(sep1);
        timePanel.add(labelMinutes);
        timePanel.add(sep2);
        timePanel.add(labelSecondes);
        
        // Panel pour la date
        labelDate = new JLabel("", SwingConstants.CENTER);
        labelDate.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        labelDate.setForeground(new Color(150, 150, 150));
        
        panel.add(labelJour, BorderLayout.NORTH);
        panel.add(timePanel, BorderLayout.CENTER);
        panel.add(labelDate, BorderLayout.SOUTH);
        
        return panel;
    }
    


    private JPanel createChronoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBackground(new Color(25, 30, 45));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 70, 90), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Label titre
        JLabel titre = new JLabel("Chronomètre", SwingConstants.CENTER);
        titre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titre.setForeground(new Color(255, 200, 100));
        
        // Label chronomètre
        labelChrono = new JLabel("00:00", SwingConstants.CENTER);
        labelChrono.setFont(new Font("Digital-7", Font.BOLD, 48));
        labelChrono.setForeground(new Color(255, 100, 100));
        
        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBackground(new Color(25, 30, 45));
        
        btnStartStop = new JButton("Demarrer");
        btnStartStop.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnStartStop.setBackground(new Color(50, 150, 50));
        btnStartStop.setForeground(Color.WHITE);
        btnStartStop.setFocusPainted(false);
        btnStartStop.setBorderPainted(false);
        btnStartStop.setPreferredSize(new Dimension(120, 35));
        btnStartStop.addActionListener(e -> toggleChrono());
        
        btnReset = new JButton("Reset");
        btnReset.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnReset.setBackground(new Color(150, 50, 50));
        btnReset.setForeground(Color.WHITE);
        btnReset.setFocusPainted(false);
        btnReset.setBorderPainted(false);
        btnReset.setPreferredSize(new Dimension(100, 35));
        btnReset.addActionListener(e -> resetChrono());
        
        buttonPanel.add(btnStartStop);
        buttonPanel.add(btnReset);
        
        panel.add(titre, BorderLayout.NORTH);
        panel.add(labelChrono, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JLabel createTimeLabel() {
        JLabel label = new JLabel("00", SwingConstants.CENTER);
        label.setFont(new Font("Digital-7", Font.BOLD, 70));
        label.setForeground(new Color(0, 255, 150));
        label.setPreferredSize(new Dimension(100, 90));
        return label;
    }
    
    private JLabel createSeparatorLabel() {
        JLabel label = new JLabel(":", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 60));
        label.setForeground(new Color(0, 200, 120));
        return label;
    }
    
    private void updateDisplay() {
        // Mettre à jour l'heure
        labelHeures.setText(String.format("%02d", timerService.getHeures()));
        labelMinutes.setText(String.format("%02d", timerService.getMinutes()));
        labelSecondes.setText(String.format("%02d", timerService.getSecondes()));
        
        // Mettre à jour la date
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formatterJour = 
            java.time.format.DateTimeFormatter.ofPattern("EEEE");
        java.time.format.DateTimeFormatter formatterDate = 
            java.time.format.DateTimeFormatter.ofPattern("dd MMMM yyyy");
        
        labelJour.setText(today.format(formatterJour).toUpperCase());
        labelDate.setText(today.format(formatterDate));
    }
    
    private void toggleChrono() {
        chronoActif = !chronoActif;
        if (chronoActif) {
            btnStartStop.setText("Pause");
            btnStartStop.setBackground(new Color(200, 100, 50));
        } else {
            btnStartStop.setText("Reprendre");
            btnStartStop.setBackground(new Color(50, 150, 50));
        }
    }
    
    private void resetChrono() {
        chronoSecondes = 0;
        chronoActif = false;
        btnStartStop.setText("Demarrer");
        btnStartStop.setBackground(new Color(50, 150, 50));
        updateChronoDisplay();
    }
    
    private void updateChronoDisplay() {
        int minutes = chronoSecondes / 60;
        int secondes = chronoSecondes % 60;
        labelChrono.setText(String.format("%02d:%02d", minutes, secondes));
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SwingUtilities.invokeLater(() -> {
            updateDisplay();
            
            // Mettre à jour le chronomètre si actif
            if (chronoActif && TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {
                chronoSecondes++;
                updateChronoDisplay();
            }
        });
    }
    
    @Override
    public void dispose() {
        timerService.removeTimeChangeListener(this);
        super.dispose();
    }
}