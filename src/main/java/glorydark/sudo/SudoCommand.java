package glorydark.sudo;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class SudoCommand extends Command {
    public SudoCommand(String command) {
        super(command);
        this.setUsage("/sudo <player> <command/message> | [Example] /sudo steve /me 233");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(strings.length < 2){ return false; }
        if(!commandSender.isPlayer()){
            StringBuilder builder = new StringBuilder();
            for(String parameter: strings){
                if(strings[strings.length-1].equals(parameter)){
                    builder.append(parameter);
                }else {
                    builder.append(parameter + " ");
                }
            }
            parseSudoCommand(commandSender, strings[0], builder.toString().replace(strings[0]+ " ", ""));
        }else{
            if(commandSender.isOp()){
                StringBuilder builder = new StringBuilder();
                for(String parameter: strings){
                    if(strings[strings.length-1].equals(parameter)){
                        builder.append(parameter);
                    }else {
                        builder.append(parameter + " ");
                    }
                }
                parseSudoCommand(commandSender, strings[0], builder.toString().replace(strings[0]+ " ", ""));
            }
        }
        return true;
    }

    public void parseSudoCommand(CommandSender sender, String player, String sudo){
        if(sudo.startsWith("/")){
            Player p = Server.getInstance().getPlayer(player);
            if(p != null && p.isOnline()){
                String command = sudo.replaceFirst("/","");
                Server.getInstance().dispatchCommand(p, command);
                sender.sendMessage("[§aSudo§f] Successfully ran command [§a"+command+"§f] as [§a"+player+"§f]");
            }else{
                sender.sendMessage("[§cSudo§f] §l§cPlayer is not online or not existed!");
            }
        }else{
            Server.getInstance().getOnlinePlayers().values().forEach(pl -> pl.sendMessage("<"+player+"> "+ sudo));
            if(!sender.isPlayer()){ sender.sendMessage("<"+player+"> "+ sudo); }
            sender.sendMessage("[§aSudo§f] Successfully said [§a"+sudo+"§f] as [§a"+player+"§f]");
        }
    }
}
