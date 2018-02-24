package com.github.ean244.jrcreator.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DbHandler {
	private static DbHandler instance;
	private static final Logger LOGGER = LoggerFactory.getLogger(DbHandler.class);
	
	private static final String CREATE_GUILD_STATEMENT = 
			"CREATE TABLE IF NOT EXISTS Guilds "
			+ "("
			+ "id BIGINT PRIMARY KEY NOT NULL,"
			+ " prefix VARCHAR(32) NOT NULL"
			+ ")";
	
	private static final String CREATE_USERS_STATEMENT = 
			"CREATE TABLE IF NOT EXISTS Users "
			+ "("
			+ "userId BIGINT NOT NULL,"
			+ " guildId BIGINT NOT NULL,"
			+ " perms TINYINT NOT NULL,"
			+ " PRIMARY KEY(userId, guildId)"
			+ ")";

	private HikariDataSource dataSource;

	private DbHandler() {
	}

	public void connDb() {
		LOGGER.info("Initializing data source...");

		this.dataSource = new HikariDataSource(new HikariConfig("src/main/resources/db.properties"));
		
		LOGGER.info("Data source successfully initialized!!");

		createTables();
	}

	private void createTables() {
		try (Connection connection = getConn();
				PreparedStatement statement = connection.prepareStatement(CREATE_GUILD_STATEMENT)) {

			LOGGER.info("Creating table Guilds....");

			statement.executeUpdate();

			LOGGER.info("Table Guilds successfully created!");

		} catch (SQLException e) {
			LOGGER.error("Failed to create table Guilds", e);
		}

		try (Connection connection = getConn();
				PreparedStatement statement = connection.prepareStatement(CREATE_USERS_STATEMENT)) {

			LOGGER.info("Creating table Users....");

			statement.executeUpdate();

			LOGGER.info("Table Users successfully created!");

		} catch (SQLException e) {
			LOGGER.error("Failed to create table Users", e);
		}
	}

	public static DbHandler getInstance() {
		if (instance == null) {
			instance = new DbHandler();
		}
		return instance;
	}

	public Connection getConn() throws SQLException {
		return dataSource.getConnection();
	}
	
	public void closeConn() {
		LOGGER.info("Closing connection to data source...");
		
		dataSource.close();
		
		LOGGER.info("Connection closed!");
	}
}
