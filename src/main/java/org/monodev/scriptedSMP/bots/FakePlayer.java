package org.monodev.scriptedSMP.bots;

import com.mojang.authlib.GameProfile;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FakePlayer {
    private final ServerPlayer me;
    private final MinecraftServer server;
    private FakeConnection connection;
    private final Location spawnLocation;

    public FakePlayer(UUID uuid, String name, Location location) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        this.server = server;
        GameProfile gameProfile = new GameProfile(uuid, name);
        this.me = new ServerPlayer(server, level, gameProfile, ClientInformation.createDefault());
        this.spawnLocation = location;
    }

    public void join() {

        me.setPos(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ());
        FakeConnection dummyConnection = new FakeConnection(PacketFlow.SERVERBOUND);
        CommonListenerCookie cookie = CommonListenerCookie.createInitial(me.gameProfile, false);

        me.connection = new FakeServerGamePacketListener(server, dummyConnection, me, cookie);

        server.getPlayerList().placeNewPlayer(dummyConnection, me, cookie);

        this.connection = dummyConnection;
    }

    public void leave() {
        connection.reportedConnectionStatus = false;
        Component msg = server.getPlayerList().remove(me);
        if (msg == null) return;
        Bukkit.broadcast(msg);
    }

    public Player getBukkitEntity() {
        return me.getBukkitEntity();
    }
}
