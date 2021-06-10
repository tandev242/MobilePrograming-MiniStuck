package hcmute.edu.vn.mssv18110361.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import hcmute.edu.vn.mssv18110361.CartActivity;
import hcmute.edu.vn.mssv18110361.DAO.CartItemDAO;
import hcmute.edu.vn.mssv18110361.DAO.CategoryDAO;
import hcmute.edu.vn.mssv18110361.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110361.LoginActivity;
import hcmute.edu.vn.mssv18110361.MainActivity;
import hcmute.edu.vn.mssv18110361.R;
import hcmute.edu.vn.mssv18110361.model.CartItem;
import hcmute.edu.vn.mssv18110361.model.Category;
import hcmute.edu.vn.mssv18110361.model.Product;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.DataViewHolder> {
    private List<CartItem> cartItemList;
    private Context context;

    public CartItemAdapter(List<CartItem> cartItemList, Context context) {
        this.cartItemList = cartItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartItemAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.cart_item, parent, false);






        return new CartItemAdapter.DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.DataViewHolder holder, int position) {

        String cartItem_id = Integer.toString(cartItemList.get(position).get_id());
        holder.cartItem_id.setText(cartItem_id);

        int product_id = cartItemList.get(position).get_product_id();
        holder.product_id.setText(Integer.toString(product_id));

        ProductDAO dao = new ProductDAO(context);
        Product product = dao.get(product_id);

        String name = product.get_name();
        holder.name.setText(name);

        String category_name = new CategoryDAO(context).get(product.get_category_id()).get_name();

        holder.category.setText(category_name);

        String price = Integer.toString(product.get_price());
        holder.price.setText(price);

        String quantity = Integer.toString(cartItemList.get(position).get_quantity());
        holder.quantity.setText(quantity);

        byte[] img = product.get_image();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        holder.image.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return cartItemList == null ? 0 : cartItemList.size();
    }


    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView cartItem_id;
        private TextView product_id;
        private TextView name;
        private TextView price;
        private TextView category;
        private TextView quantity;
        private ImageView image;
        private ImageView plus;
        private ImageView minus;
        private ImageView delete;




        public DataViewHolder(View itemView) {
            super(itemView);
            cartItem_id = (TextView) itemView.findViewById(R.id.cartItem_id);
            product_id = (TextView) itemView.findViewById(R.id.product_id);
            name = (TextView) itemView.findViewById(R.id.product_name);
            image = (ImageView) itemView.findViewById(R.id.product_img);
            price = (TextView) itemView.findViewById(R.id.product_price);
            category = (TextView) itemView.findViewById(R.id.category_name);
            quantity = (TextView) itemView.findViewById(R.id.product_qty);
            image = (ImageView) itemView.findViewById(R.id.product_img);
            plus = (ImageView) itemView.findViewById(R.id.add);
            minus = (ImageView) itemView.findViewById(R.id.minus);
            delete = (ImageView) itemView.findViewById(R.id.product_delete);



            // Handle event


            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartItemDAO cIDAO = new CartItemDAO(v.getContext());
                    int id = Integer.parseInt(cartItem_id.getText().toString());
                    int qty = Integer.parseInt(quantity.getText().toString());
                    cIDAO.updateCartItem(id , qty + 1);
                    reloadActivity(v);
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final CartItemDAO cIDAO = new CartItemDAO(v.getContext());
                    final int id = Integer.parseInt(cartItem_id.getText().toString());
                    int qty = Integer.parseInt(quantity.getText().toString());

                    if(qty > 1){
                        cIDAO.updateCartItem(id , qty - 1);
                        reloadActivity(v);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Warning....")
                                .setMessage("Are you sure to delete item from your cart ?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) { cIDAO.deleteCartItem(id);
                                        Toast.makeText(v.getContext(), "Deleted Successful", Toast.LENGTH_SHORT).show();
                                        reloadActivity(v);
                                    }
                                })
                                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setCancelable(false);
                        builder.show();
                    }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final CartItemDAO cIDAO = new CartItemDAO(v.getContext());
                    final int id = Integer.parseInt(cartItem_id.getText().toString());
                    int qty = Integer.parseInt(quantity.getText().toString());


                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Warning....")
                            .setMessage("Are you sure to delete item from your cart ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cIDAO.deleteCartItem(id);
                                    Toast.makeText(v.getContext(), "Deleted Successful", Toast.LENGTH_SHORT).show();
                                    reloadActivity(v);
                                }
                            })
                            .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setCancelable(false);
                    builder.show();
                }
            });
        }
        public void reloadActivity(View v){
            Intent i = new Intent(v.getContext() , CartActivity.class);
            ((Activity)v.getContext()).finish();
            v.getContext().startActivity(i);
        }
    }
}
