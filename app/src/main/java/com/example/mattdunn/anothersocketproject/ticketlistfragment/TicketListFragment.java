package com.example.mattdunn.anothersocketproject.ticketlistfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mattdunn.anothersocketproject.FragmentInteractionListener;
import com.example.mattdunn.anothersocketproject.R;
import com.example.mattdunn.anothersocketproject.models.TicketModel;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TicketListFragment extends android.support.v4.app.Fragment
        implements TicketListAdapterListener {

    private Context context;
    private FragmentInteractionListener fragmentInteractionListener;

    private RecyclerView re_tickets;
    private TicketListAdapter adapter;

    private Socket socket;

    public TicketListFragment() {
        // Required empty public constructor
    }

    public static TicketListFragment newInstance() {
        TicketListFragment fragment = new TicketListFragment();
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
        View view = inflater.inflate(R.layout.fragment_ticket_list, container, false);

        setUpRecyclerView(view);

        setUpSocket();

        socket.on("history", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final String test = (String) args[0];
                Log.d("TEST", test);
                fragmentInteractionListener.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setUpListAndAdapter(test);
                    }
                });
            }
        });

        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                fragmentInteractionListener.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String message = (String) args[0];
                        handleMessageEmit(message);
                    }
                });
            }
        });

        return view;
    }

    private void setUpRecyclerView(View view){
        re_tickets = view.findViewById(R.id.re_tickets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        re_tickets.setLayoutManager(linearLayoutManager);
    }

    private void setUpSocket(){
        try {
            socket = IO.socket("http://67cfdc45.ngrok.io/");
            socket.connect();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setUpListAndAdapter(String jsonArrayString){
        try {
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            List<TicketModel> ticketModels = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++){
                ticketModels.add(new TicketModel(jsonArray.getJSONObject(i)));
            }
            adapter = new TicketListAdapter(ticketModels, context, this);
            re_tickets.setAdapter(adapter);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleMessageEmit(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if(jsonObject.getBoolean("entered")){
                adapter.setTicketToRedeemed(new TicketModel(jsonObject));
            } else {
                adapter.setTicketToUnRedeemed(new TicketModel(jsonObject));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.fragmentInteractionListener = (FragmentInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void redeemTicket(TicketModel ticketModel) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("barcode", Integer.parseInt(ticketModel.getBarcode()));
            jsonObject.put("scannerName", "mattScan");
            jsonObject.put("entered", true);
            socket.emit("message", jsonObject.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void unRedeemTicket(TicketModel ticketModel) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("barcode", Integer.parseInt(ticketModel.getBarcode()));
            jsonObject.put("scannerName", "mattScan");
            jsonObject.put("entered", false);
            socket.emit("message", jsonObject.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
