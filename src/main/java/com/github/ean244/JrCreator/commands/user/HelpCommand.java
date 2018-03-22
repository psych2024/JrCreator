package com.github.ean244.jrcreator.commands.user;

import java.io.File;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.db.impl.PrefixImpl;
import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta
(aliases = { },
name = "help",
permission = PermissionLevel.USER)
public class HelpCommand implements Commands {
	
	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if(args.length != 0)
			return false;
		
		String prefix = new PrefixImpl().request(guild);
		channel.sendFile(new File("src/main/resources/banner.png")).queue((m) ->  {
			Message message = new MessageBuilder()
					.append("\n")
					.append("JrCreator - music bot created by Lim Hee Lai\n")
					.append("Feel free to contribute your ideas or code via github\n")
					.append("Full documentation can also be found there\n")
					.append("\n")
					.append("Invite this bot to your guild by clicking this link\n")
					.append("https://discordapp.com/oauth2/authorize?client_id=413213106177572875&permissions=3164160&scope=bot")
					.append("\n")
					.append("```md\n")
					.append("#Basic Commands\n")
					.append(prefix + "github - shows link to repository\n")
					.append("#Music Commands\n")
					.append(prefix + "yt <search> - runs a search for a song\n")
					.append("```")
					.build();
			
			channel.sendMessage(message).queue();
		});
		return true;
	}

}
