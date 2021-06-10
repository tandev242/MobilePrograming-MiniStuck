package hcmute.edu.vn.mssv18110361.model;

public class Category {

    private int _id;
    private String _name;
    private byte[] _image;

    public Category() {
    }

    public Category(String _name, byte[] _image) {
        this._name = _name;
        this._image = _image;
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

    public byte[] get_image() {
        return _image;
    }

    public void set_image(byte[] _image) {
        this._image = _image;
    }
}
