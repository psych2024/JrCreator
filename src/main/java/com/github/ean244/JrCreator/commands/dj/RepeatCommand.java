package com.github.ean244.JrCreator.commands.dj;

import com.github.ean244.JrCreator.commands.CommandMeta;
import com.github.ean244.JrCreator.commands.Commands;
import com.github.ean244.JrCreator.music.GuildPlayer;
import com.github.ean244.JrCreator.music.GuildPlayerRegistry;
import com.github.ean244.JrCreator.music.PlayerState;
import com.github.ean244.JrCreator.music.TrackWrapper;
import com.github.ean244.JrCreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandMeta(aliases = { "" }, name = "repeat", permission = PermissionLevel.DJ)
public class RepeatCommand implements Commands {

	@Override
	public boolean onExecute(TextChannel channel, Guild guild, Member member, String[] args) {
		if (args.length != 0)
			return false;
		
		GuildPlayer player = GuildPlayerRegistry.getGuildPlayer(guild);
		
		if(player.getState() != PlayerState.PLAYING) {
			channel.sendMessage("No music playing currently!").queue();
			return true;
		}
		
		player.getScheduler().loadTrack(new TrackWrapper(player.getScheduler().currentTrack().getTrack(), member));
		channel.sendMessage("Current track will repeat!").queue();
		return true;
	}

}
