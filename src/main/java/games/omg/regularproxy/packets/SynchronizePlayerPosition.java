package games.omg.regularproxy.packets;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.proxy.connection.MinecraftSessionHandler;
import com.velocitypowered.proxy.protocol.MinecraftPacket;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import com.velocitypowered.proxy.protocol.ProtocolUtils.Direction;

import dev.simplix.protocolize.api.Location;
import io.netty.buffer.ByteBuf;

public class SynchronizePlayerPosition implements MinecraftPacket {
  
  private Location location;
  private byte flags;
  private int teleportId;

  public SynchronizePlayerPosition() {
  }

  public SynchronizePlayerPosition(Location location, byte flags, int teleportId) {
    this.location = location;
    this.flags = flags;
    this.teleportId = teleportId;
  }

  public Location location() {
    return location;
  }

  public byte flags() {
    return flags;
  }

  public int teleportId() {
    return teleportId;
  }

  public void location(Location location) {
    this.location = location;
  }

  public void flags(byte flags) {
    this.flags = flags;
  }

  public void teleportId(int teleportId) {
    this.teleportId = teleportId;
  }

  public String toString() {
    return "SynchronizePlayerPosition{" +
      "location=" + location +
      ", flags=" + flags +
      ", teleportId=" + teleportId +
      '}';
  }

  private int readVarInt(ByteBuf buffer) {
    int numRead = 0;
    int result = 0;
    byte read;
    do {
      read = buffer.readByte();
      int value = (read & 0b01111111);
      result |= (value << (7 * numRead));

      numRead++;
      if (numRead > 5) {
        throw new RuntimeException("VarInt is too big");
      }
    } while ((read & 0b10000000) != 0);

    return result;
  }

  public void decode(ByteBuf buf, ProtocolUtils.Direction direction, int protocolVersion) {
    
  }

  private void writeVarInt(ByteBuf buffer, int value) {
    do {
      byte temp = (byte)(value & 0b01111111);
      value >>>= 7;
      if (value != 0) {
        temp |= 0b10000000;
      }
      buffer.writeByte(temp);
    } while (value != 0);
  }

  public void decode(ByteBuf buf, Direction arg1, ProtocolVersion arg2) {
    double x = buf.readDouble();
    double y = buf.readDouble();
    double z = buf.readDouble();
    float yaw = buf.readFloat();
    float pitch = buf.readFloat();
    byte flags = buf.readByte();
    int teleportId = readVarInt(buf);

    this.location = new Location(x, y, z, yaw, pitch);
    this.flags = flags;
    this.teleportId = teleportId;
  }

  public void encode(ByteBuf buf, Direction arg1, ProtocolVersion arg2) {
    buf.writeDouble(location.x());
    buf.writeDouble(location.y());
    buf.writeDouble(location.z());
    buf.writeFloat(location.yaw());
    buf.writeFloat(location.pitch());
    buf.writeByte(flags);
    writeVarInt(buf, teleportId);
  }

  public boolean handle(MinecraftSessionHandler handler) {
    handler.handleGeneric(this);
    return true;
  }
}
