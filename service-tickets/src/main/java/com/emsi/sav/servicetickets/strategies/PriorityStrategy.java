package com.emsi.sav.servicetickets.strategies;

import com.emsi.sav.servicetickets.entities.Priority;
import com.emsi.sav.servicetickets.entities.Ticket;

public interface PriorityStrategy {
    Priority calculerPriorite(Ticket ticket);
}
