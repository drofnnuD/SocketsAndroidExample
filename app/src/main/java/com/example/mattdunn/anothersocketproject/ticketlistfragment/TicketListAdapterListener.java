package com.example.mattdunn.anothersocketproject.ticketlistfragment;

import com.example.mattdunn.anothersocketproject.models.TicketModel;

/**
 * Created by mattdunn on 06/02/2019.
 */

interface TicketListAdapterListener {

    void redeemTicket(TicketModel ticketModel);
    void unRedeemTicket(TicketModel ticketModel);

}
