package de.luuuuuis.commands;

import de.luuuuuis.Banns;
import de.luuuuuis.Info;
import de.luuuuuis.MojangUUIDResolve;
import de.luuuuuis.SQL.BanSQLHandler;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class UnBanCmd
  extends Command
{
  public UnBanCmd(String cmd)
  {
    super(cmd);
  }
  
  @SuppressWarnings("deprecation")
public void execute(CommandSender sender, String[] args) {
	  if(sender instanceof ProxiedPlayer) {
    ProxiedPlayer p = (ProxiedPlayer)sender;
    if (!p.hasPermission(Banns.getUnbanperm()))
    {
  	      p.sendMessage(Info.getNoPerm());
      return;
    }
    
    if (args.length == 1) {
    	String uuid = MojangUUIDResolve.getUUIDResult(args[0]).getValue();
    	
    	if(BanSQLHandler.playerExists(uuid)) {
    	
        BanSQLHandler.unban(uuid);
        	p.sendMessage(Banns.getPrefix() + "§aYou unbaned §e" + args[0] + " §asuccessfully.");
      
    	} else {
	    		p.sendMessage(Banns.getPrefix() + "This player is not banished.");
    	}
    
    } else {
        p.sendMessage(Banns.getPrefix() + "§c/unban <Player>");
    }
  }
  }
}
