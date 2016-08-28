package fr.kaice.tools;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.member.Member;
import fr.kaice.tools.generic.DFormat;

import java.io.*;

/**
 * Created by Raphael on 19/08/2016.
 */
public class Converter {
    public Converter() {
        readUser();
    }

    public static void readUser() {
        String fileName = KFilesParameters.pathMembers + 15 + ".cens";
        try {
            InputStream f = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(f);
            BufferedReader d = new BufferedReader(isr);
            String line;
            String[] data;
            line = d.readLine();
            while (line != null) {
                data = line.split("; ");
                Member member = new Member(Integer.parseInt(data[0]), data[1], data[2], Boolean.parseBoolean(data[4]), DFormat.DATE_ONLY.parse(data[3]), data[11], data[5], data[6], data[7], data[8], data[9], Boolean.parseBoolean(data[10]));
                KaiceModel.getMemberCollection().addMember(member);
                line = d.readLine();
            }
            d.close();
        } catch (FileNotFoundException e) {
            File file = new File(fileName + " erreur de lecture");
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERREUR : Lecture du fichier");
            System.exit(1);
        }
    }

}
