package org.emp.gl.core.launcher;

import org.emp.gl.clients.CompteARebours;
import org.emp.gl.clients.Horloge ;
import org.emp.gl.clients.HorlogeAnalogique;
import org.emp.gl.clients.HorlogeGUI;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.timer.service.TimerService;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        TimerService timerService = new DummyTimeServiceImpl();
            
        testDuTimeService(timerService);

        new HorlogeGUI(timerService).setVisible(true);
        new HorlogeAnalogique(timerService).setVisible(true);
    }

    private static void testDuTimeService(TimerService timerService) {
        //Horloge horloge1 = new Horloge("Num 1", timerService) ;
        //Horloge horloge2 = new Horloge("Num 2", timerService) ;
        //Horloge horloge3 = new Horloge("Num 3", timerService) ;
       //CompteARebours cmp = new CompteARebours("Cmp", 5, timerService);
        /*for (int i = 1; i <= 10; i++) {
            int val = 10 + (int)(Math.random() * 11); 
            new CompteARebours("Cmp " + i, val, timerService);
        }*/
        // (d) Probème = modification de la liste des listeners pendant son parcours.
        // (e)

    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
