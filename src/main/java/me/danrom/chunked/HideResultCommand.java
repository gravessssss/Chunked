package me.danrom.chunked;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
public class HideResultCommand implements CommandExecutor {
    private final Chunked plugin;
    public HideResultCommand(Chunked plugin) { this.plugin = plugin; }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("hideresult")) {
            plugin.statement = false;
            sender.sendMessage(ChatColor.DARK_GRAY + "[<CHUNKED>]" + ChatColor.GOLD + " Showing info is now turned" +  ChatColor.RED + " OFF");
        }
        return false;
    }
}