package fr.kaice.model.membre;

import java.util.Calendar;
import java.util.Date;

import fr.kaice.tools.DFormat;

/**
 * This class represent one member of the association.
 * 
 * @author Raph
 * @version 2.0
 *
 */
public class Member {

	private int userId;
	private String name;
	private String firstname;
	private boolean gender; // true : male; false : female
	private Date birthDate;
	private String phoneNumber;
	private String studies;
	private String mailStreet;
	private String mailPostalCode;
	private String mailTown;
	private String eMail;
	private boolean newsLetter;
	private int account;

	/**
	 * Simple constructor. For the gender : true = male, false = female.
	 * 
	 * @param userId
	 *            int - membership number.
	 * @param name
	 *            {@link String} - name of the member.
	 * @param firstname
	 *            {@link String} - first name of the member.
	 * @param gender
	 *            boolean - gender of the member (true = male, false = female).
	 * @param birthDate
	 *            {@link Date} - birth date of the member.
	 * @param phoneNumber
	 *            {@link String} - phone number of the member.
	 * @param studies
	 *            {@link String} - studies of the member.
	 * @param mailStreet
	 *            {@link String} - address of the member (street and number).
	 * @param mailPostalCode
	 *            {@link String} - address of the member (postal code).
	 * @param mailTown
	 *            {@link String} - address of the member (town).
	 * @param eMail
	 *            {@link String} - e-mail address of the member.
	 * @param newsLetter
	 *            boolean - true if the member want receive news by e-mails.
	 */
	public Member(int userId, String name, String firstname, boolean gender, Date birthDate, String phoneNumber,
			String studies, String mailStreet, String mailPostalCode, String mailTown, String eMail,
			boolean newsLetter) {
		this.userId = userId;
		this.name = name;
		if (this.name.equals("")) {
			this.name = "[Nom]";
		}
		this.firstname = firstname;
		if (this.firstname.equals("")) {
			this.firstname = "[Prenom]";
		}
		this.gender = gender;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		if (this.phoneNumber.equals("")) {
			this.phoneNumber = "XX XX XX XX XX";
		}
		this.studies = studies;
		if (this.studies.equals("")) {
			this.studies = "[Études]";
		}
		this.mailStreet = mailStreet;
		if (this.mailStreet.equals("")) {
			this.mailStreet = "[Rue]";
		}
		this.mailPostalCode = mailPostalCode;
		if (this.mailPostalCode.equals("")) {
			this.mailPostalCode = "[Code postal]";
		}
		this.mailTown = mailTown;
		if (this.mailTown.equals("")) {
			this.mailTown = "[Commune]";
		}
		this.mailTown = mailTown;
		this.eMail = eMail;
		if (this.eMail.equals("")) {
			this.eMail = "[Adresse e-mail]";
		}
		this.newsLetter = newsLetter;
		this.account = 0;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public boolean isMale() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStudies() {
		return studies;
	}

	public void setStudies(String studies) {
		this.studies = studies;
	}

	public String getMailStreet() {
		return mailStreet;
	}

	public void setMailStreet(String mailStreet) {
		this.mailStreet = mailStreet;
	}

	public String getMailPostalCode() {
		return mailPostalCode;
	}

	public void setMailPostalCode(String mailPostalCode) {
		this.mailPostalCode = mailPostalCode;
	}

	public String getMailTown() {
		return mailTown;
	}

	public void setMailTown(String mailTown) {
		this.mailTown = mailTown;
	}

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public boolean isNewsLetter() {
		return newsLetter;
	}

	public void setNewsLetter(boolean newsLetter) {
		this.newsLetter = newsLetter;
	}

	public int getAccount() {
		return account;
	}

	public void creditAccount(int credit) {
		account += credit;
	}

	public void debitAccount(int debit) {
		account -= debit;
	}

	/**
	 * Check if the e-mail address is correct or note.
	 * 
	 * @return True if the e-mail address of this member is correct, false if
	 *         note.
	 */
	public boolean isValidEmailAddress() {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(eMail);
		return m.matches();
	}

	/**
	 * Check if the member address is under age or note.
	 * 
	 * @return False if the member is under age, true if note.
	 */
	public boolean isAdult() {
		Calendar nowM18 = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		birth.setTime(birthDate);
		nowM18.add(Calendar.YEAR, -18);
		return nowM18.after(birth);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(userId);
		sb.append(';');
		sb.append(name);
		sb.append(';');
		sb.append(firstname);
		sb.append(';');
		sb.append(DFormat.DATE_FORMAT.format(birthDate));
		sb.append(';');
		sb.append(gender);
		sb.append(';');
		sb.append(studies);
		sb.append(';');
		sb.append(mailStreet);
		sb.append(';');
		sb.append(mailPostalCode);
		sb.append(';');
		sb.append(mailTown);
		sb.append(';');
		sb.append(eMail);
		sb.append(';');
		sb.append(newsLetter);
		sb.append(';');
		sb.append(phoneNumber);
		sb.append(';');
		return sb.toString();
	}
}
