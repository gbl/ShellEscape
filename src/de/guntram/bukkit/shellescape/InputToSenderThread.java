package de.guntram.bukkit.shellescape;

import java.io.BufferedReader;
import java.io.IOException;
import org.bukkit.command.CommandSender;

class InputToSenderThread extends Thread {

    CommandSender sender;
    BufferedReader input;
    String linePrefix;
    public InputToSenderThread(CommandSender sender, BufferedReader br, String s) {
        this.sender=sender;
        this.input=br;
        this.linePrefix=s;
    }
    
    @Override
    public void run() {
        try {
            String s;
            while ((s=input.readLine())!=null) {
                sender.sendMessage(linePrefix+s.trim());
            }
        } catch (IOException ex) {
            sender.sendMessage("§5"+ex.getMessage());
        }
    }
}
