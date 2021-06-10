package hcmute.edu.vn.mssv18110361.model;

public class CartItem {

    private int _id;
    private int _cart_id;
    private int _product_id;
    private int _quantity;


    public CartItem() {
    }

    public CartItem(int _cart_id, int _product_id, int _quantity) {
        this._cart_id = _cart_id;
        this._product_id = _product_id;
        this._quantity = _quantity;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_cart_id() {
        return _cart_id;
    }

    public void set_cart_id(int _cart_id) {
        this._cart_id = _cart_id;
    }

    public int get_product_id() {
        return _product_id;
    }

    public void set_product_id(int _product_id) {
        this._product_id = _product_id;
    }

    public int get_quantity() {
        return _quantity;
    }

    public void set_quantity(int _quantity) {
        this._quantity = _quantity;
    }
}
