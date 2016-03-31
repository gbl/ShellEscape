/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.bukkit.shellescape;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author gbl
 */
public class ShellEscape extends JavaPlugin {

    File configFile;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configFile=new File(getDataFolder(), "config.yml");
    }

    @Override
    public void onDisable() {
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName=command.getName();
        if (commandName.equalsIgnoreCase("shell")) {
            if (args.length==0) {
                sender.sendMessage("§4You need to give a command");
                return true;
            }
            if (args.length >0 ) {
                try {
                    Process p=Runtime.getRuntime().exec(args);
                    p.getOutputStream().close();
                    BufferedReader stdout=new BufferedReader(new InputStreamReader(p.getInputStream()));
                    BufferedReader stderr=new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    InputToSenderThread outThread, errThread;
                    (outThread=new InputToSenderThread(sender, stdout, "§2")).start();
                    (errThread=new InputToSenderThread(sender, stderr, "§6")).start();
                    new CommandCompleteNotifier(sender, outThread, errThread, p).start();
                } catch (IOException ex) {
                    sender.sendMessage("§5"+ex.getMessage());
                }
                return true;
            }
        }
        return false;
    }
}
