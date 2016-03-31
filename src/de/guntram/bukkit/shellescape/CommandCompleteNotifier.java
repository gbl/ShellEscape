/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.bukkit.shellescape;

import org.bukkit.command.CommandSender;

/**
 *
 * @author gbl
 */
class CommandCompleteNotifier extends Thread {

    CommandSender sender;
    Thread t1, t2;
    Process p;
         
    public CommandCompleteNotifier(CommandSender sender, InputToSenderThread outThread, InputToSenderThread errThread, Process p) {
        this.p=p;
        this.sender=sender;
        this.t1=outThread;
        this.t2=errThread;
    }
    
    @Override
    public void run() {
        try {
            if (p!=null) p.waitFor();
            if (t1!=null) t1.join();
            if (t2!=null) t2.join();
            sender.sendMessage("Shell command complete");
        } catch (InterruptedException ex) {
            sender.sendMessage("§5"+ex.getMessage());
        }
    }
}
