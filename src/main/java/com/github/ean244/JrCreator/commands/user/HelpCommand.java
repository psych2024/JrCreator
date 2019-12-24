package com.github.ean244.JrCreator.commands.user;

import java.io.File;

import com.github.ean244.JrCreator.commands.CommandMeta;
import com.github.ean244.JrCreator.commands.Commands;
import com.github.ean244.JrCreator.db.impl.PrefixImpl;
import com.github.ean244.JrCreator.main.JrCreator;
import com.github.ean244.JrCreator.perms.PermissionLevel;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
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
		channel.sendFile(new File("\\src\\main\\resources\\banner.png"),
			new MessageBuilder()
					.append("\n")
					.append("JrCreator - music bot created by Lim Hee Lai\n")
					.append("\n")
					.append("One of my features is machine learning. You can talk to me to request songs.\n")
					.append("Just mention " + JrCreator.getJda().getSelfUser().getAsMention() + " in your message to talk to me.\n")
					.append("\n")
					.append("Feel free to contribute your ideas or code via github\n")
					.append("Full documentation can also be found there\n")
					.append("<https://goo.gl/3reGPA>\n")
					.append("\n")
					.append("Invite this bot to your guild by clicking this link:\n")
					.append("<https://goo.gl/gEHkMp>\n")
					.append("\n")
					.append("Do `" + prefix + "docs` to see a full list of commands and permissions")
					.setEmbed(null)
					.build()
		).queue();
		return true;
	}

}
