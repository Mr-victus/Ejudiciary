package dev.cavemen.ejudiciary;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    CustomPhotoAdapter customPhotoAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager;
        viewPager = view.findViewById(R.id.viewpager);
        customPhotoAdapter=new CustomPhotoAdapter(getContext());
        viewPager.setAdapter(customPhotoAdapter);

        Button initiatecase=view.findViewById(R.id.initiatecase);



        initiatecase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new FileFirFragment();

                getFragmentManager().beginTransaction().addToBackStack("HomeFragment").replace(R.id.fragment_container,fragment).commit();
            }
        });



    }
}
