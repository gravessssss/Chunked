package me.danrom.chunked;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class SetIntervalCommand implements CommandExecutor {
    private final Chunked plugin;

    public SetIntervalCommand(Chunked plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("setinterval")) {

            if (args.length != 1) {
                sender.sendMessage(ChatColor.DARK_GRAY + "[<CHUNKED>]" + ChatColor.WHITE + " Usage: /setinterval <interval>");
                return true;
            }

            try {
                int newInterval = Integer.parseInt(args[0]);
                plugin.interval = newInterval;
                plugin.task.cancel();

                if ((newInterval == -1) || (newInterval == 0)) {
                    sender.sendMessage(ChatColor.DARK_GRAY + "[<CHUNKED>]" +  ChatColor.GOLD + " Plugin has been disabled.");
                    plugin.task.cancel();
                    return true;
                }

                if(newInterval > 0) {
                    plugin.task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            plugin.cleanRandomChunk();
                        }
                    }.runTaskTimer(plugin, 0, newInterval);
                    sender.sendMessage(ChatColor.DARK_GRAY + "[<CHUNKED>]" + ChatColor.YELLOW + " Interval has been set to " + newInterval + " ticks.");
                    return true;
                }
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.DARK_GRAY + "[<CHUNKED>]" + ChatColor.RED +" Invalid interval. Please enter a valid integer.");
                e.printStackTrace();
                return true;
            }
        }
        return false;
    }
}
