package dev.cavemen.ejudiciary;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class LandingPageAdvocate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_advocate);



        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomnavbaradvocate);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedfragment=null;
            switch (menuItem.getItemId()) {
                case R.id.feed:
                    selectedfragment = new FeedFragment();
                    break;
                case R.id.home:
                    selectedfragment = new HomeAdvocateFragment();
                    break;
                case R.id.profile:
                    selectedfragment = new ProfileFragment();
                    break;
                case R.id.notification:
                    selectedfragment = new NotificationFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_advocate,selectedfragment).commit();
            return true;
        }

    };
}
