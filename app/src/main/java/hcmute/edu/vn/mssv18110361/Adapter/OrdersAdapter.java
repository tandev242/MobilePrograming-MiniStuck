package hcmute.edu.vn.mssv18110361.Adapter;

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

import hcmute.edu.vn.mssv18110361.DAO.BillDAO;
import hcmute.edu.vn.mssv18110361.DAO.CartItemDAO;
import hcmute.edu.vn.mssv18110361.DAO.CategoryDAO;
import hcmute.edu.vn.mssv18110361.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110361.R;
import hcmute.edu.vn.mssv18110361.model.BillDetails;
import hcmute.edu.vn.mssv18110361.model.CartItem;
import hcmute.edu.vn.mssv18110361.model.Product;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.DataViewHolder> {
    private List<BillDetails> bdtList;
    private Context context;

    public OrdersAdapter(List<BillDetails> bdtList, Context context) {
        this.bdtList = bdtList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.order_item, parent, false);
        return new OrdersAdapter.DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.DataViewHolder holder, int position) {

        int pid = bdtList.get(position).get_product_id();
        ProductDAO pDao = new ProductDAO(context);
        Product p = pDao.get(pid);

        String name = p.get_name();
        holder.tvName.setText(name);

        String total = String.valueOf(bdtList.get(position).get_quantity() * p.get_price());
        holder.tvTotal.setText(total);

        String quantity = String.valueOf(bdtList.get(position).get_quantity());
        holder.tvQuantity.setText("x"+quantity);

        int id_bill = bdtList.get(position).get_bill_id();
        String date = new BillDAO(context).getDate(id_bill);
        holder.tvDate.setText(date);

        String category = new CategoryDAO(context).get(p.get_category_id()).get_name();
        holder.tvCategory.setText(category);

        byte[] img = p.get_image();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        holder.iProduct.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return bdtList == null ? 0 : bdtList.size();
    }


    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvTotal;
        private TextView tvDate;
        private TextView tvQuantity;
        private TextView tvCategory;
        private ImageView iProduct;
        LinearLayout layout;

        public DataViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.product_name);
            tvTotal = itemView.findViewById(R.id.total_amount);
            tvDate = itemView.findViewById(R.id.payment_date);
            tvCategory = itemView.findViewById(R.id.category_name);
            tvQuantity = itemView.findViewById(R.id.product_qty);
            iProduct = itemView.findViewById(R.id.product_img);
        }
    }
}
