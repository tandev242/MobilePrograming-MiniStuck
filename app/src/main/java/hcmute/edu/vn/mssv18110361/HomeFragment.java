package hcmute.edu.vn.mssv18110361;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hcmute.edu.vn.mssv18110361.Adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110361.DAO.CategoryDAO;
import hcmute.edu.vn.mssv18110361.model.Category;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = HomeFragment.class.getSimpleName();

    private SliderLayout sliderLayout;
    public HashMap<String, Integer> sliderImages;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        CategoryDAO categoryDAO = new CategoryDAO(getActivity());
        List<Category> cate = categoryDAO.getAllCategory();
        RecyclerView category_recyclerview = view.findViewById(R.id.recyclerview_category);
        category_recyclerview.setNestedScrollingEnabled(false);
        category_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        category_recyclerview.setAdapter(new CategoryAdapter(cate, getActivity()));

        // Slider ne
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        sliderImages = new HashMap<>();
        SliderImage(view);
        return view;
    }

    private void SliderImage(View view) {

        ArrayList<Integer> drawablelist = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();
        drawablelist.add(R.drawable.banner0);
        drawablelist.add(R.drawable.banner1);
        drawablelist.add(R.drawable.banner2);
        for (int i = 0; i < drawablelist.size(); i++) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(view.getContext());
            // initialize a SliderLayout
            defaultSliderView
                    .image(drawablelist.get(i));
            sliderLayout.addSlider(defaultSliderView);
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sliderLayout.setCustomIndicator((PagerIndicator) view.findViewById(R.id.custom_indicator));
        }
    }
}