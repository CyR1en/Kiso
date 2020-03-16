package com.cyr1en.kiso.mc;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;

public class UpdateChecker implements Listener {

  private static final String RESOURCE_URL = "https://www.spigotmc.org/resources/%s.%s/";
  private static final String API_URL = "https://api.spigotmc.org/legacy/update.php?resource=%s";

  private JavaPlugin plugin;

  private String resourceName;
  private int resourceID;
  private boolean isDisabled;

  public UpdateChecker(JavaPlugin plugin, int resourceID) {
    this.plugin = plugin;
    resourceName = plugin.getDescription().getName().toLowerCase();
    this.resourceID = resourceID;
    isDisabled = !plugin.getConfig().getBoolean("Update-Checker");
  }

  public boolean isDisabled() {
    return isDisabled;
  }

  public Version getCurrVersion() {
    Version version = new Version();
    try{
      HttpURLConnection connection = buildConnection(Objects.requireNonNull(stringAsUrl()));
      InputStreamReader ir = new InputStreamReader(connection.getInputStream());
      BufferedReader br = new BufferedReader(ir);
      version = version.parse(br.readLine());
    } catch (IOException e) {
      plugin.getLogger().log(Level.WARNING, "An error occurred while getting update data: ''{0}''!", e.getCause());
    }
    return version;
  }

  public boolean newVersionAvailable() {
    Version curr = new Version(plugin.getDescription().getVersion());
    Version arg = getCurrVersion();
    return arg.isNewerThan(curr);
  }

  private URL stringAsUrl() {
    String formatted = String.format(API_URL, resourceID);
    try {
      return new URL(formatted);
    } catch (MalformedURLException e) {
      plugin.getLogger().log(Level.WARNING, "''{0}'' is a malformed URL!", formatted);
      return null;
    }
  }

  private HttpURLConnection buildConnection(URL url) {
    try {
      HttpURLConnection.setFollowRedirects(true);
      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
      httpURLConnection.setRequestMethod("GET");

      httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
              "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
      return httpURLConnection;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onJoin(PlayerJoinEvent event) {
    Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> sendUpdateAvailableMessage(event.getPlayer()));
  }

  public void sendUpdateAvailableMessage(CommandSender sender) {
    if (!newVersionAvailable())
      return;
    Version v = getCurrVersion();
    if (sender instanceof Player && sender.isOp()) {
      try {
        if (Class.forName("org.spigotmc.SpigotConfig") != null) {
          BaseComponent[] textComponent = new ComponentBuilder("[")
                  .color(ChatColor.GOLD)
                  .append("CommandPrompter")
                  .color(ChatColor.GREEN)
                  .append("]")
                  .color(ChatColor.GOLD)
                  .append(" A new update is available: ")
                  .color(ChatColor.AQUA)
                  .append(v.asString())
                  .color(ChatColor.YELLOW)
                  .event(new ClickEvent(ClickEvent.Action.OPEN_URL, String.format(RESOURCE_URL, resourceName, resourceID)))
                  .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click here download the new version.").create()))
                  .create();
          sender.spigot().sendMessage(textComponent);
        }
      } catch (ClassNotFoundException e) {
        String msg = org.bukkit.ChatColor.translateAlternateColorCodes('&', "&6[&aCommandPrompter&6] &bA new update is available: &e" + v);
        Objects.requireNonNull(sender).sendMessage(msg);
      }
    }
  }

  /**
   * Class for representing the version of the plugin.
   *
   * <p>This class will follow the conventions of Semantic versioning.</p>
   *
   * @see <a href="https://semver.org/">https://semver.org/</a>
   */
  public class Version {
    private int major;
    private int minor;
    private int patch;

    public Version(int major, int minor, int patch){
      this.major = major;
      this.minor = minor;
      this.patch = patch;
    }

    public Version(String versionString) {
      this();
      parse(versionString);
    }

    public Version() {
      this(0,0,0);
    }

    public int getMajor() {
      return major;
    }

    public void setMajor(int major) {
      this.major = major;
    }

    public int getMinor() {
      return minor;
    }

    public void setMinor(int minor) {
      this.minor = minor;
    }

    public int getPatch() {
      return patch;
    }

    public void setPatch(int patch) {
      this.patch = patch;
    }

    public String asString() {
      return getMajor() + "." + getMinor() + "." + getPatch();
    }

    public Version parse(String versionString) {
      String[] split = versionString.split(".");
      assertVersionStringSize(split);
      setMajor(Integer.parseInt(split[0]));
      setMinor(Integer.parseInt(split[1]));
      setPatch(Integer.parseInt(split[2]));
      return this;
    }

    public boolean isNewerThan(Version version) {
      if(getMajor() > version.getMajor()) return true;
      if(getMinor() > version.getMinor()) return true;
      return getPatch() > version.getPatch();
    }

    private void assertVersionStringSize(String[] strings) {
      if(strings.length != 3)
        throw new IllegalArgumentException("The string of version must contain a major, minor, and patch.");
    }

    @Override
    public String toString() {
      return "Version{" +
              "major=" + major +
              ", minor=" + minor +
              ", patch=" + patch +
              '}';
    }
  }
}