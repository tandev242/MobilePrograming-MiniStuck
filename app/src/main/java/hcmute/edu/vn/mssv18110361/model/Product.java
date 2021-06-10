package hcmute.edu.vn.mssv18110361.model;

import java.util.Date;

public class Product {
    private int _id;
    private int _category_id;
    private String _name;
    private byte[] _image;
    private Date _date_of_manufacture;
    private Date _expiration_date;
    private int _price;


    public Product() {
    }

    public Product(int _category_id, String _name, byte[] _image, Date _date_of_manufacture, Date _expiration_date, int _price) {
        this._category_id = _category_id;
        this._name = _name;
        this._image = _image;
        this._date_of_manufacture = _date_of_manufacture;
        this._expiration_date = _expiration_date;
        this._price = _price;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public Date get_date_of_manufacture() {
        return _date_of_manufacture;
    }

    public void set_date_of_manufacture(Date _date_of_manufacture) {
        this._date_of_manufacture = _date_of_manufacture;
    }

    public Date get_expiration_date() {
        return _expiration_date;
    }

    public void set_expiration_date(Date _expiration_date) {
        this._expiration_date = _expiration_date;
    }
    public byte[] get_image() {
        return _image;
    }

    public void set_image(byte[] _image) {
        this._image = _image;
    }
    public int get_category_id() {
        return _category_id;
    }

    public void set_category_id(int _category_id) {
        this._category_id = _category_id;
    }

    public int get_price() {
        return _price;
    }

    public void set_price(int _price) {
        this._price = _price;
    }
}
