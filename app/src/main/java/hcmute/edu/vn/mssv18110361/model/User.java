
package hcmute.edu.vn.mssv18110361.model;

import java.sql.Date;

public class User {
    private  int _id;
    private String _username;
    private String _password;
    private String _name;
    private String _phone_number;
    private byte[] _image;
    private String _email;
    private String _gender;
    private String _address;
    private int _active;
    private int _type;


    public User(int _id ,String _name, String _phone_number, byte[] _image, String _email, String _gender, String _address) {
        this._id = _id;
        this._name = _name;
        this._phone_number = _phone_number;
        this._image = _image;
        this._email = _email;
        this._gender = _gender;
        this._address = _address;
    }

    public User() {
    }

    public User(String _username, String _password, String _name, String _phone_number, byte[] _image, String _email, String _gender, String _address) {
        this._username = _username;
        this._password = _password;
        this._name = _name;
        this._phone_number = _phone_number;
        this._image = _image;
        this._email = _email;
        this._gender = _gender;
        this._address = _address;
    }

    public User(String _username, String _password, byte[] _image, String _email) {
        this._username = _username;
        this._password = _password;
        this._image = _image;
        this._email = _email;
    }

    public User(String _username, String _password, String _name, String _phone_number, byte[] _image, String _email, String _gender, String _address, int _active, int _type) {
        this._username = _username;
        this._password = _password;
        this._name = _name;
        this._phone_number = _phone_number;
        this._image = _image;
        this._email = _email;
        this._gender = _gender;
        this._address = _address;
        this._active = _active;
        this._type = _type;
    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_phone_number() {
        return _phone_number;
    }

    public void set_phone_number(String _phone_number) {
        this._phone_number = _phone_number;
    }

    public byte[] get_image() {
        return _image;
    }

    public void set_image(byte[] _image) {
        this._image = _image;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_gender() {
        return _gender;
    }

    public void set_gender(String _gender) {
        this._gender = _gender;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public int get_active() {
        return _active;
    }

    public void set_active(int _active) {
        this._active = _active;
    }

    public int get_type() {
        return _type;
    }

    public void set_type(int _type) {
        this._type = _type;
    }




}
