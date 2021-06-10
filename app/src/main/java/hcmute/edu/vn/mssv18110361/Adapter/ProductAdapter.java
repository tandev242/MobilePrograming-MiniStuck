package hcmute.edu.vn.mssv18110361.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import hcmute.edu.vn.mssv18110361.DAO.CartItemDAO;
import hcmute.edu.vn.mssv18110361.MainActivity;
import hcmute.edu.vn.mssv18110361.R;
import hcmute.edu.vn.mssv18110361.model.CartItem;
import hcmute.edu.vn.mssv18110361.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.DataViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.product_item, parent, false);
        return new ProductAdapter.DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.DataViewHolder holder, int position) {

        String id = Integer.toString(productList.get(position).get_id());
        holder.id.setText(id);

        String name = productList.get(position).get_name();
        holder.name.setText(name);

        String price = Integer.toString(productList.get(position).get_price());
        holder.price.setText(price);

        byte[] img = productList.get(position).get_image();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        holder.image.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }


    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView name;
        private TextView price;
        private ImageView image;
        private ImageView addToCart;
        LinearLayout layout;

        public DataViewHolder(View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.product_id);
            name = (TextView) itemView.findViewById(R.id.product_name);
            image = (ImageView) itemView.findViewById(R.id.product_img);
            price = (TextView) itemView.findViewById(R.id.product_price);
            layout = itemView.findViewById(R.id.product_card);
            addToCart = (ImageView) itemView.findViewById(R.id.add_to_cart);

            // Khi click vao image thi hien thi danh sach san pham ung voi Product
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = v.getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                    try {
                        int cart_id = sp.getInt("cart_id", 0);
                        int product_id = Integer.parseInt(id.getText().toString());
                        // Save to db
                        CartItemDAO dao = new CartItemDAO(v.getContext());
                        CartItem existCartItem = dao.getExistCartItem(product_id);
                        if(existCartItem == null){
                            dao.addCartItem(new CartItem(cart_id, product_id , 1));
                        }else{
                            dao.updateCartItem(existCartItem.get_id() , existCartItem.get_quantity() + 1);
                        }
                        Toast.makeText(v.getContext(), "Successfully added to cart !", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.d("....HEY", "Please login to add to cart");
                    }
                }
            });
        }
    }
}
