package hcmute.edu.vn.mssv18110361.model;

public class Cart {

    private int _id;
    private int _user_id;

    public Cart() {
    }

    public Cart(int _user_id) {
        this._user_id = _user_id;
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

}
