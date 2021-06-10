package hcmute.edu.vn.mssv18110361.model;

public class BillDetails {
    private int _id;
    private int _bill_id;
    private int _product_id;
    private int _quantity;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_bill_id() {
        return _bill_id;
    }

    public void set_bill_id(int _bill_id) {
        this._bill_id = _bill_id;
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

    public BillDetails() {
    }

    public BillDetails(int _bill_id, int _product_id, int _quantity) {
        this._bill_id = _bill_id;
        this._product_id = _product_id;
        this._quantity = _quantity;
    }


}
