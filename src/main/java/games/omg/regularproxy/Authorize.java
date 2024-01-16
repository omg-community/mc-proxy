package games.omg.regularproxy;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.proxy.connection.registry.DimensionInfo;
import com.velocitypowered.proxy.protocol.packet.JoinGame;

import dev.simplix.protocolize.api.Location;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import games.omg.regularproxy.packets.SynchronizePlayerPosition;

public class Authorize {

  @Subscribe
  public void onServerPreConnect(ServerPreConnectEvent event) {
    Player player = event.getPlayer();
    
    event.setResult(ServerPreConnectEvent.ServerResult.denied());
    // logger.log(event.getPlayer().getProtocolVersion());
    sendToVoid(player);
  }

  public void sendToVoid(Player player) {
    // RespawnPacket respawnPacket = new RespawnPacket("minecraft:the_void", "minecraft:the_void", 0, (short) 0, (short) 0, false, false, false, null, null, 0, (byte) 0);
    // Respawn respawn = new Respawn(0, 0l, (short) 0, (short) 0, "minecraft:overworld", (byte) 0, new DimensionInfo("minecraft:overworld", "minecraft:overworld", false, false), (short) 0, CompoundBinaryTag.builder().build(), null, 0);
    JoinGame joinGame = new JoinGame();
    joinGame.setEntityId(0);
    joinGame.setGamemode((short) 0);
    joinGame.setDimension(0);
    joinGame.setDifficulty((short) 0);
    joinGame.setIsHardcore(false);
    joinGame.setMaxPlayers(1);
    joinGame.setLevelType("minecraft:overworld");
    joinGame.setViewDistance(5);
    joinGame.setReducedDebugInfo(false);
    joinGame.setDoLimitedCrafting(false);
    joinGame.setDimensionInfo(new DimensionInfo("minecraft:overworld", "minecraft:overworld", false, false));
    joinGame.setPreviousGamemode((short) 0);
    joinGame.setSimulationDistance(5);
    joinGame.setLastDeathPosition(null);
    joinGame.setPortalCooldown(0);

    SynchronizePlayerPosition synchronizePlayerPosition = new SynchronizePlayerPosition();
    synchronizePlayerPosition.location(new Location(0d, 0d, 0d, 0f, 0f));
    synchronizePlayerPosition.flags((byte) 0);
    synchronizePlayerPosition.teleportId(0);

    ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(player.getUniqueId());
    protocolizePlayer.sendPacket(joinGame);
    protocolizePlayer.sendPacket(synchronizePlayerPosition);
  }
}
