package com.emsi.sav.servicetickets.strategies;

import com.emsi.sav.servicetickets.entities.Category;
import com.emsi.sav.servicetickets.entities.Ticket;

public interface CategoryStrategy {

    Category calculerCategorie(Ticket ticket);
}
