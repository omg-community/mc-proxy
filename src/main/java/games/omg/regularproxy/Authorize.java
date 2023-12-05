package games.omg.regularproxy;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;

import dev.simplix.protocolize.api.Location;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.data.packets.PlayerPosition;

public class Authorize {

  @Subscribe
  public void onServerPreConnect(ServerPreConnectEvent event) {
    Player player = event.getPlayer();
    
    event.setResult(ServerPreConnectEvent.ServerResult.denied());
    sendToVoid(player);
  }

  public void sendToVoid(Player player) {
    PlayerPosition playerPositionPacket = new PlayerPosition(
      new Location(0, 0, 0, 0, 0),
      true
    );

    Protocolize.playerProvider().player(player.getUniqueId()).sendPacket(playerPositionPacket);
  }
}
