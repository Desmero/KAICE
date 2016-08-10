package fr.kaice.tools;

import fr.kaice.model.member.Member;

import java.io.*;

/**
 * This class is used to save and restore objects stats.
 *
 * @author Raphaël Merkling
 * @version 1.0
 * @see java.io.Serializable
 */
public class Serializer {
    
    public static void serializeMembers() {
        Member e = new Member(0);
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("/tmp/employee.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(e);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    public static Member deserializeMember() {
        Member e = null;
        try {
            FileInputStream fileIn = new FileInputStream("/tmp/employee.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Member) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return null;
        }
        return e;
    }
}
