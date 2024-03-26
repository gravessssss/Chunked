package me.danrom.chunked;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
public class ShowResultCommand implements CommandExecutor {
    private final Chunked plugin;
    public ShowResultCommand(Chunked plugin) { this.plugin = plugin; }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("showresult")) {
            plugin.statement = true;
            sender.sendMessage(ChatColor.DARK_GRAY + "[<CHUNKED>]" + ChatColor.GOLD + " Showing info is now turned" +  ChatColor.GREEN + " ON");
        }
        return false;
    }
}
