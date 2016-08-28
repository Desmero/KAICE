package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.ArchivedProduct;
import fr.kaice.model.historic.Transaction;
import fr.kaice.model.member.Member;
import fr.kaice.tools.IdSpinner;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.view.window.WindowAskAdmin;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

import static java.awt.BorderLayout.*;

/**
 * Created by merkling on 28/08/16.
 */
public class PanelNewHistoricLine extends JPanel {
    
    private Member client;
    private JTextField tfName;
    private JTextField tfFirstName;
    private IdSpinner sId;
    private JRadioButton rbMember;
    private JRadioButton rbCens;
    private JRadioButton rbNull;
    private JTextField tfText;
    private DMonetarySpinner msPrice;
    private DMonetarySpinner msCash;
    
    public PanelNewHistoricLine() {
        client = null;
        
        PanelTitle title = new PanelTitle("Nouvelle entrée", e -> KaiceModel.getInstance().setDetails(new JPanel()));
        JPanel all = new JPanel(new BorderLayout());
        JPanel allD = new JPanel();
        JPanel client = new JPanel();
        JPanel details = new JPanel();
        JPanel ctrl = new JPanel();
        
        this.setLayout(new BorderLayout());
        this.add(title, NORTH);
        this.add(allD, CENTER);
        this.add(ctrl, SOUTH);
        
        JButton accept = new JButton("Valider");
        accept.addActionListener(e -> WindowAskAdmin.generate(e2 -> valid()));
        ctrl.add(accept);
        
        allD.add(all);
        
        all.add(client, CENTER);
        all.add(details, EAST);
        
        int col = 10;
        JLabel lName = new JLabel("Nom : "), lFirstName = new JLabel("Prénom : "), lId = new JLabel("Num adhérent : ");
        tfName = new JTextField(col);
        tfName.addActionListener(e -> selectClient());
        tfFirstName = new JTextField(col);
        tfFirstName.addActionListener(e -> selectClient());
        sId = new IdSpinner();
        sId.addChangeListener(e -> selectClient(sId.getValue()));
        rbMember = new JRadioButton("Membre", true);
        rbCens = new JRadioButton("Cens");
        rbNull = new JRadioButton("Aucun");
        ButtonGroup clientButtonGroup = new ButtonGroup();
        clientButtonGroup.add(rbMember);
        clientButtonGroup.add(rbCens);
        clientButtonGroup.add(rbNull);
        rbMember.addActionListener(e -> updateClientType());
        rbCens.addActionListener(e -> updateClientType());
        rbNull.addActionListener(e -> updateClientType());
        
        client.setBorder(BorderFactory.createTitledBorder("Client"));
        details.setBorder(BorderFactory.createTitledBorder("Détails"));
        
        GroupLayout groupLayoutClient = new GroupLayout(client);
        groupLayoutClient.setAutoCreateContainerGaps(false);
        groupLayoutClient.setAutoCreateGaps(false);
    
        GroupLayout.SequentialGroup vGroupClient = groupLayoutClient.createSequentialGroup();
        GroupLayout.SequentialGroup hGroupClient = groupLayoutClient.createSequentialGroup();
        
        vGroupClient.addGroup(groupLayoutClient.createParallelGroup().addComponent(lName).addComponent(tfName)
                .addComponent(rbMember));
        vGroupClient.addGroup(groupLayoutClient.createParallelGroup().addComponent(lFirstName).addComponent
                (tfFirstName).addComponent(rbCens));
        vGroupClient.addGroup(groupLayoutClient.createParallelGroup().addComponent(lId).addComponent(sId)
                .addComponent(rbNull));
        
        hGroupClient.addGroup(groupLayoutClient.createParallelGroup().addComponent(lName).addComponent(lFirstName)
                .addComponent(lId));
        hGroupClient.addGroup(groupLayoutClient.createParallelGroup().addComponent(tfName).addComponent(tfFirstName)
                .addComponent(sId));
        hGroupClient.addGroup(groupLayoutClient.createParallelGroup().addComponent(rbMember).addComponent(rbCens)
                .addComponent(rbNull));
        
        groupLayoutClient.setVerticalGroup(vGroupClient);
        groupLayoutClient.setHorizontalGroup(hGroupClient);
        client.setLayout(groupLayoutClient);
        
        JLabel lText = new JLabel("Texte : "), lPrice = new JLabel("Prix : "), lCash = new JLabel("Espece : ");
        tfText = new JTextField(10);
        msPrice = new DMonetarySpinner(0.1);
        msCash = new DMonetarySpinner(0.1);
        
        GroupLayout groupLayoutDetails = new GroupLayout(details);
        groupLayoutDetails.setAutoCreateGaps(false);
        groupLayoutDetails.setAutoCreateContainerGaps(false);
        
        GroupLayout.SequentialGroup vGroupDetails = groupLayoutDetails.createSequentialGroup();
        GroupLayout.SequentialGroup hGroupDetails = groupLayoutDetails.createSequentialGroup();
        
        vGroupDetails.addGroup(groupLayoutDetails.createParallelGroup().addComponent(lText).addComponent(tfText));
        vGroupDetails.addGroup(groupLayoutDetails.createParallelGroup().addComponent(lPrice).addComponent(msPrice));
        vGroupDetails.addGroup(groupLayoutDetails.createParallelGroup().addComponent(lCash).addComponent(msCash));
    
        hGroupDetails.addGroup(groupLayoutDetails.createParallelGroup().addComponent(lText).addComponent(lPrice)
                .addComponent(lCash));
        hGroupDetails.addGroup(groupLayoutDetails.createParallelGroup().addComponent(tfText).addComponent(msPrice)
                .addComponent(msCash));
        
        groupLayoutDetails.setVerticalGroup(vGroupDetails);
        groupLayoutDetails.setHorizontalGroup(hGroupDetails);
        details.setLayout(groupLayoutDetails);
        
    }
    
    private void selectClient() {
        client = KaiceModel.getMemberCollection().getMemberByName(tfName.getText(), tfFirstName.getText());
        updateText();
    }
    
    private void selectClient(int id) {
        client = KaiceModel.getMemberCollection().getMember(id);
        updateText();
    }
    
    private void updateText() {
        if (client != null) {
            tfName.setText(client.getName());
            tfFirstName.setText(client.getFirstName());
            sId.setValue(client.getMemberId());
        }
    }
    
    private void updateClientType() {
        if (rbMember.isSelected()) {
            tfName.setEnabled(true);
            tfFirstName.setEnabled(true);
            sId.setEnabled(true);
        } else {
            tfName.setEnabled(false);
            tfFirstName.setEnabled(false);
            sId.setEnabled(false);
        }
    }
    
    private void valid() {
        int clientId;
        if (rbMember.isSelected()) {
            clientId = sId.getValue();
        } else if (rbCens.isSelected()) {
            clientId = -1;
        } else {
            clientId = 0;
        }
        Transaction transaction = new Transaction(clientId, Transaction.transactionType.MISC, msPrice.getIntValue(), msCash.getIntValue(), new
                Date());
        transaction.addArchivedProduct(new ArchivedProduct(tfText.getText(), 1, msPrice.getIntValue(), 0));
        KaiceModel.getHistoric().addTransaction(transaction);
    }
}
