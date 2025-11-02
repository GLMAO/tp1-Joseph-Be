package org.emp.gl.clients ; 

import java.beans.PropertyChangeEvent;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService ; 


public class Horloge implements TimerChangeListener{

    String name; 
    TimerService timerService ; 


    public Horloge (String name, TimerService time) {
        this.name = name ; 
        this.timerService = time;
        System.out.println ("Horloge "+name+" initialized!") ;
        timerService.addTimeChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        
        if (TimerChangeListener.SECONDE_PROP.equals(propertyName)) {
            afficherHeure();
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
