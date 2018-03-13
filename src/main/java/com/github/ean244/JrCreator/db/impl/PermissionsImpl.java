package com.github.ean244.jrcreator.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ean244.jrcreator.db.DbHandler;
import com.github.ean244.jrcreator.db.api.PermRequest;
import com.github.ean244.jrcreator.db.api.PermUpdate;
import com.github.ean244.jrcreator.guild.DefaultSettings;
import com.github.ean244.jrcreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

public class PermissionsImpl implements PermRequest, PermUpdate {

	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionsImpl.class);
	private static final String REQUEST_MEMBER = "SELECT * FROM Users WHERE userId=? AND guildId=?";
	private static final String REQUEST_CATEGORY = "SELECT * FROM Users WHERE guildId=? AND perms=?";
	private static final String UPDATE_PERMISSION = "INSERT INTO Users (userId, guildId, perms) VALUES (?,?,?) ON DUPLICATE KEY UPDATE perms=?";

	@Override
	public Set<Long> requestCategory(Guild guild, PermissionLevel perms) {
		LOGGER.info("Fetching {} for guild {}", perms, guild.getName());

		try (Connection connection = DbHandler.getInstance().getConn();
				PreparedStatement statement = connection.prepareStatement(REQUEST_CATEGORY)) {

			statement.setLong(1, guild.getIdLong());
			statement.setInt(2, perms.level());

			ResultSet result = statement.executeQuery();

			Set<Long> set = new HashSet<>();

			while (result.next()) {
				set.add(result.getLong("userId"));
			}

			guild.getMemberCache().stream().filter(m -> !set.contains(m.getUser().getIdLong()))
					.filter(m -> DefaultSettings.defaultPermission(m).level() >= perms.level())
					.filter(m -> !m.getUser().isBot()).forEach(m -> set.add(m.getUser().getIdLong()));

			return Collections.unmodifiableSet(set);

		} catch (SQLException e) {
			LOGGER.error("Failed to fetch {}!", perms.toString(), e);
		}

		return DefaultSettings.defaultCategory(guild, perms);
	}

	@Override
	public PermissionLevel requestIndividual(Guild guild, Member member) {
		LOGGER.info("Fetching permissions for member {} in guild {}...", member.getUser().getName(), guild.getName());

		try (Connection connection = DbHandler.getInstance().getConn();
				PreparedStatement statement = connection.prepareStatement(REQUEST_MEMBER)) {

			statement.setLong(1, member.getUser().getIdLong());
			statement.setLong(2, guild.getIdLong());

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				LOGGER.info("Recieved {}", result.getInt("perms"));
				
				return PermissionLevel.of(result.getInt("perms"));
			}

		} catch (SQLException e) {
			LOGGER.error("Failed to fetch permissions for member!", e);
		}

		LOGGER.info("Permissions not found, default to {}", DefaultSettings.defaultPermission(member));

		return DefaultSettings.defaultPermission(member);
	}

	@Override
	public void update(Guild guild, Member member, PermissionLevel perms) {
		LOGGER.info("Updating permissions for member {} in guild {}...", member.getUser().getName(), guild.getName());

		try (Connection connection = DbHandler.getInstance().getConn();
				PreparedStatement statement = connection.prepareStatement(UPDATE_PERMISSION)) {

			statement.setLong(1, member.getUser().getIdLong());
			statement.setLong(2, guild.getIdLong());
			statement.setLong(3, perms.level());
			statement.setLong(4, perms.level());

			statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error("Failed to update permissions for member!", e);
		}
	}
}
