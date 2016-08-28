package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.PartialHistoric;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Raphael on 20/08/2016.
 */
public class PanelRawMaterialDetails extends JPanel implements Observer{

    private RawMaterial material;
    private PanelTitle title;
    private JLabel price, qty, alert;
    private final PartialHistoric historic;

    public PanelRawMaterialDetails(RawMaterial material) {
        this.material = material;
        KaiceModel.getInstance().addObserver(this);
        JPanel all = new JPanel(new BorderLayout());
        JPanel details = new JPanel();

        title = new PanelTitle("Stock : " + material.getName(), e -> KaiceModel.getInstance().setDetails(new JPanel()));
        price = new JLabel("Prix : " + DMonetarySpinner.intToString(material.getPrice()));
        qty = new JLabel("Quantité : " + material.getStock());
        alert = new JLabel("Alerte : " + material.getAlert());

        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.NORTH);
        this.add(all, BorderLayout.CENTER);

        all.add(details, BorderLayout.NORTH);
        details.add(price);
        details.add(qty);
        details.add(alert);

        historic = new PartialHistoric(material);

        all.add(new DTablePanel(KaiceModel.getInstance(), historic, 8), BorderLayout.CENTER);
    }

    public void setBackPanel(JPanel back) {
        if (back == null) {
            title.setGreenAction(null);
        } else {
            title.setGreenAction(e -> KaiceModel.getInstance().setDetails(back));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (KaiceModel.isPartModified(KaiceModel.HISTORIC_PERIOD)) {
            historic.update();
        }
        if (KaiceModel.isPartModified(KaiceModel.RAW_MATERIAL)) {
            title.setTitle("Stock : " + material.getName());
            price.setText("Prix : " + DMonetarySpinner.intToString(material.getPrice()));
            qty.setText("Quantité : " + material.getStock());
            alert.setText("Alerte : " + material.getAlert());
        }
    }
}
