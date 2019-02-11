package com.example.mattdunn.anothersocketproject;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.mattdunn.anothersocketproject.joinlistfragment.JoinTicketListFragment;
import com.example.mattdunn.anothersocketproject.ticketlistfragment.TicketListFragment;

public class MainActivity extends FragmentActivity implements FragmentInteractionListener {

    private FrameLayout fl_fragment_container;

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpFragmentManager();
        setUpFrameLayout();

        setUpFirstFragment();
    }

    private void setUpFragmentManager(){
        fragMan = getSupportFragmentManager();
    }

    private void setUpFrameLayout(){
        fl_fragment_container = findViewById(R.id.fl_fragment_container);
    }

    private void setUpFirstFragment(){
        fragmentTransaction = fragMan.beginTransaction();
        fragmentTransaction.replace(fl_fragment_container.getId(),
                JoinTicketListFragment.newInstance()).addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void openTicketListFragment() {
        fragmentTransaction = fragMan.beginTransaction();
        fragmentTransaction.replace(fl_fragment_container.getId(),
                TicketListFragment.newInstance()).addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
