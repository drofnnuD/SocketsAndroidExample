package com.example.mattdunn.anothersocketproject.ticketlistfragment;

import android.content.Context;
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

        socket.on("userjoinedthechat", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                fragmentInteractionListener.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String response = (String) args[0];
                        Toast.makeText(context, "IT WORKED", Toast.LENGTH_SHORT).show();
                        Log.d("RESPONSE", "YES");
                        Log.d("RESPONSE", response + " ");
                        handleUserJoinedData(response);
                    }
                });

            }
        });

        socket.on("custom", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                fragmentInteractionListener.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "THIS WORKED?", Toast.LENGTH_SHORT).show();
                        Log.d("RESPONSE", "YES MATE");
                    }
                });

            }
        });

        socket.on("ticketUpdated", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                fragmentInteractionListener.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("REDEEM WORKED", "IT DID");
                        Toast.makeText(context, "FUCKING REDEEMED", Toast.LENGTH_SHORT).show();
                        String resposnse = (String) args[0];
                        Log.d("RED RESPONSE", resposnse + " ");
                        handleRedeemEmit(resposnse);
                    }
                });
            }
        });

        socket.on("ticketUnredeemed", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                fragmentInteractionListener.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String resposnse = (String) args[0];
                        Log.d("RED RESPONSE", resposnse + " ");
                        handleUnredeemEdit(resposnse);
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
            socket = IO.socket("http://10.0.2.2:3000");
            socket.connect();
            socket.emit("join", "MATT JOINED");
            socket.emit("custom", "");

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleUserJoinedData(String data){
        try {
            List<TicketModel> ticketList = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i < jsonArray.length(); i++){
                ticketList.add(new TicketModel(jsonArray.getJSONObject(i)));
            }
            adapter = new TicketListAdapter(ticketList, context, this);
            re_tickets.setAdapter(adapter);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleRedeemEmit(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            TicketModel ticketModel = new TicketModel(jsonObject);
            adapter.setTicketToRedeemed(ticketModel);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleUnredeemEdit(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            TicketModel ticketModel = new TicketModel(jsonObject);
            adapter.setTicketToUnRedeemed(ticketModel);
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
        socket.emit("redeem", ticketModel.getBarcode());
    }

    @Override
    public void unRedeemTicket(TicketModel ticketModel) {
        socket.emit("unredeem", ticketModel.getBarcode());
    }
}
