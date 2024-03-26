package me.danrom.chunked;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import java.util.Random;

public class Chunked extends JavaPlugin {

    public BukkitTask task;
    private final Random random = new Random();
    public int interval = 0;
    public boolean statement = true;

    @Override
    public void onEnable() {
        getLogger().info("Chunked has been enabled!");

        getCommand("setinterval").setExecutor(new SetIntervalCommand(this));
        getCommand("showresult").setExecutor(new ShowResultCommand(this));
        getCommand("hideresult").setExecutor(new HideResultCommand(this));

        task = new BukkitRunnable() {
            @Override
            public void run() {
            }
        }.runTaskTimer(this, 0, interval);
    }

    @Override
    public void onDisable() {
        getLogger().info("Chunked has been disabled!");
    }

    // clearing random chunk around random player method
    public void cleanRandomChunk() {

        Player randomPlayer = getRandomPlayer();
        if (randomPlayer == null) return;

        int randomRadius = random.nextInt(12) + 1;
        Chunk randomChunk = getRandomChunk(randomPlayer.getLocation().getChunk(), randomRadius);
        loadChunk(randomChunk); // loading chunk before deleting it

        if(playerWithinChunk(randomChunk, randomPlayer)) {
            if(statement) {skipped(randomPlayer);}
            return;
        }

        removeBlocksInChunk(randomChunk);
        if(statement) {chunkdeletedfunc(randomPlayer, randomRadius); }
    }

    private boolean playerWithinChunk(Chunk chunk, Player player) {
        Location playerLocation = player.getLocation();
        int chunkX = chunk.getX() << 4;
        int chunkZ = chunk.getZ() << 4;
        return playerLocation.getBlockX() >= chunkX && playerLocation.getBlockX() < chunkX + 16 &&
                playerLocation.getBlockZ() >= chunkZ && playerLocation.getBlockZ() < chunkZ + 16;
    }

    // getting a random player method
    private Player getRandomPlayer() {
        Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        if (onlinePlayers.length == 0) return null;
        return onlinePlayers[random.nextInt(onlinePlayers.length)];
    }

    // getting a random radius
    private Chunk getRandomChunk(Chunk centerChunk, int radius) {
        World world = centerChunk.getWorld();
        int chunkX = centerChunk.getX() + random.nextInt(radius * 2 + 1) - radius;
        int chunkZ = centerChunk.getZ() + random.nextInt(radius * 2 + 1) - radius;
        return world.getChunkAt(chunkX, chunkZ);
    }

    // loading a chunk
    private void loadChunk(Chunk chunk) {
        chunk.getWorld().loadChunk(chunk.getX(), chunk.getZ(), true);
    }

    // deleting a chunk method
    private void removeBlocksInChunk(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = -64; y < chunk.getWorld().getMaxHeight(); y++) {
                    chunk.getBlock(x, y, z).setType(Material.AIR);
                }
            }
        }
    }

    public void skipped(Player randomplayer) {
        randomplayer.sendMessage(ChatColor.DARK_GRAY + "[<CHUNKED>]" + ChatColor.RED + " Chunk contains a player. Skipped.");
    }
    public void chunkdeletedfunc(Player randomPlayer, int randomRadius) {
        randomPlayer.sendMessage( ChatColor.DARK_GRAY + "[<CHUNKED>]" + ChatColor.GREEN + " Chunk deleted. Radius: " + randomRadius);
    }
}


