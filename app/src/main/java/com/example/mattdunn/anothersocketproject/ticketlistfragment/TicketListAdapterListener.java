package com.example.mattdunn.anothersocketproject.ticketlistfragment;

import com.example.mattdunn.anothersocketproject.models.TicketModel;

interface TicketListAdapterListener {

    void redeemTicket(TicketModel ticketModel);
    void unRedeemTicket(TicketModel ticketModel);

}
