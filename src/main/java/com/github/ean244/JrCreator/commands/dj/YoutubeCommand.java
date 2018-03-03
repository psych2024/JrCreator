package com.github.ean244.jrcreator.commands.dj;

import com.github.ean244.jrcreator.commands.CommandMeta;
import com.github.ean244.jrcreator.commands.Commands;
import com.github.ean244.jrcreator.main.JrCreator;
import com.github.ean244.jrcreator.music.MusicHandler;
import com.github.ean244.jrcreator.music.YtResultHandler;
import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta
(aliases = { "yt" },
name = "youtube",
permission = PermissionLevel.DJ)
public class YoutubeCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if(!member.getVoiceState().inVoiceChannel()) {
			channel.sendMessage("You are not in a voice channel").queue();
			return true;
		}
		
		if(!guild.getMember(JrCreator.getJda().getSelfUser()).hasPermission(Permission.VOICE_CONNECT, Permission.VOICE_SPEAK)) {
			channel.sendMessage(String.format("I need permissions `%s` `%s` to do that", Permission.VOICE_CONNECT.getName(), Permission.VOICE_SPEAK.getName())).queue();
			return true;
		}
		
		StringBuilder builder = new StringBuilder("ytsearch:" + args[0]);
		
		for(int i = 1; i < args.length; i++) {
			builder.append(" ");
			builder.append(args[i]);
		}
		
		MusicHandler.getInstance().getManager().loadItem(builder.toString(), new YtResultHandler(channel, builder.toString(), member));
		return true;
	}

}
