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

import static fr.kaice.tools.local.French.*;

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

        title = new PanelTitle(TF_STOCK + material.getName(), e -> KaiceModel.getInstance().setDetails(new JPanel()));
        price = new JLabel(TF_PRICE + DMonetarySpinner.intToString(material.getPrice()));
        qty = new JLabel(TF_QUANTITY + material.getStock());
        alert = new JLabel(TF_ALERT + material.getAlert());

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
            title.setTitle(TF_STOCK + material.getName());
            price.setText(TF_PRICE + DMonetarySpinner.intToString(material.getPrice()));
            qty.setText(TF_QUANTITY + material.getStock());
            alert.setText(TF_ALERT + material.getAlert());
        }
    }
}
