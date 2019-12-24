package com.github.ean244.JrCreator.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.ean244.JrCreator.db.DbHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ean244.JrCreator.db.api.PermRequest;
import com.github.ean244.JrCreator.db.api.PermUpdate;
import com.github.ean244.JrCreator.guild.DefaultSettings;
import com.github.ean244.JrCreator.perms.PermissionLevel;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

public class PermissionsImpl implements PermRequest, PermUpdate {

	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionsImpl.class);
	private static final String REQUEST_MEMBER = "SELECT * FROM Users WHERE userId=? AND guildId=?";
	private static final String REQUEST_CATEGORY = "SELECT * FROM Users WHERE guildId=?";
	private static final String UPDATE_PERMISSION = "INSERT INTO Users (userId, guildId, perms) VALUES (?,?,?) ON DUPLICATE KEY UPDATE perms=?";

	@Override
	public Set<Long> request(Guild guild, PermissionLevel perms) {
		LOGGER.info("Fetching {} for guild {}", perms, guild.getName());

		try (Connection connection = DbHandler.getInstance().getConn();
			 PreparedStatement statement = connection.prepareStatement(REQUEST_CATEGORY)) {

			statement.setLong(1, guild.getIdLong());

			ResultSet result = statement.executeQuery();

			Map<Long, Integer> values = new HashMap<>();

			while (result.next()) {
				values.put(result.getLong("userId"), result.getInt("perms"));
			}
			
			//TODO: >= or == ? should we display all users that has specific permissions?
			
			Set<Long> set = new HashSet<>();
			values.entrySet().stream().filter(e -> e.getValue() >= perms.level()).forEach(e -> set.add(e.getKey()));

			guild.getMemberCache().stream()
					.filter(m -> !values.containsKey(m.getUser().getIdLong()))
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
				PermissionLevel level = PermissionLevel.of(result.getInt("perms"));
				return level;
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
