package fr.kaice;

import java.util.Date;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.membre.Member;
import fr.kaice.model.membre.MemberCollection;
import fr.kaice.view.MainWindow;

/**
 * The class KaincLauncher is the starting point of the program KAICE.
 * 
 * @author Raph
 * @version 2.0
 *
 */
public abstract class KaiceLauncher {

	public static void main(String[] args) {
		new MainWindow();
		Member m = new Member(150001, "Merkling", "Raph", true, new Date(), "00 00 00 00 00", "Info", "41 rue Sr Cat", "54000", "Nancy", "email@mail.com", true);
		MemberCollection col = KaiceModel.getInstance().getMemberCollection();
		col.addMember(m);
	}

}
