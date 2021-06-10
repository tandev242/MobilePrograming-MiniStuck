package hcmute.edu.vn.mssv18110361.model;

public class WareHouse {
    private  int _id;
    private int _product_id;
    private int _quantity;
    private  int _supplier_id;
    private int _price;


    public WareHouse() {
    }

    public WareHouse(int _id, int _product_id, int _quantity, int _supplier_id, int _price) {
        this._id = _id;
        this._product_id = _product_id;
        this._quantity = _quantity;
        this._supplier_id = _supplier_id;
        this._price = _price;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public int get_supplier_id() {
        return _supplier_id;
    }

    public void set_supplier_id(int _supplier_id) {
        this._supplier_id = _supplier_id;
    }

    public int get_price() {
        return _price;
    }

    public void set_price(int _price) {
        this._price = _price;
    }

}
