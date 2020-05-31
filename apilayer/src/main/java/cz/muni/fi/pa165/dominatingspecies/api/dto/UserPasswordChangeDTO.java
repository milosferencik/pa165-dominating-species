package cz.muni.fi.pa165.dominatingspecies.api.dto;

/**
 * @author Katarina Matusova
 */
public class UserPasswordChangeDTO {

    private long id;

    private String password;

    private String newPassword;

    private String repeatedPassword;

    public UserPasswordChangeDTO(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}