package hcmute.edu.vn.mssv18110361.model;

import java.util.Date;

public class Bill {

    private int _id;
    private int _user_id;
    private Date _date;
    private int _total;
    private int _discount;

    public Bill(int _user_id, Date _date, int _total, int _discount) {
        this._user_id = _user_id;
        this._date = _date;
        this._total = _total;
        this._discount = _discount;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_user_id() {
        return _user_id;
    }

    public void set_user_id(int _user_id) {
        this._user_id = _user_id;
    }

    public Date get_date() {
        return _date;
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    public int get_total() {
        return _total;
    }

    public void set_total(int _total) {
        this._total = _total;
    }

    public int get_discount() {
        return _discount;
    }

    public void set_discount(int _discount) {
        this._discount = _discount;
    }

    public Bill() {
    }
}
