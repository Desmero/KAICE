package fr.kaice.model.member;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


/**
 * This class represent one member of the association. <br/>
 * <p>
 * It contains : <br/>
 * - Name; <br/>
 * - First name; <br/>
 * - Gender; <br/>
 * - Birth date; <br/>
 * - Phone number; <br/>
 * - Address; <br/>
 * - E-mail address; <br/>
 * - News letters registration; <br/>
 * - Studies; <br/>
 * - And an id. <br/>
 *
 * @author Raphaël Merkling
 * @version 2.2
 */
public class Member implements Serializable {
    
    private static final long serialVersionUID = -3707505691830630156L;
    private final int memberId; // also call membership number
    private String name;
    private String firstName;
    private boolean gender; // true : male; false : female
    private Date birthDate;
    private String phoneNumber;
    private String studies;
    private String mailStreet;
    private String mailPostalCode;
    private String mailTown;
    private String eMail;
    private boolean newsLetter;
    private boolean admin;
    
    /**
     * Create an empty member with a given id. All field are filled with generic string.
     *
     * @param memberId int - membership number.
     */
    public Member(int memberId) {
        this(memberId, "[Nom]", "[Prénom]", true, new Date(), "XX XX XX XX XX", "[Études]", "[Rue]", "[Code postal]",
                "[Commune]", "[Adresse e-mail]", false);
    }
    
    /**
     * Create a new {@link Member} with all fields. All empty parameters are replace with generic ones.
     *
     * @param memberId       int - Membership number.
     * @param name           {@link String} - Mame of the member.
     * @param firstName      {@link String} - First name of the member.
     * @param gender         boolean - Gender of the member (true = male, false = female).
     * @param birthDate      {@link Date} - Birth date of the member.
     * @param phoneNumber    {@link String} - Phone number of the member.
     * @param studies        {@link String} - Studies of the member.
     * @param mailStreet     {@link String} - Address of the member (street and number).
     * @param mailPostalCode {@link String} - Address of the member (postal code).
     * @param mailTown       {@link String} - Address of the member (town).
     * @param eMail          {@link String} - E-mail address of the member.
     * @param newsLetter     boolean - True if the member want receive news by e-mails.
     */
    public Member(int memberId, String name, String firstName, boolean gender, Date birthDate, String phoneNumber,
                  String studies, String mailStreet, String mailPostalCode, String mailTown, String eMail,
                  boolean newsLetter) {
        this.memberId = memberId;
        this.name = name;
        if (this.name.equals("")) {
            this.name = "[Nom]";
        }
        this.firstName = firstName;
        if (this.firstName.equals("")) {
            this.firstName = "[Prénom]";
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
        this.admin = false;
    }
    
    /**
     * Return the membership number of the {@link Member}.
     *
     * @return The membership number of the {@link Member}.
     */
    public int getMemberId() {
        return memberId;
    }
    
    /**
     * Return the name of the {@link Member}.
     *
     * @return The name of the {@link Member}.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the name of the {@link Member}.
     *
     * @param name {@link String} - The name of the {@link Member}.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Return the first name of the {@link Member}.
     *
     * @return The first name of the {@link Member}.
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Set the first name of the {@link Member}.
     *
     * @param firstName {@link String} - The first name of the {@link Member}.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * Return the full name (first name + name) of the {@link Member}.
     *
     * @return The full name of the {@link Member}.
     */
    public String getFullName() {
        return firstName + " " + name;
    }
    
    /**
     * Return a boolean corresponding of the gender of the {@link Member} (true if man, false if woman).
     *
     * @return True if the {@link Member} is a man, false for a woman.
     */
    public boolean isMale() {
        return gender;
    }
    
    /**
     * Set the gender of the {@link Member}.
     *
     * @param gender boolean - The gender of the {@link Member}, true for a man, false for a woman.
     */
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    
    /**
     * Return the birth date of the {@link Member}.
     *
     * @return The birth date of the {@link Member}.
     */
    public Date getBirthDate() {
        return birthDate;
    }
    
    /**
     * Set the birth date of the {@link Member}.
     *
     * @param birthDate {@link Date} - The birth date of the {@link Member}.
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    /**
     * Return the phone number of the {@link Member}.
     *
     * @return The phone number of the {@link Member}.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    /**
     * Set the phone number of the {@link Member}.
     *
     * @param phoneNumber {@link String} - The phone number of the {@link Member}.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Return the studies of the {@link Member}.
     *
     * @return The studies of the {@link Member}.
     */
    public String getStudies() {
        return studies;
    }
    
    /**
     * Set the studies of the {@link Member}.
     *
     * @param studies {@link String} - The studies of the {@link Member}.
     */
    public void setStudies(String studies) {
        this.studies = studies;
    }
    
    /**
     * Return the street where the {@link Member} lives.
     *
     * @return The street where the {@link Member} lives.
     */
    public String getMailStreet() {
        return mailStreet;
    }
    
    /**
     * Set the street where the {@link Member} lives.
     *
     * @param mailStreet {@link String} - The street where the {@link Member} lives.
     */
    public void setMailStreet(String mailStreet) {
        this.mailStreet = mailStreet;
    }
    
    /**
     * Return the postal code where the {@link Member} lives.
     *
     * @return The postal code where the {@link Member} lives.
     */
    public String getMailPostalCode() {
        return mailPostalCode;
    }
    
    /**
     * Set the postal code where the {@link Member} lives.
     *
     * @param mailPostalCode {@link String} - The postal code where the {@link Member} lives.
     */
    public void setMailPostalCode(String mailPostalCode) {
        this.mailPostalCode = mailPostalCode;
    }
    
    /**
     * Return the town where the {@link Member} lives.
     *
     * @return The town where the {@link Member} lives.
     */
    public String getMailTown() {
        return mailTown;
    }
    
    /**
     * Set the town where the {@link Member} lives.
     *
     * @param mailTown {@link String} - The town where the {@link Member} lives.
     */
    public void setMailTown(String mailTown) {
        this.mailTown = mailTown;
    }
    
    /**
     * Return the e-mail address of the {@link Member}.
     *
     * @return The a-mail address of the {@link Member}.
     */
    public String getEMail() {
        return eMail;
    }
    
    /**
     * Set the e-mail address of the {@link Member}.
     *
     * @param eMail {@link String} - The e-mail address of the {@link Member}.
     */
    public void setEMail(String eMail) {
        this.eMail = eMail;
    }
    
    /**
     * Return true if the {@link Member} subscribe to the newsletter.
     *
     * @return True if the {@link Member} subscribe to the newsletter.
     */
    public boolean isNewsLetter() {
        return newsLetter;
    }
    
    /**
     * Set if the {@link Member} subscribe to the newsletter or not.
     *
     * @param newsLetter boolean - True if the {@link Member} subscribe to the newsletter.
     */
    public void setNewsLetter(boolean newsLetter) {
        this.newsLetter = newsLetter;
    }
    
    public boolean isAdmin() {
        return admin;
    }
    
    public void changeAdminState() {
        this.admin = !admin;
    }
    
    /**
     * Check if the e-mail address is correct or not.
     *
     * @return True if the e-mail address of this member is correct, false if not.
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
        return name + " " + firstName + " (" + memberId + ")";
    }
}
