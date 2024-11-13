package distanciapublishprogress;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingUtilities;

public class Controller implements ActionListener, PropertyChangeListener {
    private Panel panel;
    private PrimosTwinWorker listaTwin;
    private PrimosCousinWorker listaCousin;
    private PrimosSexyWorker listaSexy;

    public Controller(Panel p1) {
        this.panel = p1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getActionCommand().equals(panel.cancelar())){
            if(listaTwin != null) 
                listaTwin.cancel(true);
            
            if(listaCousin != null) 
                listaCousin.cancel(true);
            
            if(listaSexy != null)
                listaSexy.cancel(true);
            panel.mensaje("Operación cancelada");
        } else {
            try {
                int numTwin = panel.numero1();
                int numCousin = panel.numero2();
                int numSexy = panel.numero3();
                
                listaTwin = new PrimosTwinWorker(panel, numTwin);
                listaCousin = new PrimosCousinWorker(panel, numCousin);
                listaSexy = new PrimosSexyWorker(panel, numSexy);
                panel.mensajeTwin("calculando primos twin...");
                panel.mensajeCousin("calculando primos cousin...");
                panel.mensajeSexy("calculando primos sexy...");
                listaTwin.addPropertyChangeListener(this);
                listaCousin.addPropertyChangeListener(this);
                listaSexy.addPropertyChangeListener(this);
                listaTwin.execute();
                listaCousin.execute();
                listaSexy.execute();
                panel.mensaje("");
                } catch(NumberFormatException n) {
                panel.mensaje("Introduzca un número valido");
            }
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("progress")) {
			int progress = (Integer)evt.getNewValue();
            if(evt.getSource() == listaTwin)
                panel.progreso1(progress);
            if(evt.getSource() == listaCousin)
                panel.progreso2(progress);
            if(evt.getSource() == listaSexy)
                panel.progreso3(progress);
            System.out.println(SwingUtilities.isEventDispatchThread());
		}

	}
}   
