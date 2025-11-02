package org.emp.gl.clients ; 

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService ; 


public class CompteARebours implements TimerChangeListener{

    String name; 
    TimerService timerService ; 
    int cmp;

    public CompteARebours (String name,int a, TimerService time) {
        this.name = name ; 
        this.timerService = time;
        this.cmp = a;
        System.out.println ("Compteur "+name+" initialized with value = "+cmp) ;
        timerService.addTimeChangeListener(this);
    }

    @Override
    public void propertyChange(String prop, Object oldValue, Object newValue) {

        if (TimerChangeListener.SECONDE_PROP.equals(prop)) {
            if (cmp > 0) {
                cmp--;
                System.out.println("[" + name + "] Compte à rebours : " + cmp);
                
                if (cmp == 0) {
                    System.out.println(">>> [" + name + "] Terminé ! Désinscription du service...");
                    timerService.removeTimeChangeListener(this);
                }
            }
        }
    }

    public void afficherHeure () {
        if (timerService != null)
            System.out.println (name + " affiche " + 
                                timerService.getHeures() +":"+
                                timerService.getMinutes()+":"+
                                timerService.getSecondes()) ;
    }

}
