package fr.kaice.tools;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.member.Member;
import fr.kaice.tools.generic.DFormat;

import java.io.*;

import static fr.kaice.tools.generic.DTerminal.GREEN;
import static fr.kaice.tools.generic.DTerminal.RESET;

/**
 * Created by Raphael on 19/08/2016.
 */
public class Converter {
    public Converter() {
        readUser(15);
    }

    public static void save(String file, String data) {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "ISO-8859-1 "));
            writer.write(data);
            System.out.println(GREEN + "Writing file " + file + " success" + RESET);
        } catch (IOException ex) {
            System.err.println("Writing file " + file + " error");
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }
    
    public static void readUser(int yearCode) {
        String fileName = KFilesParameters.getMemberFile(yearCode) + ".txt";
        try {
            InputStream f = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(f);
            BufferedReader d = new BufferedReader(isr);
            String line;
            String[] data;
            line = d.readLine();
            while (line != null) {
                data = line.split(";");
                Member member = new Member(Integer.parseInt(data[0]), data[1], data[2], Boolean.parseBoolean(data[3])
                        , DFormat.DATE_ONLY.parse(data[4]), data[5], data[6], data[7], data[8], data[9], data[10],
                        Boolean.parseBoolean(data[11]));
                if (Boolean.parseBoolean(data[12])) {
                    member.changeAdminState();
                }
                KaiceModel.getMemberCollection().addReadMember(member);
                line = d.readLine();
            }
            KaiceModel.getMemberCollection().updateDisplayList();
            KaiceModel.getMemberCollection().serialize(yearCode);
            d.close();
            System.out.println(GREEN + "Read file " + fileName + " success" + RESET);
        } catch (FileNotFoundException e) {
            System.err.println(fileName + " file not found");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(fileName + " read file error");
            System.exit(1);
        }
    }
    
    public static void readRawMaterial() {
        String fileName = KFilesParameters.getRawMaterialFile() + ".txt";
        try {
            InputStream f = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(f);
            BufferedReader d = new BufferedReader(isr);
            String line;
            String[] data;
            line = d.readLine();
            while (line != null) {
                data = line.split(";");
                KaiceModel.getRawMatCollection().readRawMaterial(Integer.parseInt(data[0]), data[1], Integer.parseInt
                        (data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), Boolean.parseBoolean(data[5]));
                line = d.readLine();
            }
            KaiceModel.getRawMatCollection().updateDisplayList();
            KaiceModel.getRawMatCollection().serialize();
            d.close();
            System.out.println(GREEN + "Read file " + fileName + " success" + RESET);
        } catch (FileNotFoundException e) {
            System.err.println(fileName + " file not found");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(fileName + " read file error");
            System.exit(1);
        }
    }
    
}
