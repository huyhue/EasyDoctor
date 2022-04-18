package fpt.edu.vn.component;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class UserForm {
    private int id;

    private String userName;

    private String password;

    private String matchingPassword;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private String address;
}
