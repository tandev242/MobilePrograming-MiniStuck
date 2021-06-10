package hcmute.edu.vn.mssv18110361.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import hcmute.edu.vn.mssv18110361.ProductFragment;
import hcmute.edu.vn.mssv18110361.R;
import hcmute.edu.vn.mssv18110361.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.DataViewHolder> {
    private List<Category> categoryList;
    private Context context;

    public CategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }
    @NonNull
    @Override
    public CategoryAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_category, parent, false);
        return new CategoryAdapter.DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.DataViewHolder holder, int position) {

        String id = Integer.toString(categoryList.get(position).get_id());
        holder.id.setText(id);

        String name = categoryList.get(position).get_name();
        holder.name.setText(name);

        byte[] img = categoryList.get(position).get_image();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        holder.image.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return categoryList == null ? 0 : categoryList.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView name;
        private ImageView image;
        private int category;

        LinearLayout layout;
        public DataViewHolder(View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.category_id);
            name = (TextView) itemView.findViewById(R.id.categoryList_name);
            image = (ImageView)  itemView.findViewById(R.id.categoryList_img);
            layout = itemView.findViewById(R.id.category_card);

            // Khi click vao image thi hien thi danh sach san pham ung voi category
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = ProductFragment.newInstance(Integer.parseInt(id.getText().toString()));
                    FragmentManager fm = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame_container, fragment);
                    ft.commit();
                }
            });
        }
    }

}
