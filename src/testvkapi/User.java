package testvkapi;

import java.util.Date;

/**
 * Класс пользователя
 * @author Vladimir Abyzov
 * @version 1.0
 */
public class User {

    /**
     * Поле "имя"
     */
    private String firstname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Поле "фамилия"
     */
    private String surname;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Поле "дата рождения"
     */
    private Date bithdate;

    public Date getBithdate() {
        return bithdate;
    }

    public void setBithdate(Date bithdate) {
        this.bithdate = bithdate;
    }

    /**
     * Поле "нет года": если год скрыт = true, если нет, то = false
     */
    private boolean noyear;

    public boolean getNoyear() {
        return noyear;
    }

    public void setNoyear(boolean noyear) {
        this.noyear = noyear;
    }

    /**
     * Поле "город"
     */
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Конструктор - создание нового объекта
     *
     * @param strs - строковый массив с параметрами друга
     */
    public User() {
        setFirstname("нет");
        setSurname("нет");
        setBithdate(null);
        setCity("нет");
    }
}
