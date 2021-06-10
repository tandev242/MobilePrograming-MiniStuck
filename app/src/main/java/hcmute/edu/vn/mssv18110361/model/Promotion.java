package hcmute.edu.vn.mssv18110361.model;

public class Promotion {
    private int _id;
    private String _voucher;
    private int _price;
    private int _expiration_date;

    public Promotion() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_voucher() {
        return _voucher;
    }

    public void set_voucher(String _voucher) {
        this._voucher = _voucher;
    }

    public int get_price() {
        return _price;
    }

    public void set_price(int _price) {
        this._price = _price;
    }

    public int get_expiration_date() {
        return _expiration_date;
    }

    public void set_expiration_date(int _expiration_date) {
        this._expiration_date = _expiration_date;
    }


    public Promotion(int _id, String _voucher, int _price, int _expiration_date) {
        this._id = _id;
        this._voucher = _voucher;
        this._price = _price;
        this._expiration_date = _expiration_date;
    }



}
