package me.Kato14123.HologramScoreboardSql;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class Main extends JavaPlugin {
	
	public String host, database, username, password, table;
	public int port, leng;
	String[] namelist = new String[1];
	String[] numlist = new String[1];

	
	@Override
	public void onEnable() {
		if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
			getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
			getLogger().severe("*** This plugin will be disabled. ***");
			this.setEnabled(false);
			return;
		}
		this.saveDefaultConfig();
		if (!this.getConfig().getBoolean("done_setup")) {
			System.out.println(format("&f[HologramScoreboardSql] &cplugin loaded let config sql and change done_setup to true"));
			this.setEnabled(false);
			return;
		}
		System.out.println(format("&f[HologramScoreboardSql] &e-------------------------"));
		System.out.println(format("&f[HologramScoreboardSql] &e|    Plugin is enable   |"));
		System.out.println(format("&f[HologramScoreboardSql] &e|  &aCreate by Kato14123  &e|"));
		System.out.println(format("&f[HologramScoreboardSql] &e-------------------------"));
		Location where = new Location(Bukkit.getWorld(this.getConfig().getString("kill.world")), this.getConfig().getDouble("kill.x"), this.getConfig().getDouble("kill.y"), this.getConfig().getDouble("kill.z"), 0, 0);
		Hologram kill = HologramsAPI.createHologram(this, where);
		where = new Location(Bukkit.getWorld(this.getConfig().getString("win.world")), this.getConfig().getDouble("win.x"), this.getConfig().getDouble("win.y"), this.getConfig().getDouble("win.z"), 0, 0);
		Hologram win = HologramsAPI.createHologram(this, where);
		where = new Location(Bukkit.getWorld(this.getConfig().getString("score.world")), this.getConfig().getDouble("score.x"), this.getConfig().getDouble("score.y"), this.getConfig().getDouble("score.z"), 0, 0);
		Hologram score = HologramsAPI.createHologram(this, where);
		where = new Location(Bukkit.getWorld(this.getConfig().getString("played.world")), this.getConfig().getDouble("played.x"), this.getConfig().getDouble("played.y"), this.getConfig().getDouble("played.z"), 0, 0);
		Hologram play = HologramsAPI.createHologram(this, where);
		kill.appendTextLine(format(this.getConfig().getString("kill.title")));
		win.appendTextLine(format(this.getConfig().getString("win.title")));
		score.appendTextLine(format(this.getConfig().getString("score.title")));
		play.appendTextLine(format(this.getConfig().getString("played.title")));
		kill.insertItemLine(0, new ItemStack(Material.matchMaterial(this.getConfig().getString("kill.item"))));
		win.insertItemLine(0, new ItemStack(Material.matchMaterial(this.getConfig().getString("win.item"))));
		score.insertItemLine(0, new ItemStack(Material.matchMaterial(this.getConfig().getString("score.item"))));
		play.insertItemLine(0, new ItemStack(Material.matchMaterial(this.getConfig().getString("played.item"))));
		getkill();
		kill.appendTextLine(format(""));
		for (int i = 1; i < namelist.length; i++) {
			kill.appendTextLine(format("&e" + i + "." + namelist[i] + "  " + numlist[i]));
		}
		leng = namelist.length;
		for (int i = 1; i < leng; i++) {
			namelist = (String[]) ArrayUtils.remove(namelist, 1);
			numlist = (String[]) ArrayUtils.remove(numlist, 1);
		}
		getwin();
		win.appendTextLine(format(""));
		for (int i = 1; i < namelist.length; i++) {
			win.appendTextLine(format("&e" + i + "." + namelist[i] + "  " + numlist[i]));
		}
		leng = namelist.length;
		for (int i = 1; i < leng; i++) {
			namelist = (String[]) ArrayUtils.remove(namelist, 1);
			numlist = (String[]) ArrayUtils.remove(numlist, 1);
		}
		getscore();
		score.appendTextLine(format(""));
		for (int i = 1; i < namelist.length; i++) {
			score.appendTextLine(format("&e" + i + "." + namelist[i] + "  " + numlist[i]));
		}
		leng = namelist.length;
		for (int i = 1; i < leng; i++) {
			namelist = (String[]) ArrayUtils.remove(namelist, 1);
			numlist = (String[]) ArrayUtils.remove(numlist, 1);
		}
		getplay();
		play.appendTextLine(format(""));
		for (int i = 1; i < namelist.length; i++) {
			play.appendTextLine(format("&e" + i + "." + namelist[i] + "  " + numlist[i]));
		}
		leng = namelist.length;
		for (int i = 1; i < leng; i++) {
			namelist = (String[]) ArrayUtils.remove(namelist, 1);
			numlist = (String[]) ArrayUtils.remove(numlist, 1);
		}
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				getkill();
				leng = kill.size();
				for (int i = 3; i < leng; i++) {
					kill.removeLine(kill.size() - 1);
				}
				for (int i = 1; i < namelist.length; i++) {
					kill.appendTextLine(format("&e" + i + "." + namelist[i] + "  " + numlist[i]));
				}
				leng = namelist.length;
				for (int i = 1; i < leng; i++) {
					namelist = (String[]) ArrayUtils.remove(namelist, 1);
					numlist = (String[]) ArrayUtils.remove(numlist, 1);
				}
				getwin();
				leng = win.size();
				for (int i = 3; i < leng; i++) {
					win.removeLine(win.size() - 1);
				}
				for (int i = 1; i < namelist.length; i++) {
					win.appendTextLine(format("&e" + i + "." + namelist[i] + "  " + numlist[i]));
				}
				leng = namelist.length;
				for (int i = 1; i < leng; i++) {
					namelist = (String[]) ArrayUtils.remove(namelist, 1);
					numlist = (String[]) ArrayUtils.remove(numlist, 1);
				}
				getscore();
				leng = score.size();
				for (int i = 3; i < leng; i++) {
					score.removeLine(score.size() - 1);
				}
				for (int i = 1; i < namelist.length; i++) {
					score.appendTextLine(format("&e" + i + "." + namelist[i] + "  " + numlist[i]));
				}
				leng = namelist.length;
				for (int i = 1; i < leng; i++) {
					namelist = (String[]) ArrayUtils.remove(namelist, 1);
					numlist = (String[]) ArrayUtils.remove(numlist, 1);
				}
				getplay();
				leng = play.size();
				for (int i = 3; i < leng; i++) {
					play.removeLine(play.size() - 1);
				}
				for (int i = 1; i < namelist.length; i++) {
					play.appendTextLine(format("&e" + i + "." + namelist[i] + "  " + numlist[i]));
				}
				leng = namelist.length;
				for (int i = 1; i < leng; i++) {
					namelist = (String[]) ArrayUtils.remove(namelist, 1);
					numlist = (String[]) ArrayUtils.remove(numlist, 1);
				}
			}
		}, 0, 200L);
	}
	
	@Override
	public void onDisable() {
		System.out.println(format("&f[Murder Rank] &e-------------------------"));
		System.out.println(format("&f[Murder Rank] &e|   Plugin is disable   |"));
		System.out.println(format("&f[Murder Rank] &e|  &aCreate by Kato14123  &e|"));
		System.out.println(format("&f[Murder Rank] &e-------------------------"));
	}	
	
	private String format(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public void getkill() {
		host = this.getConfig().getString("mysql.host");
		port = this.getConfig().getInt("mysql.port");
		database = this.getConfig().getString("mysql.dbname");
		username = this.getConfig().getString("mysql.user");
		password = this.getConfig().getString("mysql.password");
		Connection connect = null;
		
		Statement s = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect =  DriverManager.getConnection("jdbc:mysql://" + this.host + ":" 
					+ this.port + "/" + this.database, this.username, this.password);
			
			s = connect.createStatement();
			
			String sql = "select * from "+ this.getConfig().getString("mysql.table") +" order by "+ this.getConfig().getString("table.killrow") +" desc limit 10";
			
			ResultSet rec = s.executeQuery(sql);
			
			while((rec!=null) && (rec.next()))
            { 
				namelist = Arrays.copyOf(namelist, namelist.length + 1);
				namelist[namelist.length - 1] = rec.getString(this.getConfig().getString("table.playername"));
				numlist = Arrays.copyOf(numlist, numlist.length + 1);
				numlist[numlist.length - 1] = rec.getString(this.getConfig().getString("table.killrow"));
            }
             
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(connect != null){
				s.close();
				connect.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void getwin() {
		host = this.getConfig().getString("mysql.host");
		port = this.getConfig().getInt("mysql.port");
		database = this.getConfig().getString("mysql.dbname");
		username = this.getConfig().getString("mysql.user");
		password = this.getConfig().getString("mysql.password");
		Connection connect = null;
		
		Statement s = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect =  DriverManager.getConnection("jdbc:mysql://" + this.host + ":" 
					+ this.port + "/" + this.database, this.username, this.password);
			
			s = connect.createStatement();
			
			String sql = "select * from "+ this.getConfig().getString("mysql.table") +" order by "+ this.getConfig().getString("table.winrow") +" desc limit 10";
			
			ResultSet rec = s.executeQuery(sql);
			
			while((rec!=null) && (rec.next()))
            { 
				namelist = Arrays.copyOf(namelist, namelist.length + 1);
				namelist[namelist.length - 1] = rec.getString(this.getConfig().getString("table.playername"));
				numlist = Arrays.copyOf(numlist, numlist.length + 1);
				numlist[numlist.length - 1] = rec.getString(this.getConfig().getString("table.winrow"));
            }
             
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(connect != null){
				s.close();
				connect.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void getscore() {
		host = this.getConfig().getString("mysql.host");
		port = this.getConfig().getInt("mysql.port");
		database = this.getConfig().getString("mysql.dbname");
		username = this.getConfig().getString("mysql.user");
		password = this.getConfig().getString("mysql.password");
		Connection connect = null;
		
		Statement s = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect =  DriverManager.getConnection("jdbc:mysql://" + this.host + ":" 
					+ this.port + "/" + this.database, this.username, this.password);
			
			s = connect.createStatement();
			
			String sql = "select * from "+ this.getConfig().getString("mysql.table") +" order by "+ this.getConfig().getString("table.scorerow") +" desc limit 10";
			
			ResultSet rec = s.executeQuery(sql);
			
			while((rec!=null) && (rec.next()))
            { 
				namelist = Arrays.copyOf(namelist, namelist.length + 1);
				namelist[namelist.length - 1] = rec.getString(this.getConfig().getString("table.playername"));
				numlist = Arrays.copyOf(numlist, numlist.length + 1);
				numlist[numlist.length - 1] = rec.getString(this.getConfig().getString("table.scorerow"));
            }
             
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(connect != null){
				s.close();
				connect.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void getplay() {
		host = this.getConfig().getString("mysql.host");
		port = this.getConfig().getInt("mysql.port");
		database = this.getConfig().getString("mysql.dbname");
		username = this.getConfig().getString("mysql.user");
		password = this.getConfig().getString("mysql.password");
		Connection connect = null;
		
		Statement s = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect =  DriverManager.getConnection("jdbc:mysql://" + this.host + ":" 
					+ this.port + "/" + this.database, this.username, this.password);
			
			s = connect.createStatement();
			
			String sql = "select * from "+ this.getConfig().getString("mysql.table") +" order by "+ this.getConfig().getString("table.roundrow") +" desc limit 10";
			
			ResultSet rec = s.executeQuery(sql);
			
			while((rec!=null) && (rec.next()))
            { 
				namelist = Arrays.copyOf(namelist, namelist.length + 1);
				namelist[namelist.length - 1] = rec.getString(this.getConfig().getString("table.playername"));
				numlist = Arrays.copyOf(numlist, numlist.length + 1);
				numlist[numlist.length - 1] = rec.getString(this.getConfig().getString("table.roundrow"));
            }
             
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(connect != null){
				s.close();
				connect.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}