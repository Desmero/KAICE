package fr.kaice.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Date;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.membre.Member;
import fr.kaice.model.membre.MemberCollection;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.model.raw.RawMaterialCollection;

public class Reader {

	public static void readAll() {
		Reader.readUser(0);
		oldReadRawMaterial();
	}

	private static void oldReadRawMaterial() {
		String fileName = KFileParameter.RAW_FILE_NAME;
		String fullPath = KFileParameter.REPOSITORY + "/" + fileName + "." + KFileParameter.EXTENSION;
		try {
			InputStream f = new FileInputStream(fullPath);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);

			RawMaterialCollection coll = KaiceModel.getRawMatCollection();
			String line;
			String[] data;
			int id = 0;
			line = d.readLine();
			while (line != null) {
				data = line.split(KFileParameter.SEPARATOR);
				coll.addReadRawMaterial(id, data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
						Integer.parseInt(data[3]));
				id++;
				line = d.readLine();
			}
			coll.updateAlphabeticalList();
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(fullPath);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e.printStackTrace();
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void readRawMaterial() {
		String fileName = KFileParameter.RAW_FILE_NAME;
		String fullPath = KFileParameter.REPOSITORY + "/" + fileName + "." + KFileParameter.EXTENSION;
		try {
			InputStream f = new FileInputStream(fullPath);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);

			RawMaterialCollection coll = KaiceModel.getRawMatCollection();
			String line;
			String[] data;
			line = d.readLine();
			while (line != null) {
				data = line.split(KFileParameter.SEPARATOR);
				coll.addReadRawMaterial(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
						Integer.parseInt(data[4]));
				line = d.readLine();
			}
			coll.updateAlphabeticalList();
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(fullPath);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e.printStackTrace();
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void readUser(int addYear) {
		String fileName = KFileParameter.MEMBERS_FILE_NAME + (KaiceModel.getActualYear() + addYear);
		String fullPath = KFileParameter.REPOSITORY + "/" + fileName + "." + KFileParameter.EXTENSION;
		try {
			InputStream f = new FileInputStream(fullPath);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);

			MemberCollection coll = KaiceModel.getMemberCollection();
			String line;
			String[] data;
			Member m;
			Date birthDate;
			line = d.readLine();
			while (line != null) {
				m = null;
				data = line.split(KFileParameter.SEPARATOR);
				try {
					birthDate = DFormat.DATE_FORMAT.parse(data[3]);
				} catch (ParseException e) {
					birthDate = new Date();
					System.err.println("Erreur de lecture de date du membre " + data[0] + " : " + data[3]);
				}
				m = new Member(Integer.parseInt(data[0]), data[1], data[2], Boolean.parseBoolean(data[4]), birthDate,
						data[11], data[5], data[6], data[7], data[8], data[9], Boolean.parseBoolean(data[10]));
				coll.addReadMember(m);
				line = d.readLine();
			}
			coll.updateOrderedList();
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(fullPath);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e.printStackTrace();
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
