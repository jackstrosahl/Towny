package com.palmergames.bukkit.towny.confirmations;

import com.palmergames.bukkit.towny.TownyEconomyHandler;
import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.command.TownCommand;
import com.palmergames.bukkit.towny.exceptions.EconomyException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownSpawnLevel;
import com.palmergames.bukkit.towny.object.TownyEconomyObject;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SpawnTaxConfirmation
{
    private TownyEconomyObject payee;
    private double travelCost;
    private Location location;
    private TownSpawnLevel townSpawnPermission;

    public SpawnTaxConfirmation(TownyEconomyObject payee, double travelCost, Location location, TownSpawnLevel townSpawnPermission)
    {
        this.payee = payee;
        this.travelCost = travelCost;
        this.location = location;
        this.townSpawnPermission = townSpawnPermission;
    }

    public void confirm(Resident resident) throws TownyException
    {
        Player player = TownyUniverse.getPlayer(resident);
        try
        {
            if (resident.payTo(travelCost, payee, String.format("Town Spawn (%s)", townSpawnPermission)))
                TownyMessaging.sendMsg(player, String.format(TownySettings.getLangString("msg_cost_spawn"), TownyEconomyHandler.getFormattedBalance(travelCost)));
            TownCommand.finishTeleport(player, resident, location, travelCost);
        } catch (EconomyException e) {
            TownyMessaging.sendErrorMsg(player, e.getMessage());
        }
    }
}
