package com.github.ean244.JrCreator.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.ean244.JrCreator.db.DbHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ean244.JrCreator.cache.PrefixCacher;
import com.github.ean244.JrCreator.db.api.PrefixRequest;
import com.github.ean244.JrCreator.db.api.PrefixUpdate;
import com.github.ean244.JrCreator.guild.DefaultSettings;

import net.dv8tion.jda.core.entities.Guild;

public class PrefixImpl implements PrefixRequest, PrefixUpdate {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrefixImpl.class);
	private static final String REQUEST_PREFIX = "SELECT * FROM Guilds WHERE id=?";
	private static final String UPDATE_PREFIX = "INSERT INTO Guilds (id, prefix) VALUES (?,?) ON DUPLICATE KEY UPDATE prefix=?";

	@Override
	public String request(Guild guild) {
		LOGGER.info("Requesting prefix for guild {}", guild.getName());

		if (PrefixCacher.getInstance().hasCache(guild.getIdLong())) {
			String prefix = PrefixCacher.getInstance().getCache(guild.getIdLong());
			LOGGER.info("Found prefix for guild {} in cacher: {}", guild.getName(), prefix);
			return prefix;
		}

		try (Connection connection = DbHandler.getInstance().getConn();
			 PreparedStatement statement = connection.prepareStatement(REQUEST_PREFIX)) {

			statement.setLong(1, guild.getIdLong());

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				String prefix = result.getString("prefix");
				LOGGER.info("Prefix found in db: {}", prefix);
				PrefixCacher.getInstance().cache(guild.getIdLong(), prefix);
				return prefix;
			}

		} catch (SQLException e) {
			LOGGER.error("Failed to request prefix!", e);
		}

		LOGGER.info("Prefix not found in db, returning default");
		return DefaultSettings.PREFIX;
	}

	@Override
	public void update(Guild guild, String prefix) {
		LOGGER.info("Updating prefix for guild {}", guild.getName());

		PrefixCacher.getInstance().update(guild.getIdLong(), prefix);

		try (Connection connection = DbHandler.getInstance().getConn();
				PreparedStatement statement = connection.prepareStatement(UPDATE_PREFIX)) {

			statement.setLong(1, guild.getIdLong());
			statement.setString(2, prefix);
			statement.setString(3, prefix);

			statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error("Failed to update prefix!", e);
		}
	}
}
