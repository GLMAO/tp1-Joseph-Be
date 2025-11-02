package org.emp.gl.clients;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;


public class HorlogeAnalogique extends JFrame implements TimerChangeListener {
    
    private TimerService timerService;
    private HorlogePanelAnalogique horlogePanel;
    
    public HorlogeAnalogique(TimerService timerService) {
        this.timerService = timerService;
        
        setTitle("Horloge Analogique");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
        timerService.addTimeChangeListener(this);
    }
    
    private void initComponents() {
        horlogePanel = new HorlogePanelAnalogique(timerService);
        add(horlogePanel);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SwingUtilities.invokeLater(() -> horlogePanel.repaint());
    }
    
    @Override
    public void dispose() {
        timerService.removeTimeChangeListener(this);
        super.dispose();
    }
}


class HorlogePanelAnalogique extends JPanel {
    
    private TimerService timerService;
    
    public HorlogePanelAnalogique(TimerService timerService) {
        this.timerService = timerService;
        setBackground(new Color(240, 240, 245));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Activer l'antialiasing pour un rendu plus lisse
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2 - 20;
        int radius = Math.min(width, height) / 2 - 40;
        
        // Dessiner le cadran
        dessinerCadran(g2d, centerX, centerY, radius);
        
        // Dessiner les chiffres
        dessinerChiffres(g2d, centerX, centerY, radius);
        
        // Dessiner les aiguilles
        int heures = timerService.getHeures();
        int minutes = timerService.getMinutes();
        int secondes = timerService.getSecondes();
        
        dessinerAiguilles(g2d, centerX, centerY, radius, heures, minutes, secondes);
        
        // Dessiner le point central
        g2d.setColor(Color.BLACK);
        g2d.fillOval(centerX - 5, centerY - 5, 10, 10);
        
        // Afficher l'heure digitale en bas
        String timeString = String.format("%02d:%02d:%02d", heures, minutes, secondes);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(timeString);
        g2d.setColor(new Color(50, 50, 50));
        g2d.drawString(timeString, centerX - textWidth / 2, height - 30);
    }
    
    private void dessinerCadran(Graphics2D g2d, int centerX, int centerY, int radius) {
        // Cercle extérieur (bordure dorée)
        g2d.setColor(new Color(180, 140, 70));
        g2d.setStroke(new BasicStroke(8));
        g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        
        // Cercle intérieur (fond blanc)
        g2d.setColor(Color.WHITE);
        g2d.fillOval(centerX - radius + 4, centerY - radius + 4, 
                    (radius - 4) * 2, (radius - 4) * 2);
        
        // Dessiner les graduations
        g2d.setColor(new Color(100, 100, 100));
        for (int i = 0; i < 60; i++) {
            double angle = Math.toRadians(i * 6 - 90);
            int startRadius = radius - (i % 5 == 0 ? 15 : 8);
            int endRadius = radius - 3;
            
            int x1 = centerX + (int) (startRadius * Math.cos(angle));
            int y1 = centerY + (int) (startRadius * Math.sin(angle));
            int x2 = centerX + (int) (endRadius * Math.cos(angle));
            int y2 = centerY + (int) (endRadius * Math.sin(angle));
            
            g2d.setStroke(new BasicStroke(i % 5 == 0 ? 3 : 1));
            g2d.drawLine(x1, y1, x2, y2);
        }
    }
    
    private void dessinerChiffres(Graphics2D g2d, int centerX, int centerY, int radius) {
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.setColor(new Color(50, 50, 50));
        FontMetrics fm = g2d.getFontMetrics();
        
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(i * 30 - 90);
            int numRadius = radius - 30;
            
            String num = String.valueOf(i);
            int numWidth = fm.stringWidth(num);
            int numHeight = fm.getHeight();
            
            int x = centerX + (int) (numRadius * Math.cos(angle)) - numWidth / 2;
            int y = centerY + (int) (numRadius * Math.sin(angle)) + numHeight / 4;
            
            g2d.drawString(num, x, y);
        }
    }
    
    private void dessinerAiguilles(Graphics2D g2d, int centerX, int centerY, 
                                   int radius, int heures, int minutes, int secondes) {
        // Aiguille des heures (courte et épaisse)
        double angleHeures = Math.toRadians((heures % 12) * 30 + minutes * 0.5 - 90);
        int longueurHeures = radius / 2;
        g2d.setColor(new Color(50, 50, 50));
        g2d.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(centerX, centerY,
                    centerX + (int) (longueurHeures * Math.cos(angleHeures)),
                    centerY + (int) (longueurHeures * Math.sin(angleHeures)));
        
        // Aiguille des minutes (moyenne)
        double angleMinutes = Math.toRadians(minutes * 6 - 90);
        int longueurMinutes = (int) (radius * 0.7);
        g2d.setColor(new Color(70, 70, 70));
        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(centerX, centerY,
                    centerX + (int) (longueurMinutes * Math.cos(angleMinutes)),
                    centerY + (int) (longueurMinutes * Math.sin(angleMinutes)));
        
        // Aiguille des secondes (longue et fine, rouge)
        double angleSecondes = Math.toRadians(secondes * 6 - 90);
        int longueurSecondes = (int) (radius * 0.85);
        g2d.setColor(new Color(220, 50, 50));
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(centerX, centerY,
                    centerX + (int) (longueurSecondes * Math.cos(angleSecondes)),
                    centerY + (int) (longueurSecondes * Math.sin(angleSecondes)));
    }
}