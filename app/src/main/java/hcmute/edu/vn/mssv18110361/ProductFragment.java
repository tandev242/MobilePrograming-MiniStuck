package hcmute.edu.vn.mssv18110361;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110361.Adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110361.Adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110361.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110361.model.Product;


public class ProductFragment extends Fragment {

    private static final String TAG = ProductFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private List<Product> products;

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance(int category_id) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putInt("category_id" , category_id);
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductFragment newInstance(String search_text) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString("search_text" , search_text);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_product, container, false);

        // Se do du lieu theo category_id
        int category_id = -1;
        String query = null;
        Bundle args = getArguments();
        try {
            category_id = args.getInt("category_id" , -1);
            query = args.getString("search_text");
        }catch (Exception e){
            Log.d(TAG, "Can't parse Int");
        }
        recyclerView = view.findViewById(R.id.recyclerview_product);

        if(query != null){
            products = new ProductDAO(getActivity()).getProductsByName(query);
        }else{
            if(category_id > 0){
                products = new ProductDAO(getActivity()).getProductsByCategoryId(category_id);
            }else{
                products = new ProductDAO(getActivity()).getAllProduct();
            }
        }
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ProductAdapter(products , getActivity()));

        return view;
    }
}