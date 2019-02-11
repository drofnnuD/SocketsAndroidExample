package com.example.mattdunn.anothersocketproject.joinlistfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mattdunn.anothersocketproject.FragmentInteractionListener;
import com.example.mattdunn.anothersocketproject.R;

public class JoinTicketListFragment extends android.support.v4.app.Fragment {

    private FragmentInteractionListener fragmentInteractionListener;

    public JoinTicketListFragment() {
        // Required empty public constructor
    }

    public static JoinTicketListFragment newInstance() {
        JoinTicketListFragment fragment = new JoinTicketListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_join_ticket_list, container,
                false);

        setUpButton(view);

        return view;
    }

    private void setUpButton(View view){
        Button btn_join_that_shit = view.findViewById(R.id.btn_join_that_shit);

        btn_join_that_shit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInteractionListener.openTicketListFragment();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.fragmentInteractionListener = (FragmentInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
