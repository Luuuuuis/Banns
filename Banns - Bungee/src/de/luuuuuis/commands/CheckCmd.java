package de.luuuuuis.commands;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import de.luuuuuis.Banns;
import de.luuuuuis.Info;
import de.luuuuuis.MojangUUIDResolve;
import de.luuuuuis.TimeManager;
import de.luuuuuis.SQL.BanInfo;
import de.luuuuuis.SQL.BanSQLHandler;
import de.luuuuuis.SQL.MuteInfo;
import de.luuuuuis.SQL.MuteSQLHandler;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CheckCmd
  extends Command
{
  public CheckCmd(String cmd)
  {
    super(cmd);
  }
  
  @SuppressWarnings("deprecation")
public void execute(CommandSender sender, String[] args)
  {
    if (!(sender instanceof ProxiedPlayer))
    {
      sender.sendMessage("You are no player!");
      return;
    }
    ProxiedPlayer p = (ProxiedPlayer)sender;
    if (!p.hasPermission(Banns.getBanperm())) {
	  p.sendMessage(Info.getNoPerm());
      return;
    }
    if (args.length == 1) {
    		checkcheck(args[0], p);
    } else {
    	p.sendMessage(Banns.getPrefix() + "§c/check <Player>");
    }
  }
  
  
  
  
  
  @SuppressWarnings("deprecation")
  public static void checkcheck(String gecheckter, ProxiedPlayer p) {
	  
      String plyr = gecheckter;
      
      String uuid = "null";
      
      uuid = MojangUUIDResolve.getUUIDResult(gecheckter).getValue();
      
      BanInfo banInfo = BanInfo.getBanInfo(uuid);
      MuteInfo muteInfo = MuteInfo.getBanInfo(uuid);

      p.sendMessage("");
      p.sendMessage(Banns.getPrefix() + "§7Check §a" + plyr);
      p.sendMessage("");
      if(banInfo != null) {
    	  if(banInfo.perm) {
    		  Date date = new Date(banInfo.timeBanned);
              DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");
              String dateFormatted = formatter.format(date);
              
	    		  p.sendMessage(Banns.getPrefix() + "§7Ban type: §4Permanent");
	    		  p.sendMessage(Banns.getPrefix() + "§7Baned by: §e" + banInfo.banner);
	    		  p.sendMessage(Banns.getPrefix() + "§7Ban reason: §e" + banInfo.reason);
	    		  p.sendMessage(Banns.getPrefix() + "§7Baned on: §e" + dateFormatted); 
              
    	  } else {
    		  
              long unbannedtime = banInfo.timeNext;
              long crt = System.currentTimeMillis();
              long uz = unbannedtime / 1000L;
              long zi = crt / 1000L;
              long hz = uz - zi;
              String lastSecs = TimeManager.calc(hz);
              
              if(lastSecs.equalsIgnoreCase("null") && unbannedtime != 0L) {
            	  BanSQLHandler.unban(uuid);
              }
    		  
              Date date = new Date(banInfo.timeBanned);
              DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");
              String dateFormatted = formatter.format(date);
    		  
	    		  p.sendMessage(Banns.getPrefix() + "§7Ban type: §cTemporary");
	    		  p.sendMessage(Banns.getPrefix() + "§7Baned by: §e" + banInfo.banner);
	    		  p.sendMessage(Banns.getPrefix() + "§7Ban reason: §e" + banInfo.reason);
	    		  p.sendMessage(Banns.getPrefix() + "§7Baned on: §e" + dateFormatted); 
	    		  p.sendMessage(Banns.getPrefix() + "§7Remaining time: §e" + lastSecs);
         
    	  }
    	  p.sendMessage("");
      } else {
    	  p.sendMessage(Banns.getPrefix() + "This player is not baned.");
    	  p.sendMessage("");
      }
    	  if(muteInfo != null) {

              long unbannedtime = muteInfo.timeNext;
              long crt = System.currentTimeMillis();
              long uz = unbannedtime / 1000L;
              long zi = crt / 1000L;
              long hz = uz - zi;
              String lastSecs = TimeManager.calc(hz);
              
              if(lastSecs.startsWith("-")) {
            	  MuteSQLHandler.unban(uuid);
              }
    		  
    			  
                  Date date = new Date(muteInfo.timeBanned);
                  DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");
                  String dateFormatted = formatter.format(date);

    	    		  p.sendMessage(Banns.getPrefix() + "§7Mute type: §cTemporary");
    	    		  p.sendMessage(Banns.getPrefix() + "§7Muted by: §e" + muteInfo.banner);
    	    		  p.sendMessage(Banns.getPrefix() + "§7Mute reason: §e" + muteInfo.reason);
    	    		  p.sendMessage(Banns.getPrefix() + "§7Muted on: §e" + dateFormatted); 
    	    		  p.sendMessage(Banns.getPrefix() + "§7Remaining time: §e" + lastSecs);
    		  p.sendMessage("");
    	  } else {
    		  p.sendMessage(Banns.getPrefix() + "This player is not muted.");
    		  p.sendMessage("");
    	  }
	  
  }
  
}
